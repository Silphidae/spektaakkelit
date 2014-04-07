package domain;

import Syotetarkistus.Syotetarkastaja;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;

public class InProceedings extends Viite {

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
        return kentat.get(Kentta.author) + ", " + kentat.get(Kentta.title) + ", " + 
                kentat.get(Kentta.booktitle) + ", " + kentat.get(Kentta.year);
    }

    @Override
    public List<String> kenttaMaarittelyVirheet() {
        List<String> virheet = new ArrayList<>();
        if(!kentat.containsKey(Kentta.author)) {
            virheet.add("Konferenssijulkaisulla tulee olla määriteltynä kenttä author");
        }
        if(!kentat.containsKey(Kentta.title)) {
            virheet.add("Konferenssijulkaisulla tulee olla määriteltynä kenttä title.");
        }
        if(!kentat.containsKey(Kentta.journal)) {
            virheet.add("Konferenssijulkaisulla tulee olla määriteltynä kenttä booktitle");
        }
        if(!kentat.containsKey(Kentta.year)) {
            virheet.add("Konferenssijulkaisulla tulee olla määriteltynä kenttä year");
        }
        return virheet;
    }
    
}
