package Database;

import miniprojekti.Viite;
import java.util.ArrayList;

public class MockDatabase implements Database {
    
    /**
     * Viitetietokantataulukko.
     */
    private ArrayList<Viite> db;
    /**
     * Pitää kirjaa lisättävän viitteen indeksistä.
     */
    int counter;
    
    
    /**
     * Luo tyhjän tietokantaolion.
     */
    public MockDatabase() {
        db = new ArrayList<Viite>();
        counter = 0;
    }
       
    /**
     * Lisää viitteen tietokantaan.
     * @param viite 
     */
    @Override
    public void insertEntry(Viite viite) {
        db.add(counter, viite);
        counter++;
    }
    
    /**
     * Hakee viitteen tietokannasta.
     * @param i Viitteen tietokantaindeksi
     * @return Palauttaa viitteen.
     */
    @Override
    public Viite getEntry(int i) {
        return db.get(i);
    }
    
    /**
     * Poistaa viitteen tietokannasta.
     * @param i Viitteen tietokantaindeksi
     */
    @Override
    public void removeEntry(int i) {
        db.remove(i);
    }
    
    /**
     * Palauttaa tietokannassa olevien viitteiden lukumäärän.
     * @return Tietokannan koko
     */
    @Override
    public int getSize() {
        return db.size();
    }
    
}
