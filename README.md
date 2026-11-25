# Private Club API

RESTful API для системы прохода в закрытый клуб по одноразовым QR-кодам (UUID).
При успешном сканировании система возвращает данные гостя, деактивирует использованный код и автоматически генерирует новый.

## Стек технологий
- **Java 21**
- **Spring Boot 4.1.2** (Web MVC, Data JPA, Validation)
- **PostgreSQL 16** (в Docker)
- **JUnit 5 + Mockito** (Unit-тесты)

## Быстрый старт

### 1. Требования
- Установленные JDK 21 и Docker Desktop.

### 2. Запуск базы данных
В корне проекта выполните команду (создаст БД и выполнит `init.sql` с тестовыми данными):
```bash
docker-compose up -d
```
Чтобы полностью сбросить базу и пересоздать данные, используйте перед запуском:
```bash
docker-compose down -v
```

### 3. Запуск приложения
Через терминал в корне проекта:
```bash
# Windows
.\mvnw.cmd spring-boot:run

# Linux / macOS
./mvnw spring-boot:run
```
Или запустите класс PrivateClubApplication через IntelliJ IDEA.

Приложение будет доступно по адресу: http://localhost:8080

