# spring-boot-multitenant
This is a Spring Boot multi-tenant sample using multiple datasources to persist data in different databases. 
That is using the [Hibernate multi-tenancy support](https://docs.jboss.org/hibernate/orm/4.2/devguide/en-US/html/ch16.html) working with the separate database strategy.

## Env Requirements

- Docker-compose >=1.18.0
- Docker >= 18.03.0-ce
- Oracle JDK 8
- Maven

## How to run this project

- First, create and populate databases for project:

```
// go to staging-db directory

docker-compose up -d

mysql -h127.0.0.1 -P3307 -ueurope_db -peurope_db < data-europe.sql
mysql -h127.0.0.1 -P3308 -uasia_db -pasia_db < data-asia.sql  
```

- Then, build project by maven

```
mvn clean package -Dmaven.test.skip=true
```

- Once you have your jar file, you can run it.

```
java -jar target/multitenant-XXX.jar
```

## Testing

Once started you can go and request the data using different tenants :

* `curl -X GET http://localhost:8080/products`
* `curl -X GET -H "tenant:asia" http://localhost:8080/products`
* `curl -X GET -H "tenant:europe" http://localhost:8080/products`
