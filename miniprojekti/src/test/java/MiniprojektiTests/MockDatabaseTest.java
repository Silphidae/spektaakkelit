/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MiniprojektiTests;

import Database.Database;
import Database.MockDatabase;
import domain.Article;
import Syotetarkistus.Syotetarkastaja;
import domain.Viite;
import junit.framework.TestCase;

/**
 *
 * @author tv
 */
public class MockDatabaseTest extends TestCase {

    private Database db;
    private Syotetarkastaja tarkastaja;

    public MockDatabaseTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        db = new MockDatabase();
        tarkastaja = new Syotetarkastaja();
        
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    // TODO add test methods here. The name must begin with 'test'. For example:
    // public void testHello() {}

    public void testViitteenLisaysJaHaku() {
        Viite viite = new Article(tarkastaja);
        db.insertEntry(viite);
        assertEquals(viite, db.getEntry(0));
    }

    public void testViitteenPoisto() {
        Viite viite = new Article(tarkastaja);
        db.insertEntry(viite);
        Viite viite2 = new Article(tarkastaja);
        db.insertEntry(viite2);
        Viite viite3 = new Article(tarkastaja);
        db.insertEntry(viite3);

        db.removeEntry(1);
        assertEquals(viite, db.getEntry(0));
        assertNotSame(viite2, db.getEntry(1));
        assertEquals(viite3, db.getEntry(1));
    }
    
    public void testTietokannanKoko() {
        Viite viite = new Article(tarkastaja);
        db.insertEntry(viite);
        Viite viite2 = new Article(tarkastaja);
        db.insertEntry(viite2);
        Viite viite3 = new Article(tarkastaja);
        db.insertEntry(viite3);
        
        assertEquals(3, db.getSize());
    }
}
