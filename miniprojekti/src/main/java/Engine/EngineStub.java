package Engine;

import Avaingenerointi.Avaingeneraattori;
import Database.Database;
import Syotetarkistus.Syotetarkastaja;
import domain.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class EngineStub implements IEngine {

    private Database db;
    private Avaingeneraattori ag;

    public EngineStub(Database database) {
        db = database;
        ag = new Avaingeneraattori(db);
    }

    @Override
    public ArrayList<String> lisaaViite(Viitetyyppi tyyppi, Map arvot) {

        Viite lisattava;
        Syotetarkastaja tarkastaja = new Syotetarkastaja();

        if (tyyppi == Viitetyyppi.article) {
            lisattava = new Article(tarkastaja);

        } else if (tyyppi == Viitetyyppi.book) {
            lisattava = new Book(tarkastaja);

        } else if (tyyppi == Viitetyyppi.inproceedings) {
            lisattava = new InProceedings(tarkastaja);

        } else {
            return null;
        }
        
        lisattava.lisaaKentat(arvot);
            
        if (tarkastaja.getVirheet().isEmpty() && lisattava.kenttaMaarittelyVirheet().isEmpty()) {
            if (lisattava.getViiteavain()==null) lisattava.lisaaViiteavain(ag.luoAvain(lisattava));
            
            try {
                db.insertEntry(lisattava);
            } catch (Exception e) { }
                
            return null;
        }
        ArrayList<String> virheet = tarkastaja.getVirheet();
        virheet.addAll(lisattava.kenttaMaarittelyVirheet());

        //jos oli virheitä palautetaan ne
        return virheet;
    }

    @Override
    public String[] listaaKaikkiViitteet() {
        ArrayList<Viite> sisalto = db.getAllEntries();

        String[] viitetaulukko = new String[sisalto.size()];
            for (int i = 0; i < sisalto.size(); i++) {
                viitetaulukko[i] = sisalto.get(i).toString();
            }
        
        return viitetaulukko;
    }

    @Override
    public void poistaViite(int i) {
        if (i >= 0 && i < db.getSize()) {
            db.removeEntry("");
        }
    }

    @Override
    public Viitetyyppi[] getViitetyypit() {
        return Viitetyyppi.values();
    }

    @Override
    public Set<Kentta> getPakollisetKentat(Viitetyyppi tyyppi) {
        if (tyyppi == Viitetyyppi.article) {
            return Article.getPakollisetKentat();

        } else if (tyyppi == Viitetyyppi.book) {
            return Book.getPakollisetKentat();

        } else if (tyyppi == Viitetyyppi.inproceedings) {
            return InProceedings.getPakollisetKentat();
        }
        return null;
    }

    @Override
    public Set<Kentta> getEiPakollisetKentat(Viitetyyppi tyyppi) {
        if (tyyppi == Viitetyyppi.article) {
            return Article.getEiPakollisetKentat();

        } else if (tyyppi == Viitetyyppi.book) {
            return Book.getEiPakollisetKentat();

        } else if (tyyppi == Viitetyyppi.inproceedings) {
            return InProceedings.getEiPakollisetKentat();
        }
        return null;
    }

    @Override
    public ArrayList<Viite> getViitteet() {
        return db.getAllEntries();
    }
}
