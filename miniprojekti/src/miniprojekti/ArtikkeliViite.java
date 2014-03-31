package miniprojekti;

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
    
    
    // User story 2 ?
    @Override
    public String toString() {
        return "";
    }
    
}
