#!/bin/bash

SCRIPT="cd PSAR_exec; javac *.java; java Trace"

expect << END
#------------------------CONNECTION TO SERVER------------------------
spawn sshpass -p $2 ssh -tt $1@ssh.ufr-info-p6.jussieu.fr
send -- "ssh -tt $1@$3\r"
expect "Enter passphrase for key */users/Etu5/3520765/.ssh/id_rsa*: "
send -- "\r"
expect "*?assword:"
send -- "$2\r"

sleep 3

#--------------------------EXECUTE PROGRAM---------------------------
send "java -cp UDPSocketTest2.jar MainTrace $4\r"
expect eof
END
