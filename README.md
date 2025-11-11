# Clippy
Clippy is an online clipboard. It simplifies sharing unsecured data that needs to be shared.
## Requirements
|Requirement| Version |
|-----------|---------|
|Java|28.1.1, build 4eba377|
|Docker|28.1.1, build 4eba377
## Features
- Share text snippets across devices instantly
- Permanent, unsecured clipboard for testing or debugging
- Web-based — no installation required
- Docker support for easy deployment
## Running
[Deployment: clippy-e3nl.onrender](https://clippy-e3nl.onrender.com/)
## Screenshots
<img width="363" height="349" alt="image" src="https://github.com/user-attachments/assets/9bef1c54-a190-4b90-8f96-3546e9221f3c" /><br>
*Figure 1: Login Page*<br>
<img width="797" height="400" alt="image" src="https://github.com/user-attachments/assets/e2fca3fe-6ea9-47cf-b886-6b8b2b230ebc" /><br>
*Figure 2: Dashboard*
## Clone
```
git clone https://github.com/sambhavmahajan/clippy.git
```
## Configuration
Create a .evn file in root or add environment variables
```
DB_URL=
DB_USERNAME=
DB_PASSWORD=
PORT=
MAX_POOL=5
MIN_IDLE=2
CONN_TIMEOUT=3000
FAIL_TIMEOUT=3000
```

## Build jar file
```
mvn clean package -DskipTests
```
Place the compiled jar file as app.jar in root.

## Building Docker image
```
Docker build -t clippy .
```
## Running Docker image
```
docker run --env-file .env -it -p 8080:[PORT] clippy
```
