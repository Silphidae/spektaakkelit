/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MiniprojektiTests;

import Engine.EngineStub;
import Engine.IEngine;
import junit.framework.TestCase;

/**
 *
 * @author tv
 */
public class EngineStubTest extends TestCase {

    private IEngine engine;

    public EngineStubTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        engine = new EngineStub();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    // TODO add test methods here. The name must begin with 'test'. For example:
    // public void testHello() {}

    public void testArtikkelinLisays() {
        engine.lisaaArticle("key", "testihenkilö", "testaustieto", "valitut palat", 12, 212, 1672, 68, 99, "kustantaja", "osoite");
        // tämä kesken.. ei pääse käsiksi tietokantaan..
    }
}
