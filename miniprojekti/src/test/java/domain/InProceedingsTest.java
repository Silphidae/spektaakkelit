
package domain;

import Syotetarkistus.Syotetarkastaja;
import junit.framework.TestCase;

public class InProceedingsTest extends TestCase {
    private InProceedings konf;

    public InProceedingsTest(String name) {
        super(name);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        konf = new InProceedings(new Syotetarkastaja());
    }
    
    public void testToString() {
        konf.lisaaKentta(Kentta.title, "Viimeiset banjonsoittajat");
        konf.lisaaKentta(Kentta.author, "Antti Akateeminen");
        konf.lisaaKentta(Kentta.booktitle, "17th annual banjo conference");
        konf.lisaaKentta(Kentta.year, "2013");
        assertEquals(konf.toString(), null + ": Antti Akateeminen, Viimeiset banjonsoittajat,"
                + " 17th annual banjo conference, 2013");
    }
    
    public void testVirheilmoituksetTulevatOikein() {
        assertEquals(4, konf.kenttaMaarittelyVirheet().size());
        
        konf.lisaaKentta(Kentta.author, "Pekka");
        konf.lisaaKentta(Kentta.title, "Artikkelin nimi");
        assertEquals(2, konf.kenttaMaarittelyVirheet().size());
        
        konf.lisaaKentta(Kentta.annote, "jotakin");
        assertEquals(2, konf.kenttaMaarittelyVirheet().size());
    }
    
}
