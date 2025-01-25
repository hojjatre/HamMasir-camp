# Restaurant API
This project using ***spring boot*** and our APIs are:
1. sing-in and sing-up user
2. get verification code for restaurant OWNER
3. see all restaurant
4. see all food's restaurants
5. add and remove restaurant by its OWNER
6. change cost food's restaurant by its OWNER
7. remove and add food to a restaurant by its OWNER
8. make a order

**Step1**: create our models that includes: 
* Food
* Order
* Restaurant
* Role
* User implementation

**Step2**: create our configuration:
* AppConfig
  * build our data
* SecurityConfig
  * use ***SecurityFilterChain*** to determine which user can access which endpoint 

**Step3**: create services that includes:
* UserService
  * assign **ROLES** to user
* OrderService
  * make order by a user
* RestaurantService

**Step4**: create schedule
* there is a verification code for each OWNER in order to change their restaurant or add they need that, so this task is scheduled every 5 min create a new verification code for OWNER.

**Step5**: create our controller

## Spring Security
1. Add security dependency and Jwt dependencies:
```xml
<dependency>
     <groupId>org.springframework.security</groupId>
     <artifactId>spring-security-test</artifactId>
     <scope>test</scope>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.11.5</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.11.5</version>
    <scope>runtime</scope>
</dependency>
```
Let's dive in to implement Spring Security:
1. implement `userDetails`:
  - represents the authenticated user in the Spring Security framework and contains details such as the user's username, password, authorities (roles), and additional attributes.
2. implement `UserDetailService`:
  - is used by `DaoAuthenticationProvider` for retrieving a username, a password, and other attributes for authenticating with a username and password.
3. extends `OncePerRequestFilter`
  - abstract class OncePerRequestFilter extends GenericFilterBean. Filter base class that aims to guarantee a single execution per request dispatch, on any servlet container.
4. implement `AuthenticationEntryPoint`
  -  an interface that acts as a point of entry for authentication that determines if the client has included valid credentials when requesting for a resource.
  - **_commence()_** method. This method will be triggerd anytime unauthenticated User requests a secured HTTP resource and an `AuthenticationException` is thrown.
5. Security Config

## Spring Data JPA
- **Hibernate**:
  - an Object-Relational Mapping (ORM) framework that provides a high-level API for interacting with relational databases.
  - allows you to map Java objects to database tables and perform database operations using a high-level API, instead of writing low-level SQL code.
- **JPA** (Java Persistence API):
  - is a specification that defines a set of interfaces and annotations for working with relational databases in Java applications.
  - JPA provides a common API for ORM frameworks like Hibernate.
- **Spring Data JPA**:
  - is a part of the Spring Framework that provides a higher-level, easier-to-use API for working with JPA.

