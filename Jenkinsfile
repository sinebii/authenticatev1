pipeline {
    agent any
    environment {
        DOCKER_HUB_CREDENTIALS = credentials('docker-hub-credentials') // ID from Step 3
        IMAGE_NAME = 'sinebi/authentication_app' // Replace with your Docker Hub repo
    }
    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/sinebii/authenticatev1', branch: 'main'
            }
        }
        stage('Build JAR') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Build Docker Image') {
            steps {
                sh 'docker build -t ${IMAGE_NAME}:latest .'
            }
        }
        stage('Push to Docker Hub') {
            steps {
                sh 'echo $DOCKER_HUB_CREDENTIALS_PSW | docker login -u $DOCKER_HUB_CREDENTIALS_USR --password-stdin'
                sh 'docker push ${IMAGE_NAME}:latest'
            }
        }
        stage('Deploy to Server') {
            steps {
                script {
                    // Navigate to the directory containing docker-compose.yml
                    sh 'cd $WORKSPACE'

                    // Stop and remove existing containers
                    sh 'docker-compose down || true'

                    // Pull the latest image
                    sh 'docker pull ${IMAGE_NAME}:latest'

                    // Start all services (app, db, pgadmin)
                    sh 'docker-compose up -d'
                }
            }

        }
    }
    post {
        always {
            sh 'docker logout'
        }
    }
}
