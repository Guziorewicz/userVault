Simply basic and basicly simple java app with user vault

.
├── app/
│   ├── UserVault.java
├── model/
│   ├── User.java
├── service/
│   ├── Vault.java
│   ├── AdminService.java
│   ├── FileService.java
│   └── UserService.java
├── util/
│   ├── ConsoleHelper.java
│   ├── InputSanitizer.java
│   ├── ValidationException.java
├── users.json
├── read.md


We have two menus
 - One for user with:
    . Register New User
    . View All Users
    . Search by Name
    . Exit (App)
    . Admin Panel (protected by password)


- Second for admin with:
    . View All Users
    . Edit User
    . Delete User
    . Exit (To User menu)

User can register new User data (name, email, age). Validation for this data is provided by InputSanitizer and ValidationException.
Viewing all data and Searching by name are placed in UserService.

Admin requires password placed in .env. Prepared .gitignore for .env file but comment it for presentantion.
In Admin menu provide View_All_Users and is extended by Editing and Deleting User data. 

Mock data is holded in users.json and saved there.
In app I made Singleton pattern in Vault.java for users list. 

# Project refactored to Maven layout to compile and run it use
mvn compile
mvn exec:java



### Tests upcomming ###

