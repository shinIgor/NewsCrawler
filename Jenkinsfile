// Jenkinsfile
pipeline {
    agent {
        kubernetes {
            yaml """
apiVersion: v1
kind: Pod
spec:
  containers:
  # Gradle 빌드를 위한 컨테이너
  - name: 'gradle'
    # --- 이 부분이 수정되었습니다 (// -> #) ---
    image: 'gradle:8.8.0-jdk17' # 프로젝트의 Java 버전에 맞는 Gradle 이미지를 사용하세요.
    command: ['sleep']
    args: ['99d']

  # Docker 명령을 실행하기 위한 컨테이너
  - name: 'docker'
    image: 'docker:latest'
    command: ['sleep']
    args: ['99d']
    volumeMounts:
    - name: 'docker-sock'
      mountPath: '/var/run/docker.sock'

  # kubectl 명령을 실행하기 위한 컨테이너
  - name: 'kubectl'
    image: 'bitnami/kubectl:latest'
    command: ['sleep']
    args: ['99d']

  volumes:
  - name: 'docker-sock'
    hostPath:
      path: '/var/run/docker.sock'
"""
        }
    }
    stages {
        stage('Build with Gradle') {
            steps {
                container('gradle') {
                    sh './gradlew build -x test'
                }
            }
        }
        stage('Build Docker Image') {
            steps {
                container('docker') {
                    sh 'docker build -t your-docker-id/news-crawler:latest . --no-cache'
                }
            }
        }
        stage('Deploy to Kubernetes') {
            steps {
                container('kubectl') {
                    // sh 'docker push your-docker-id/news-crawler:latest'
                    sh 'kubectl apply -f k8s/deployment.yaml'
                }
            }
        }
    }
}
