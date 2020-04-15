#This script connect to each site and copy the logfile
#in our local

#$1=username

#!/bin/bash

while read line
do
  url=$(echo $line | cut -f 1 -d ' ')
  filname=file$(echo $line | cut -f 1 -d ' ').txt
  echo $filename
  expect << END
  spawn scp $1@ssh.ufr-info-p6.jussieu.fr:PSAR_exec/$filname /Users/CAROO/PSAR/datas
  expect "3520765@ssh.ufr-info-p6.jussieu.fr's password: "
  send -- "$2\r"
  expect eof
END
done < configFile.txt
