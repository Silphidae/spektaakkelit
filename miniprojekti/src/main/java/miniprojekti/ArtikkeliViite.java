package miniprojekti;

import java.util.ArrayList;

/**
 *
 */
public class ArtikkeliViite extends Viite {
    
    private String type;
    private String citationKey;
    private String author;
    private String title;
    private String journal;
    private int volume;
    private int number;
    private int year;
    private int page1;
    private int page2;
    private String publisher;
    private String address;
    private Syotetarkastaja tarkastaja;
    
    public ArtikkeliViite(String citationKey, String author, String title, 
            String journal, int volume, int number, int year, int page1, 
            int page2, String publisher, String address) {
        type = "article";
        this.citationKey = citationKey;
        this.author = author;
        this.title = title;
        this.journal = journal;
        this.volume = volume;
        this.number = number;
        this.year = year;
        this.page1 = page1;
        this.page2 = page2;
        this.publisher = publisher;
        this.address = address;
    }
    
    public ArtikkeliViite(Syotetarkastaja tarkastaja) {
        this.tarkastaja = tarkastaja;
    }
    
    public void lisaaCitationKey(String citationKey) {
        if (tarkastaja.tarkastaCitationKey(citationKey)) {
            this.citationKey = citationKey;
        }
    }
    
    public void lisaaAuthor(String author) {
        if (tarkastaja.tarkastaAuthor(author)) {
            this.author = author;
        }
    }
    
    public void lisaaTitle(String title) {
        if (tarkastaja.tarkastaTitle(title)) {
            this.title = title;
        }
    }
    
    public void lisaaJournal(String journal) {
        if (tarkastaja.tarkastaJournal(journal)) {
            this.journal = journal;
        }
    }
    
    public void lisaaVolume(int volume) {
        if (tarkastaja.tarkastaVolume(volume)) {
            this.volume = volume;
        }
    }
    
    public void lisaaNumber(int number) {
        if (tarkastaja.tarkastaNumber(number)) {
            this.number = number;
        }
    }
    
    public void lisaaYear(int year) {
        if (tarkastaja.tarkastaYear(year)) {
            this.year = year;
        }
    }
    
    public void lisaaPage1Page2(int page1, int page2) {
        if (tarkastaja.tarkastaPage1Page2(page1, page2)) {
            this.page1 = page1;
            this.page2 = page2;
        }
    }
    
    public void lisaaPublisher(String publisher) {
        if (tarkastaja.tarkastaPublisher(publisher)) {
            this.publisher = publisher;
        }
    }
    
    public void lisaaAddress(String address) {
        if (tarkastaja.tarkastaAddress(address)) {
            this.address = address;
        }
    }
    
    @Override
    public String toString() {
        return author + ". \"" + title + "\" " + 
                journal + " " + volume + "." + number +
                "(" + year + "): " + page1 + "-" + page2 + 
                ". " + publisher;
    }
    
}
