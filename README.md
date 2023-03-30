## A Simple API

#### Built using Java Spring Boot

## Functionality

### Post data
### Get data

## Getting Started
Step 1: Clone the repository
```
git clone https://github.com/Humphrey-nyaga/simpleregistrationproject.git
```
Step 2: Clone the repository
```
cd $dirName
```

Step 3: Create a MySQL Database in your local instance
Make sure you have MySQL installed.
You can also download MySQL from here [https://dev.mysql.com/downloads/installer/]
```
mysql> CREATE DATABASE login_info;
```

Step 4: cd into the project directory
```
mvn clean install
```
Step 4: Run the project.
The api can be accessed from
```
http://localhost:8080/
```
## Usage Example
Endpoints

| Method   | URL                           | Description                                                                                                       |
| -------- |-------------------------------|-------------------------------------------------------------------------------------------------------------------|
| `GET`    | `/api/v1/getData/`            | Retrieve data for  a given account using and password.<br/> <b>Request Parameters<b> <br/>  email <br/>  password |
| `POST`   | `/api/v1/postData/`           | Create new user. <br><b> Request body values <b><br/> email <br/>  password                                       |


```
GET http://localhost:8080/api/v1/getData?email=<your-email>&password=<your-password>
``` 
```
POST http://localhost:8080/api/v1/postData/

Body
{
  "email":"your-email",
  "password":"your-password"
}

``` 


## Project Configuration

| Components        | Technology        | 
   |-------------------|-------------------|
| Backend           | Spring Boot 6.0+, Java 17 | 
| Database          | MySQL             |  
| Server Build      | Maven             |
| API testing       | POSTMAN           |
| Tool              | Intellj Idea      |
   
