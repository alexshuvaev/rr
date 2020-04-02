# Russian Robotics Test Task

Application which downloading CSV-file from email, parsing it and persist to DB

## Requirements

1. Java - 1.8.x

2. Maven 3

## Used technologies

* Java 8,
* Spring Boot 2.2.5, 
* Hibernate 5.4.12,
* H2 Database (inmemory),
* Vaadin 14 (UI),
* Maven 3

## Steps to Setup

**1. Clone the application**

```bash
git clone https://github.com/alexshuvaev/rr.git
```

**2. Build and run the app using maven**

```bash
mvn spring-boot:run
```

### Or you can do next: 

**1. Create JAR file with Maven profile "production-mode"**

```bash
mvn clean package -Pproduction-mode
```
JAR file will be stored in target folder.


**2. Execute JAR file in Terminal**

```bash
java -jar <path/file_name>
```

The app will start running at <http://localhost:8080>.

## Screanshots

![RR](http://emiimi.ru/rr.jpg)


