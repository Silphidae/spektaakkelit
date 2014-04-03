package domain;

import java.util.EnumMap;
import java.util.EnumSet;

/**
 *
 */
public class ArtikkeliViite extends Viite {
    
    public ArtikkeliViite(Syotetarkastaja tarkastaja) {
        this.kentat = new EnumMap(Kentta.class);
        this.pakollisetKentat = EnumSet.of(Kentta.author, Kentta.title, Kentta.journal, Kentta.year);
        this.sallitutKentat = EnumSet.of(Kentta.author, Kentta.title, Kentta.journal, Kentta.year,
                Kentta.volume, Kentta.number, Kentta.pages, Kentta.month, Kentta.note, Kentta.key);
        this.tarkastaja = tarkastaja;
    }
}
