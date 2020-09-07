# project_cartographer_admin_server

# About

Extremely simple UI/web server used to manage the user base in project cartographer community.

Supports:

- [x] Banning user accounts
- [x] Banning user machines
- [x] User lookup
- [x] User edits
- [x] New user activation/review
- [x] New user resend activation email
- [x] Showing linked user/machines (users who cheat and use different machines)
- [x] Auditing of user changes (i.e., who banned who)
- [ ] Editing blacklist emails
- [ ] Add h2pc rest api (/login, /register, etc)
- [ ] Add h2pc relays

# To build:

1) Set JAVA_HOME environment variable
2) CD into the git workspace folder
3) If windows, mvnw.cmd package -Dmaven.test.skip=true, If linux, ./mvnw package -Dmaven.test.skip=true

# To run:

1.0) Setup the mysql database (you can install one with docker or lamp/wamp packages or however you want)
- In the project root there is a creates.sql that you can run against the database to create all the necessary tables
- If you use "database-security" spring profile for your users, use the following sql to insert a default user named "root" with an encrypted password "default". Change it when you login

```     
INSERT INTO admin_users(username,password,enabled)VALUES ('root','$2a$10$v7rYUWiKW.gamVZH.CPmDOtDdjhbnCySFqMkFjb60IF6vi5xCIqdi', true);

INSERT INTO admin_authorities (username, authority) VALUES ('root', 'ROLE_USER');
INSERT INTO admin_authorities (username, authority) VALUES ('root', 'ROLE_ADMIN');
```

1.1) Create an application properties filling all required fields below (name it application.properties)
```
spring.jpa.hibernate.ddl-auto=none
#jdbc:mysql://DATABASE_HOST_HERE:3306/DATABASE_NAME_HERE
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=

server.port=9000

# comma delimited list of admin users who get access (i.e., user1, jf#$j#$f, ADMIN, user2, F$#FM#, ADMIN)
# USERS/ADMIN are the only supported roles.
users=

# Tell Spring Security (if used) to require requests over HTTPS
security.require-ssl=true

# The format used for the keystore 
server.ssl.key-store-type=
# The path to the keystore containing the certificate
server.ssl.key-store=
# The password used to generate the certificate
server.ssl.key-store-password=
# The alias mapped to the certificate
server.ssl.key-alias=

# Allowed spring profiles
#
# database-security
#   requires the table admin_users to be loaded with at least one admin user
#
# simple-security
#   requires "users" property to be set (i.e., users=USERNAME,PASSWORD,ROLE
#   there are only 2 supported roles, USER and ADMIN         
#
# web-debug
#   turns on spring web debugging for troubleshooting purposes
spring.profiles.active=
```
2) Get the jar (you need to build it)
3) Use the below command or any variation of running the jar
```
java -jar -Dspring.config.location=application.properties project_cartographer_admin.jar
```
