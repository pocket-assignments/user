pipeline {
  agent {
    node {
      label 'ecs-agent'
    }

  }
  stages {
    stage('Pull code') {
      agent any
      steps {
        git(url: 'https://github.com/pocket-assignments/user.git', branch: 'main', changelog: true, poll: true)
      }
    }

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

  }
}