#!/bin/bash


expect << END
spawn scp -r $1 3520765@ssh.ufr-info-p6.jussieu.fr:
expect "3520765@ssh.ufr-info-p6.jussieu.fr's password: "
send -- "q#4TBE3#9\r"
expect eof
