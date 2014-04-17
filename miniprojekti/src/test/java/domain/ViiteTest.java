package domain;

import Syotetarkistus.Syotetarkastaja;
import domain.Article;
import domain.Kentta;
import java.util.EnumSet;
import static junit.framework.Assert.assertNotNull;
import junit.framework.TestCase;

public class ViiteTest extends TestCase {

    private Article viite;

    public ViiteTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        viite = new Article(new Syotetarkastaja());
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    // TODO add test methods here. The name must begin with 'test'. For example:

    public void testViitteenLuonti() {
        assertNotNull(viite);
    }
    
    public void testViiteavaimenLisaysOnnistuu() {
        viite.lisaaCitationKey("pekka01");
        assertEquals(viite.getCitationKey(), "pekka01");
    }
    
    public void testEpavalidinViiteavaimenLisaysEiOnnistu() {
        viite.lisaaCitationKey("@%");
        assertNull(viite.getCitationKey());
    }

    public void testKenttienLisaaminenOnnistuu() {
        viite.lisaaKentta(Kentta.author, "Pekka");
        viite.lisaaKentta(Kentta.title, "Banjonsoiton monet kasvot");
        assertEquals(viite.getKentanSisalto(Kentta.author), "Pekka");
        assertEquals(viite.getKentanSisalto(Kentta.title), "Banjonsoiton monet kasvot");
        assertEquals(viite.kaytossaOlevatKentat(), EnumSet.of(Kentta.author, Kentta.title));
    }
    
    public void testKenttienPoistaminenOnnistuu() {
        viite.lisaaKentta(Kentta.title, "Banjonsoiton monet kasvot");
        assertEquals(viite.getKentanSisalto(Kentta.title), "Banjonsoiton monet kasvot");
        viite.poistaKentta(Kentta.title);
        assertNull(viite.getKentanSisalto(Kentta.title));
    }
    
    public void testKenttienMuokkausOnnistuu() {
        viite.lisaaKentta(Kentta.title, "Banjonsoiton monet kasvot");
        assertEquals(viite.getKentanSisalto(Kentta.title), "Banjonsoiton monet kasvot");
        viite.muokkaaKenttaa(Kentta.title, "Banjonsoiton monien kasvojen pojan paluu");
        assertEquals(viite.getKentanSisalto(Kentta.title), "Banjonsoiton monien kasvojen pojan paluu");
    }
    
    public void testEiVoiLisataKenttaaJokaEiViitetyypillaSallittu() {
        viite.lisaaKentta(Kentta.publisher, "WSOY");
        assertNull(viite.getKentanSisalto(Kentta.publisher));
    }
}
