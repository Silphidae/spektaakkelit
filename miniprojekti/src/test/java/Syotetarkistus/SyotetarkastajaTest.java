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
    
    public void testAuthor() {
        assertTrue(tarkastaja.tarkastaAuthor("Pekka"));
        assertFalse(tarkastaja.tarkastaAuthor(""));
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
        assertTrue(tarkastaja.tarkastaVolume(4));
        assertFalse(tarkastaja.tarkastaVolume(-2));
    }
    
    public void testNumber() {
        assertTrue(tarkastaja.tarkastaNumber(4));
        assertFalse(tarkastaja.tarkastaNumber(-2));
    }
    
    public void testYear() {
        assertTrue(tarkastaja.tarkastaYear(1756));
        assertFalse(tarkastaja.tarkastaYear(1400));
    }
    
    public void testPages() {
        assertTrue(tarkastaja.tarkastaPage1Page2(3, 65));
        assertFalse(tarkastaja.tarkastaPage1Page2(54, 32));
    }
    
    public void testPublisher() {
        assertTrue(tarkastaja.tarkastaPublisher("WSOY"));
        assertFalse(tarkastaja.tarkastaPublisher(""));
    }
    
    public void testAddress() {
        assertTrue(tarkastaja.tarkastaAddress("Kumpulan kampus"));
        assertFalse(tarkastaja.tarkastaAddress(""));
    }
}

