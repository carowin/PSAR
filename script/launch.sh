#!/bin/bash

expect << END
#------------------------CONNECTION TO SERVER------------------------
spawn ssh -l upmc_pierre -i publique $1
send -- "sleep 3\r"
#--------------------------EXECUTE PROGRAM---------------------------
send -- "java -Djava.net.preferIPv4Stack=true -cp UDPSocketTest2.jar MainTrace $2\r"
send -- "sleep 20\r"
expect eof
END
