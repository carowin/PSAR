#!/bin/bash
time=$(date +"%s")
./exec_mac.sh $1 $2 ppti-14-302-10 $time &
./exec_mac.sh $1 $2 ppti-14-302-13 $time &
./exec_mac.sh $1 $2 ppti-14-302-14 $time &
