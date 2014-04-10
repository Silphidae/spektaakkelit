package Syotetarkistus;

import domain.Kentta;
import junit.framework.TestCase;

public class SyotetarkastajaTest extends TestCase {

    Syotetarkastaja tarkastaja;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        tarkastaja = new Syotetarkastaja();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testCitationKey() {
        assertTrue(tarkastaja.tarkastaCitationKey("pekka01"));
        assertFalse(tarkastaja.tarkastaCitationKey("#@"));
    }

    public void testAddress() {
        assertTrue(tarkastaja.tarkastaAddress("Kumpulan kampus"));
        assertTrue(tarkastaja.tarkasta(Kentta.address, "Kumpulan kampus"));
        assertFalse(tarkastaja.tarkastaAddress(""));
    }

    public void testAnnote() {
        assertTrue(tarkastaja.tarkastaAnnote("Merkintä"));
        assertTrue(tarkastaja.tarkasta(Kentta.annote, "Merkintä"));
        assertFalse(tarkastaja.tarkastaAnnote(""));
    }

    public void testAuthor() {
        assertTrue(tarkastaja.tarkastaAuthor("Pekka"));
        assertTrue(tarkastaja.tarkasta(Kentta.author, "Pekka"));
        assertFalse(tarkastaja.tarkastaAuthor(""));
    }

    public void testBooktitle() {
        assertTrue(tarkastaja.tarkastaBooktitle("Nimi"));
        assertTrue(tarkastaja.tarkasta(Kentta.booktitle, "Nimi"));
        assertFalse(tarkastaja.tarkastaBooktitle(""));
    }

    public void testChapter() {
        assertTrue(tarkastaja.tarkastaChapter("12"));
        assertTrue(tarkastaja.tarkastaChapter("0"));
        assertFalse(tarkastaja.tarkastaChapter("-3"));
        assertFalse(tarkastaja.tarkastaChapter("Luku 5"));
        assertTrue(tarkastaja.tarkasta(Kentta.chapter, "5"));
    }

    public void testCrossref() {
        assertTrue(tarkastaja.tarkastaCrossref("HaKu2012"));
        assertFalse(tarkastaja.tarkastaCrossref(""));
        assertTrue(tarkastaja.tarkasta(Kentta.crossref, "5"));
    }

    public void testEdition() {
        assertTrue(tarkastaja.tarkastaEdition("Painos"));
        assertFalse(tarkastaja.tarkastaEdition(""));
        assertTrue(tarkastaja.tarkasta(Kentta.edition, "5"));
    }

    public void testEditor() {
        assertTrue(tarkastaja.tarkastaEditor("Nimi"));
        assertFalse(tarkastaja.tarkastaEditor(""));
        assertTrue(tarkastaja.tarkasta(Kentta.editor, "Julkaisija"));
    }

    public void testEprint() {
        assertTrue(tarkastaja.tarkastaEprint("Eprint"));
        assertFalse(tarkastaja.tarkastaEprint(""));
        assertTrue(tarkastaja.tarkasta(Kentta.eprint, "Eprint"));
    }

    public void testHowpublished() {
        assertTrue(tarkastaja.tarkastaHowpublished("Läpyskä"));
        assertFalse(tarkastaja.tarkastaHowpublished(""));
        assertTrue(tarkastaja.tarkasta(Kentta.howpublished, "Läpyskä"));
    }

    public void testInstitution() {
        assertTrue(tarkastaja.tarkastaInstitution("Instituutio"));
        assertFalse(tarkastaja.tarkastaInstitution(""));
        assertTrue(tarkastaja.tarkasta(Kentta.institution, "Instituutio"));
    }

    public void testJournal() {
        assertTrue(tarkastaja.tarkastaJournal("Studia Logica"));
        assertFalse(tarkastaja.tarkastaJournal(""));
        assertTrue(tarkastaja.tarkasta(Kentta.journal, "Instituutio"));
    }

    public void testKey() {
        assertTrue(tarkastaja.tarkastaKey("Avain"));
        assertFalse(tarkastaja.tarkastaKey(""));
        assertTrue(tarkastaja.tarkasta(Kentta.key, "Avain"));
    }

