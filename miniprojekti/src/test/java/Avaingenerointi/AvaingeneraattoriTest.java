
package Avaingenerointi;

import Database.Database;
import Database.MockDatabase;
import Syotetarkistus.Syotetarkastaja;
import domain.Article;
import domain.Book;
import domain.InProceedings;
import domain.Kentta;
import domain.Viite;
import junit.framework.TestCase;

public class AvaingeneraattoriTest extends TestCase{
    
    private Database db;
    private Avaingeneraattori generaattori;
    private Syotetarkastaja tarkastaja;
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        db = new MockDatabase();
        tarkastaja = new Syotetarkastaja();
        generaattori = new Avaingeneraattori(db);
    }

    public void testNimenLyhennysToimii() {
        String nimi = "Etunimi Sukunimi";
        String lyhenne = generaattori.lyhennaNimet(nimi);
        assertEquals("S", lyhenne);
        
        nimi = "Sukunimi";
        lyhenne = generaattori.lyhennaNimet(nimi);
        assertEquals("S", lyhenne);
        
        nimi = "Sukunimi, E.";
        lyhenne = generaattori.lyhennaNimet(nimi);
        assertEquals("S", lyhenne);
        
        nimi = "Eka, Etunimi and Toinen, Etunimi";
        lyhenne = generaattori.lyhennaNimet(nimi);
        assertEquals("ET", lyhenne);
        
        nimi = "Joku Nimi and Toinen Nimi and Kolmas Nimi";
        lyhenne = generaattori.lyhennaNimet(nimi);
        assertEquals("NNN", lyhenne);
    }
    
    public void testUseitaSamojaArticle() {
        Viite v = luoTestiArticle("Etunimi Sukunimi", "1999");
        db.insertEntry(v);
        Viite v2 = luoTestiArticle("Etunimi Sukunimi", "1999");
        db.insertEntry(v2);
        Viite v3 = luoTestiArticle("Etunimi Sukunimi", "1999");
        db.insertEntry(v3);
        
        assertEquals("S99-1", v2.getViiteavain());
        assertEquals("S99-2", v3.getViiteavain());
    }
    
    public void testUseitaSamojaBook() {
        Viite v = luoTestiBook("Etunimi Sukunimi", null, "1999");
        db.insertEntry(v);
        Viite v2 = luoTestiBook("Etunimi Sukunimi", "Toinen Nimi", "1999");
        db.insertEntry(v2);
        Viite v3 = luoTestiBook(null, "Etunimi Sukunimi", "1999");
        db.insertEntry(v3);
        
        assertEquals("S99-1", v2.getViiteavain());
        assertEquals("S99-2", v3.getViiteavain());
    }
    
    public void testUseitaSamojaInProceedings() {
        Viite v = luoTestiInProceedings("Etunimi Sukunimi", "1999");
        db.insertEntry(v);
        Viite v2 = luoTestiInProceedings("Sukunimi, Etunimi", "1999");
        db.insertEntry(v2);
        Viite v3 = luoTestiInProceedings("Etunimi Sukunimi", "1999");
        db.insertEntry(v3);
        
        assertEquals("S99", v.getViiteavain());
        assertEquals("S99-1", v2.getViiteavain());
        assertEquals("S99-2", v3.getViiteavain());
    }
    
    public void testPalautuuOdotettuAvainArticle() {
        Viite v = luoTestiArticle("Joku Nimi", "2000");
        assertEquals("N00", v.getViiteavain());
        
        Viite v2 = luoTestiArticle("Joku Toinen", "1567");
        assertEquals("T67", v2.getViiteavain());
    }
    
    public void testPalautuuOdotettuAvainBook() {
        Viite v = luoTestiBook("Joku Nimi", "Toinen Nimi", "2000");
        assertEquals("N00", v.getViiteavain());
        
        Viite v2 = luoTestiBook(null, "Joku Toinen and Kolmas, Joku", "1567");
        assertEquals("TK67", v2.getViiteavain());
    }
    
    public void testPalautuuOdotettuAvainInProceedings() {
        Viite v = luoTestiInProceedings("Joku Nimi and Toinen Nimi and Kolmas Nimi", "2000");
        assertEquals("NNN00", v.getViiteavain());
        
        Viite v2 = luoTestiInProceedings("Eka, Joku and Toinen, Joku", "1567");
        assertEquals("ET67", v2.getViiteavain());
    }
    
    public void testEiSopimattomiaMerkkeja() {
        Viite v = luoTestiArticle("J~oku Nimi", "2000");
        assertEquals("N00", v.getViiteavain());
        
        Viite v2 = luoTestiInProceedings("Joku T\\inen", "1567");
        assertEquals("T67", v2.getViiteavain());
        
        Viite v3 = luoTestiBook("Joku N@mi", null, "2000");
        assertEquals("N00", v3.getViiteavain());
        
        Viite v4 = luoTestiArticle("Joku T\"{o}inen", "1567");
        assertEquals("T67", v4.getViiteavain());
        
        Viite v5 = luoTestiArticle("J,ku Nimi", "1567");
        assertEquals("N67", v5.getViiteavain());
        
        Viite v6 = luoTestiArticle("J\\ku Nim i", "1567");
        assertEquals("N67", v6.getViiteavain());
        
        Viite v7 = luoTestiArticle("Joku N#mi", "1567");
        assertEquals("N67", v7.getViiteavain());
    }    
    
    public Viite luoTestiArticle(String nimi, String vuosi) {
        Viite v = new Article(tarkastaja);
        v.lisaaKentta(Kentta.author, nimi);
        v.lisaaKentta(Kentta.year, vuosi);
        v.lisaaViiteavain(generaattori.luoAvain(v));
        return v;
    }    
    
    public Viite luoTestiBook(String author, String editor, String vuosi) {
        Viite v = new Book(tarkastaja);
        if (editor!=null) v.lisaaKentta(Kentta.editor, editor);
        if (author!=null) v.lisaaKentta(Kentta.author, author);
        v.lisaaKentta(Kentta.year, vuosi);
        v.lisaaViiteavain(generaattori.luoAvain(v));
        return v;
    } 
    
    public Viite luoTestiInProceedings(String nimi, String vuosi) {
        Viite v = new InProceedings(tarkastaja);
        v.lisaaKentta(Kentta.author, nimi);
        v.lisaaKentta(Kentta.year, vuosi);
        v.lisaaViiteavain(generaattori.luoAvain(v));
        return v;
    }
}
