#!/bin/bash

export SPRING_PROFILES_ACTIVE=prod
export DB_URL=jdbc:mysql://localhost:3306/app_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
export DB_USERNAME=app_user
export DB_PASSWORD=app_pass

echo "===================================="
echo "Starting Tomcat with Spring Profile: $SPRING_PROFILES_ACTIVE"
echo "Database URL: $DB_URL"
echo "===================================="

/home/anderson/jboss/apache-tomcat-11.0.18/bin/catalina.sh run
