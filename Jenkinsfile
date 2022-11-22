//Basis Pipeline Schema anlegen

pipeline {
  agent any
  stages {
    stage('Building Feature') {
      when {
              branch 'feature/*'
              beforeAgent true
            }

      //Agent overwrite and run in a docker container
      agent {
        docker {
            image 'gradle:7.5.1-jdk17-focal'
        }
      }


      steps {
        echo 'Building feature'
        sh 'gradle clean build -x test'
      }
    }

    stage('Testing Feature') {
      when {
              branch 'feature/*'
              beforeAgent true
            }

    //Agent overwrite and run in a docker container
      agent {
        docker {
            image 'gradle:7.5.1-jdk17-focal'
        }
      }


      steps {
        echo 'Testing feature'
        sh 'gradle test'
        sh 'ls -la build/test-results/test'
        sh 'ls -la build/reports/tests/test'
      }

      post {
        always {
            junit 'build/test-results/**/*.xml'
      }

    }

    stage('Integrating Feature') {
      when {
        branch 'feature/*'
        beforeAgent true
      }

      steps {
        echo 'Integrating feature'
      }
    }
  }
}