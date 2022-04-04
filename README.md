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