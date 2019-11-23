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
2) Cd into the git workspace folder
3) If windows, mvnw.cmd package, If linux, ./mvnw package

# To run:

1) Create an application properties filling all required fields below (name it application.properties)
```
spring.jpa.hibernate.ddl-auto=none
#jdbc:mysql://DATABASE_HOST_HERE:3306/DATABASE_NAME_HERE
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=

server.port=9000

# comma delimited list of admin users who get access (i.e., user1, jf#$j#$f, ADMIN, user2, F$#FM#, ADMIN)
# ADMIN is the only supported role.
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

spring.profiles.active=prod
```
2) Get the jar (you need to build it)
3) Use the below command or any variation of running the jar
```
java -jar -Dspring.config.location=application.properties project_cartographer_admin.jar
```
