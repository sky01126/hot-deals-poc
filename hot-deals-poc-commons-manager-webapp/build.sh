#!/bin/bash

mvn dockerfile:build
docker images
