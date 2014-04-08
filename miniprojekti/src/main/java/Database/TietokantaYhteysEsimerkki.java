package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

public class TietokantaYhteysEsimerkki {

    public static void main(String[] args) throws NamingException, SQLException {

        Connection yhteys = TietokantaYhteys.getYhteys(); //Haetaan yhteysolio
        PreparedStatement kysely;
        ResultSet tulokset;

        try {
            //Alustetaan muuttuja jossa on Select-kysely, joka palauttaa lukuarvon:
            String sqlkysely = "SELECT * FROM tuotteet;";

            kysely = yhteys.prepareStatement(sqlkysely);
            tulokset = kysely.executeQuery();
            if (tulokset.next()) {
                //Tuloksen arvoksi pitäisi tulla numero mp3-soitin
                String tulos = tulokset.getString("nimi");
                System.out.println("Tulos: " + tulos);
            } else {
                System.out.println("Virhe!");
            }

            if (tulokset.next()) {
                //Tuloksen arvoksi pitäisi tulla numero 3.
                int tulos = tulokset.getInt("Tuotenumero");
                System.out.println("Tulos: " + tulos);
            } else {
                System.out.println("Virhe!");
            }

            tulokset.close();
            kysely.close();
                    } catch (Exception e) {
            System.out.println("Virhe: " + e.getMessage());
        }

            System.out.println("moi");


        }
    }
