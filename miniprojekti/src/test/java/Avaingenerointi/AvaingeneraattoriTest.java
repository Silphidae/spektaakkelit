
package Avaingenerointi;

import AvaimenGenerointi.Avaingeneraattori;
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
        String lyhenne = generaattori.lyhennaNimi(nimi);
        assertEquals("SuEt", lyhenne);
        
        nimi = "Sukunimi";
        lyhenne = generaattori.lyhennaNimi(nimi);
        assertEquals("Su", lyhenne);
        
        nimi = "Etunimi S";
        lyhenne = generaattori.lyhennaNimi(nimi);
        assertEquals("SEt", lyhenne);
        
        nimi = "Etunimi Sukunimi, Toinen Tekijä";
        lyhenne = generaattori.lyhennaNimi(nimi);
        assertEquals("SuEt", lyhenne);
    }
    
    public void testUseitaSamojaArticle() {
        Viite v = luoTestiArticle("Etunimi Sukunimi", "1999");
        db.insertEntry(v);
        Viite v2 = luoTestiArticle("Etunimi Sukunimi", "1999");
        db.insertEntry(v2);
        Viite v3 = luoTestiArticle("Etunimi Sukunimi", "1999");
        db.insertEntry(v3);
        
        assertEquals("SuEt1999-1", v2.getViiteavain());
        assertEquals("SuEt1999-2", v3.getViiteavain());
    }
    
    public void testUseitaSamojaBook() {
        Viite v = luoTestiBook("Etunimi Sukunimi", null, "1999");
        db.insertEntry(v);
        Viite v2 = luoTestiBook("Etunimi Sukunimi", "Toinen Nimi", "1999");
        db.insertEntry(v2);
        Viite v3 = luoTestiBook(null, "Etunimi Sukunimi", "1999");
        db.insertEntry(v3);
        
        assertEquals("SuEt1999-1", v2.getViiteavain());
        assertEquals("SuEt1999-2", v3.getViiteavain());
    }
    
    public void testUseitaSamojaInProceedings() {
        Viite v = luoTestiInProceedings("Etunimi Sukunimi", "1999");
        db.insertEntry(v);
        Viite v2 = luoTestiInProceedings("Etunimi Sukunimi", "1999");
        db.insertEntry(v2);
        Viite v3 = luoTestiInProceedings("Etunimi Sukunimi", "1999");
        db.insertEntry(v3);
        
        assertEquals("SuEt1999-1", v2.getViiteavain());
        assertEquals("SuEt1999-2", v3.getViiteavain());
    }
    
    public void testPalautuuOdotettuAvainArticle() {
        Viite v = luoTestiArticle("Joku Nimi", "2000");
        assertEquals("NiJo2000", v.getViiteavain());
        
        Viite v2 = luoTestiArticle("Joku Toinen", "1567");
        assertEquals("ToJo1567", v2.getViiteavain());
    }
    
    public void testPalautuuOdotettuAvainBook() {
        Viite v = luoTestiBook("Joku Nimi", "Toinen Nimi", "2000");
        assertEquals("NiJo2000", v.getViiteavain());
        
        Viite v2 = luoTestiBook(null, "Joku Toinen", "1567");
        assertEquals("ToJo1567", v2.getViiteavain());
    }
    
    public void testPalautuuOdotettuAvainInProceedings() {
        Viite v = luoTestiInProceedings("Joku Nimi", "2000");
        assertEquals("NiJo2000", v.getViiteavain());
        
        Viite v2 = luoTestiInProceedings("Joku Toinen", "1567");
        assertEquals("ToJo1567", v2.getViiteavain());
    }
    
    public void testEiSopimattomiaMerkkeja() {
        Viite v = luoTestiArticle("J~oku Nimi", "2000");
        assertEquals("NiJ2000", v.getViiteavain());
        
        Viite v2 = luoTestiInProceedings("Joku T\\inen", "1567");
        assertEquals("TJo1567", v2.getViiteavain());
        
        Viite v3 = luoTestiBook("Joku N mi", null, "2000");
        assertEquals("NJo2000", v3.getViiteavain());
        
        Viite v4 = luoTestiArticle("#oku Toinen", "1567");
        assertEquals("Too1567", v4.getViiteavain());
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
