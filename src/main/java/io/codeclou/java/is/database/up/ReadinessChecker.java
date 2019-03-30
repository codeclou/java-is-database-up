package io.codeclou.java.is.database.up;

import org.apache.commons.cli.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ReadinessChecker {

    private CommandLineParser parser = new DefaultParser();
    private Options options = new Options();
    private Boolean hasCmdLineParameterErrors = false;

    protected void run(String[] args) throws Exception {
        //
        // Pull in drivers
        //
        Class.forName("org.postgresql.Driver");
        Class.forName("com.mysql.cj.jdbc.Driver");
        //
        // Options
        //
        options.addOption("j", "jdbc", true, "the jdbc url e.g.: jdbc:postgresql://localhost/test");
        options.addOption("u", "user", true, "the database username");
        options.addOption("p", "password", true, "the database password");
        options.addOption("s", "ssl", true, "use ssl true/false. default true.");
        options.addOption("w", "maxwait", true, "how long should we wait until we exit with error in minutes.");
        options.addOption("v", "verbose", false, "if specified a lot of info is printed.");
        CommandLine cmd = this.parser.parse(options, args);
        System.out.println("\033[35m+-------------------------+\033[0m");
        System.out.println("\033[35m|   Java Is Database Up   |\033[0m");
        System.out.println("\033[35m+-------------------------+\033[0m");

        if (!cmd.hasOption("jdbc")) {
            System.out.println("\033[31mError >> Please specify connection with -j\033[0m");
            hasCmdLineParameterErrors = true;
        }
        if (!cmd.hasOption("user")) {
            System.out.println("\033[31mError >> Please specify database user with -u\033[0m");
            hasCmdLineParameterErrors = true;
        }
        if (!cmd.hasOption("password")) {
            System.out.println("\033[31mError >> Please specify database password with -p\033[0m");
            hasCmdLineParameterErrors = true;
        }
        if (!cmd.hasOption("ssl")) {
            System.out.println("\033[31mError >> Please specify database connection should use ssl with -s\033[0m");
            hasCmdLineParameterErrors = true;
        }
        if (!cmd.hasOption("maxwait")) {
            System.out.println("\033[31mError >> Please specify maxwait in minutes with -w\033[0m");
            hasCmdLineParameterErrors = true;
        }
        if (!hasCmdLineParameterErrors) {
            //
            // CONNECTION PROPS
            //
            String url = cmd.getOptionValue("jdbc");
            Properties props = new Properties();
            props.setProperty("user", cmd.getOptionValue("user"));
            props.setProperty("password", cmd.getOptionValue("password"));
            props.setProperty("ssl", cmd.getOptionValue("ssl"));
            Integer maxWaitMinutes = Integer.parseInt(cmd.getOptionValue("maxwait"));
            System.out.println("\033[36mTrying  >> to connect to db " + url + " and waiting for max " + maxWaitMinutes + "min. \033[0m");
            for (int i=0; i < maxWaitMinutes * 2; i++) {
                //
                // TRY CONNECTION
                //
                try {
                    Connection conn = DriverManager.getConnection(url, props);
                    System.out.println("\033[32mSuccess >> database is up\033[0m");
                    System.exit(0);
                } catch (Exception e) {
                    if (cmd.hasOption("verbose")) {
                        System.out.println("  .  " + e.getMessage());
                    } else {
                        System.out.println("  .");
                    }
                    Thread.sleep(30000); // sleep 30s
                }
            }
            //
            // FINALLY (after maxwait reached)
            //
            System.out.println("\033[31mError >> Could not connect to db. Max wait reached. Exit.\033[0m");
            System.exit(1);
        }
    }
}
