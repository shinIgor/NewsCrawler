# 빌드 스테이지
FROM eclipse-temurin:17-jdk-jammy AS build
WORKDIR /app

# Gradle 관련 파일들을 먼저 복사하여 캐시 효율을 높임
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle .
COPY settings.gradle .

# 소스 코드를 복사
COPY src ./src

# Gradle 빌드 실행
# --no-daemon 옵션으로 데몬 없이 빌드하여 컨테이너 환경에 최적화
RUN ./gradlew build --no-daemon

---

# 실행 스테이지
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# 빌드 스테이지에서 생성된 최종 JAR 파일을 복사
# your-application-name.jar을 실제 프로젝트의 JAR 파일 이름으로 변경해주세요.
COPY --from=build /app/build/libs/your-application-name.jar app.jar

# 애플리케이션이 사용할 포트 노출
EXPOSE 18080

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]