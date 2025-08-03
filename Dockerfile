# 빌드 스테이지
FROM eclipse-temurin:17-jdk-jammy AS build
WORKDIR /app

# Gradle 관련 파일들을 먼저 복사
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle .
COPY settings.gradle .

# 소스 코드를 복사
COPY src ./src

# Gradle 빌드 실행
RUN ./gradlew build --no-daemon

# --- 이 줄을 추가하여 빌드 결과를 직접 확인합니다 ---
RUN ls -l /app/build/libs/

# 실행 스테이지
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# 확인 후 정확한 JAR 파일 이름으로 수정합니다.
COPY --from=build /app/build/libs/NewsCrawler-1.0.0-SNAPSHOT.jar app.jar

# 애플리케이션이 사용할 포트 노출
EXPOSE 18080

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]