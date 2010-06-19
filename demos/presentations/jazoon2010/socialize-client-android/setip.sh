#!/bin/bash

IP=`/sbin/ifconfig | grep -o 'inet addr:[^ ]*' | sed 's/inet addr://' | grep -v '127.0.0.1' | tail -1`
sed -i "s;<string name=\"server_addr\">.*</string>;<string name=\"server_addr\">$IP</string>;" res/values/strings.xml
cat res/values/strings.xml

exit 0
