pipeline {
    agent any

    tools {
        maven 'Maven' 
    }

    environment {
        MAVEN_OPTS = '-Xmx1024m'
        DOCKER_IMAGE = 'aymane2025/aymanerepo' 
        DOCKER_TAG = 'latest'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package'
            }
        }

        stage('Docker Build and Push') {
            steps {
                script {
                    docker.withRegistry('', 'docker-hub-credentials') {
                        def customImage = docker.build("${DOCKER_IMAGE}:${DOCKER_TAG}")
                        customImage.push()
                    }
                }
            }
        }
    }

    post {
        success {
            echo 'Pipeline exécuté avec succès.'
        }
        failure {
            echo 'Le pipeline a échoué.'
        }
    }
}
