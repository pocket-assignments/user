pipeline {
  agent any
  stages {
    stage('Pull code') {
      agent any
      steps {
        git(url: 'https://github.com/pocket-assignments/user.git', branch: 'main', changelog: true, poll: true)
      }
    }

    stage('Build') {
      steps {
        sh 'mvn clean package -Dmaven.test.skip -DargLine="-Xmx1024m"'
      }
    }

    stage('Test') {
      steps {
        sh 'mvn test -DargLine="-Xmx1024m"'
      }
    }

  }
}