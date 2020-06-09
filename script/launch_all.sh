#connecect and execute to all file in config file

#!/bin/bash

time=$(date +"%s")
while read line
do
  url=$(echo $line | cut -f 2 -d ' ')
  ./launch.sh $url $time &
done < configFile.txt
wait
