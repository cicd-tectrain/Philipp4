pipeline {
  agent any
  stages {
    stage('Build Feature') {

      when {
            branch 'feature/*'
            beforeAgent true
      }

      agent {
          docker {
              image 'gradle:7.5.1-jdk17-focal'
        }
      }

      steps {
        echo 'Build'
      }
    }

    stage('Test') {
        when {
             branch 'feature/*'
             beforeAgent true
        }
      steps {
        echo 'Testing'
      }
    }

    stage('Integration') {
       when {
          branch 'feature/*'
          beforeAgent true
       }
      steps {
        echo 'Integrating'
      }
    }
  }
}

