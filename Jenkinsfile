// Jenkinsfile
pipeline {
    // 1. 빌드 환경 정의: 쿠버네티스 위에 동적으로 생성될 Pod를 빌드 환경으로 사용합니다.
    agent {
        kubernetes {
            yaml """
apiVersion: v1
kind: Pod
spec:
  containers:
  # Gradle 빌드를 위한 컨테이너
  - name: 'gradle'
    image: 'gradle:8.8.0-jdk17' // 프로젝트의 Java 버전에 맞는 Gradle 이미지를 사용하세요.
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

  # --- 이 부분이 추가되었습니다 ---
  # kubectl 명령을 실행하기 위한 컨테이너
  - name: 'kubectl'
    image: 'bitnami/kubectl:latest'
    command: ['sleep']
    args: ['99d']
  # -----------------------------

  volumes:
  - name: 'docker-sock'
    hostPath:
      path: '/var/run/docker.sock'
"""
        }
    }

    // 2. 빌드/배포 단계 정의
    stages {
        // 첫 번째 단계: Gradle을 사용하여 프로젝트를 빌드합니다.
        stage('Build with Gradle') {
            steps {
                // 'gradle' 이라는 이름의 컨테이너 안에서 아래 명령을 실행합니다.
                container('gradle') {
                    // Gradle Wrapper를 사용하여 빌드하는 것을 권장합니다.
                    // -x test 옵션은 빌드 시 테스트를 건너뛰고 싶을 때 사용합니다.
                    sh './gradlew build -x test'
                }
            }
        }

        // 두 번째 단계: 빌드된 결과물로 Docker 이미지를 만듭니다.
        stage('Build Docker Image') {
            steps {
                // 'docker' 이라는 이름의 컨테이너 안에서 아래 명령을 실행합니다.
                container('docker') {
                    // Docker Hub ID와 프로젝트 이름을 포함하여 태그를 지정하는 것이 좋습니다.
                    sh 'docker build -t your-docker-id/news-crawler:latest . --no-cache'
                }
            }
        }

        // 세 번째 단계: Kubernetes에 배포합니다.
        stage('Deploy to Kubernetes') {
            steps {
                // 'kubectl' 이라는 이름의 컨테이너 안에서 아래 명령을 실행합니다.
                container('kubectl') {
                    // 1. Docker 이미지를 Docker Hub 같은 이미지 저장소에 Push
                    //    (Jenkins에 Docker Hub 인증 정보(Credentials) 설정이 필요합니다)
                    // withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    //     sh 'echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin'
                    //     sh 'docker push your-docker-id/news-crawler:latest'
                    // }

                    // 2. Kubernetes의 Deployment 설정을 적용하여 배포
                    //    (k8s/deployment.yaml 파일이 Git 소스코드에 있어야 합니다.)
                    sh 'kubectl apply -f k8s/deployment.yaml'
                }
            }
        }
    }
}
