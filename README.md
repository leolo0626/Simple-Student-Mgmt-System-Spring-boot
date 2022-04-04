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
5. 
6. 