#!/bin/bash

# Archetype Project Create
mvn -Dmaven.test.skip=true -Pprocess clean install

cd hot-deals-poc-process-manager-webapp
