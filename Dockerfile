# Используем официальный образ с Java 11
FROM eclipse-temurin:11-jdk-alpine AS build

# Устанавливаем необходимые утилиты, включая Maven
RUN apk add --no-cache maven

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем pom.xml и файлы зависимостей
COPY pom.xml .
COPY src ./src

# Собираем проект
RUN mvn clean package -DskipTests

# Второй этап - создаем образ для выполнения
FROM eclipse-temurin:11-jdk-alpine

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем скомпилированный jar файл из первого этапа
COPY --from=build /app/target/AIS_ECC-0.0.1-SNAPSHOT.jar app.jar

# Устанавливаем команду для запуска приложения
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
