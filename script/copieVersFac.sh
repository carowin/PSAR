#!/bin/bash


expect << END
spawn scp -r $1 $1@ssh.ufr-info-p6.jussieu.fr:
expect "$1@ssh.ufr-info-p6.jussieu.fr's password: "
send -- "$2\r"
expect eof
