package domain;

import Engine.IEngine;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

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
        } else if (viite instanceof Article) {
            return "article";
        } else {
            return "What???";
        }
    }

    public File generoiTiedosto(String tiedostonNimi) throws FileNotFoundException {
        File bibtexTiedosto = new File(tiedostonNimi);
        try (PrintWriter tiedosto = new PrintWriter(bibtexTiedosto)) {
            for (Viite viite : moottori.getViitteet()) {
                tiedosto.println("@" + haeViitteenTyyppi(viite) + "{" + viite.getViiteavain() + ",");
                for (Kentta kentta : viite.kaytossaOlevatKentat()) {
                    tiedosto.println(kentta.name() + " = {" + muunnaAakkoset(viite.getKentanSisalto(kentta)) + "},");
                }
                tiedosto.println("}\n");
            }
            tiedosto.close();
        }
        return bibtexTiedosto;
    }
}
