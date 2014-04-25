/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine;

import Avaingenerointi.Avaingeneraattori;
import Database.Database;
import Syotetarkistus.Syotetarkastaja;
import domain.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Set;
import javax.naming.NamingException;
import junit.framework.TestCase;
import static org.mockito.Mockito.*;

/**
 *
 * @author tv
 */
public class EngineStubTest extends TestCase {

    private EngineStub engine;
    private Database db;

    public EngineStubTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        db = mock(Database.class);
        engine = new EngineStub(db);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    // TODO add test methods here. The name must begin with 'test'. For example:
    // public void testHello() {}

    public void testViitteenLisaysOnnistuu() throws NamingException, SQLException {
        HashMap<Kentta, String> article = new HashMap();
        article.put(Kentta.author, "Etunimi Sukunimi");
        article.put(Kentta.year, "1999");
        article.put(Kentta.title, "Otsikko");
        article.put(Kentta.journal, "Paras Lehti");
        engine.lisaaViite(Viitetyyppi.article, article);
        verify(db, times(1)).insertEntry(any(Article.class));

        HashMap<Kentta, String> book = article;
        book.put(Kentta.publisher, "Quiui");
        book.remove(Kentta.journal);
        engine.lisaaViite(Viitetyyppi.book, book);
        verify(db, times(2)).insertEntry(any(Book.class));

        HashMap<Kentta, String> inproceedings = article;
        inproceedings.put(Kentta.booktitle, "Top Secret");
        engine.lisaaViite(Viitetyyppi.inproceedings, inproceedings);
        verify(db, times(3)).insertEntry(any(InProceedings.class));

        assertNull(engine.lisaaViite(null, null));
    }

    public void testViitteidenListausOnnistuu() {
        engine.listaaKaikkiViitteet();
        verify(db).getAllEntries();
    }

    public void testLisaaViitePalauttaaNullKunViiteVirheeton() {
        HashMap<Kentta, String> tiedot = new HashMap();
        tiedot.put(Kentta.author, "Etunimi Sukunimi");
        tiedot.put(Kentta.year, "1999");
        tiedot.put(Kentta.title, "Otsikko");
        tiedot.put(Kentta.journal, "Paras Lehti");

        assertNull(engine.lisaaViite(Viitetyyppi.article, tiedot));
    }

    public void testLisaaViitePalauttaaListanVirheistaKunSyotetettyVirheellisiaArvoja() {
        HashMap<Kentta, String> tiedot = new HashMap();
        tiedot.put(Kentta.year, "1.5");
        tiedot.put(Kentta.title, "");
        tiedot.put(Kentta.booktitle, "Parasta ikinä");

        ArrayList<String> virheet = engine.lisaaViite(Viitetyyppi.inproceedings, tiedot);

        //kirjoittaja puuttuu (1 virhe) + vuosiluku väärin ja otsikko väärin, joten ne ovat virheellisiä (2 virhettä) eikä niitä
        //lisätä viittelle, joten kenttaMaarittelyvirheet() katsoo ne puuttuviks (2 virhettä) Tämän voisi ehkä korjata....
        assertEquals(5, virheet.size());
    }

    public void testPoistaViiteToimii() {
        engine.poistaViite("ckey");
        verify(db).removeEntry("ckey");
    }
    
    public void testPoistoPoistaaEnsinTagiViittaukset() {
        ArrayList<String> tagit = new ArrayList();
        tagit.add("tag1");
        tagit.add("tag2");
        when(db.getTagsByViite("ckey")).thenReturn(tagit);
        
        engine.poistaViite("ckey");
        verify(db).removeTagFromViite("ckey","tag1");
        verify(db).removeTagFromViite("ckey","tag2");
    }

    public void testPalautetaanViitetyypit() {
        Viitetyyppi[] tyypit = engine.getViitetyypit();
        Viitetyyppi[] pitaisOlla = {Viitetyyppi.article, Viitetyyppi.book, Viitetyyppi.inproceedings};

        assertTrue(Arrays.equals(pitaisOlla, tyypit));
    }

    public void testPalautetaanPakollisetKentat() {
        Set<Kentta> haetut = engine.getPakollisetKentat(Viitetyyppi.book);
        Set<Kentta> pitaisOlla = EnumSet.of(Kentta.author, Kentta.editor, Kentta.title, Kentta.publisher, Kentta.year);

        assertTrue(haetut.containsAll(pitaisOlla));
        assertTrue(pitaisOlla.containsAll(haetut));

        haetut = engine.getPakollisetKentat(Viitetyyppi.article);
        pitaisOlla = EnumSet.of(Kentta.author, Kentta.title, Kentta.journal, Kentta.year);

        assertTrue(haetut.containsAll(pitaisOlla));
        assertTrue(pitaisOlla.containsAll(haetut));

        haetut = engine.getPakollisetKentat(Viitetyyppi.inproceedings);
        pitaisOlla = EnumSet.of(Kentta.author, Kentta.title, Kentta.booktitle, Kentta.year);

        assertTrue(haetut.containsAll(pitaisOlla));
        assertTrue(pitaisOlla.containsAll(haetut));

        assertNull(engine.getPakollisetKentat(null));
    }

