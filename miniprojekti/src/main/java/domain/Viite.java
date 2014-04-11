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
        String sql = "INSERT INTO viitteet(key, author, title, journal, year, volume, number, pages, month, note, editor, publisher, series, address, edition, booktitle, organization) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        Connection yhteys = TietokantaYhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        
        kysely.setString(1, viiteavain);
        kysely.setString(2, getKentanSisalto(author));
        kysely.setString(3, getKentanSisalto(title));
        kysely.setString(4, getKentanSisalto(journal));
        kysely.setString(5, getKentanSisalto(year));
        kysely.setString(6, getKentanSisalto(volume));
        kysely.setString(7, getKentanSisalto(number));
        kysely.setString(8, getKentanSisalto(pages));
        kysely.setString(9, getKentanSisalto(month));
        kysely.setString(10, getKentanSisalto(note));
        kysely.setString(11, getKentanSisalto(editor));
        kysely.setString(12, getKentanSisalto(publisher));
        kysely.setString(13, getKentanSisalto(series));
        kysely.setString(14, getKentanSisalto(address));
        kysely.setString(15, getKentanSisalto(edition));
        kysely.setString(16, getKentanSisalto(booktitle));
        kysely.setString(17, getKentanSisalto(organization));

        ResultSet syotto = kysely.executeQuery(); 
        syotto.next();
        
        try { syotto.close(); } catch (Exception e) {}
        try { kysely.close(); } catch (Exception e) {}
        try { yhteys.close(); } catch (Exception e) {}
    }
    
    public void listaaViitteet() throws NamingException, SQLException  {
        Connection yhteys = TietokantaYhteys.getYhteys();
        PreparedStatement kysely;
        ResultSet tulokset;

        try {
            String sqlkysely = "SELECT * FROM viitteet;";

            kysely = yhteys.prepareStatement(sqlkysely);
            tulokset = kysely.executeQuery();
            if (tulokset.next()) {
                String tulos = tulokset.toString();
                System.out.println(tulos);
            } else {
                System.out.println("Virhe!");
            }

            tulokset.close();
            kysely.close();
            yhteys.close();
        } catch (Exception e) {
            System.out.println("Virhe: " + e.getMessage());
        }
    }
    
    // poistaViiteKannasta
    
}
