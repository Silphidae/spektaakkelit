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

    public void lisaaViiteKantaan() throws NamingException, SQLException {
        String sql = "INSERT INTO viitteet(key, author, title, journal, year, volume, number, pages, month, note, editor, publisher, series, address, edition, booktitle, organization, type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
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
        kysely.setString(18, this.getTyyppi());

        ResultSet syotto = kysely.executeQuery();
        syotto.next();

        try {
            syotto.close();
        } catch (Exception e) {
        }
        try {
            kysely.close();
        } catch (Exception e) {
        }
        try {
            yhteys.close();
        } catch (Exception e) {
        }
    }

    public static String[] haeKaikkiViitteetKannasta() {
        try {
            Connection yhteys = TietokantaYhteys.getYhteys(); //Haetaan yhteysolio
            PreparedStatement kysely;
            ResultSet tulokset;

            //Alustetaan muuttuja jossa on Select-kysely, joka palauttaa lukuarvon:
            String sqlkysely = "SELECT * FROM viitteet;";

            kysely = yhteys.prepareStatement(sqlkysely);
            tulokset = kysely.executeQuery();

            ArrayList<Viite> viitteet = new ArrayList<Viite>();

            while (tulokset.next()) {
                EnumMap<Kentta, String> arvot = new EnumMap(Kentta.class);
                for (Kentta kentta : Kentta.values()) {
                    String arvo = tulokset.getString(kentta.toString());
                    if (arvo != null) {
                        arvot.put(kentta, arvo);
                    }

                }

                String tyyppi = tulokset.getString("type");
                Syotetarkastaja tarkastaja = new Syotetarkastaja();
                Viite lisattava = new Book(tarkastaja);

                if (tyyppi.equals("book")) {
                    lisattava = new Book(tarkastaja);
                } else if (tyyppi.equals("article")) {
                    lisattava = new Article(tarkastaja);
                } else if (tyyppi.equals("inproceedings")) {
                    lisattava = new InProceedings(tarkastaja);
                }

                lisattava.lisaaKentat(arvot);

                viitteet.add(lisattava);
            }

            tulokset.close();
            kysely.close();
            
            String[] viitetaulukko = new String[viitteet.size()];
            for (int i = 0; i < viitteet.size(); i++) {
                viitetaulukko[i] = viitteet.get(i).toString();
            }
            
            return viitetaulukko;
        } catch (Exception e) {
            System.out.println("Virhe: " + e.getMessage());
        }
        return null;
    }

    // poistaViiteKannasta
}