    public void testMonth() {
        assertTrue(tarkastaja.tarkastaMonth("Tämä ei ole kuukausi"));
        assertFalse(tarkastaja.tarkastaMonth(""));
        assertTrue(tarkastaja.tarkasta(Kentta.month, "Kuukausi"));
    }
    
    public void testNote() {
        assertTrue(tarkastaja.tarkastaNote("Merkintä"));
        assertFalse(tarkastaja.tarkastaNote(""));
        assertTrue(tarkastaja.tarkasta(Kentta.note, "Merkintä"));
    }

    public void testNumber() {
        assertTrue(tarkastaja.tarkastaNumber("4"));
        assertFalse(tarkastaja.tarkastaNumber("0"));
        assertFalse(tarkastaja.tarkastaNumber("-2"));
        assertFalse(tarkastaja.tarkastaNumber("viisi"));
        assertTrue(tarkastaja.tarkasta(Kentta.number, "5"));
    }
    
    public void testOrganization() {
        assertTrue(tarkastaja.tarkastaOrganization("Organisaatio"));
        assertFalse(tarkastaja.tarkastaOrganization(""));
        assertTrue(tarkastaja.tarkasta(Kentta.organization, "5"));
    }

    public void testPages() {
        assertTrue(tarkastaja.tarkastaPages("123-234"));
        assertTrue(tarkastaja.tarkastaPages("123+"));
        assertTrue(tarkastaja.tarkastaPages("456"));
        assertFalse(tarkastaja.tarkastaPages("22-22"));
        assertFalse(tarkastaja.tarkastaPages("34-23"));
        assertFalse(tarkastaja.tarkastaPages("-45"));
        assertFalse(tarkastaja.tarkastaPages("-3-45"));
        assertFalse(tarkastaja.tarkastaPages("-45+"));
        assertTrue(tarkastaja.tarkasta(Kentta.pages, "5"));
    }

    public void testPublisher() {
        assertTrue(tarkastaja.tarkastaPublisher("WSOY"));
        assertFalse(tarkastaja.tarkastaPublisher(""));
        assertTrue(tarkastaja.tarkasta(Kentta.publisher, "5"));
    }
    
    public void testSchool() {
        assertTrue(tarkastaja.tarkastaSchool("Koulu"));
        assertFalse(tarkastaja.tarkastaSchool(""));
        assertTrue(tarkastaja.tarkasta(Kentta.school, "5"));
    }
    
    public void testSeries() {
        assertTrue(tarkastaja.tarkastaSeries("Sarja"));
        assertFalse(tarkastaja.tarkastaSeries(""));
        assertTrue(tarkastaja.tarkasta(Kentta.series, "5"));
    }

    public void testTitle() {
        assertTrue(tarkastaja.tarkastaTitle("Banjonsoiton sata vuotta"));
        assertFalse(tarkastaja.tarkastaTitle(""));
        assertTrue(tarkastaja.tarkasta(Kentta.title, "5"));
    }
    
    public void testType() {
        assertTrue(tarkastaja.tarkastaType("Tyyppi"));
        assertFalse(tarkastaja.tarkastaType(""));
        assertTrue(tarkastaja.tarkasta(Kentta.type, "5"));
    }
    
    public void testUrl() {
        assertTrue(tarkastaja.tarkastaUrl("http://cs.helsinki.fi"));
        assertFalse(tarkastaja.tarkastaUrl(".fi"));
        assertTrue(tarkastaja.tarkasta(Kentta.url, "http://cs.helsinki.fi"));
    }
    
    public void testVolume() {
        assertTrue(tarkastaja.tarkastaVolume("4"));
        assertFalse(tarkastaja.tarkastaVolume("0"));
        assertFalse(tarkastaja.tarkastaVolume("-2"));
        assertFalse(tarkastaja.tarkastaVolume("Volume"));
        assertTrue(tarkastaja.tarkasta(Kentta.volume, "5"));
    }

    public void testYear() {
        assertTrue(tarkastaja.tarkastaYear("1756"));
        assertFalse(tarkastaja.tarkastaYear("1400pekka"));
        assertTrue(tarkastaja.tarkasta(Kentta.year, "5"));
    }

}
