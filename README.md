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

## Problems not resolved

- Test ACID properties
- Change dialect to `MySQL5InnoDBDialect` (current config is not applied)
- Set Hikari Pool Config for each pool for each tenant

## References

- https://stackoverflow.com/questions/17666249/how-to-import-an-sql-file-using-the-command-line-in-mysql
- https://tech.asimio.net/2017/01/17/Multitenant-applications-using-Spring-Boot-JPA-Hibernate-and-Postgres.html
- https://dzone.com/articles/spring-boot-hibernate-multitenancy-implementation
- https://docs.spring.io/spring-data/jpa/docs/2.1.3.RELEASE/reference/html/#jpa.repositories
- https://github.com/eugenp/tutorials/blob/master/spring-boot-autoconfiguration/src/main/java/com/baeldung/autoconfiguration/MySQLAutoconfiguration.java
- https://github.com/alonsegal/springboot-schema-per-tenant
- https://vladmihalcea.com/hibernate-database-catalog-multitenancy/
- https://www.slideshare.net/rcandidosilva/supporting-multitenancy-applications-with-java-ee