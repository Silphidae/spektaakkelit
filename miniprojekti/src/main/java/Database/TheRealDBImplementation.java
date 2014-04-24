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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

public class TheRealDBImplementation implements Database {

    @Override
    public void insertEntry(Viite viite) throws NamingException, SQLException {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet syotto = null;

        try {
            String sql = "INSERT INTO viitteet(ckey, author, title, journal, year, volume, number, pages, month, note, editor, publisher, series, address, edition, booktitle, organization, type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            yhteys = TietokantaYhteys.getYhteys();
            kysely = yhteys.prepareStatement(sql);

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

            syotto = kysely.executeQuery();
            syotto.next();
        } catch (Exception e) {
            System.out.println("Virhe: " + e.getMessage());
        } finally {
            if (kysely != null) {
                kysely.close();
            }
            if (syotto != null) {
                syotto.close();
            }
            if (yhteys != null) {
                yhteys.close();
            }
        }
    }

    @Override
    public ArrayList<Viite> getAllEntries() {
        try {
            return getViitteet("SELECT * FROM viitteet;");
        } catch (SQLException ex) {
            Logger.getLogger(TheRealDBImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Viite getEntry(String ckey) {
        ArrayList<Viite> viitteet = null;
        try {
            viitteet = getViitteet("SELECT * FROM viitteet WHERE ckey = '" + ckey + "';");
        } catch (SQLException ex) {
            Logger.getLogger(TheRealDBImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (viitteet == null || viitteet.size() < 1) {
            return null;
        }

        return viitteet.get(0);
    }

    private ArrayList<Viite> getViitteet(String sqlkysely) throws SQLException {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            yhteys = TietokantaYhteys.getYhteys(); //Haetaan yhteysolio

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
                lisattava.lisaaCitationKey(tulokset.getString("ckey"));

                viitteet.add(lisattava);
            }
            return viitteet;
        } catch (Exception e) {
            System.out.println("Virhe: " + e.getMessage());
        } finally {
            if (kysely != null) {
                kysely.close();
            }
            if (tulokset != null) {
                tulokset.close();
            }
            if (yhteys != null) {
                yhteys.close();
            }
        }
        return null;
    }

    @Override
    public void removeEntry(String ckey) {
        String sql = "DELETE FROM viitteet WHERE ckey = '" + ckey + "';";
        try {
            dbConnection(sql);
        } catch (SQLException ex) {
            Logger.getLogger(TheRealDBImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void dbConnection(String sql) throws SQLException {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;
        try {
            yhteys = TietokantaYhteys.getYhteys();

            kysely = yhteys.prepareStatement(sql);
            tulokset = kysely.executeQuery();
        } catch (Exception e) {
            System.out.println("Virhe: " + e.getMessage());
        } finally {
            if (yhteys != null) {
                yhteys.close();
            }
            if (kysely != null) {
                kysely.close();
            }
            if (tulokset != null) {
                tulokset.close();
            }
        }
    }

    public void addTag(String ckey, String tag) throws NamingException, SQLException {
        String sql = "INSERT INTO tagit(tag, viite) VALUES('" + tag + "', '" + ckey + "');";
        dbConnection(sql);
    }

    public ArrayList<Viite> listByTag(String tag) {
        ArrayList<Viite> viitteet = null;
        try {
            viitteet = getViitteet("SELECT * FROM viitteet JOIN tagit ON viitteet.ckey = tagit.viite WHERE tagit.tag = '" + tag + "';");
        } catch (SQLException ex) {
            Logger.getLogger(TheRealDBImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (viitteet == null || viitteet.size() < 1) {
            return null;
        }

        return viitteet;
    }

    @Override
    public ArrayList<String> getTagsByViite(String ckey) {
        return getTagit(" WHERE tagit.viite = '" + ckey + "'");
    }

    @Override
    public void removeTagFromViite(String ckey, String tag) {
        String sql = "DELETE FROM tagit WHERE viite = '" + ckey + "' AND tag = '" + tag + "';";
        try {
            dbConnection(sql);
        } catch (SQLException ex) {
            Logger.getLogger(TheRealDBImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ArrayList<String> getTagit(String rajaus) {
        Connection yhteys = null;
        PreparedStatement kysely = null;
        ResultSet tulokset = null;

        try {
            yhteys = TietokantaYhteys.getYhteys(); //Haetaan yhteysolio

            kysely = yhteys.prepareStatement("SELECT DISTINCT tag FROM tagit" + rajaus);
            tulokset = kysely.executeQuery();

            ArrayList<String> tagit = new ArrayList();

            while (tulokset.next()) {
                tagit.add(tulokset.getString("tag"));
            }

            return tagit;

        } catch (SQLException e) {
            System.out.println("Virhe: " + e.getMessage());
            return null;
        } catch (NamingException ex) {
            Logger.getLogger(TheRealDBImplementation.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (yhteys != null) {
                    yhteys.close();
                }
                if (kysely != null) {
                    kysely.close();
                }
                if (tulokset != null) {
                    tulokset.close();
                }
            } catch (SQLException e) {
                System.out.println("Virhe: " + e.getMessage());
            }
        }
        return null;
    }
}
