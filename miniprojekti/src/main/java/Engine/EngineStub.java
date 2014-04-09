package Engine;

import AvaimenGenerointi.Avaingeneraattori;
import Database.Database;
import Syotetarkistus.Syotetarkastaja;
import domain.Article;
import domain.Book;
import domain.InProceedings;
import domain.Kentta;
import domain.Viite;
import domain.Viitetyyppi;
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
            lisattava.lisaaViiteavain(ag.luoAvain(lisattava));
            db.insertEntry(lisattava);
            return null;
        }
        ArrayList<String> virheet = tarkastaja.getVirheet();
        virheet.addAll(lisattava.kenttaMaarittelyVirheet());

        //jos oli virheitä palautetaan ne
        return virheet;
    }

    @Override
    public String[] listaaKaikkiViitteet() {
        String[] sisalto = new String[db.getSize()];

        int i = 0;
        while (i < db.getSize()) {
            sisalto[i] = db.getEntry(i).toString();
            i++;
        }

        return sisalto;
    }

    @Override
    public void poistaViite(int i) {
        if (i >= 0 && i < db.getSize()) {
            db.removeEntry(i);
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
        ArrayList<Viite> viitteet = new ArrayList<>();
        int i = 0;
        while (i < db.getSize()) {
            viitteet.add(db.getEntry(i));
            i++;
        }
        return viitteet;
    }
}
