:mega: **End of Lifetime Notice**: We no longer provide support for this repository. It will be made readonly, you can still fork it and adapt it to your needs.

----

# java-is-database-up

Is database ready for connections? Check PostgreSQL and MySQL databases if they accept connections.

&nbsp;

<p align="center"><img src"=width="80%" src="https://codeclou.github.io/java-is-database-up/img/demo.gif" /></p>

&nbsp;

### Is this for me?

You are starting maybe dockerized MySQL or PostgreSQL databases and they need some time
until they are ready and accept connections?
You want to busy wait for the database to accept connections?
Then this is for you.

---

&nbsp;

### Usage

Simply download the jar file and use it to wait for a database to accept connections like so and wait max 10 minutes.
Currently supported database are PostgreSQL and MySQL.

```
# INSTALL
curl -L -o is-database-up.jar \
     https://github.com/codeclou/java-is-database-up/releases/download/1.0.0/is-database-up.jar

# RUN
java -jar is-database-up.jar \
     -j "jdbc:postgresql://myhostname/mydb" \
     -u admin \
     -p myS3cr3t \
     -s true \
     -w 10
```

You can specify `-v` to see more information printed out.

---

&nbsp;

### License

[MIT](https://github.com/codeclou/java-is-database-up/blob/master/LICENSE) © [Bernhard Grünewaldt](https://github.com/clouless)
