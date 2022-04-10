# Simple-Student-Mgmt-System-Spring-boot

##Bundle frontend and backend
- Frontend Maven Plugin
- Copy static file from frontend folder to static folder 
---
##Run the jar

- cd target

then you will see snapshot of jar

- java -jar demo-0.0.1-SNAPSOT.jar
---
##Build the Docker Image

###Prequsite

- we need to jib , the Opensource Container for the maven plugin

If -Djib.to.image=fullstack:v1 does not work try -Dimage=fullstack:v1

- To build docker image

Mac
- ./mvnw compile jib:dockerBuild -Djib.to.image=fullstack:v1 or ./mvnw compile jib:dockerBuild -Dimage=fullstack:v1
- ./mvnw clean install jib:dockerBuild -Djib.to.image=fullstack:v1 or ./mvnw clean install jib:dockerBuild -Dimage=fullstack:v1


Windows
- mvnw compile jib:dockerBuild -Djib.to.image=fullstack:v1 
  - or mvnw compile jib:dockerBuild -Dimage=fullstack:v1
- mvnw clean install jib:dockerBuild -Djib.to.image=fullstack:v1 
  - or mvnw clean install jib:dockerBuild -Dimage=fullstack:v1

To run container
- docker run --name fullstack -p 8080:8080 fullstack:v1

To delete container with name fullstack
- docker rm -f fullstack

To view local images run
- docker image ls or docker images

To view running containers
- docker ps 

---
##Build image to DokcerHub

1. Open terminal and 
`docker login`

2. Push the image to docker hub.

```aidl
./mvnw clean install jib:build -Djib.to.image=leolo950626/spring-react-fullstack:v1
```

3. Pull the image from docker hub

```aidl
./mvnw clean install jib:build -Djib.to.image=leolo950626/spring-react-fullstack:latest
```
Go to Docker hub
https://hub.docker.com

---
##Build Profiles

Please look at session `jib-push-to-local` and `jib-push-to-dockerhub` in pom.xml  
```aidl
./mvnw help:active-profiles
```

There are two things achieved in the command
1. Build up the application
2. Push the image to docker hub 
   1. Versions `latest` and `v2`are created. 
   
```aidl
./mvnw clean install -P build-frontend -P jib-push-to-dockerhub -Dapp.image.tag=v2
```

3. Push the image to docker local
```aidl
./mvnw clean install -P build-frontend -P jib-push-to-local -Dapp.image.tag=v2
```

`--rm` remove the container once it is shut down.
```aidl
docker run --rm -p 8080:8080 leolo950626/springboot-react-fullstack
```
---
##Amazon Elastic Beanstalk

1. Go to Amazon Cloud Service
   https://aws.amazon.com/tw/
2. Create a elastic beanstalk
3. Upload the docker compose to elastic beanstalk
   1. docker compose https://docs.docker.com/compose/
   2. Remarks : Map 80 port https://docs.amazonaws.cn/en_us/elasticbeanstalk/latest/dg/java-se-nginx.html

---
##Postgres
1. Create a network db
```aidl
docker network create db
```
-  Delete a network
```aidl
docker network rm db
```
2. Create a folder for mounting data here
```aidl
cd Desktop
mkdir db-data
cd db-data
```
3. run a postgres container
- `-v`: `volume` mounting data from internal file to our OS (desktop/db-data) 
- `-e`: `environment` 

WINDOWS USER use %cd% instead of "$PWD"
```aidl
docker run --name db -p 5432:5432 --network=db \
-v "%cd%:/var/lib/postgresql/data" 
-e POSTGRES_PASSWORD=password -d postgres:alpine
```
MAC USER 
```aidl
docker run --name db -p 5432:5432 --network=db \
-v "$PWD:/var/lib/postgresql/data" -e POSTGRES_PASSWORD=password \
-d postgres:alpine
```
4. Connect a db using PSQL Contianer
- `-h` : host : We can't use the localhost in `-h`. They are connected in different container. Before that we create a network , called db, we can make use of it to connect two different container.
- `-U` : User default postgres
```aidl
docker run -it --rm --network=db postgres:alpine psql -h db -U postgres
```

Postgres in docker will store the data in folder we specify as well. Once we terminate the service, we won't lose the data. 
5. Start a postgres instance https://hub.docker.com/_/postgres

---
##Spring Data JPA
1. Add two dependencies , `postgres` and `spring-boot-starter-data-jpa` in `pom.xml`
2. Add configurations in `resources\applications.properites`
3. Before running the application, we need to create db, called leolo950626
```aidl
CREATE Database leolo950626
```
Remark : make sure you run docker container for PSQL.

Introudction to `lombok` library : it can help us reduce substantial amount of code .

Details :-> https://blog.csdn.net/motui/article/details/79012846

4. mock up some data
   https://www.mockaroo.com/

---
##Error Handling 
Server side

in application.properties , it will shows the error msg, path in the response.

```
server.error.include-message=always

server.error.include-binding-errors=always
```


---

##AWS Database

1. Go to Configuration
2. Database session and edit 

Snapshot - restore something

Engine Postgres

let the AWS helps use to set up everything

Create application-dev.properties 
- change the db connection as well as username
- change the environment in configuration in intellij 
- environment variable - set to `SPRING_PROFILES_ACTIVE=dev`

Change the Security group
- allow or deny trafic to EC2 
- go to AWS console
- Search for rds and change the inbound rule 
- add the inbound rule to my ip 
- then you can use it in your local machine via psql or intelij 

`create database leolo950626`

---

## CICD

https://github.com/features/actions

###For the build 
- look at `.github\workflows\build.yml`
  - `on` : what action will trigger the github action
  - `env` : environment variable
  - `jobs`
    - `build` 
      - `run-on` : what kind of os
      - `service` : set up docker image and container for the application
      - `steps` : contains the steps of workflow
  
- maven command review
  - `--no-transfer-progress` : suppress the maven download msg
  - `validate` - validate the project is correct and all necessary information is available
  - `compile` - compile the source code of the project
  - `test` - test the compiled source code using a suitable unit testing framework. These tests should not require the code be packaged or deployed
  - `package` - take the compiled code and package it in its distributable format, such as a JAR.
  - `verify` - run any checks on results of integration tests to ensure quality criteria are met
  - `install` - install the package into the local repository, for use as a dependency in other projects locally
  - `deploy` - done in the build environment, copies the final package to the remote repository for sharing with other developers and projects.
from https://stackoverflow.com/questions/16602017/how-are-mvn-clean-package-and-mvn-clean-install-different

- error facing during CI
  - failed in frontend-maven-plugin
    - it's due to eslint error. fix it and then solve. Nothing about the version

### Create Slack WebHook
- Go to https://api.slack.com/apps
- Create a new application
- Create a new webhook
- Create a new Channel
- Copy webhook token to the secret (under setting) of this repository

## AWS User , Groups , Permission
- Go to AWS
- Select security credential 
- Create User Group, called GithubActions
- Create User, called github-actions
- check the selection , which is to allow application to use keys 
- add user to group
- After that, you will get a secret key and id (remark, they only show once)
- place them on to the secret (under setting) in the github

### For the deployment
- look at `.github\workflows\deploy.yml`

---
## Testing
`JUnit5`