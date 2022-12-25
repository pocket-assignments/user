pipeline {
  agent {
    node {
      label 'ecs-agent'
    }

  }
  stages {
    stage('Build') {
      steps {
        sh 'mvn clean package -Dmaven.test.skip -DargLine="-Xms500m -Xmx1000m"'
      }
    }

    stage('Test') {
      steps {
        sh 'mvn clean test -DargLine="-Xms500m -Xmx1000m"'
      }
    }

    stage('Deploy') {
      steps {
        echo 'Under construction...'
      }
    }

  }
  tools {
    maven 'Maven'
  }
  post {
    success {
      step([
                                                                                      $class: "GitHubCommitStatusSetter",
                                                                                      reposSource: [$class: "ManuallyEnteredRepositorySource", url: "https://github.com/pocket-assignments/user.git"],
                                                                                      contextSource: [$class: "ManuallyEnteredCommitContextSource", context: "ci/jenkins/build-status"],
                                                                                      errorHandlers: [[$class: "ChangingBuildStatusErrorHandler", result: "UNSTABLE"]],
                                                                                      statusResultSource: [ $class: "ConditionalStatusResultSource", results: [[$class: "AnyBuildResult", message: 'Build succeeded', state: 'SUCCESS']] ]
                                                                                  ])
        slackSend(message: "Build deployed successfully - ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)")
      }

      failure {
        step([
                                                                                                          $class: "GitHubCommitStatusSetter",
                                                                                                          reposSource: [$class: "ManuallyEnteredRepositorySource", url: "https://github.com/pocket-assignments/user.git"],
                                                                                                          contextSource: [$class: "ManuallyEnteredCommitContextSource", context: "ci/jenkins/build-status"],
                                                                                                          errorHandlers: [[$class: "ChangingBuildStatusErrorHandler", result: "UNSTABLE"]],
                                                                                                          statusResultSource: [ $class: "ConditionalStatusResultSource", results: [[$class: "AnyBuildResult", message: 'Build failed', state: 'FAILURE']] ]
                                                                                                      ])
          slackSend(failOnError: true, message: "Build failed  - ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)")
        }

      }
    }