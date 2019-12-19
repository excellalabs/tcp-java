void setBuildStatus(String message, String state) {
  step([
      $class: "GitHubCommitStatusSetter",
      reposSource: [$class: "ManuallyEnteredRepositorySource", url: "${env.GIT_URL}"],
      contextSource: [$class: "ManuallyEnteredCommitContextSource", context: "ci/jenkins/build-status"],
      errorHandlers: [[$class: "ChangingBuildStatusErrorHandler", result: "UNSTABLE"]],
      statusResultSource: [ $class: "ConditionalStatusResultSource", results: [[$class: "AnyBuildResult", message: message, state: state]] ]
  ]);
}

pipeline {
    agent {
      label 'excellanator'
    }
    tools {
        jdk "11"
    }
    stages {
        stage('SlackNotify'){
          when {
            expression { env.JOB_BASE_NAME.startsWith('PR') }
          }
          steps {
            slackSend(channel: '#tcp-java', color: '#FFFF00', message: ":jenkins-triggered: Build Triggered - ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)")
          }
        }
        stage('Linter') {
            steps {
              gradlew('verGJF')
              echo "${JOB_BASE_NAME}"
            }
        }
        stage('Clean') {
            steps {
                gradlew('clean')
            }
        }
        stage('Unit Tests') {
            steps {
                gradlew('testNG')
            }
            post{
              always {
                junit '**/build/test-results/testNG/TEST-*.xml'
              }
            }
        }
        stage('Jacoco') {
            steps {
                gradlew('jacocoTestReport')
            }
        }
        stage('Jacoco Verficiation') {
            steps {
                gradlew('jacocoTestCoverageVerification')
            }
        }
        stage('SonarQube analysis') {
          steps{
            withSonarQubeEnv('default') {
              gradlew('sonarqube')
            }
          }
        }
        stage('Record Coverage') {
            when { branch 'master' }
            steps {
                script {
                    currentBuild.result = 'SUCCESS'
                 }
                step([$class: 'MasterCoverageAction', scmVars: [GIT_URL: env.GIT_URL]])
            }
        }
        stage('PR Coverage to Github') {
            when { allOf {not { branch 'master' }; expression { return env.CHANGE_ID != null }} }
            steps {
                script {
                    currentBuild.result = 'SUCCESS'
                 }
                step([$class: 'CompareCoverageAction', publishResultAs: 'statusCheck', scmVars: [GIT_URL: env.GIT_URL]])
            }
        }
        stage('Build ECS Image'){
          steps{
              sh './tcp-java-ecs/package-for-ecs tcp-java'
          }
        }
        stage('Deploy Dev Image'){
          steps{
            dir('tcp-java-ecs'){
              sh "./configure-for-ecs ${PROJECT_NAME} dev ${AWS_REGION} ${env.GIT_COMMIT}"
              sh "./deploy-to-ecs ${PROJECT_NAME} dev ${AWS_REGION}"
            }
          }
        }
        /*stage('Deploy Test Image'){
          steps{
            dir('tcp-java-ecs'){
              sh './configure-for-ecs ${PROJECT_NAME} test ${AWS_REGION} ${env.GIT_COMMIT}'
              sh "./deploy-to-ecs ${PROJECT_NAME} test ${AWS_REGION}"
            }
          }
        } */
        stage('Deploy Prod Image'){
          when {
            branch 'master'
          }
          steps{
            dir('tcp-java-ecs'){
              sh './tag-as-latest'
              sh './configure-for-ecs ${PROJECT_NAME} prod ${AWS_REGION} latest'
              sh "./deploy-to-ecs ${PROJECT_NAME} prod ${AWS_REGION}"
            }
          }
        }
      }
    post {
        success {
           setBuildStatus("Build succeeded", "SUCCESS");
           script {
             if (env.JOB_BASE_NAME.startsWith('PR'))
              slackSend(channel: '#tcp-java', color: '#00FF00', message: ":jenkins_ci: Build Successful!  ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>) :jenkins_ci:")
            }
           cleanWs()
        }
        failure {
           setBuildStatus("Build failed", "FAILURE");
           script {
             if (env.JOB_BASE_NAME.startsWith('PR'))
              slackSend(channel: '#tcp-java', color: '#FF0000', message: ":alert: :jenkins_exploding: *Build Failed!  Please remedy this malbuildage at your earliest convenience* ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>) :jenkins_exploding: :alert:")
            }
        }
    }
}

def gradlew(String... args) {
    sh "./gradlew ${args.join(' ')} -s"
}
