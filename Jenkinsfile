pipeline {
  agent any
  stages {
    stage('build') {
      steps {
        echo 'iniando build'
        sh 'mvn clean package'
        sh './gradlew clean build'
      }
    }
    stage('Test') {
      steps {
        echo 'Testando'
        sh './gradlew test'
      }
    }
  }
  environment {
    agent = 'master'
  }
}