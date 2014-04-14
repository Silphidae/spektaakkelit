package domain;

import Database.TietokantaYhteys;
import Syotetarkistus.Syotetarkastaja;
import static domain.Kentta.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.naming.NamingException;

public abstract class Viite {

    protected Map<Kentta, String> kentat;
    // protected static Set<Kentta> pakollisetKentat;
    protected Set<Kentta> sallitutKentat;
    protected String viiteavain;
    protected Syotetarkastaja tarkastaja;
    int pkey;

    public void lisaaViiteavain(String viiteavain) {
        if (tarkastaja.tarkastaCitationKey(viiteavain)) {
            this.viiteavain = viiteavain;
        }
    }

    public String getViiteavain() {
        return viiteavain;
    }

    public void lisaaKentat(Map<Kentta, String> arvot) {
        for (Kentta kentta : arvot.keySet()) {
            lisaaKentta(kentta, arvot.get(kentta));
        }
    }

    public void lisaaKentta(Kentta kentta, String syote) {
        if (sallitutKentat.contains(kentta) && !kaytossaOlevatKentat().contains(kentta)) {
            if (tarkastaja.tarkasta(kentta, syote)) {
                kentat.put(kentta, syote);
            }
        }
    }

    public void muokkaaKenttaa(Kentta kentta, String syote) {
        if (kaytossaOlevatKentat().contains(kentta)) {
            if (tarkastaja.tarkasta(kentta, syote)) {
                kentat.put(kentta, syote);
            }
        }
    }

    public void poistaKentta(Kentta kentta) {
        kentat.remove(kentta);
    }

    public String getKentanSisalto(Kentta kentta) {
        return kentat.get(kentta);
    }

    public Set<Kentta> kaytossaOlevatKentat() {
        return kentat.keySet();
    }

    /**
     * Metodi tarkastaa onko viitteelle määritelty kaikki sen pakolliset kentät.
     *
     * @return Lista virheistä , jos niitä on.
     */
    public abstract List<String> kenttaMaarittelyVirheet();

    public abstract String getTyyppi();

    
}
