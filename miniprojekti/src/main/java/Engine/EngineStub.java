package Engine;

import Avaingenerointi.Avaingeneraattori;
import Database.Database;
import Syotetarkistus.Syotetarkastaja;
import domain.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import javax.naming.NamingException;

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
            if (lisattava.getCitationKey() == null) {
                lisattava.lisaaCitationKey(ag.luoAvain(lisattava));
            }

            try {
                db.insertEntry(lisattava);
            } catch (SQLException | NamingException e) {
                if (true) {
                    String asd = "asd";
                }
            }

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
    public void poistaViite(String ckey) {
        db.removeEntry(ckey);
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

    @Override
    public EnumMap<Kentta, String> getKentat(String ckey) {
        Viite viite = db.getEntry(ckey);
        if (viite == null) {
            return null;
        }

        Set<Kentta> kentat = viite.kaytossaOlevatKentat();
        EnumMap<Kentta, String> viitteenKentat = new EnumMap<Kentta, String>(Kentta.class);

        for (Kentta kentta : kentat) {
            viitteenKentat.put(kentta, viite.getKentanSisalto(kentta));
        }

        return viitteenKentat;
    }

    @Override
    public Viitetyyppi getViitetyyppi(String ckey) {
        Viite viite = db.getEntry(ckey);
        if (viite != null) {
            String tyyppi = viite.getTyyppi();
            for (Viitetyyppi viitetyyppi : Viitetyyppi.values()) {
                if (viitetyyppi.toString().equals(tyyppi)) {
                    return viitetyyppi;
                }
            }
        }
        return null;
    }

    @Override
    public ArrayList<Viite> listByTag(String tag) {
        return db.listByTag(tag);
    }

    @Override
    public void addTagi(String ckey, String tagi) {
        try {
            db.addTag(ckey, tagi);
        } catch (NamingException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public ArrayList<String> getTagsByViite(String ckey) {
        return db.getTagsByViite(ckey);
    }

    @Override
    public void removeTagi(String ckey, String tagi) {
        db.removeTagFromViite(ckey, tagi);
    }

    @Override
    public ArrayList<String> getTagit() {
        return db.getTagit();
    }
}
