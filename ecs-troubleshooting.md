Jenkins takes care of building the Docker image and deploying it to the ECS cluster.
This document explains how to do the equivalent, in case something goes wrong and
you need to troubleshoot.

This document should not be needed for normal operation.

To deploy to the ECS cluster, you need to
build the production Docker image, push it to the ECR,
use ecs-cli to create and bring up the service, and open ports in the load balancer.

1. Installing and Configuring the aws and ecs-cli Command-Line Tools

    Follow the instructions from the tcp-ecs repo's README, in the
    section titled "Installing and Configuring the aws and ecs-cli Command-Line Tools".
    https://github.com/excellaco/tcp-ecs/

1. Build the production Docker image:

    From the root directory:
    `docker build -t tcp-java-api:latest .`

    Note: you may need to run this as root: `sudo docker build -t tcp-java-api:latest .`
    This step will take about 2 minutes.  It uses the `Dockerfile`.

    Do `docker image ls` to make sure the image was built: `tcp-java-api`

1. Push the image to the Elastic Container Repository (ECR):

    `ecs-cli push --aws-profile default tcp-java-api:latest`

    or, if necessary,

    `sudo ecs-cli push --aws-profile default tcp-java-api:latest`

1. Use ecs-cli to create a new task and bring up the service:

    Go to the tcp-java-ecs subdirectory

    Note: all `ecs-cli compose` commands for tcp-java *must* be run from this subdirectory.

    `ecs-cli compose --aws-profile default service ps`

    Make sure the service is not running (either it doesn't show up, or it
    shows up with State = STOPPED). If it is running, do:

    `ecs-cli compose --aws-profile default service down`

    and wait for completion.

    The following command will both create the task and bring up the service on the ECS cluster:

    `ecs-cli compose --aws-profile default service up`

    This will take about 20 seconds.

    Double-check that the service is running:

    `ecs-cli compose --aws-profile default service ps`

    You should see both `/api` and `/db` containers.

    This will also tell you which host(s) it's running on.

1. Scale up the service (Optional)

    `ecs-cli compose --aws-profile default service scale 2`

1. Enable connections from the internet to the tcp-java containers

    [In the future, we will replace this step with Terraform automation.]

    - a) Create a new target group; make its name end with "-to-8080-tg".
        Make sure to select the correct VPC.
        Set its type to Instance.  Set its protocol to HTTP. Set its target port to 8080.
        Do *not* register any instances with it directly: the Auto-Scaling Group (ASG) will do that for us.

    - b) Go to the cluster node ASG (name contains "-cluster-node") and attach the new target group:
        "Details" tab, "Edit" button.  Click in the "Target Groups" box, type "8080", select the new target group.
        Click "Save".

    - c) Remove any exsiting Listeners on the ALB that are listening on port 8080.
        Add a Listener to the ALB: listen on 8080, forward to the new target group.

    - d) Make sure the ALB's Security Group (name ends with "-alb-sg") allows connections on port 8080.
         Also note the ALB's ARN for the next step.

    - e) Set the cluster node security group (name ends with "-cluster-instance-sg") to
        accept connections on 8080 from the ALB's Security Group.

1. Check that the API service is running and accessible

    You can get the ALB's FQDN by running: `terraform output cluster_url`

    From a machine that can connect to the ALB (e.g. a machine on Excella's network):

    From the project root directory,

    `alb=[fqdn of ALB here, no port]`
    `./get-token $alb:8000`

    This will ask the API at `:8080` on the ALB to create a bearer token, then store that token in
    a file called `token`.  The token file can be used with CURL to access the rest of the API

    Run:
    `curl -K token $alb:8080/api/employee/2`

    You should get back a JSON response with information about the employeed with ID=2.

1. Troubleshooting

    If either of the above commands fails, try `ssh`ing into one of the cluster nodes, going to
    the tcp-java root directory, and running:
    `./get-token localhost:8080`
    `curl -K token localhost:8080/api/employee/2`
    If this succeeds, the problem is probably with the networking (ALB, Target Groups, Security Groups).
