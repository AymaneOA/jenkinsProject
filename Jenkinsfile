pipeline {
    agent any

    tools {
        maven 'Maven' 
    }

    environment {
        MAVEN_OPTS = '-Xmx1024m'
        DOCKER_IMAGE = 'aymane5/aymanove' 
        DOCKER_TAG = 'latest'
        REMOTE_SERVER='aymane2@192.168.153.130'
        REMOTE_SERVER_SSH='ssh2021'
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
                    docker.withRegistry('', 'docker2021') {
                        def customImage = docker.build("${DOCKER_IMAGE}:${DOCKER_TAG}")
                        customImage.push()
                    }
                    
                }
                
            }
            
        }
         stage('Deploy to Remote Server') {
            steps {
                echo 'Deploying to a Remote Server...'
                sshagent([REMOTE_SERVER_SSH]) {
                    sh """
                    ssh -o StrictHostKeyChecking=no $REMOTE_SERVER "
                        docker stop \$(docker ps -q --filter ancestor=${DOCKER_REPO}:${DOCKER_TAG}) || true &&
                        docker rm \$(docker ps -q --filter ancestor=${DOCKER_REPO}:${DOCKER_TAG}) || true &&
                        docker pull ${DOCKER_REPO}:${DOCKER_TAG} &&
                        docker run -d -p 8080:8080 ${DOCKER_REPO}:${DOCKER_TAG}
                    "
                    """
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
