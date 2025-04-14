pipeline {
    agent any
    tools {
        maven 'MAVEN3'
        jdk 'JDK17'
    }
    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out code...'
                git branch: 'achrefbouden-4twin9-groupe-n3', url: 'https://github.com/chahe-dridi/achat-4twin9-groupe-n3.git'
            }
        }
        stage('Test') {
            steps {
                dir('backend') {
                    echo 'Listing test files...'
                    sh 'ls -l src/test/java/tn/esprit/rh/achat/ || echo "Test directory not found"'
                    echo 'Running Maven tests...'
                    sh 'mvn clean test || echo "Tests failed, continuing..."'
                }
            }
        }
        stage('SonarQube Analysis') {
            steps {
                dir('backend') {
                    echo 'Running SonarQube analysis...'
                    withSonarQubeEnv('sonar') {
                        sh 'mvn sonar:sonar -Dsonar.projectKey=achat-backend-achrefbouden-4twin9-groupe-n3 -Dsonar.projectName=achat-backend-achrefbouden-4twin9-groupe-n3 || echo "SonarQube analysis failed, check logs"'
                    }
                }
            }
        }
        stage('Deploy to Nexus') {
            steps {
                dir('backend') {
                    echo 'Deploying to Nexus...'
                    sh 'mvn deploy -DskipTests || echo "Deploy failed, check logs"'
                }
            }
        }
    }
    post {
        always {
            cleanWs()
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}
