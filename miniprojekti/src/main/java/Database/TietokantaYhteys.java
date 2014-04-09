package Database;


import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;


public class TietokantaYhteys {

    public static Connection getYhteys() throws NamingException, SQLException {
        //ssh -L 5432:localhost:5432 käyttäjätunnus@users.cs.helsinki.fi
        
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl("jdbc:postgresql://localhost/ottohant");
        ds.setUsername("korvaa käyttäjällä");
        ds.setPassword("korvaa salasanalla");
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setRemoveAbandoned(true);
        ds.setRemoveAbandonedTimeout(30);
        ds.setMaxActive(100);
        ds.setMaxIdle(30);
        ds.setMaxWait(1000);
        
        DataSource yhteysVarasto = ds;

        Connection yhteys = yhteysVarasto.getConnection();

        return yhteys;
    }

}
