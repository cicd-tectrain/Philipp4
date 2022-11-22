// Basis-Pipeline-Schema anlegen

pipeline {

    agent any

    // Define global Environment
    environment {
        INTEGRATION_BRANCH = 'integration'
        PRODUCTION_BRANCH = 'master'
    }

    stages {

        stage('Log Environment') {
            steps {
                echo "Local branch: ${BRANCH_NAME}"
                echo "Integration branch: ${INTEGRATION_BRANCH}"
            }
        }

        stage('Build Feature') {
            // Run only on feature branches
            when {
                branch 'feature/*'
                beforeAgent true
            }

            // Diese Stage in einem Docker-Container ausführen
            agent {
                docker {
                    image 'gradle:7.5.1-jdk17-focal'
                }
            }

            steps {
                echo 'Building feature'
                // Cleanup and build proejct (skip tests)
                sh 'gradle clean build -x test'
            }
        }

        stage('Testing Feature') {

            // Run only on feature branches
            when {
                branch 'feature/*'
                beforeAgent true
            }

            // Diese Stage in einem Docker-Container ausführen
            agent {
                docker {
                    image 'gradle:7.5.1-jdk17-focal'
                }
            }

            steps {
                echo 'Testing feature'
                // Run test suite
                sh 'gradle test'
                // List JUnit test files
                sh 'ls -la build/test-results/test'
                // List HTML Test-Report
                sh 'ls -la build/reports/tests/test'
            }

            // Post-build
            post {
                always {
                    // Collect JUnit test results
                    junit 'build/test-results/**/*.xml'
                }

            }

        }

        stage('Integrating Feature') {

            // Run only on feature branches
            when {
                branch 'feature/*'
                beforeAgent true
            }

            steps {
                echo 'Integrating feature'
                sh 'ls -la'
                sh 'git branch -a'
                sh 'git checkout ${BRANCH_NAME}'
                sh 'git checkout ${INTEGRATION_BRANCH}'
                sh 'git merge ${BRANCH_NAME}'
                // Push requires credentials
                withCredentials([
                    gitUsernamePassword(credentialsId: 'GitHub_cicd_pat', gitToolName: 'Default')
                ]) {
                    sh 'git push origin ${INTEGRATION_BRANCH}'
                }

            }
        }

        // ======= Integration Stages =======

        stage('Build Integration branch') {

            when {
                branch 'integration'
                beforeAgent true
            }

            agent {
                docker {
                    image 'gradle:7.5.1-jdk17-focal'
                }
            }

            steps {
                echo 'Building integration...'
                sh 'gradle clean build -x test'
            }
        }

        stage('Test Integration branch') {

            when {
                branch 'integration'
                beforeAgent true
            }

            agent {
                docker {
                    image 'gradle:7.5.1-jdk17-focal'
                }
            }

            steps {
                echo 'Testing integration...'
                sh 'gradle test'
            }

            post {
                always {
                    // Collect JUnit test results
                    junit 'build/test-results/**/*.xml'
                }
            }

        }

        stage('Publish artifacts') {
            when {
                branch 'integration'
                beforeAgent true
            }

            agent {
                docker {
                    image 'gradle:7.5.1-jdk17-focal'
                }
            }

            steps {
                echo 'Publishing artifacts...'
                sh 'ls -la'

                // Upload .jar file to Nexus Maven repository
                nexusArtifactUploader artifacts: [[
                    artifactId: 'at.tectrain.cicd',
                    classifier: '',
                    file: 'build/libs/demo-0.0.1-SNAPSHOT.jar',
                    type: 'jar'
                ]],
                credentialsId: 'nexus_credentials',
                groupId: '',
                nexusUrl: 'nexus:8081',
                nexusVersion: 'nexus3',
                protocol: 'http',
                repository: 'maven-snapshots',
                version: '0.0.1-SNAPSHOT'

            }
        }

        stage('Deploy Integration branch') {
            steps {
                echo 'Deploying integration...'
            }
        }

        stage('Merge integration into master') {
            steps {
                echo 'Merge into master'
                sh 'ls -la'
                sh 'git branch -a'
                sh 'git checkout ${BRANCH_NAME}'
                sh 'git checkout ${PRODUCTION_BRANCH}'
                sh 'git merge ${BRANCH_NAME}'
                // Push requires credentials
                withCredentials([
                    gitUsernamePassword(credentialsId: 'GitHub_cicd_pat', gitToolName: 'Default')
                ]) {
                    sh 'git push origin ${PRODUCTION_BRANCH}'
                }

            }
        }

    }


}