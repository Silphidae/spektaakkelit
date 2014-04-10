package Syotetarkistus;

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
        assertFalse(tarkastaja.tarkastaAddress(""));
    }

    public void testAnnote() {
        assertTrue(tarkastaja.tarkastaAnnote("Merkintä"));
        assertFalse(tarkastaja.tarkastaAnnote(""));
    }

    public void testAuthor() {
        assertTrue(tarkastaja.tarkastaAuthor("Pekka"));
        assertFalse(tarkastaja.tarkastaAuthor(""));
    }

    public void testBooktitle() {
        assertTrue(tarkastaja.tarkastaBooktitle("Nimi"));
        assertFalse(tarkastaja.tarkastaBooktitle(""));
    }

    public void testChapter() {
        assertTrue(tarkastaja.tarkastaChapter("12"));
        assertFalse(tarkastaja.tarkastaChapter("-3"));
        assertFalse(tarkastaja.tarkastaChapter("Luku 5"));
    }

    public void testCrossref() {
        assertTrue(tarkastaja.tarkastaCrossref("HaKu2012"));
        assertFalse(tarkastaja.tarkastaCrossref(""));
    }

    public void testEdition() {
        assertTrue(tarkastaja.tarkastaEdition("Painos"));
        assertFalse(tarkastaja.tarkastaEdition(""));
    }

    public void testEditor() {
        assertTrue(tarkastaja.tarkastaEditor("Nimi"));
        assertFalse(tarkastaja.tarkastaEditor(""));
    }

    public void testEprint() {
        assertTrue(tarkastaja.tarkastaEprint("Eprint"));
        assertFalse(tarkastaja.tarkastaEprint(""));
    }

    public void testHowpublished() {
        assertTrue(tarkastaja.tarkastaHowpublished("Läpyskä"));
        assertFalse(tarkastaja.tarkastaHowpublished(""));
    }

    public void testInstitution() {
        assertTrue(tarkastaja.tarkastaInstitution("Instituutio"));
        assertFalse(tarkastaja.tarkastaInstitution(""));
    }

    public void testJournal() {
        assertTrue(tarkastaja.tarkastaJournal("Studia Logica"));
        assertFalse(tarkastaja.tarkastaJournal(""));
    }

    public void testKey() {
        assertTrue(tarkastaja.tarkastaKey("Avain"));
        assertFalse(tarkastaja.tarkastaKey(""));
    }

    public void testMonth() {
        assertTrue(tarkastaja.tarkastaMonth("Tämä ei ole kuukausi"));
        assertFalse(tarkastaja.tarkastaMonth(""));
    }
    
    public void testNote() {
        assertTrue(tarkastaja.tarkastaNote("Merkintä"));
        assertFalse(tarkastaja.tarkastaNote(""));
    }

    public void testNumber() {
        assertTrue(tarkastaja.tarkastaNumber("4"));
        assertFalse(tarkastaja.tarkastaNumber("-2"));
        assertFalse(tarkastaja.tarkastaNumber("viisi"));
    }
    
    public void testOrganization() {
        assertTrue(tarkastaja.tarkastaOrganization("Organisaatio"));
        assertFalse(tarkastaja.tarkastaOrganization(""));
    }

    public void testPages() {
        assertTrue(tarkastaja.tarkastaPages("123-234"));
        assertTrue(tarkastaja.tarkastaPages("123+"));
        assertTrue(tarkastaja.tarkastaPages("456"));
        assertFalse(tarkastaja.tarkastaPages("34-23"));
        assertFalse(tarkastaja.tarkastaPages("-45"));
        assertFalse(tarkastaja.tarkastaPages("-3-45"));
        assertFalse(tarkastaja.tarkastaPages("-45+"));
    }

    public void testPublisher() {
        assertTrue(tarkastaja.tarkastaPublisher("WSOY"));
        assertFalse(tarkastaja.tarkastaPublisher(""));
    }
    
    public void testSchool() {
        assertTrue(tarkastaja.tarkastaSchool("Koulu"));
        assertFalse(tarkastaja.tarkastaSchool(""));
    }
    
    public void testSeries() {
        assertTrue(tarkastaja.tarkastaSeries("Sarja"));
        assertFalse(tarkastaja.tarkastaSeries(""));
    }

    public void testTitle() {
        assertTrue(tarkastaja.tarkastaTitle("Banjonsoiton sata vuotta"));
        assertFalse(tarkastaja.tarkastaTitle(""));
    }
    
    public void testType() {
        assertTrue(tarkastaja.tarkastaType("Tyyppi"));
        assertFalse(tarkastaja.tarkastaType(""));
    }
    
    public void testUrl() {
        assertTrue(tarkastaja.tarkastaUrl("http://cs.helsinki.fi"));
        assertFalse(tarkastaja.tarkastaUrl(".fi"));
    }
    
    public void testVolume() {
        assertTrue(tarkastaja.tarkastaVolume("4"));
        assertFalse(tarkastaja.tarkastaVolume("-2"));
        assertFalse(tarkastaja.tarkastaVolume("Volume"));
    }

    public void testYear() {
        assertTrue(tarkastaja.tarkastaYear("1756"));
        assertFalse(tarkastaja.tarkastaYear("1400pekka"));
    }

}
