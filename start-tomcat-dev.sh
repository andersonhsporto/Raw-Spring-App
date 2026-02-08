#!/bin/bash

export SPRING_PROFILES_ACTIVE=dev
export DB_URL=jdbc:mysql://localhost:3306/app_db_dev?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
export DB_USERNAME=app_user
export DB_PASSWORD=app_pass

echo "===================================="
echo "Starting Tomcat with Spring Profile: $SPRING_PROFILES_ACTIVE"
echo "Database URL: $DB_URL"
echo "===================================="

/home/${USER}/jboss/apache-tomcat-11.0.18/bin/catalina.sh run
