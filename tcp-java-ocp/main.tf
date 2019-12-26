provider "aws" {
  region                  = "us-east-2"
}

resource "aws_db_instance" "sampledb" {
  allocated_storage    = 20
  engine               = "postgres"
  engine_version       = "11.5"
  storage_type         = "gp2"
  identifier_prefix    = "tcp-ocp-"
  instance_class       = "db.t2.micro"
  name                 = "app"
  username             = "foo"
  password             = "changeme"
}
