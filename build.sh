#!/bin/bash

# Archetype Project Create
sh mvnw -Dmaven.test.skip=true -Pevent clean install

cd hotdeals-event-management