    public void testPalautetaanEiPakollisetKentat() {
        Set<Kentta> haetut = engine.getEiPakollisetKentat(Viitetyyppi.book);
        Set<Kentta> pitaisOlla = EnumSet.of(Kentta.volume, Kentta.number, Kentta.pages, Kentta.month,
                Kentta.note, Kentta.key, Kentta.series, Kentta.address, Kentta.edition);

        assertTrue(haetut.containsAll(pitaisOlla));
        assertTrue(pitaisOlla.containsAll(haetut));

        haetut = engine.getEiPakollisetKentat(Viitetyyppi.article);
        pitaisOlla = EnumSet.of(Kentta.volume, Kentta.number, Kentta.pages, Kentta.month, Kentta.note, Kentta.key);

        assertTrue(haetut.containsAll(pitaisOlla));
        assertTrue(pitaisOlla.containsAll(haetut));

        haetut = engine.getEiPakollisetKentat(Viitetyyppi.inproceedings);
        pitaisOlla = EnumSet.of(Kentta.editor, Kentta.publisher, Kentta.volume, Kentta.number, Kentta.pages, Kentta.month,
                Kentta.note, Kentta.key, Kentta.series, Kentta.address, Kentta.edition, Kentta.journal,
                Kentta.organization);

        assertTrue(haetut.containsAll(pitaisOlla));
        assertTrue(pitaisOlla.containsAll(haetut));

        assertNull(engine.getEiPakollisetKentat(null));
    }

    public void testHakeeViitteetTietokannasta() {
        engine.getViitteet();
        verify(db).getAllEntries();
    }
    
    public void testHakuPalauttaaListanViitteista() {
        ArrayList<Viite> viitteet = new ArrayList();
        when(db.getAllEntries()).thenReturn(viitteet);
        assertEquals(viitteet, engine.getViitteet());
    }
    
    public void testLuoTietokannastaHaetuistaViitteistaTaulukonOikein() {
        Viite v = new Article(new Syotetarkastaja());
        v.lisaaKentta(Kentta.author, "Pekka Pekkarinen");
        v.lisaaKentta(Kentta.year, "1987");
        v.lisaaKentta(Kentta.journal, "Lehti");
        v.lisaaKentta(Kentta.title, "Nimi");
        v.lisaaCitationKey("ckey");
        
        Viite v2 = new Book(new Syotetarkastaja());
        v2.lisaaKentta(Kentta.author, "Pekka Maurinen");
        v2.lisaaKentta(Kentta.year, "1956");
        v2.lisaaKentta(Kentta.publisher, "Julkaisija");
        v2.lisaaKentta(Kentta.title, "KirjanNimi");
        v2.lisaaCitationKey("ckey2");
        
        ArrayList<Viite> viitteet = new ArrayList();
        viitteet.add(v);
        viitteet.add(v2);
        
        when(db.getAllEntries()).thenReturn(viitteet);
        
        String[] lista = engine.listaaKaikkiViitteet();
        assertEquals(2, lista.length);
        assertEquals(lista[0], v.toString());
        assertEquals(lista[1], v2.toString());
    }

    public void testHakeeKentatOikein() {
        Viite v = new Article(new Syotetarkastaja());
        v.lisaaKentta(Kentta.author, "Pekka Pekkarinen");
        v.lisaaKentta(Kentta.year, "1987");
        v.lisaaKentta(Kentta.journal, "Lehti");
        v.lisaaKentta(Kentta.title, "Nimi");
        
        when(db.getEntry("ckey")).thenReturn(v);
        
        assertEquals("Pekka Pekkarinen", engine.getKentat("ckey").get(Kentta.author));
        assertEquals("1987", engine.getKentat("ckey").get(Kentta.year));
        assertEquals("Lehti", engine.getKentat("ckey").get(Kentta.journal));
        assertEquals("Nimi", engine.getKentat("ckey").get(Kentta.title));
        assertEquals(null, engine.getKentat("ckey").get(Kentta.annote));
    }
    
    public void testPalauttaaNullJosViitettaEiLoydy() {
        when(db.getEntry("ckey")).thenReturn(null);
        assertNull(engine.getKentat("ckey"));
    }
    
