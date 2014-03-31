package Database;

import miniprojekti.Viite;

/**
 * Rajapintaluokka tietokannalle
 */
public interface Database {
    
    public void insertEntry(Viite viite);
    public Viite getEntry(int i);
    public void removeEntry(int i);
}
