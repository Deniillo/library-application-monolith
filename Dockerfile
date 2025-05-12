# Этап сборки
FROM eclipse-temurin:21 AS builder

# Зависимости для Gradle
RUN apt-get update && apt-get install -y curl findutils

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

COPY . .

# Копируем файлы Gradle
COPY gradlew .
COPY gradle .

# Копируем файлы проекта
COPY . .

# Даем права на выполнение Gradle wrapper
RUN chmod +x gradlew

# Сборка проекта
RUN ./gradlew build -x test

# Используем официальный образ OpenJDK для запуска
FROM eclipse-temurin:21-ubi9-minimal

WORKDIR /app

# Копируем собранный JAR-файл из этапа сборки
COPY --from=builder /app/build/libs/*.jar library-application-monolith.jar

# Команда для запуска приложения
ENTRYPOINT ["java", "-jar", "library-application-monolith.jar"]
