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
