package domain;

import Engine.IEngine;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Bibtex {

    private IEngine moottori;

    public Bibtex(IEngine moottori) {
        this.moottori = moottori;
    }

    private String muunnaAakkoset(String sana) {
        sana = sana.replace("ä", "\\\"{a}");
        sana = sana.replace("ö", "\\\"{o}");
        sana = sana.replace("Ä", "\\\"{A}");
        sana = sana.replace("Ö", "\\\"{O}");
        return sana;
    }

    private String haeViitteenTyyppi(Viite viite) {
        if (viite instanceof Book) {
            return "book";
        } else if (viite instanceof InProceedings) {
            return "inProceedings";
        } else {
            return "article";
        }
    }

    private File tiedostonKasittely(String tiedostonNimi, ArrayList<Viite> viitteet) throws FileNotFoundException {
        if (!tiedostonNimi.isEmpty()) {
            File bibtexTiedosto = new File(tiedostonNimi);
            try (PrintWriter tiedosto = new PrintWriter(bibtexTiedosto)) {
                for (Viite viite : viitteet) {
                    tiedosto.println("@" + haeViitteenTyyppi(viite) + "{" + viite.getCitationKey() + ",");
                    for (Kentta kentta : viite.kaytossaOlevatKentat()) {
                        tiedosto.println(kentta.name() + " = {" + muunnaAakkoset(viite.getKentanSisalto(kentta)) + "},");
                    }
                    tiedosto.println("}\n");
                }
                tiedosto.close();
            }
            return bibtexTiedosto;
        }
        return null;
    }

    public File generoiTiedosto(String tiedostonNimi) throws FileNotFoundException {
        return tiedostonKasittely(tiedostonNimi, moottori.getViitteet());
    }

    public File generoiTiedostoByTag(String tiedostonNimi, String tag) throws FileNotFoundException {
        return tiedostonKasittely(tiedostonNimi, moottori.listByTag(tag));
    }
}
