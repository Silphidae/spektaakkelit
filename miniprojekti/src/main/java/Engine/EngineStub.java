
package Engine;

import Database.Database;
import java.util.ArrayList;
import miniprojekti.ArtikkeliViite;
import miniprojekti.Syotetarkastaja;

public class EngineStub implements IEngine {
    
    private Database db;
    
    
    public EngineStub(Database database) {
        db = database;
    }

    
    @Override
    public ArrayList<String> lisaaArticle(String citationKey, String author, String title, 
            String journal, int volume, int number, int year, int page1, 
            int page2, String publisher, String address) {
        
        Syotetarkastaja tarkastaja = new Syotetarkastaja();
        ArtikkeliViite viite = new ArtikkeliViite(tarkastaja);
        //Lisätään arvot uuteen viite-olioon, joka tarkastaa ne samalla
        viite.lisaaCitationKey(citationKey);
        viite.lisaaAuthor(author);
        viite.lisaaTitle(title);
        viite.lisaaJournal(journal);
        viite.lisaaVolume(volume);
        viite.lisaaNumber(number);
        viite.lisaaYear(year);
        viite.lisaaPage1Page2(page1, page2);
        viite.lisaaPublisher(publisher);
        viite.lisaaAddress(address);
        
        if (tarkastaja.getVirheet().isEmpty()) {
            db.insertEntry(viite);
            return null;
        }
        
        //jos oli virheitä palautetaan ne
        return tarkastaja.getVirheet();
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
