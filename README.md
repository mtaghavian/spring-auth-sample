## Basic REST Authentication Login/Logout Example Project
A few days ago, I needed a simple Spring-Boot service to do basic authentications via REST APIs. 
I wanted to login to users with simple rest request and perform some CRUD operations on my data model.
After several hours of searching and examining different git-hub projects, I was mystified and frustrated because most of the projects were using thymeleaf and other technologies to handle authentication.
So I started to learn about spring authentications and ask some of my friends to help. Finally I managed to write the current project to do what I wanted. I hope this project can help others to do the same job I wanted.

## Acknowledgements
I want to appreciate [Mohamad Zeinali](https://github.com/madz0) and [Mohsen Esmaeili](https://github.com/mosssi) who were kind enough to help me to write this project.

## How to use?
You can use Linux curl command or Postman application to create REST requests to simply login to system and do some CRUD operations on a data model named Task

## Sample REST requests
```
1) Login
  http://localhost:60500/user/login
  {
    "username":"admin",
    "password":"123456"
  }
2) Logout
  http://localhost:60500/user/logout
  {}
3) List Users
  http://localhost:60500/user/list
  {}
4) Get current user
  http://localhost:60500/user/current
  {}
5) Save user
  http://localhost:60500/user/save
  {
    "username":"user1",
    "password":"123456",
    "enabled":true
  }
6) Delete user
  http://localhost:60500/user/delete
  {
    "username":"user1"
  }
7) changePassword
  http://localhost:60500/user/changePassword
  {
    "password":"1234"
  }
8) List tasks
  http://localhost:60500/task/list
  {}
9) Save a task
  http://localhost:60500/task/save
  {
    "description": "clean the room"
  }
10) Delete a task
  http://localhost:60500/task/delete
  {
    "id": "f7f0b5e0-d540-428a-a3d9-fcfec7aab18f"
  }
11) Toggle complete a task
  http://localhost:60500/task/toggleComplete
  {
    "id": "66e31e26-98b3-4f47-96a7-c8ac14b49323"
  }
```
