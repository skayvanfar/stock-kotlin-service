#!/bin/bash

# Clean and package the project
./mvnw clean package

# Build a Docker image with the tag "stock-service"
docker build -t stock-service .
