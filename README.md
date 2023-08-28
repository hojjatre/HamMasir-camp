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
1. Add security dependency:
   - `<dependency>
     <groupId>org.springframework.security</groupId>
     <artifactId>spring-security-test</artifactId>
     <scope>test</scope>
     </dependency>`
2. Add Jwt dependencies:
  - `<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.11.5</version>
    </dependency>`
  - `<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.11.5</version>
    <scope>runtime</scope>
    </dependency>`
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
