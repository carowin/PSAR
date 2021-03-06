#!/bin/bash

while read line
do
  url=$(echo $line | cut -f 2 -d ' ')
  filname=logFile$(echo $line | cut -f 1 -d ' ').txt
  echo $filename
  expect << END
  spawn scp -i publique upmc_pierre@$url:$filname /Users/CAROO/PSAR/datas/tracesPlanetLab5min
  expect eof
END
done < configFile.txt
