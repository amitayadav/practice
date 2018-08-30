unzip seed-1.0-SNAPSHOT.zip
ps -ef | grep seed-1.0-SNAPSHOT | grep -v grep | awk '{print $2}' | xargs kill
./seed-1.0-SNAPSHOT/bin/seed &
rm -r *


