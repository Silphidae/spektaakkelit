package domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Bibtex {

    private List<Viite> viitteet;

    public Bibtex(ArrayList<Viite> viitteet) {
        this.viitteet = viitteet;
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
            for (Viite viite : viitteet) {
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
