/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine;

import Database.Database;
import Database.MockDatabase;
import Engine.EngineStub;
import Engine.IEngine;
import domain.Kentta;
import domain.Viitetyyppi;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Set;
import junit.framework.TestCase;

/**
 *
 * @author tv
 */
public class EngineStubTest extends TestCase {

    private IEngine engine;
    private Database db = new MockDatabase();

    public EngineStubTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        engine = new EngineStub(db);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    // TODO add test methods here. The name must begin with 'test'. For example:
    // public void testHello() {}

    public void testViitteenLisaysOnnistuu() {
        HashMap<Kentta, String> article = new HashMap();
        article.put(Kentta.author, "etunimi sukunimi");
        article.put(Kentta.year, "1999");
        article.put(Kentta.title, "Otsikko");
        article.put(Kentta.journal, "Paras Lehti");
        engine.lisaaViite(Viitetyyppi.article, article);

        HashMap<Kentta, String> book = article;
        book.put(Kentta.publisher, "Quiui");
        book.remove(Kentta.journal);
        engine.lisaaViite(Viitetyyppi.book, book);

        HashMap<Kentta, String> inproceedings = article;
        inproceedings.put(Kentta.booktitle, "Top Secret");
        engine.lisaaViite(Viitetyyppi.inproceedings, inproceedings);
        
        engine.lisaaViite(null, null);
        
        assertEquals(3, db.getSize());

    }

    public void testViitteidenListausOnnistuu() {
        HashMap<Kentta, String> tiedot = new HashMap();

        tiedot.put(Kentta.author, "etunimi sukunimi");
        tiedot.put(Kentta.year, "1999");
        tiedot.put(Kentta.title, "Otsikko");
        tiedot.put(Kentta.journal, "Paras Lehti");
        engine.lisaaViite(Viitetyyppi.article, tiedot);

        //booktitle on pakollinen kenttä inproceedingissä
        tiedot.put(Kentta.booktitle, "Parasta ikinä");
        engine.lisaaViite(Viitetyyppi.inproceedings, tiedot);

        tiedot.put(Kentta.organization, "Laitos");
        engine.lisaaViite(Viitetyyppi.inproceedings, tiedot);

        assertEquals(3, engine.listaaKaikkiViitteet().length);
    }

    public void testLisaaViitePalauttaaNullKunViiteVirheeton() {
        HashMap<Kentta, String> tiedot = new HashMap();
        tiedot.put(Kentta.author, "etunimi sukunimi");
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
        HashMap<Kentta, String> tiedot = new HashMap();

        tiedot.put(Kentta.author, "etunimi sukunimi");
        tiedot.put(Kentta.year, "1999");
        tiedot.put(Kentta.title, "Otsikko");
        tiedot.put(Kentta.journal, "Paras Lehti");
        engine.lisaaViite(Viitetyyppi.article, tiedot);

        tiedot.put(Kentta.booktitle, "Mahtavaa");
        engine.lisaaViite(Viitetyyppi.inproceedings, tiedot);

        engine.poistaViite(1);

        assertEquals(1, db.getSize());
    }

    public void testPoistaViiteEiPoistaMitaanKunIndeksiaEiOle() {
        HashMap<Kentta, String> tiedot = new HashMap();

        tiedot.put(Kentta.author, "etunimi sukunimi");
        tiedot.put(Kentta.year, "1999");
        tiedot.put(Kentta.title, "Otsikko");
        tiedot.put(Kentta.journal, "Paras Lehti");
        engine.lisaaViite(Viitetyyppi.article, tiedot);

        tiedot.put(Kentta.booktitle, "Muhahaha");
        engine.lisaaViite(Viitetyyppi.inproceedings, tiedot);

        engine.poistaViite(-1);
        engine.poistaViite(2);

        assertEquals(2, engine.listaaKaikkiViitteet().length);
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
}
