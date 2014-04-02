
package Engine;

import Database.Database;
import domain.ArtikkeliViite;
import domain.Kentta;
import domain.Syotetarkastaja;
import domain.Viite;
import java.util.ArrayList;

public class EngineStub implements IEngine {
    
    private Database db;
    
    
    public EngineStub(Database database) {
        db = database;
    }

    
    @Override
    public ArrayList<String> lisaaArticle(String citationKey, String author, String title, 
            String journal, int volume, int number, int year, int page1, 
            int page2) {
        
        Syotetarkastaja tarkastaja = new Syotetarkastaja();
        ArtikkeliViite viite = new ArtikkeliViite(tarkastaja);
        //Lisätään arvot uuteen viite-olioon, joka tarkastaa ne samalla
        viite.lisaaViiteavain(citationKey);
        viite.lisaaKentta(Kentta.author, author);
        viite.lisaaKentta(Kentta.title, title);
        viite.lisaaKentta(Kentta.journal, journal);
        viite.lisaaKentta(Kentta.volume, Integer.toString(volume));
        viite.lisaaKentta(Kentta.number, Integer.toString(number));
        viite.lisaaKentta(Kentta.year, Integer.toString(year));
        viite.lisaaKentta(Kentta.pages, Integer.toString(page1)+ "-"+ Integer.toString(page2));
        
        if (tarkastaja.getVirheet().isEmpty() && kaikkiPakollisetKentat(viite)) {
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
    
    public boolean kaikkiPakollisetKentat(Viite viite) {
        for (Kentta kentta : viite.getPakollisetKentat()) {
            if(!viite.kaytossaOlevatKentat().contains(kentta)) {
                return false;
            }
        }
        return true;
    }
    
}
