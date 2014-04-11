package domain;

import Syotetarkistus.Syotetarkastaja;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class Book extends Viite {

    private static final Set pakollisetKentat = EnumSet.of(Kentta.author, Kentta.editor, Kentta.title, Kentta.publisher, Kentta.year);
    private static final Set eiPakollisetKentat = EnumSet.of(Kentta.volume, Kentta.number, Kentta.pages, Kentta.month,
                Kentta.note, Kentta.key, Kentta.series, Kentta.address, Kentta.edition);
    
    public Book(Syotetarkastaja tarkastaja) {
        this.kentat = new EnumMap(Kentta.class);
        this.sallitutKentat = EnumSet.of(Kentta.author, Kentta.editor, Kentta.publisher, Kentta.title,
                Kentta.journal, Kentta.year, Kentta.volume, Kentta.number, Kentta.pages, Kentta.month,
                Kentta.note, Kentta.key, Kentta.series, Kentta.address, Kentta.edition);
        this.tarkastaja = tarkastaja;
    }

    @Override
    public String toString() {
        if (kentat.containsKey(Kentta.author)) {
            return kentat.get(Kentta.author) + ", " + kentat.get(Kentta.title)
                    + ", " + kentat.get(Kentta.publisher) + ", " + kentat.get(Kentta.year);
        } else {
            return kentat.get(Kentta.editor) + ", " + kentat.get(Kentta.title)
                    + ", " + kentat.get(Kentta.publisher) + ", " + kentat.get(Kentta.year);
        }

    }

    @Override
    public List<String> kenttaMaarittelyVirheet() {
        List<String> virheet = new ArrayList<>();
        if (kentat.containsKey(Kentta.author) == kentat.containsKey(Kentta.editor)) {
            virheet.add("Kirjalle tulee olla määriteltynä joko kenttä author tai"
                    + " kenttä editor.");
        }
        if (!kentat.containsKey(Kentta.title)) {
            virheet.add("Kirjalle tulee olla määriteltynä kenttä title.");
        }
        if (!kentat.containsKey(Kentta.publisher)) {
            virheet.add("Kirjalle tulee olla määriteltynä kenttä publisher");
        }
        if (!kentat.containsKey(Kentta.year)) {
            virheet.add("Kirjalle tulee olla määriteltynä kenttä year");
        }
        return virheet;
    }

    public static Set<Kentta> getPakollisetKentat() {
        return pakollisetKentat;
    }

    public static Set<Kentta> getEiPakollisetKentat() {
        return eiPakollisetKentat;
    }

    @Override
    public String getTyyppi() {
        return "book";
    }
}
