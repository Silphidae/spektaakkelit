package domain;

import Database.TietokantaYhteys;
import Syotetarkistus.Syotetarkastaja;
import static domain.Kentta.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
     * @return Lista virheistä , jos niitä on.
     */
    public abstract List<String> kenttaMaarittelyVirheet();
    
    public void lisaaViiteKantaan() throws NamingException, SQLException {
        String sql = "INSERT INTO viitteet(pkey, key, author, title, journal, year, "
                + "volume, number, pages, month, note, editor, publisher, series, "
                + "address, edition, booktitle, organization) VALUES (?, ?, ?, "
                + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        Connection yhteys = TietokantaYhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        
        kysely.setString(1, viiteavain);
        kysely.setString(2, getKentanSisalto(key));
        kysely.setString(3, getKentanSisalto(author));
        kysely.setString(4, getKentanSisalto(title));
        kysely.setString(5, getKentanSisalto(journal));
        kysely.setString(6, getKentanSisalto(year));
        kysely.setString(7, getKentanSisalto(volume));
        kysely.setString(8, getKentanSisalto(number));
        kysely.setString(9, getKentanSisalto(pages));
        kysely.setString(10, getKentanSisalto(month));
        kysely.setString(11, getKentanSisalto(note));
        kysely.setString(12, getKentanSisalto(editor));
        kysely.setString(13, getKentanSisalto(publisher));
        kysely.setString(14, getKentanSisalto(series));
        kysely.setString(15, getKentanSisalto(address));
        kysely.setString(16, getKentanSisalto(edition));
        kysely.setString(17, getKentanSisalto(booktitle));
        kysely.setString(18, getKentanSisalto(organization));

        ResultSet syotto = kysely.executeQuery(); 
        syotto.next();
        
        try { syotto.close(); } catch (Exception e) {}
        try { kysely.close(); } catch (Exception e) {}
        try { yhteys.close(); } catch (Exception e) {}
    }
    
    
    
}
