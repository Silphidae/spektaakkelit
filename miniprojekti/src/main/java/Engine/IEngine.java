package Engine;

import domain.Kentta;
import domain.Viite;
import domain.Viitetyyppi;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

public interface IEngine {

    //Halutaan palauttavan listan virheistä
    public ArrayList<String> lisaaViite(Viitetyyppi tyyppi, Map arvot);

    public String[] listaaKaikkiViitteet();

    public ArrayList<Viite> getViitteet();
    
    public void poistaViite(String ckey);

    public Viitetyyppi[] getViitetyypit();

    public Set<Kentta> getPakollisetKentat(Viitetyyppi tyyppi);

    public Set<Kentta> getEiPakollisetKentat(Viitetyyppi tyyppi);
    
    public EnumMap<Kentta, String> getKentat(String ckey);
    
    public Viitetyyppi getViitetyyppi(String ckey);
    
    public ArrayList<Viite> listByTag(String tag);
    
    public void addTagi(Viite viite, String tagi);
    
    public ArrayList<String> getTagsByViite(Viite viite);
}
