// Basis-Pipeline-Schema anlegen

pipeline {

    agent any

    environment {
     INTEGRATION_BRANCH = 'integration'
     }

    stage {
        echo "${BRANCH_NAME}"
        echo "Integration branch: %{INTEGRATION_BRANCH}"
    }


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
                echo 'Building feature'
                sh 'gradle clean build -x test'
            }
        }

        stage('Testing Feature') {

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
        }

        stage('Integrating Feature') {

            when {
                branch 'feature/*'
                beforeAgent true
            }

            steps {
                echo 'Integrating feature'
                sh 'ls -la'
                sh 'git branch -a'
                sh 'git checkout feature/feature-2'
                sh 'git checkout integration'
                sh 'git merge feature/feature-2'

                withCredentials([
                    gitUsernamePassword(credentialsId: 'GitHub_cicd_pat', gitToolName: 'Default')
                ]) {
                    sh 'git push origin integration'
                }
            }
        }

    }


}