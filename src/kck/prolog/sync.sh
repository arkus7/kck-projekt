#!/bin/sh
rsync -chavz --partial --progress --stats kck2.pl kck.pl root@46.101.96.206:/root/
curl http://46.101.96.206:5000/reload
echo ""