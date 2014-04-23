package Database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

public class TietokantaYhteys {
    
    private static String asetustiedosto = "tietokanta.txt";
    private static String kayttaja = "";
    private static String salasana = "";

    public static Connection getYhteys() throws NamingException, SQLException {
        //ssh -L 5432:localhost:5432 käyttäjätunnus@users.cs.helsinki.fi

        File f = new File(asetustiedosto);
        if (f.exists()) {
            haeTunnukset();
        }

        BasicDataSource ds = new BasicDataSource();
        ds.setUrl("jdbc:postgresql://localhost/ottohant");
        ds.setUsername(kayttaja);
        ds.setPassword(salasana);
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

    private static void haeTunnukset() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(asetustiedosto));
            
            String line = br.readLine();    
            kayttaja = line;
            
            line = br.readLine();
            salasana = line;
            
            br.close();
        } catch (IOException iOException) {
        }
    }

}
