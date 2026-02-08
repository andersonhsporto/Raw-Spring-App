#!/bin/bash

PROFILE=${1:-dev}
TOMCAT_HOME=/home/${USER}/jboss/apache-tomcat-11.0.18
PROJECT_DIR=/home/${USER}/documentos-2/raw-spring-app

echo "===================================="
echo "Building and Deploying with profile: $PROFILE"
echo "===================================="

cd "$PROJECT_DIR" || exit

echo "1. Building application..."
mvn clean package -P$PROFILE -DskipTests

if [ $? -ne 0 ]; then
    echo "Build failed!"
    exit 1
fi

echo "2. Stopping Tomcat if running..."
"$TOMCAT_HOME"/bin/shutdown.sh 2>/dev/null || true
sleep 3

echo "3. Cleaning old deployment..."
rm -rf "$TOMCAT_HOME"/webapps/raw-spring-app-application
rm -f "$TOMCAT_HOME"/webapps/raw-spring-app-application.war

echo "4. Deploying new WAR..."
cp application/target/raw-spring-app-application.war $TOMCAT_HOME/webapps/

echo "5. Setting environment variables..."
if [ "$PROFILE" = "prod" ]; then
    export SPRING_PROFILES_ACTIVE=prod
    export DB_URL=jdbc:mysql://localhost:3306/app_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
    export DB_USERNAME=app_user
    export DB_PASSWORD=app_pass
else
    export SPRING_PROFILES_ACTIVE=dev
    export DB_URL=jdbc:mysql://localhost:3306/app_db_dev?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
    export DB_USERNAME=app_user
    export DB_PASSWORD=app_pass
fi

echo ""
echo "===================================="
echo "Deployment completed!"
echo "Profile: $SPRING_PROFILES_ACTIVE"
echo ""
echo "To start Tomcat, run:"
echo "  ./start-tomcat-$PROFILE.sh"
echo ""
echo "Or start manually:"
echo "  SPRING_PROFILES_ACTIVE=$PROFILE $TOMCAT_HOME/bin/startup.sh"
echo ""
echo "Access the API at:"
echo "  http://localhost:8080/raw-spring-app-application/api/rooms"
echo "===================================="
