/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MiniprojektiTests;

import domain.Article;
import Syotetarkistus.Syotetarkastaja;
import domain.Kentta;
import java.util.EnumSet;
import junit.framework.TestCase;

/**
 *
 * @author tv
 */
public class ArticleTest extends TestCase {

    private Article artikkeli;

    public ArticleTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        artikkeli = new Article(new Syotetarkastaja());
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    // TODO add test methods here. The name must begin with 'test'. For example:
    
    public void testPakollisetKentatOikein() {
        assertEquals(artikkeli.getPakollisetKentat(), 
                EnumSet.of(Kentta.author, Kentta.title, Kentta.journal, Kentta.year));
    }
}
