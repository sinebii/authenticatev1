pipeline {
    agent any
    environment {
        DOCKER_HUB_CREDENTIALS = credentials('docker-hub-credentials')
        IMAGE_NAME = 'sinebi/authentication_app'
        SPRING_PROFILES_ACTIVE = 'prod'

        // Fetch DB credentials from Jenkins
        DB_URL = credentials('DB_URL')
        DB_CREDENTIALS = credentials('DB_CREDENTIALS')
    }
    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/sinebii/authenticatev1', branch: 'main'
            }
        }
        stage('Build JAR') {
            steps {
                sh 'mvn clean package -Dspring.profiles.active=prod'
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
                    sh 'docker stop authentication || true'
                    sh 'docker rm authentication || true'
                    sh 'docker pull ${IMAGE_NAME}:latest'

                    sh '''docker run -d --name authentication \
                        -p 7071:7071 \
                        -e SPRING_PROFILES_ACTIVE=prod \
                        -e SPRING_DATASOURCE_URL="$DB_URL" \
                        -e SPRING_DATASOURCE_USERNAME="$DB_CREDENTIALS_USR" \
                        -e SPRING_DATASOURCE_PASSWORD="$DB_CREDENTIALS_PSW" \
                        ${IMAGE_NAME}:latest'''
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
