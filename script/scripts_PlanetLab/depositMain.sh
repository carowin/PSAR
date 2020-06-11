#!/bin/bash

while read line
do
  url=$(echo $line | cut -f 2 -d ' ')
  echo $url
  scp -i publique -r ../../../UDPSocketTest2.jar upmc_pierre@$url:
done < configFile.txt
