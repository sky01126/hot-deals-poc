#!/bin/bash

# Archetype Project Create
mvn -Dmaven.test.skip=true -Pevent clean install

cd hot-deals-poc-process-manager-webapp
