pipeline {
    agent any
    environment {
        DOCKER_HUB_CREDENTIALS = credentials('docker-hub-credentials')
        IMAGE_NAME = 'sinebi/authentication_app'
        DB_HOST = credentials('DB_HOST')
        DB_NAME = credentials('DB_NAME')
        DB_USERNAME = credentials('DB_USERNAME')
        DB_PASSWORD = credentials('DB_PASSWORD')
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
                sh 'docker build --build-arg DB_HOST=${DB_HOST} --build-arg DB_NAME=${DB_NAME} --build-arg DB_USERNAME=${DB_USERNAME} --build-arg DB_PASSWORD=${DB_PASSWORD} -t ${IMAGE_NAME}:latest .'
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
                    // Stop and remove existing container (if any)
                    sh 'docker stop authentication || true'
                    sh 'docker rm authentication || true'

                    // Pull and run the new image
                    sh 'docker pull ${IMAGE_NAME}:latest'
                    sh """
                        docker run -d --name authentication -p 7070:7070 \
                        -e DB_HOST=${DB_HOST} \
                        -e DB_NAME=${DB_NAME} \
                        -e DB_USERNAME=${DB_USERNAME} \
                        -e DB_PASSWORD=${DB_PASSWORD} \
                        ${IMAGE_NAME}:latest
                    """
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