
package Engine;

import Database.Database;
import miniprojekti.ArtikkeliViite;

public class EngineStub implements IEngine {
    
    private Database db;
    
    
    public EngineStub(Database database) {
        db = database;
    }

    
    @Override
    public void lisaaArticle(String citationKey, String author, String title, 
            String journal, int volume, int number, int year, int page1, 
            int page2, String publisher, String address) {
        ArtikkeliViite viite = new ArtikkeliViite(citationKey, author, title,
            journal, volume, number, year, page1, page2, publisher, address);
        db.insertEntry(viite);
    }

    
}
