/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MiniprojektiTests;

import Database.Database;
import Database.MockDatabase;
import junit.framework.TestCase;
import miniprojekti.ArtikkeliViite;
import miniprojekti.Viite;

/**
 *
 * @author tv
 */
public class MockDatabaseTest extends TestCase {

    private Database db;

    public MockDatabaseTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        db = new MockDatabase();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    // TODO add test methods here. The name must begin with 'test'. For example:
    // public void testHello() {}

    public void testViitteenLisaysJaHaku() {
        Viite viite = new ArtikkeliViite("key", "testihenkilö", "testaustieto", "valitut palat", 12, 212, 1672, 68, 99, "kustantaja", "osoite");
        db.insertEntry(viite);
        assertEquals(viite, db.getEntry(0));
    }

    public void testViitteenPoisto() {
        Viite viite = new ArtikkeliViite("key", "testihenkilö", "testaustieto", "valitut palat", 12, 212, 1672, 68, 99, "kustantaja", "osoite");
        db.insertEntry(viite);
        Viite viite2 = new ArtikkeliViite("key2", "testihenkilö", "testaustieto", "valitut palat", 12, 212, 1672, 68, 99, "kustantaja", "osoite");
        db.insertEntry(viite2);
        Viite viite3 = new ArtikkeliViite("key3", "testihenkilö", "testaustieto", "valitut palat", 12, 212, 1672, 68, 99, "kustantaja", "osoite");
        db.insertEntry(viite3);

        db.removeEntry(1);
        assertEquals(viite, db.getEntry(0));
        assertNotSame(viite2, db.getEntry(1));
        assertEquals(viite3, db.getEntry(1));
    }
}
