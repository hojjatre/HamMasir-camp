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
4. Find Most Expensive Food
```java
RestaurantView findTopByOrderByFoodsCostDesc();
```
5. Find Restaurant By Foods Description Containing a input
```java
List<RestaurantView> findByFoodsDescriptionContaining(String description);
```
</details>