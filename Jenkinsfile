
pipeline {

    agent any

    tools {
        jdk 'JDK21'
        maven 'Maven3'
    }

    environment {
        APP_DIR = '/opt/java-cicd-app'
    }

    options {
        timestamps()
        disableConcurrentBuilds()
    }

    stages {

        stage('Checkout') {
            steps {
                echo 'Checking out source code from GitHub'

                checkout scm
            }
        }

        stage('Build and Test') {
            steps {
                echo 'Building Java 21 application'

                sh '''
                    java -version
                    mvn -version
                    mvn -B clean verify
                '''
            }
        }

        stage('Package') {
            steps {
                echo 'Checking generated JAR'

                sh 'ls -lh target/*.jar'

                archiveArtifacts(
                    artifacts: 'target/*.jar',
                    fingerprint: true
                )
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying application'

                sh '''
                    set -eu

                    install -m 0644 \
                    target/java-cicd-app-1.0.0.jar \
                    "$APP_DIR/app.jar"

                    sudo -n /usr/bin/systemctl restart \
                    java-cicd-app.service

                    systemctl is-active --quiet \
                    java-cicd-app.service

                    curl -fsS http://localhost:8081
                '''
            }
        }
    }

    post {

        success {
            echo 'Java 21 CI/CD Pipeline completed successfully!'
        }

        failure {
            echo 'Pipeline failed. Check Console Output.'
        }

        always {
            echo 'Pipeline execution completed.'
        }
    }
}
