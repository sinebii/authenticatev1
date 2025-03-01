pipeline {
    agent any
    environment {
        DOCKER_IMAGE = 'sinebi/authentication_app:latest'
        CONTAINER_NAME = 'authentication_app'
        SERVER_IP = '67.217.58.16'
        SERVER_USER = 'sinebi'
    }
    stages {
        stage('Clone Repository') {
            steps {
                git branch: 'main', url: 'https://github.com/sinebii/authenticatev1'

            }
        }
        stage('Build JAR') {
            steps {
                sh './mvnw clean package -DskipTests'
            }
        }
        stage('Build Docker Image') {
            steps {
                sh "docker build -t $DOCKER_IMAGE ."
            }
        }
        stage('Push to Docker Hub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh "docker login -u $DOCKER_USER -p $DOCKER_PASS"
                }
            }
        }
        stage('Deploy to Server') {
            steps {
               steps {
                   sshagent(['server-ssh-credentials']) {
                       sh """
                       ssh -o StrictHostKeyChecking=no $SERVER_USER@$SERVER_IP << EOF
                       docker pull $DOCKER_IMAGE
                       docker stop $CONTAINER_NAME || true
                       docker rm $CONTAINER_NAME || true
                       docker run -d --name $CONTAINER_NAME -p 7070:7070 $DOCKER_IMAGE
                       EOF
                       """
                   }
               }



            }
        }
    }
}
