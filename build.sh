#!/bin/bash

# Exit on error
set -e

## OS를 확인한다.
export OS='unknown'
if [[ "$(uname)" == "Darwin" ]]; then
    OS="darwin"
elif [[ "$(expr substr $(uname -s) 1 5)" == "Linux" ]]; then
    OS="linux"
fi

# Archetype Project Create
mvn -Dmaven.test.skip=false clean install

#mvn dockerfile:build
#docker images
