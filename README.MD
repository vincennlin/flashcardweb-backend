Asymmetric Encryption

keytool -genkeypair -alias flashcardwebApiEncryptionKey -keyalg RSA -dname "CN=Vincent Lin,OU=API Development,O=vincennlin.com,L=Luzhou,S=NT,C=TW" -keypass flashcardwebKeypassPassw0rd -keystore flashcardwebApiEncryptionKey.jks -storepass flashcardwebStorepassPassw0rd


--------------------------------------------

Make keypair file only readable by owner

chmod 400 flashcardweb-key-pair.pem

--------------------------------------------

Docker command on AWS EC2:

Install Docker on AWS EC2
sudo yum install docker
sudo service docker start
sudo usermod -a -G docker ec2-user

--------------------------------------------

RabbitMQ

docker run -d -it -p 15672:15672 -p 5672:5672 rabbitmq:3.13.4-management

--------------------------------------------

Build Config Server Docker Image

docker build --tag=flashcardweb-config-server --force-rm=true --platform linux/amd64 .


Tag Config Server Docker Image for Docker Hub

docker tag fddaf9e435a1 vincennlin/flashcardweb-config-server


Run Config Server Docker Container

docker run -d -p 8888:8888 -e "spring.rabbitmq.host=172.31.7.182" vincennlin/flashcardweb-config-server

--------------------------------------------

Build Discovery Service Docker Image

docker build --tag=flashcardweb-discovery-service --force-rm=true --platform linux/amd64 .

docker tag 4db81302f588 vincennlin/flashcardweb-discovery-service

docker push vincennlin/flashcardweb-discovery-service


docker run -d -p 8761:8761 -e "spring.profiles.active=prod" -e "config.server.ip=172.31.7.182" vincennlin/flashcardweb-discovery-service


docker run -d -p 8761:8761 -e

--------------------------------------------

Run MySQL Docker Container

docker run -d -p 3306:3306 --name flashcardweb-mysql -e MYSQL_ROOT_PASSWORD=FlashcardwebRootPassw0rd -e MYSQL_DATABASE=flashcardweb -e MYSQL_USER=FlashcardwebUser -e MYSQL_PASSWORD=FlashcardwebUserPassw0rd mysql:latest


Run Bash in MySQL Docker Container

docker exec -it c0acd75cfc58 bash

--------------------------------------------

Build User Service Docker Image

docker build --tag=flashcardweb-user-service --force-rm=true --platform linux/amd64 .
docker tag b9a6b0a69038 vincennlin/flashcardweb-user-service
docker push vincennlin/flashcardweb-user-service


Run User Service Docker Container

docker run -d -p 8081:8081 -e "spring.profiles.active=prod" -e "config.server.ip=172.31.7.182" vincennlin/flashcardweb-user-service

--------------------------------------------

Build Note Service Docker Image

docker build --tag=flashcardweb-note-service --force-rm=true --platform linux/amd64 .
docker tag bd0413817f52 vincennlin/flashcardweb-note-service
docker push vincennlin/flashcardweb-note-service


Run Note Service Docker Container

docker run -d -p 8082:8082 -e "spring.profiles.active=prod" -e "config.server.ip=172.31.7.182" vincennlin/flashcardweb-note-service

--------------------------------------------

Build Flashcard Service Docker Image

docker build --tag=flashcardweb-flashcard-service --force-rm=true --platform linux/amd64 .
docker tag ebcc9f4f183b vincennlin/flashcardweb-flashcard-service
docker push vincennlin/flashcardweb-flashcard-service


Run Flashcard Service Docker Container

docker run -d -p 8083:8083 -e "spring.profiles.active=prod" -e "config.server.ip=172.31.7.182" vincennlin/flashcardweb-flashcard-service

--------------------------------------------

Build AI Service Docker Image

docker build --tag=flashcardweb-ai-service --force-rm=true --platform linux/amd64 .
docker tag beb3f96d59b8 vincennlin/flashcardweb-ai-service
docker push vincennlin/flashcardweb-ai-service


Run AI Service Docker Container

docker run -d -p 8084:8084 -e "spring.profiles.active=prod" -e "config.server.ip=172.31.7.182" vincennlin/flashcardweb-ai-service

--------------------------------------------

Build API Gateway Docker Image

docker build --tag=flashcardweb-api-gateway --force-rm=true --platform linux/amd64 .


Run API Gateway Docker Container

docker run -d -p 8765:8765 -e "spring.profiles.active=prod" -e "config.server.ip=172.31.7.182" vincennlin/flashcardweb-api-gateway

--------------------------------------------

將 docker-compose.yml 檔案上傳至 AWS EC2

scp -i /Users/vincennlin/Repository/flashcardweb-backend/misc/flashcardweb-key-pair.pem /Users/vincennlin/Repository/flashcardweb-backend/docker-compose.yml ec2-user@3.106.14.156:/home/ec2-user/


docker-compose up -d

