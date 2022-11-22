pipeline {
  agent any
  stages {
    stage('Build Feature') {

      when {
            branch 'feature/*'
            beforeAgent true
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

