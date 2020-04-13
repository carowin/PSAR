#This script will connect in ssh on each site
#then the script will put the java program
#into the site and execute ir

# $1=username $2=pwd

#!/bin/bash

while read line
do
  url=$(echo $line | cut -f 2 -d ' ')
  sshpass -p $2 ssh -tt -p 22 $1@ssh.ufr-info-p6.jussieu.fr
  ssh -tt $1@$url
  expect "Enter passphrase for key '/users/Etu5/3520765/.ssh/id_rsa': "
  send -- "\r"

  expect "*?assword:"
  send -- $2
  send -- "\r"

  expect eof
done < configFile.txt
