package domain;

import Syotetarkistus.Syotetarkastaja;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class InProceedings extends Viite {

    public static Set pakollisetKentat = EnumSet.of(Kentta.author, Kentta.title, Kentta.booktitle, Kentta.year);
    public static final Set eiPakollisetKentat = EnumSet.of(Kentta.editor, Kentta.publisher, Kentta.volume, Kentta.number, Kentta.pages, Kentta.month,
            Kentta.note, Kentta.key, Kentta.series, Kentta.address, Kentta.edition, Kentta.journal,
            Kentta.organization);

    public InProceedings(Syotetarkastaja tarkastaja) {
        this.kentat = new EnumMap(Kentta.class);
        this.sallitutKentat = EnumSet.of(Kentta.author, Kentta.editor, Kentta.publisher, Kentta.title,
                Kentta.journal, Kentta.year, Kentta.volume, Kentta.number, Kentta.pages, Kentta.month,
                Kentta.note, Kentta.key, Kentta.series, Kentta.address, Kentta.edition, Kentta.booktitle,
                Kentta.organization);
        this.tarkastaja = tarkastaja;
    }

    @Override
    public String toString() {
        return kentat.get(Kentta.author) + ", " + kentat.get(Kentta.title) + ", "
                + kentat.get(Kentta.booktitle) + ", " + kentat.get(Kentta.year);
    }

    @Override
    public List<String> kenttaMaarittelyVirheet() {
        List<String> virheet = new ArrayList<>();
        if (!kentat.containsKey(Kentta.author)) {
            virheet.add("Konferenssijulkaisulla tulee olla määriteltynä kenttä author");
        }
        if (!kentat.containsKey(Kentta.title)) {
            virheet.add("Konferenssijulkaisulla tulee olla määriteltynä kenttä title.");
        }
        if (!kentat.containsKey(Kentta.booktitle)) {
            virheet.add("Konferenssijulkaisulla tulee olla määriteltynä kenttä booktitle");
        }
        if (!kentat.containsKey(Kentta.year)) {
            virheet.add("Konferenssijulkaisulla tulee olla määriteltynä kenttä year");
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
        return "inproceedings";
    }
}
