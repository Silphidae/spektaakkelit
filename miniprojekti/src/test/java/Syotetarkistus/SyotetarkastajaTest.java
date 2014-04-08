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
        //TODO
    }

    public void testAuthor() {
        assertTrue(tarkastaja.tarkastaAuthor("Pekka"));
        assertFalse(tarkastaja.tarkastaAuthor(""));
    }
    
    public void testBooktitle() {
        //TODO
    }

    public void testTitle() {
        assertTrue(tarkastaja.tarkastaTitle("Banjonsoiton sata vuotta"));
        assertFalse(tarkastaja.tarkastaTitle(""));
    }

    public void testJournal() {
        assertTrue(tarkastaja.tarkastaJournal("Studia Logica"));
        assertFalse(tarkastaja.tarkastaJournal(""));
    }

    public void testVolume() {
        assertTrue(tarkastaja.tarkastaVolume("4"));
        assertFalse(tarkastaja.tarkastaVolume("-2"));
    }

    public void testNumber() {
        assertTrue(tarkastaja.tarkastaNumber("4"));
        assertFalse(tarkastaja.tarkastaNumber("-2"));
    }

    public void testYear() {
        assertTrue(tarkastaja.tarkastaYear("1756"));
        assertFalse(tarkastaja.tarkastaYear("1400pekka"));
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
}
