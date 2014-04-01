
package Engine;

import Database.Database;
import java.util.ArrayList;
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
    
    @Override
    public ArrayList<String> listaaKaikkiViitteet() {
        ArrayList<String> sisalto = new ArrayList<String>();
        
        int i = 0;
        while (i<db.getSize()){
            System.out.println(db.getEntry(i).toString());
            sisalto.add(db.getEntry(i).toString());
            i++;
        }
        
        return sisalto;
    }
    
}
