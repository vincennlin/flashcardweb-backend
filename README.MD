Asymmetric Encryption
````sh
keytool -genkeypair -alias flashcardwebApiEncryptionKey -keyalg RSA -dname "CN=Vincent Lin,OU=API Development,O=vincennlin.com,L=Luzhou,S=NT,C=TW" -keypass flashcardwebKeypassPassw0rd -keystore flashcardwebApiEncryptionKey.jks -storepass flashcardwebStorepassPassw0rd
````
--------------------------------------------
Make keypair file only readable by owner
````sh
chmod 400 flashcardweb-key-pair.pem
````
--------------------------------------------
Docker command on AWS EC2:

Install Docker on AWS EC2
````sh
sudo yum install docker
sudo service docker start
sudo usermod -a -G docker ec2-user
````
--------------------------------------------
RabbitMQ
````sh
docker run -d -it -p 15672:15672 -p 5672:5672 rabbitmq:3.13.4-management
````
--------------------------------------------
Build Config Server Docker Image
````sh
docker build --tag=flashcardweb-config-server --force-rm=true --platform linux/amd64 .
````
Tag Config Server Docker Image for Docker Hub
````sh
docker tag fddaf9e435a1 vincennlin/flashcardweb-config-server
````
Run Config Server Docker Container
````sh
docker run -d -p 8888:8888 -e "spring.rabbitmq.host=172.31.7.182" vincennlin/flashcardweb-config-server
````
--------------------------------------------

Build Discovery Service Docker Image
````sh
docker build --tag=flashcardweb-discovery-service --force-rm=true --platform linux/amd64 .
````
Tag Discovery Service Docker Image for Docker Hub
````sh
docker tag 4db81302f588 vincennlin/flashcardweb-discovery-service
````
Run Discovery Service Docker Container
````sh
docker push vincennlin/flashcardweb-discovery-service
````

````sh
docker run -d -p 8761:8761 -e "spring.profiles.active=prod" -e "config.server.ip=172.31.7.182" vincennlin/flashcardweb-discovery-service
````
````sh
docker run -d -p 8761:8761 -e
````
--------------------------------------------
Create Network for MySQL Docker Container
````sh
docker network create flashcardweb-network
````
Run MySQL Docker Container
````sh
docker run -d -p 3306:3306 --name flashcardweb-mysql -e MYSQL_ROOT_PASSWORD=FlashcardwebRootPassw0rd -e MYSQL_DATABASE=flashcardweb -e MYSQL_USER=FlashcardwebUser -e MYSQL_PASSWORD=FlashcardwebUserPassw0rd mysql:latest
````
Connect MySQL Docker Container to Network
````sh
docker network connect flashcardweb-network flashcardweb-mysql
````
Run Bash in MySQL Docker Container
````sh
docker exec -it c9825f078097 bash
````
將 docker-compose-mysql.yml 檔案上傳至 AWS EC2
````sh
scp -i /Users/vincennlin/Repository/flashcardweb-backend/misc/flashcardweb-key-pair.pem /Users/vincennlin/Repository/flashcardweb-backend/misc/docker-compose-mysql.yml ec2-user@54.180.176.32:/home/ec2-user/
````
啟動該容器
````sh
docker-compose -f docker-compose-mysql.yml up -d
````
--------------------------------------------

Build User Service Docker Image
````sh
docker build --tag=flashcardweb-user-service --force-rm=true --platform linux/amd64 .
docker tag b9a6b0a69038 vincennlin/flashcardweb-user-service
docker push vincennlin/flashcardweb-user-service
````

Run User Service Docker Container
````sh
docker run -d -p 8081:8081 -e "spring.profiles.active=prod" -e "config.server.ip=172.31.7.182" vincennlin/flashcardweb-user-service
````
--------------------------------------------

Build Note Service Docker Image
````sh
docker build --tag=flashcardweb-note-service --force-rm=true --platform linux/amd64 .
docker tag bd0413817f52 vincennlin/flashcardweb-note-service
docker push vincennlin/flashcardweb-note-service
````

Run Note Service Docker Container
````sh
docker run -d -p 8082:8082 -e "spring.profiles.active=prod" -e "config.server.ip=172.31.7.182" vincennlin/flashcardweb-note-service
````
--------------------------------------------

Build Flashcard Service Docker Image
````sh
docker build --tag=flashcardweb-flashcard-service --force-rm=true --platform linux/amd64 .
docker tag ebcc9f4f183b vincennlin/flashcardweb-flashcard-service
docker push vincennlin/flashcardweb-flashcard-service
````

Run Flashcard Service Docker Container
````sh
docker run -d -p 8083:8083 -e "spring.profiles.active=prod" -e "config.server.ip=172.31.7.182" vincennlin/flashcardweb-flashcard-service
````
--------------------------------------------

Build AI Service Docker Image
````sh
docker build --tag=flashcardweb-ai-service --force-rm=true --platform linux/amd64 .
docker tag beb3f96d59b8 vincennlin/flashcardweb-ai-service
docker push vincennlin/flashcardweb-ai-service
````

Run AI Service Docker Container
````sh
docker run -d -p 8084:8084 -e "spring.profiles.active=prod" -e "config.server.ip=172.31.7.182" vincennlin/flashcardweb-ai-service
````
--------------------------------------------

Build API Gateway Docker Image
````sh
docker build --tag=flashcardweb-api-gateway --force-rm=true --platform linux/amd64 .
````

Run API Gateway Docker Container
````sh
docker run -d -p 8765:8765 -e "spring.profiles.active=prod" -e "config.server.ip=172.31.7.182" vincennlin/flashcardweb-api-gateway
````
--------------------------------------------
將 docker-compose.yml 檔案上傳至 AWS EC2
````sh
scp -i /Users/vincennlin/Repository/flashcardweb-backend/misc/flashcardweb-key-pair.pem /Users/vincennlin/Repository/flashcardweb-backend/misc/docker-compose.yml ec2-user@54.180.176.32:/home/ec2-user/
````
將 docker-compose-mysql.yml 檔案上傳至 AWS EC2
````sh
scp -i /Users/vincennlin/Repository/flashcardweb-backend/misc/flashcardweb-key-pair.pem /Users/vincennlin/Repository/flashcardweb-backend/misc/docker-compose-mysql.yml ec2-user@54.180.176.32:/home/ec2-user/
````
在 AWS EC2 上安裝 Docker Compose
````sh
sudo curl -L https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m) -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
docker-compose version
````
````sh
docker-compose up -d
````
---------------------------------------------
在 AWS EC2 上運行前端
````sh
docker pull vincennlin/flashcardweb-frontend
docker run -d -p 3000:3000 --name flashcardweb-frontend vincennlin/flashcardweb-frontend
docker network connect flashcardweb-network flashcardweb-frontend
````
--------------------------------------------
Build Tessearct Docker Image
````sh
docker build --tag=tesseract --force-rm=true --platform linux/amd64 .
````