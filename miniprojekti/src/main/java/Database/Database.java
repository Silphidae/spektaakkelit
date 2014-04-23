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

    public ArrayList<Viite> getAllEntries();

    public Viite getEntry(String ckey);

    public void addTag(String ckey, String tag) throws NamingException, SQLException;

    public ArrayList<Viite> listByTag(String tag);
    
    public ArrayList<String> getTagsByViite(String ckey);
}
