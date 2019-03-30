# Development

### Build & Test

```
mvn package -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true
java -jar target/is-database-up.jar \
     -j "jdbc:postgresql://localhost:5441/test" \
     -u postgres \
     -p s3cr3t \
     -s true \
     -w 10
```

Now in another terminal start dockerized postgres
```
docker run -i -t --rm -p 5441:5432 \
       -e POSTGRES_PASSWORD=s3cr3t \
       -e POSTGRES_USER=postgres \
       -e POSTGRES_DB=test \
       postgres:9.5 \
       postgres -c 'log_statement=all'
```

### Testcoverage

[Run OpenClover](http://openclover.org/) with maven:

```bash
mvn clean clover:setup test clover:aggregate clover:clover
```

Now look into `target/site/clover/`
