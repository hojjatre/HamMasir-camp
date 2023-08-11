# Online Library Management System

There are 4 packages:
1. User management:
    - Librarian and Member **extends** User
    - UserSystem was created to **save user** in Map and also **user authenticated**
2. Book management:
   - Author and Book class
   - Genre is an Enum class 
3. Catalog system:
    - Catalog is a interface
    - CatalogImp was created to find books by **name** and **id**, and **author** also has a HashMap to save books by their author (author is key and values are books).
4. Loan:
    - A member can loan books and if it didn't return book until expiration date there is a penalty for every day.

- Security class:
  - Sign in and Sign up
