#This script will execute commands to do in the
#server site

#!/bin/bash

ssh -tt $1@ppti-14-302-11'
expect "Enter passphrase for key */users/Etu5/3520765/.ssh/id_rsa*: ";
send -- "\r";

expect "*?assword:";
send -- $2;
send -- "\r";

expect eof'
