pipeline {
  agent any
  stages {
    stage('build') {
      steps {
        parallel(
          "build": {
            echo 'something'
            
          },
          "code analyze": {
            echo 'blah'
            
          },
          "unit test": {
            echo 'blah'
            
          }
        )
      }
    }
    stage('integrate') {
      steps {
        echo 'something'
      }
    }
    stage('test') {
      steps {
        echo 'something'
      }
    }
    stage('qa-deploy') {
      steps {
        echo 'something'
      }
    }
  }
}