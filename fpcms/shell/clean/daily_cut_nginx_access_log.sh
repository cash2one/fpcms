#!/bin/bash

source /etc/profile

day=`date -d '-1 day'  +%Y-%m-%d`
mv /data/log/nginx/access.log /data/log/nginx/access.log.${day}
mv /data/log/nginx/error.log /data/log/nginx/error.log.${day}

# send sign for renew access log
kill -USR1 `cat /var/run/nginx.pid`

