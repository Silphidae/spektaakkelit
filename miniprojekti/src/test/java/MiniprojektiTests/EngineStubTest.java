/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MiniprojektiTests;

import Database.Database;
import Database.MockDatabase;
import Engine.EngineStub;
import Engine.IEngine;
import java.util.ArrayList;
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

    public void testArtikkelinLisays() {
        engine.lisaaArticle("key", "testihenkilö", "testaustieto", "valitut palat", 12, 212, 1672, 68, 99, "kustantaja", "osoite");
        assertNotNull(db.getEntry(0));        
    }
    
    public void testViitteidenListaus() {
            engine.lisaaArticle("key", "testihenkilö", "testaustieto", "valitut palat", 12, 212, 1672, 68, 99, "kustantaja", "osoite");
            engine.lisaaArticle("key2", "testihenkilö2", "testaustieto2", "valitut palat2", 12, 212, 1672, 68, 99, "kustantaja", "osoite2");
            engine.lisaaArticle("key3", "testihenkilö3", "testaustieto3", "valitut palat3", 12, 212, 1672, 68, 99, "kustantaja3", "osoite3");
            
            assertEquals(3, engine.listaaKaikkiViitteet().size());
    }
    
    public void testLisaaArticlePalauttaaNullKunViiteVirheeton() {
        assertNull(engine.lisaaArticle("key", "testihenkilö", "testaustieto", "valitut palat", 12, 212, 1672, 68, 99, "kustantaja", "osoite"));
    }
    
    public void testLisaaArticlePalauttaaListanVirheistaKunSyotetettyVirheellisiaArvoja() {
        ArrayList<String> virheet = engine.lisaaArticle("@%", "", "", "", -12, -212, 0, 2, 1, "", "");
        assertEquals(10, virheet.size());
    }
}
