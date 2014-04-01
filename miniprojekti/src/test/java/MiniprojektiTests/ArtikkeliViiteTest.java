/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MiniprojektiTests;

import junit.framework.TestCase;
import miniprojekti.ArtikkeliViite;
import miniprojekti.Viite;

/**
 *
 * @author tv
 */
public class ArtikkeliViiteTest extends TestCase {

    private Viite viite = null;

    public ArtikkeliViiteTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    // TODO add test methods here. The name must begin with 'test'. For example:

    public void testViitteenLuonti() {
        viite = new ArtikkeliViite("key", "testihenkilö", "testaustieto", "valitut palat", 12, 212, 1672, 68, 99, "kustantaja", "osoite");
        assertNotNull(viite);
    }
}
