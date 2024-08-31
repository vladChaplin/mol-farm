#!/usr/bin/env bash

mvn package -D maven.test.skip

echo 'Copy files...'

scp -i ~/.ssh/id_rsa \
  target/web-0.0.1-SNAPSHOT.jar \
  farm-mol-admin@194.87.101.45:/home/farm-mol-admin/mol-farm-project

echo 'Restart server...'

scp -i ~/.ssh/id_rsa farm-mol-admin@194.87.101.45 << EOF

pgrep java | xargs kill -9
nohup java -jar /home/farm-mol-admin/mol-farm-project/web-0.0.1-SNAPSHOT.jar > log.txt &

EOF

echo 'Bye'