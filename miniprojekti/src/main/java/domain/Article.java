package domain;

import Syotetarkistus.Syotetarkastaja;
import java.util.EnumMap;
import java.util.EnumSet;

/**
 *
 */
public class Article extends Viite {
    
    public Article(Syotetarkastaja tarkastaja) {
        this.kentat = new EnumMap(Kentta.class);
        this.pakollisetKentat = EnumSet.of(Kentta.author, Kentta.title, Kentta.journal, Kentta.year);
        this.sallitutKentat = EnumSet.of(Kentta.author, Kentta.title, Kentta.journal, Kentta.year,
                Kentta.volume, Kentta.number, Kentta.pages, Kentta.month, Kentta.note, Kentta.key);
        this.tarkastaja = tarkastaja;
    }
}
