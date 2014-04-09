package domain;

import Database.Database;
import Database.MockDatabase;
import Engine.EngineStub;
import Syotetarkistus.Syotetarkastaja;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import junit.framework.TestCase;

public class BibtexTest extends TestCase {

    private Bibtex bibtex;
    private Database db;

    public BibtexTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        db = new MockDatabase();
        bibtex = new Bibtex(new EngineStub(db));
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testFilenLuonti() throws FileNotFoundException {
        Viite viite1 = new Book(new Syotetarkastaja());
        viite1.lisaaViiteavain("foxis");
        viite1.lisaaKentta(Kentta.author, "Örnävesi-Mäyrä-Mäkinen Otto");
        viite1.lisaaKentta(Kentta.title, "Öljynjalostustaito käänteisesti");
        db.insertEntry(viite1);

        Scanner tiedosto = new Scanner(bibtex.generoiTiedosto("bibtex.bib"));
        String luettu = "";
        while (tiedosto.hasNextLine()) {
            luettu += tiedosto.nextLine() + "\n";
        }
        assertEquals("@book{foxis,\nauthor = {\\\"{O}rn\\\"{a}vesi-M\\\"{a}yr\\\"{a}-M\\\"{a}kinen Otto},\n"
                + "title = {\\\"{O}ljynjalostustaito k\\\"{a}\\\"{a}nteisesti},\n}\n\n", luettu);
    }

    public void testFilenLuontiUseammallaViitteella() throws FileNotFoundException {
        Viite viite2 = new InProceedings(new Syotetarkastaja());
        viite2.lisaaViiteavain("jai23");
        viite2.lisaaKentta(Kentta.author, "Kalle Rääppäinen");
        viite2.lisaaKentta(Kentta.title, "Raparperin maistelu");
        Viite viite3 = new Article(new Syotetarkastaja());
        viite3.lisaaViiteavain("kui44");
        viite3.lisaaKentta(Kentta.author, "Itse Tekijä");
        viite3.lisaaKentta(Kentta.title, "Oman tiedon taito");
        db.insertEntry(viite2);
        db.insertEntry(viite3);

        Scanner tiedosto = new Scanner(bibtex.generoiTiedosto("sigproc.bib"));
        String luettu = "";
        while (tiedosto.hasNextLine()) {
            luettu += tiedosto.nextLine() + "\n";
        }
        assertEquals("@inProceedings{jai23,\nauthor = {Kalle R\\\"{a}\\\"{a}pp\\\"{a}inen},\n"
                + "title = {Raparperin maistelu},\n}\n\n@article{kui44,\nauthor = {Itse Tekij\\\"{a}},\n"
                + "title = {Oman tiedon taito},\n}\n\n", luettu);
    }
}