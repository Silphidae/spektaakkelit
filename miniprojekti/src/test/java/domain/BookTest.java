package domain;

import Syotetarkistus.Syotetarkastaja;
import static junit.framework.Assert.assertEquals;
import junit.framework.TestCase;

public class BookTest extends TestCase {

    private Book kirja;

    public BookTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        kirja = new Book(new Syotetarkastaja());
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    // TODO add test methods here. The name must begin with 'test'. For example:

    public void testToStringAuthor() {
        kirja.lisaaKentta(Kentta.title, "Viimeiset banjonsoittajat");
        kirja.lisaaKentta(Kentta.author, "Antti Akateeminen");
        kirja.lisaaKentta(Kentta.publisher, "Pieni kirjakustantamo");
        kirja.lisaaKentta(Kentta.year, "2013");
        assertEquals(kirja.toString(), null + ": Antti Akateeminen, Viimeiset banjonsoittajat,"
                + " Pieni kirjakustantamo, 2013");
    }
    
    public void testToStringEditor() {
        kirja.lisaaKentta(Kentta.title, "Viimeiset banjonsoittajat");
        kirja.lisaaKentta(Kentta.editor, "Antti Akateeminen");
        kirja.lisaaKentta(Kentta.publisher, "Pieni kirjakustantamo");
        kirja.lisaaKentta(Kentta.year, "2013");
        assertEquals(kirja.toString(), null + ": Antti Akateeminen, Viimeiset banjonsoittajat,"
                + " Pieni kirjakustantamo, 2013");
    }
    
    public void testVirheilmoituksetTulevatOikein() {
        assertEquals(4, kirja.kenttaMaarittelyVirheet().size());
        
        kirja.lisaaKentta(Kentta.author, "Kirjoittaja");
        kirja.lisaaKentta(Kentta.title, "Kirjan nimi");
        
        assertEquals(2, kirja.kenttaMaarittelyVirheet().size());
        
        kirja.lisaaKentta(Kentta.editor, "Toimittaja");
        
        assertEquals(3, kirja.kenttaMaarittelyVirheet().size());
    }
}
