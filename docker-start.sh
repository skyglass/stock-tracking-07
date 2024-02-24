docker-compose -f docker-app-compose.yml down
docker-compose down
docker-compose up -d

mvn clean install -DskipTests

cd customer-service
mvn spring-boot:build-image -DskipTests \
  -Dspring-boot.build-image.imageName=customer-service

cd ../inventory-service
mvn spring-boot:build-image -DskipTests \
  -Dspring-boot.build-image.imageName=inventory-service

cd ../order-service
mvn spring-boot:build-image -DskipTests \
  -Dspring-boot.build-image.imageName=order-service

cd ../gateway-service
mvn spring-boot:build-image -DskipTests \
  -Dspring-boot.build-image.imageName=gateway-service

cd ../

docker-compose -f docker-app-compose.yml up -d