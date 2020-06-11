#connecect and execute to all file in config file

#!/bin/bash

time=$(date +"%s")

./launch.sh merkur.planetlab.haw-hamburg.de $time &
./launch.sh ple44.planet-lab.eu $time &
./launch.sh planetlab2.u-strasbg.fr $time &
./launch.sh planet1.elte.hu $time &

sleep 30
