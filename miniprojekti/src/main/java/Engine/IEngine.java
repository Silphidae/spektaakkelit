
package Engine;

import java.util.ArrayList;

public interface IEngine {
    //Halutaan palauttavan listan virheistä
    public ArrayList<String> lisaaArticle(
            String citationKey,
            String author,
            String title,
            String journal,
            int volume,
            int number,
            int year,
            int page1,
            int page2
    );
    
    public String[] listaaKaikkiViitteet();
    
    public void poistaViite(int i);
}
