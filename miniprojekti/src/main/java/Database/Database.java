package Database;

import domain.Viite;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.naming.NamingException;

/**
 * Rajapintaluokka tietokannalle
 */
public interface Database {
    
    public void insertEntry(Viite viite) throws NamingException, SQLException;
    public void removeEntry(String ckey);
    public int getSize();
    public ArrayList<Viite> getAllEntries();
    
}
