#This script will connect in ssh on each site
#then the script will put the java program
#into the site and execute it

# $1=username $2=pwd or use ssh-keygen

#!/bin/bash

SCRIPT="cd PSAR_exec; javac *.java; java Trace"

while read line
do
  url=$(echo $line | cut -f 2 -d ' ')
  #scp -r ../src $1@ssh.ufr-info-p6.jussieu.fr:PSAR_exec
  expect << END
  #------------------------CONNECTION TO SERVER------------------------
  spawn sshpass -p $2 ssh -tt -p 22 $1@ssh.ufr-info-p6.jussieu.fr
  #scp $1@ssh.ufr-info-p6.jussieu.fr:$filname /Users/CAROO/PSAR/datas
  send -- "ssh -tt $1@$url\r"
  expect "Enter passphrase for key */users/Etu5/3520765/.ssh/id_rsa*: "
  send -- "\r"
  expect "*?assword:"
  send -- "$2\r"

  sleep 5
  send "cd PSAR_exec\r"
  send "javac *.java\r"
  send "java Trace\r"

  sleep 10
  send "exit\r"
  expect eof
END
done < configFile.txt
