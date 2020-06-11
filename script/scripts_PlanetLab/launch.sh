#!/bin/bash

ssh -t -l upmc_pierre -i publique $1 "java -Djava.net.preferIPv4Stack=true -cp UDPSocketTest2.jar MainTrace $2"
