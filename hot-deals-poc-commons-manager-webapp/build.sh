#!/bin/bash

../mvnw dockerfile:build
docker images
