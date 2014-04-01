package miniprojekti;

import java.util.ArrayList;
import java.util.Calendar;

public class Syotetarkastaja {

    private ArrayList<String> virheet = new ArrayList<String>();

    public boolean tarkastaCitationKey(String citationKey) {
        String[] eiSallitutMerkit = {"@", "\'", ",", "\\", "#", "}", "{", "~", "%", " "};

        //Tarkastaa sisältääkö merkkejä joita ei sallita
        for (String merkki : eiSallitutMerkit) {
            if (citationKey.contains(merkki)) {
                virheet.add("Citation key sisältää merkin jota ei sallita.");
                return false;
            }
        }

        return true;
    }

    public boolean tarkastaAuthor(String author) {
        return true;
    }

    public boolean tarkastaTitle(String title) {
        return true;
    }

    public boolean tarkastaJournal(String journal) {
        return true;
    }

    public boolean tarkastaVolume(int volume) {
        return true;
    }

    public boolean tarkastaNumber(int number) {
        return true;
    }

    public boolean tarkastaYear(int year) {
        int vuosiAlaraja = 1500;
        int tamaVuosi = Calendar.getInstance().get(Calendar.YEAR); //Hakee nykyisen 
        if (year < vuosiAlaraja || year > tamaVuosi) {
            virheet.add("Vuoden pitää olla väliltä " + vuosiAlaraja + "-" + tamaVuosi);
            return false;
        }
        
        return true;
    }

    public boolean tarkastaPage1Page2(int page1, int page2) {
        if (page2 < page1) {
            virheet.add("Sivu2 ei saa olla pienempi kuin sivu1");
            return false;
        }

        return true;
    }

    public boolean tarkastaPublisher(String publisher) {
        return true;
    }

    public boolean tarkastaAddress(String address) {
        return true;
    }

    public ArrayList<String> getVirheet() {
        return virheet;
    }

}
