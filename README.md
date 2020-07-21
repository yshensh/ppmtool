## ppmtool backend server
Backend server for [Personal Project Management Tool](https://github.com/yshensh/ppmtool-heroku)

## Local Development Setup
IDE: IntelliJ IDEA Ultimate <br />
Dependency Management Tool: Maven <br />
API Endpoint Testing Tool: Postman <br />
Database Viewer: MySQL Workbench <br />
Databse: mySQL database (running on Docker) <br />

If you are using in-memory H2 database during the early stage of the development,
you may access H2 Database with [link](http://localhost:8080/h2-console/) <br />
`JDBC URL: jdbc:h2:mem:testdb`

### `mvn clean install` 
Builds `jar` file for deployment