**Step1**: Add dependencies:
1. ```xml
   <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
2. ```xml
   <dependency>
      <groupId>org.postgresql</groupId>
      <artifactId>postgresql</artifactId>
      <scope>runtime</scope>
   </dependency>
   ```
3. add this to application.properties:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/nameYourDatabase
spring.datasource.username=
spring.datasource.password=
# Hibernate ddl auto (create, create-drop, validate, update)
spring.jpa.hibernate.ddl-auto=update
```
**Step2**: Create our Models:
1. Learn about relation between our table
   - [One-to-One](https://www.baeldung.com/jpa-one-to-one)
   - [Many-to-One and One-to-Many](https://www.baeldung.com/hibernate-one-to-many)
   - [Many-to-Many](https://www.baeldung.com/hibernate-many-to-many)
2. Learn annotations
3. ID Generation Strategies:
   - _AUTO_: the persistence provider will determine values based on the type of the primary key attribute.
   - _IDENTITY_: Assign primary keys using database identify column.
   - _SEQUENCE_
   - _TABLE_
4. Learn about [JPA entity life cycle](https://www.objectdb.com/java/jpa/persistence/managed#:~:text=The%20life%20cycle%20of%20entity,persistence.)
5. Learn about [Projection](https://www.baeldung.com/spring-data-jpa-projections)
6. Types of behavior of transaction in JPA
   - **Isolation Levels**: determine how concurrent transactions interact with each other.
     - Read Uncommitted
     - Read Committed
     - Repeatable Read
     - Serializable
   - **Propagation**: defines how a transaction behaves when a method is invoked within an existing transaction context.
     - REQUIRED
     - REQUIRES_NEW
     - MANDATORY
     - SUPPORTS
     - NOT_SUPPORTED
     - NEVER
   - **Rollback**: You can specify under what conditions a transaction should be rolled back
     - RollbackFor
     - NoRollbackFor
   - **Timeout**: define a timeout for a transaction, after which the transaction will be automatically rolled back if it hasn't completed.


<details>
<summary>Additional API + QUERIES:</summary>

1. Find Users With Total Sum Cost Exceeding Threshold
```java
    @Query("SELECT DISTINCT u FROM UserImp u JOIN u.orders o GROUP BY u HAVING SUM(o.totalCost) > :thresholdAmount")
    List<UserView> findUsersWithTotalSumCostExceedingThreshold(@Param("thresholdAmount") int thresholdAmount);
```
2. Count Orders By User And Restaurant
```java
    @Query("SELECT NEW org.example.dto.OrderCountDTO(o.user_order, o.restaurant, COUNT(o)) FROM Order o GROUP BY o.user_order, o.restaurant")
    List<OrderCountDTO> countOrdersByUserAndRestaurant();
```
3. Search Orders
```java
private Specification<Order> buildQuery(OrderSearchCriteria criteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getUsername() != null) {
                predicates.add(criteriaBuilder.equal(root.get("user_order").get("username"), criteria.getUsername()));
            }

            if (criteria.getRestaurantId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("restaurant").get("id"), criteria.getRestaurantId()));
            }

            if (criteria.getTotalCost() != null) {
                predicates.add(criteriaBuilder.greaterThan(root.get("totalCost"), criteria.getTotalCost()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
```
- the Specification interface will serve as a container for defining the dynamic query predicates using criteria-based conditions.
- The toPredicate method takes three parameters: **Root**, **CriteriaQuery**, and **CriteriaBuilder**.
- The **Root** object represents the entity being queried and allows access to its attributes.
- The **CriteriaQuery** object defines the query structure and can be used to modify the query aspects like ordering and grouping.
- The **CriteriaBuilder** object provides a set of methods for building the criteria-based predicates.
4. Find Most Expensive Food
```java
RestaurantView findTopByOrderByFoodsCostDesc();
```
5. Find Restaurant By Foods Description Containing a input
```java
List<RestaurantView> findByFoodsDescriptionContaining(String description);
```
</details>

## Add Redis and Redisson
1. Add dependency
```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-redis</artifactId>
    </dependency>
    <dependency>
        <groupId>org.redisson</groupId>
        <artifactId>redisson</artifactId>
        <version>3.5.0</version>
    </dependency>
```
2. Add Redis config
```java
    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://127.0.0.1:6379");

        RedissonClient client = Redisson.create(config);
        return client;
    }
```
I use `RMap` that keys are `RestaurantDTOredis` and values a `RList` are `FoodDTOredis`.
In this part I have to use `EntityGraph` and `Mapstruct`.
- Learn about [EntityGraph](https://www.baeldung.com/jpa-entity-graph)
- Learn about [Mapstruct](https://www.baeldung.com/mapstruct) and how add dependency.
- I used EntityGraph in **Restaurant**
    ```java
    @NamedEntityGraph(
            name = "graph.restaurant",
            attributeNodes = {
                    @NamedAttributeNode("restaurantID"),
                    @NamedAttributeNode("name"),
                    @NamedAttributeNode("location"),
                    @NamedAttributeNode(value = "owner", subgraph = "graph.user"),
                    @NamedAttributeNode(value = "foods", subgraph = "graph.foods"),
            },
            subgraphs = {
                    @NamedSubgraph(
                            name = "graph.foods",
                            attributeNodes = {
                                    @NamedAttributeNode("foodID"),
                                    @NamedAttributeNode("name"),
                                    @NamedAttributeNode("typeFood"),
                                    @NamedAttributeNode("cost"),
                                    @NamedAttributeNode("description")
                            }
                    ),
                    @NamedSubgraph(
                            name = "graph.user",
                            attributeNodes = {
                                    @NamedAttributeNode("username"),
                            }
                    )
    
            }
    )
    public class Restaurant
    ```
- add this entity graph in `Repository` for loading related entities or attributes
  ```java
      @EntityGraph(value = "graph.restaurant", type = EntityGraph.EntityGraphType.FETCH)
      @Query("select r from Restaurant r")
      List<RestaurantView> findAllRestaurant();
  ```
- Mapper for **Food**
    ```java
    @Mapper
    public interface FoodMapperRedis {
        FoodMapperRedis instanse = Mappers.getMapper(FoodMapperRedis.class);
    
        @Mapping(source = "foodID", target = "foodID")
        @Mapping(source = "name", target = "name")
        @Mapping(source = "typeFood", target = "typeFood")
        @Mapping(source = "cost", target = "cost")
        @Mapping(source = "description", target = "description")
        FoodDTOredis entityToDTO(Food food);
    
        @Mapping(source = "foodID", target = "foodID")
        @Mapping(source = "name", target = "name")
        @Mapping(source = "typeFood", target = "typeFood")
        @Mapping(source = "cost", target = "cost")
        @Mapping(source = "description", target = "description")
        Food dtoTOEntity(FoodDTOredis foodDTOredis);
    }
    ```
- Mapper for **Restaurant**
    ```java
    @Mapper
    public interface RestaurantMapperRedis {
        RestaurantMapperRedis instance =  Mappers.getMapper(RestaurantMapperRedis.class);
    
        @Mapping(source = "restaurantID", target = "restaurantID")
        @Mapping(source = "name", target = "name")
        @Mapping(source = "location", target = "location")
        RestaurantDTOredis entityToDTO(RestaurantView restaurantView);
    
    
        @Mapping(source = "restaurantID", target = "restaurantID")
        @Mapping(source = "name", target = "name")
        @Mapping(source = "location", target = "location")
        RestaurantDTOredis entityToDTO(Restaurant restaurant);
    
    
    }
    ```
Every time a request is sent to get all restaurants, we fill or update the cache and if the owner wants to **change the cost of food**, if the restaurant exists in the cache, the operation applies to the cache. 
After a specific time by `task schedule` database updates.
```java
    @Scheduled(fixedRate = 50000)
    public void loadFoodToDatabase(){
        foodMapperRedis = FoodMapperRedis.instanse;
        RMap<RestaurantDTOredis, RList<FoodDTOredis>> restaurantMap = restaurantCache.getRedisConfig().redissonClient()
                .getMap(NameCache.RESTAURANT_CACHE);
        for (RestaurantDTOredis res:restaurantMap.keySet()) {
            RList<FoodDTOredis> foodRedis = restaurantCache.getRedisConfig().redissonClient()
                    .getList("restaurantID:"+res.getRestaurantID());
            for (int i = 0; i < foodRedis.size(); i++) {
                Food food = foodMapperRedis.dtoTOEntity(foodRedis.get(i));
                food.setRestaurant(restaurantRepository.findByRestaurantID(res.getRestaurantID(),
                        Restaurant.class));
                foodRepository.save(food);
            }
        }
    }
```

## Migrate to Ubuntu
1. Run `postgresql` on **Docker**
```dockerfile
sudo docker run --name postgresql -e POSTGRES_PASSWORD=postgresql -p 5432:5432 -d postgres
```
2. Run `pgAdmin` on **Docker**
```dockerfile
sudo docker run --name pgadmin-container -p 5050:80 -e PGADMIN_DEFAULT_EMAIL=hojjat7878.h@gmail.com -e PGADMIN_DEFAULT_PASSWORD=postgresql -d dpage/pgadmin4
```
3. change settings database on `properties` file.
4. Run `RabbitMQ`on **Docker**
   5. I use `docker-compose`
```dockerfile
version: "3.6"

services:
  rabbitmq:
    image: 'rabbitmq:3.9-management'
    ports:
      # The standard AMQP protocol port
      - '5672:5672'
      # HTTP management UI
      - '15672:15672'
    environment:
      RABBITMQ_DEFAULT_USER: "hojjat"
      RABBITMQ_DEFAULT_PASS: "hojjat"
    networks:
      - network
networks:
  network: {}
```

## Add RabbitMQ
1. Add dependency
```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-amqp</artifactId>
        </dependency>
```
2. Learn about [Message Broker and RabbitMQ](https://geekflare.com/top-message-brokers/)
3. Exchange Type:
   - **Fanout**: Sender will produce to the exchange, the exchange will duplicate the message and send it to every single queue that it knows about.
   - **Direct**: Sender will produce the message and then  that message will get a **routing key**, so the routing key is being compared to the **binding key** and if it's an exact match then the message will move through the system.
   - **Topic**: We can do partial match between the routing key and binding key. if we had a routing key on this message called `ship.shoes` and the binding key was called `ship.any` that message would get routed through to that queue.
   - **Default**: Is unique only to RabbitMQ. Is getting tied to the name of the queue itself.

## Add PostGIS
We need go inside to the container of Docker.
```shell
sudo docker exec -it postgresql bash
```
```shell
apt update
```
```shell
apt install postgis
```
- after that we go the pgAdmin and select our database and write this query
```postgresql
CREATE EXTENSION postgis
```
## Dockerize
We need dockerize all of our project, in order that we use `docker-compose`
```dockerfile
version: '3.6'
services:
  database:
    image: 'postgres'
    container_name: postgresql

    ports:
      - "5432:5432"
    
    environment:
      - POSTGRES_DB=restaurant
      - POSTGRES_PASSWORD=postgresql
  
  pgadmin:
    image: 'dpage/pgadmin4'
    container_name: pgadmin-container-1

    ports:
      - "5050:80"
    
    environment:
      - PGADMIN_DEFAULT_EMAIL=hojjat7878.h@gmail.com 
      - PGADMIN_DEFAULT_PASSWORD=postgresql

  
  redis:
    image: redis
    container_name: restaurant-redis-1
    ports:
      - "6379:6379"
    
  rabbitmq:
    image: 'rabbitmq:3.9-management'
    container_name: rabbitmq
    ports:
      # The standard AMQP protocol port
      - '5672:5672'
      # HTTP management UI
      - '15672:15672'
    environment:
      RABBITMQ_DEFAULT_USER: "hojjat"
      RABBITMQ_DEFAULT_PASS: "hojjat"


  spring-app:
    build:
      context: ./HamMasir-camp
      dockerfile: Dockerfile
    ports:
      - "8080:8080"
    depends_on:
      - redis
      - rabbitmq
      - database
```