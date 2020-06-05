#This script will connect in ssh on each site
#and then execute the program

# $1=username $2=pwd or use ssh-keygen

#!/bin/bash

SCRIPT="cd PSAR_exec; javac *.java; java Trace"

while read line
do
  url=$(echo $line | cut -f 2 -d ' ')
  expect << END
  #------------------------CONNECTION TO SERVER------------------------
  spawn sshpass -p $2 ssh -tt -p 22 $1@ssh.ufr-info-p6.jussieu.fr
  send -- "ssh -tt $1@$url\r"
  expect "Enter passphrase for key */users/Etu5/3520765/.ssh/id_rsa*: "
  send -- "\r"
  expect "*?assword:"
  send -- "$2\r"

  sleep 5
  
  #--------------------------EXECUTE PROGRAM---------------------------
  send "java -cp UDPSocketTest2.jar MainTrace 7070"

  sleep 10
  send "exit\r"
  expect eof
END
done < configFile.txt
