Asymmetric Encryption

keytool -genkeypair -alias flashcardwebApiEncryptionKey -keyalg RSA -dname "CN=Vincent Lin,OU=API Development,O=vincennlin.com,L=Luzhou,S=NT,C=TW" -keypass flashcardwebKeypassPassw0rd -keystore flashcardwebApiEncryptionKey.jks -storepass flashcardwebStorepassPassw0rd


RabbitMQ

docker run --rm -it -p 15672:15672 -p 5672:5672 rabbitmq:3.13.4-management



Docker command on AWS EC2:

Install Docker on AWS EC2
sudo yum install docker
sudo service docker start
sudo usermod -a -G docker ec2-user



Build Config Server Docker Image

docker build --tag=flashcardweb-config-server --force-rm=true --platform linux/amd64 .


Tag Config Server Docker Image for Docker Hub

docker tag fddaf9e435a1 vincennlin/flashcardweb-config-server



Build Discovery Service Docker Image

docker build --tag=flashcardweb-discovery-service --force-rm=true --platform linux/amd64 .
docker tag 4db81302f588 vincennlin/flashcardweb-discovery-service
docker push vincennlin/flashcardweb-discovery-service
