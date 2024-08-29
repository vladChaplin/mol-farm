#!/usr/bin/env bash

mvn package -D maven.test.skip

echo 'Copy files...'

scp -i ~/.ssh/id_rsa \
  target/web-0.0.1-SNAPSHOT.jar \
  root@194.87.101.45:/root/mol_farm_project

echo 'Restart server...'

scp -i ~/.ssh/id_rsa root@194.87.101.45 << EOF

pgrep java | xargs kill -9
nohup java -jar mol_farm_project/web-0.0.1-SNAPSHOT.jar > log.txt &

EOF

echo 'Bye'