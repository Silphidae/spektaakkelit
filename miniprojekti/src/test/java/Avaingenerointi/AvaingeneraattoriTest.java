
package Avaingenerointi;

import Database.Database;
import Syotetarkistus.Syotetarkastaja;
import domain.Article;
import domain.Book;
import domain.InProceedings;
import domain.Kentta;
import domain.Viite;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.naming.NamingException;
import junit.framework.TestCase;
import static org.mockito.Mockito.*;

public class AvaingeneraattoriTest extends TestCase{
    
    private Database db;
    private Avaingeneraattori generaattori;
    private Syotetarkastaja tarkastaja;
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        db = mock(Database.class);
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
    
    public void testUseitaSamojaArticle() throws NamingException, SQLException {
        Viite v = luoTestiArticle("Etunimi Sukunimi", "1999");
        ArrayList<Viite> viitteet = new ArrayList<>();
        viitteet.add(v);
        when(db.getAllEntries()).thenReturn(viitteet);
        
        Viite v2 = luoTestiArticle("Etunimi Sukunimi", "1999");
        
        assertEquals("S99-1", v2.getCitationKey());
    }
    
    public void testUseitaSamojaBook() throws NamingException, SQLException {
        Viite v = luoTestiBook("Etunimi Sukunimi", null, "1999");
        ArrayList<Viite> viitteet = new ArrayList<>();
        viitteet.add(v);
        when(db.getAllEntries()).thenReturn(viitteet);
        
        Viite v2 = luoTestiBook("Etunimi Sukunimi", "Toinen Nimi", "1999");
        assertEquals("S99-1", v2.getCitationKey());
    }
    
    public void testUseitaSamojaInProceedings() throws NamingException, SQLException {
        Viite v = luoTestiInProceedings("Etunimi Sukunimi", "1999");
        ArrayList<Viite> viitteet = new ArrayList<>();
        viitteet.add(v);
        when(db.getAllEntries()).thenReturn(viitteet);
        Viite v2 = luoTestiInProceedings("Sukunimi, Etunimi", "1999");
        
        assertEquals("S99", v.getCitationKey());
        assertEquals("S99-1", v2.getCitationKey());
    }
    
    public void testPalautuuOdotettuAvainArticle() {
        Viite v = luoTestiArticle("Joku Nimi", "2000");
        assertEquals("N00", v.getCitationKey());
        
        Viite v2 = luoTestiArticle("Joku Toinen", "1567");
        assertEquals("T67", v2.getCitationKey());
    }
    
    public void testPalautuuOdotettuAvainBook() {
        Viite v = luoTestiBook("Joku Nimi", "Toinen Nimi", "2000");
        assertEquals("N00", v.getCitationKey());
        
        Viite v2 = luoTestiBook(null, "Joku Toinen and Kolmas, Joku", "1567");
        assertEquals("TK67", v2.getCitationKey());
    }
    
    public void testPalautuuOdotettuAvainInProceedings() {
        Viite v = luoTestiInProceedings("Joku Nimi and Toinen Nimi and Kolmas Nimi", "2000");
        assertEquals("NNN00", v.getCitationKey());
        
        Viite v2 = luoTestiInProceedings("Eka, Joku and Toinen, Joku", "1567");
        assertEquals("ET67", v2.getCitationKey());
    }
    
    public void testEiSopimattomiaMerkkeja() {
        Viite v = luoTestiArticle("J~oku Nimi", "2000");
        assertEquals("N00", v.getCitationKey());
        
        Viite v2 = luoTestiInProceedings("Joku T\\inen", "1567");
        assertEquals("T67", v2.getCitationKey());
        
        Viite v3 = luoTestiBook("Joku N@mi", null, "2000");
        assertEquals("N00", v3.getCitationKey());
        
        Viite v4 = luoTestiArticle("Joku T\"{o}inen", "1567");
        assertEquals("T67", v4.getCitationKey());
        
        Viite v5 = luoTestiArticle("J,ku Nimi", "1567");
        assertEquals("N67", v5.getCitationKey());
        
        Viite v6 = luoTestiArticle("J\\ku Nim i", "1567");
        assertEquals("N67", v6.getCitationKey());
        
        Viite v7 = luoTestiArticle("Joku N#mi", "1567");
        assertEquals("N67", v7.getCitationKey());
    }    
    
    public Viite luoTestiArticle(String nimi, String vuosi) {
        Viite v = new Article(tarkastaja);
        v.lisaaKentta(Kentta.author, nimi);
        v.lisaaKentta(Kentta.year, vuosi);
        v.lisaaCitationKey(generaattori.luoAvain(v));
        return v;
    }    
    
    public Viite luoTestiBook(String author, String editor, String vuosi) {
        Viite v = new Book(tarkastaja);
        if (editor!=null) v.lisaaKentta(Kentta.editor, editor);
        if (author!=null) v.lisaaKentta(Kentta.author, author);
        v.lisaaKentta(Kentta.year, vuosi);
        v.lisaaCitationKey(generaattori.luoAvain(v));
        return v;
    } 
    
    public Viite luoTestiInProceedings(String nimi, String vuosi) {
        Viite v = new InProceedings(tarkastaja);
        v.lisaaKentta(Kentta.author, nimi);
        v.lisaaKentta(Kentta.year, vuosi);
        v.lisaaCitationKey(generaattori.luoAvain(v));
        return v;
    }
}
