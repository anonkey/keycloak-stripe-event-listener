
install:
	mvn install

make:
	mvn clean package

docker-up:
	docker-compose -f src/main/docker/docker-compose.yml up -d

docker-down:
	docker-compose -f src/main/docker/docker-compose.yml down
