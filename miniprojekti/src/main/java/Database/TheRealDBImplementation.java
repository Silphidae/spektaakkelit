package Database;

import Syotetarkistus.Syotetarkastaja;
import domain.Article;
import domain.Book;
import domain.InProceedings;
import domain.Kentta;
import static domain.Kentta.*;
import domain.Viite;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EnumMap;
import javax.naming.NamingException;

public class TheRealDBImplementation implements Database {
    
    @Override
    public void insertEntry(Viite viite) throws NamingException, SQLException {
        String sql = "INSERT INTO viitteet(key, author, title, journal, year, volume, number, pages, month, note, editor, publisher, series, address, edition, booktitle, organization, type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        Connection yhteys = TietokantaYhteys.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);

        kysely.setString(1, viite.getCitationKey());
        kysely.setString(2, viite.getKentanSisalto(author));
        kysely.setString(3, viite.getKentanSisalto(title));
        kysely.setString(4, viite.getKentanSisalto(journal));
        kysely.setString(5, viite.getKentanSisalto(year));
        kysely.setString(6, viite.getKentanSisalto(volume));
        kysely.setString(7, viite.getKentanSisalto(number));
        kysely.setString(8, viite.getKentanSisalto(pages));
        kysely.setString(9, viite.getKentanSisalto(month));
        kysely.setString(10, viite.getKentanSisalto(note));
        kysely.setString(11, viite.getKentanSisalto(editor));
        kysely.setString(12, viite.getKentanSisalto(publisher));
        kysely.setString(13, viite.getKentanSisalto(series));
        kysely.setString(14, viite.getKentanSisalto(address));
        kysely.setString(15, viite.getKentanSisalto(edition));
        kysely.setString(16, viite.getKentanSisalto(booktitle));
        kysely.setString(17, viite.getKentanSisalto(organization));
        kysely.setString(18, viite.getTyyppi());

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
    
    
    @Override
    public ArrayList<Viite> getAllEntries() {
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
                lisattava.lisaaViiteavain(tulokset.getString("ckey"));

                viitteet.add(lisattava);
            }

            tulokset.close();
            kysely.close();
            
            return viitteet;
        } catch (Exception e) {
            System.out.println("Virhe: " + e.getMessage());
        }
        return null;
    }
    
    public void removeEntry(String ckey) {
        
        
    }
    
    
    // edit
    
    // tägijutut
    
    
}
