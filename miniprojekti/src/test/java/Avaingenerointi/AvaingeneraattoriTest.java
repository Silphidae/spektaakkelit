
package Avaingenerointi;

import AvaimenGenerointi.Avaingeneraattori;
import Database.Database;
import Database.MockDatabase;
import Syotetarkistus.Syotetarkastaja;
import domain.Article;
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
    
    public void testUseitaSamojaArticleja() {
        Viite v = luoTestiArticle("Etunimi Sukunimi", "1999");
        db.insertEntry(v);
        Viite v2 = luoTestiArticle("Etunimi Sukunimi", "1999");
        db.insertEntry(v2);
        Viite v3 = luoTestiArticle("Etunimi Sukunimi", "1999");
        db.insertEntry(v3);
        
        assertEquals("SuEt1999-1", v2.getViiteavain());
        assertEquals("SuEt1999-2", v3.getViiteavain());
    }
    
    public void testPalautuuOdotettuAvainArticlelle() {
        Viite v = luoTestiArticle("Joku Nimi", "2000");
        assertEquals("NiJo2000", v.getViiteavain());
        
        Viite v2 = luoTestiArticle("Joku Toinen", "1567");
        assertEquals("ToJo1567", v2.getViiteavain());
    }
    
    public Viite luoTestiArticle(String nimi, String vuosi) {
        Viite v = new Article(tarkastaja);
        v.lisaaKentta(Kentta.author, nimi);
        v.lisaaKentta(Kentta.year, vuosi);
        v.lisaaViiteavain(generaattori.luoAvain(v));
        return v;
    }
}
