/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MiniprojektiTests;

import domain.Article;
import Syotetarkistus.Syotetarkastaja;
import domain.Viite;
import junit.framework.TestCase;

/**
 *
 * @author tv
 */
public class ArticleTest extends TestCase {

    private Viite viite = null;

    public ArticleTest(String testName) {
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
        viite = new Article(new Syotetarkastaja());
        assertNotNull(viite);
    }
}
