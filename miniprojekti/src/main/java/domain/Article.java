package domain;

import Syotetarkistus.Syotetarkastaja;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 *
 */
public class Article extends Viite {
    
    private static final Set pakollisetKentat = EnumSet.of(Kentta.author, Kentta.title, Kentta.journal, Kentta.year);
    private static final Set eiPakollisetKentat = EnumSet.of(Kentta.volume, Kentta.number, Kentta.pages, Kentta.month, Kentta.note, Kentta.key);
    
    public Article(Syotetarkastaja tarkastaja) {
        this.kentat = new EnumMap(Kentta.class);
        this.sallitutKentat = EnumSet.of(Kentta.author, Kentta.title, Kentta.journal, Kentta.year,
                Kentta.volume, Kentta.number, Kentta.pages, Kentta.month, Kentta.note, Kentta.key);
        this.tarkastaja = tarkastaja;
    }

    @Override
    public String toString() {
        return citationKey + ": " + kentat.get(Kentta.author) + ", " + kentat.get(Kentta.title) + ", " + 
                kentat.get(Kentta.journal) + ", " + kentat.get(Kentta.year);
    }

    @Override
    public List<String> kenttaMaarittelyVirheet() {
        List<String> virheet = new ArrayList<>();
        if(!kentat.containsKey(Kentta.author)) {
            virheet.add("Artikkelille tulee olla määriteltynä kenttä author");
        }
        if(!kentat.containsKey(Kentta.title)) {
            virheet.add("Artikkelille tulee olla määriteltynä kenttä title.");
        }
        if(!kentat.containsKey(Kentta.journal)) {
            virheet.add("Artikkelille tulee olla määriteltynä kenttä journal");
        }
        if(!kentat.containsKey(Kentta.year)) {
            virheet.add("Artikkelille tulee olla määriteltynä kenttä year");
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
        return "article";
    }
    
    
}
