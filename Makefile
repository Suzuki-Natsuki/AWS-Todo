# Makefile

IMAGE=todoapp

image-build: backend-build
	cd server; docker build -t $(IMAGE) .

backend-build: frontend-build backend-clean
	cd server; ./gradlew build -x test; # backendのtestをスキップ（GitHub actionsで実行）

backend-clean:
	cd server; rm -rf build

frontend-build: frontend-clean frontend-npm-install
	cd frontend; npm run build;
	mv frontend/dist server/src/main/resources/static

frontend-clean:
	cd frontend; rm -rf dist;
	cd server/src/main/resources; rm -rf static

frontend-npm-install:
	cd frontend; npm i;