    public void testHakeeViitetyypinOikein() {
        when(db.getEntry("ckey")).thenReturn(new Article(new Syotetarkastaja()));
        when(db.getEntry("ckey2")).thenReturn(new Book(new Syotetarkastaja()));
        when(db.getEntry("ckey3")).thenReturn(new InProceedings(new Syotetarkastaja()));
        when(db.getEntry("ckey4")).thenReturn(null);
        assertEquals(Viitetyyppi.article, engine.getViitetyyppi("ckey"));
        assertEquals(Viitetyyppi.book, engine.getViitetyyppi("ckey2"));
        assertEquals(Viitetyyppi.inproceedings, engine.getViitetyyppi("ckey3"));
        assertNull(engine.getViitetyyppi("ckey4"));
    }
    
    public void testTaginLisaysSaaAikaanTietokantakyselyn() throws NamingException, SQLException {
        engine.addTagi("ckey", "tagi");
        verify(db).addTag("ckey", "tagi");
    }
    
    public void testViitteeseenLiittyvienTagienHaku() {
        engine.getTagsByViite("ckey");
        verify(db).getTagsByViite("ckey");
    }
    
    public void testKaikkiTagitHaetaan() {
        ArrayList<String> tagit = new ArrayList();
        tagit.add("tag1");
        tagit.add("tag2");
        
        when(db.getTagit("")).thenReturn(tagit);
        
        assertTrue(tagit.equals(engine.getTagit()));
    }
    
    public void testTagiinKuuluvatViitteetHaetaanOikein() {
        Viite v = new Article(new Syotetarkastaja());
        v.lisaaKentta(Kentta.author, "Pekka Pekkarinen");
        v.lisaaKentta(Kentta.year, "1987");
        v.lisaaKentta(Kentta.journal, "Lehti");
        v.lisaaKentta(Kentta.title, "Nimi");
        v.lisaaCitationKey("ckey");
        
        Viite v2 = new Book(new Syotetarkastaja());
        v2.lisaaKentta(Kentta.author, "Pekka Maurinen");
        v2.lisaaKentta(Kentta.year, "1956");
        v2.lisaaKentta(Kentta.publisher, "Julkaisija");
        v2.lisaaKentta(Kentta.title, "KirjanNimi");
        v2.lisaaCitationKey("ckey2");
        
        ArrayList<Viite> viitteet = new ArrayList();
        viitteet.add(v);
        viitteet.add(v2);
        
        when(db.listByTag("tag")).thenReturn(viitteet);
        
        String[] pitaisiPalauttaa = {v.toString(), v2.toString()};
        
        assertTrue(Arrays.equals(pitaisiPalauttaa, engine.listaaByTag("tag")));
    }
    
    public void testPalautetaanTyhjaTaulukkoKunTagiaEiOle() {
        String[] asd = engine.listaaByTag("xokwybpb");
        assertEquals(0, asd.length);
    }
    
    public void testViitteeseenKuuluvatTagitPalautuvatOikein() {
        Viite v = new Article(new Syotetarkastaja());
        v.lisaaKentta(Kentta.author, "Pekka Pekkarinen");
        v.lisaaKentta(Kentta.year, "1987");
        v.lisaaKentta(Kentta.journal, "Lehti");
        v.lisaaKentta(Kentta.title, "Nimi");
        v.lisaaCitationKey("ckey");
        
        engine.addTagi("ckey", "test1");
        engine.addTagi("ckey", "test2");
                
        ArrayList<String> tagit = new ArrayList<>();
        tagit.add("test1");
        tagit.add("test2");
        
        when(db.getTagsByViite("ckey")).thenReturn(tagit);
        
        ArrayList<String> haku = engine.getTagsByViite("ckey");
        
        assertEquals(haku.get(0), "test1");
        assertEquals(haku.get(1), "test2");
    }
    
    public void testTaginPoistoViitteeltaAiheuttaaKyselyn() {
        engine.removeTagi("A77", "test");
        verify(db).removeTagFromViite("A77","test");
    }
    
    public void testTagiPoistuuViitteeltaToivotusti() {
        Viite v = new Article(new Syotetarkastaja());
        v.lisaaKentta(Kentta.author, "Pekka Pekkarinen");
        v.lisaaKentta(Kentta.year, "1987");
        v.lisaaKentta(Kentta.journal, "Lehti");
        v.lisaaKentta(Kentta.title, "Nimi");
        v.lisaaCitationKey("ckey");

        engine.addTagi("ckey", "test1");
        engine.addTagi("ckey", "test2");
              
        ArrayList<String> tagit = new ArrayList<>();
        tagit.add("test2");
        
        when(db.getTagsByViite("ckey")).thenReturn(tagit);
        
        engine.removeTagi("ckey", "test1");
        
        assertEquals(tagit, engine.getTagsByViite("ckey"));
    }
    
}
