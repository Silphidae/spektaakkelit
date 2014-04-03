package Syotetarkistus;


import domain.Kentta;
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
    
    public boolean tarkasta(Kentta kentta, String syote) {
        switch(kentta) {
            case address:
                return tarkastaAddress(syote);
            case annote:
                //TODO
            case author:
                return tarkastaAuthor(syote);
            case booktitle:
                //TODO
            case chapter:
                //TODO
            case crossref:
                //TODO
            case edition:
                //TODO
            case editor:
                //TODO
            case eprint:
                //TODO
            case howpublished:
                //TODO
            case institution:
                //TODO
            case journal:
                return tarkastaJournal(syote);
            case key:
                //TODO
            case month:
                //TODO
            case note:
                //TODO
            case number:
                return tarkastaNumber(Integer.parseInt(syote));
            case organization:
                //TODO
            case pages:
                String[] sivut = syote.split("-");
                return tarkastaPage1Page2(Integer.parseInt(sivut[0]), Integer.parseInt(sivut[1]));
            case publisher:
                //TODO
                return tarkastaPublisher(syote);
            case school:
                //TODO
            case series:
                //TODO
            case title:
                return tarkastaTitle(syote);
            case type:
                //TODO
            case url:
                //TODO
            case volume:
                return tarkastaVolume(Integer.parseInt(syote));
            case year:
                return tarkastaYear(Integer.parseInt(syote));
        }
        return false;
    }

    public boolean tarkastaAuthor(String author) {
        if (author.isEmpty()) {
            virheet.add("Kirjoittajan nimi ei saa olla tyhjä");
            return false;
        }
        return true;
    }

    public boolean tarkastaTitle(String title) {
        if (title.isEmpty()) {
            virheet.add("Otsikko ei saa olla tyhjä");
            return false;
        }
        return true;
    }

    public boolean tarkastaJournal(String journal) {
        if (journal.isEmpty()) {
            virheet.add("Lehden nimi ei saa olla tyhjä");
            return false;
        }
        return true;
    }

    public boolean tarkastaVolume(int volume) {
        if (volume < 0) {
            virheet.add("Vuosikerta ei saa olla negatiivinen");
            return false;
        }
        return true;
    }

    public boolean tarkastaNumber(int number) {
        if (number < 0) {
            virheet.add("Lehden numero ei saa olla negatiivinen");
            return false;
        }
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
        if (publisher.isEmpty()) {
            virheet.add("Julkaisija ei saa olla tyhjä");
            return false;
        }
        return true;
    }

    public boolean tarkastaAddress(String address) {
        if (address.isEmpty()) {
            virheet.add("Osoite ei saa olla tyhjä");
            return false;
        }
        return true;
    }

    public ArrayList<String> getVirheet() {
        return virheet;
    }

}
