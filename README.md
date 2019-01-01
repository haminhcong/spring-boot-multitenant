# spring-boot-multitenant
This is a Spring Boot multi-tenant sample using multiple datasources to persist data in different databases. 
That is using the [Hibernate multi-tenancy support](https://docs.jboss.org/hibernate/orm/4.2/devguide/en-US/html/ch16.html) working with the separate database strategy.

## Compile and package

Being Maven centric, you can compile and package it without tests using:
```
mvn clean package -Dmaven.test.skip=true
```
Once you have your jar file, you can run it.

## Run it

To run it you can go to the Maven target folder generated and execute the following command:
```
java -jar multitenant-XXX.jar
```

## Testing

Once started you can go and request the data using different tenants :

* `curl -X GET http://localhost:8080/products`
* `curl -X GET -H "tenant:secondDS" http://localhost:8080/products`
* `curl -X GET -H "tenant:thirdDS" http://localhost:8080/products`
