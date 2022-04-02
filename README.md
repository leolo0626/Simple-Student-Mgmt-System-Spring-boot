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

Mac
- ./mvnw compile jib:dockerBuild -Djib.to.image=fullstack:v1 or ./mvnw compile jib:dockerBuild -Dimage=fullstack:v1
- ./mvnw clean install jib:dockerBuild -Djib.to.image=fullstack:v1 or ./mvnw clean install jib:dockerBuild -Dimage=fullstack:v1


Windows
- mvnw compile jib:dockerBuild -Djib.to.image=fullstack:v1 or mvnw compile jib:dockerBuild -Dimage=fullstack:v1
- mvnw clean install jib:dockerBuild -Djib.to.image=fullstack:v1 or mvnw clean install jib:dockerBuild -Dimage=fullstack:v1

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

`docker login`

Push the image to docker hub.
```aidl
./mvnw clean install jib:build -Djib.to.image=leolo950626/spring-react-fullstack:v1
```

Go to Docker hub
https://hub.docker.com