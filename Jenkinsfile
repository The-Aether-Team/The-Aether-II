pipeline {
    agent {
        docker {
            image 'gradle:4.10.3-jdk8-alpine'
            args '-v gradle-cache:/home/gradle/.gradle'
        }
    }

    stages {
        stage('Clean') {
            steps {
                dir('build/libs') {
                    deleteDir()
                }
            }
        }

        stage('Build') {
            environment {
                MAVEN_SECRETS_FILE = credentials('maven-secrets')

                JARSIGN_KEYSTORE_FILE = credentials('gilded-games-jarsign-keystore')
                JARSIGN_SECRETS_FILE = credentials('gilded-games-jarsign-secrets')
            }

            steps {
                sh 'gradle build --no-daemon'
            }
        }
    }

    post {
        success {
            archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true
        }
    }
}