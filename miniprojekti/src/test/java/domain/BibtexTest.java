package domain;

import Database.Database;
import Engine.EngineStub;
import Engine.IEngine;
import Syotetarkistus.Syotetarkastaja;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import junit.framework.TestCase;
import static org.mockito.Mockito.*;

public class BibtexTest extends TestCase {

    private Bibtex bibtex;
    private IEngine engine;

    public BibtexTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        engine = mock(IEngine.class);
        bibtex = new Bibtex(engine);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testFilenLuonti() throws FileNotFoundException {
        Viite viite1 = new Book(new Syotetarkastaja());
        viite1.lisaaCitationKey("foxis");
        viite1.lisaaKentta(Kentta.author, "Örnävesi-Mäyrä-Mäkinen Otto");
        viite1.lisaaKentta(Kentta.title, "Öljynjalostustaito käänteisesti");

        ArrayList<Viite> viitteet = new ArrayList<>();
        viitteet.add(viite1);

        when(engine.getViitteet()).thenReturn(viitteet);

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
        viite2.lisaaCitationKey("jai23");
        viite2.lisaaKentta(Kentta.author, "Kalle Rääppäinen");
        viite2.lisaaKentta(Kentta.title, "Raparperin maistelu");
        Viite viite3 = new Article(new Syotetarkastaja());
        viite3.lisaaCitationKey("kui44");
        viite3.lisaaKentta(Kentta.author, "Itse Tekijä");
        viite3.lisaaKentta(Kentta.title, "Oman tiedon taito");

        ArrayList<Viite> viitteet = new ArrayList<>();
        viitteet.add(viite2);
        viitteet.add(viite3);

        when(engine.getViitteet()).thenReturn(viitteet);

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
