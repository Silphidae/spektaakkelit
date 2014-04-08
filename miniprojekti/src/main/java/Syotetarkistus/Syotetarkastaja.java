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
        switch (kentta) {
            case address:
                return tarkastaAddress(syote);
            case annote:
                return tarkastaAnnote(syote);
            case author:
                return tarkastaAuthor(syote);
            case booktitle:
                return tarkastaBooktitle(syote);
            case chapter:
                return tarkastaChapter(syote);
            case crossref:
                return tarkastaCrossref(syote);
            case edition:
                return tarkastaEdition(syote);
            case editor:
                return tarkastaEditor(syote);
            case eprint:
                return tarkastaEprint(syote);
            case howpublished:
                return tarkastaHowpublished(syote);
            case institution:
                return tarkastaInstitution(syote);
            case journal:
                return tarkastaJournal(syote);
            case key:
                return tarkastaKey(syote);
            case month:
                return tarkastaMonth(syote);
            case note:
                return tarkastaNote(syote);
            case number:
                return tarkastaNumber(syote);
            case organization:
                return tarkastaOrganization(syote);
            case pages:
                return tarkastaPages(syote);
            case publisher:
                return tarkastaPublisher(syote);
            case school:
                return tarkastaSchool(syote);
            case series:
                return tarkastaSeries(syote);
            case title:
                return tarkastaTitle(syote);
            case type:
                return tarkastaType(syote);
            case url:
                return tarkastaUrl(syote);
            case volume:
                return tarkastaVolume(syote);
            case year:
                return tarkastaYear(syote);
        }
        return false;
    }

    public boolean tarkastaAddress(String address) {
        if (address.isEmpty()) {
            virheet.add("Osoite ei saa olla tyhjä");
            return false;
        }
        return true;
    }

    public boolean tarkastaAnnote(String annote) {
        //TODO
        return true;
    }

    public boolean tarkastaAuthor(String author) {
        if (author.isEmpty()) {
            virheet.add("Kirjoittajan nimi ei saa olla tyhjä");
            return false;
        }
        return true;
    }

    public boolean tarkastaBooktitle(String booktitle) {
        //TODO
        return true;
    }

    private boolean tarkastaChapter(String syote) {
        //TODO
        return true;
    }

    private boolean tarkastaCrossref(String syote) {
        //TODO
        return true;
    }

    private boolean tarkastaEdition(String syote) {
        //TODO
        return true;
    }

    private boolean tarkastaEditor(String syote) {
        //TODO
        return true;
    }

    private boolean tarkastaEprint(String syote) {
        //TODO
        return true;
    }

    private boolean tarkastaHowpublished(String syote) {
        //TODO
        return true;
    }

    private boolean tarkastaInstitution(String syote) {
        //TODO
        return true;
    }

    public boolean tarkastaJournal(String journal) {
        if (journal.isEmpty()) {
            virheet.add("Lehden nimi ei saa olla tyhjä");
            return false;
        }
        return true;
    }

    private boolean tarkastaKey(String syote) {
        //TODO
        return true;
    }

    private boolean tarkastaMonth(String syote) {
        //TODO
        return true;
    }

    private boolean tarkastaNote(String syote) {
        //TODO
        return true;
    }

    private boolean tarkastaNumber(String syote) {
        try {
            int number = Integer.parseInt(syote);
            if (number < 0) {
                virheet.add("Lehden numero ei saa olla negatiivinen");
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            virheet.add("Lehden numeron tulee olla positiivinen kokonaisluku.");
            return false;
        }
    }

    private boolean tarkastaOrganization(String syote) {
        //TODO
        return true;
    }

    private boolean tarkastaPages(String syote) {
        //TODO
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

    private boolean tarkastaSchool(String syote) {
        //TODO
        return true;
    }

    private boolean tarkastaSeries(String syote) {
        //TODO
        return true;
    }

    public boolean tarkastaTitle(String title) {
        if (title.isEmpty()) {
            virheet.add("Otsikko ei saa olla tyhjä");
            return false;
        }
        return true;
    }

    private boolean tarkastaType(String syote) {
        //TODO
        return true;
    }

    private boolean tarkastaUrl(String syote) {
        //TODO
        return true;
    }

    private boolean tarkastaVolume(String syote) {
        //TODO
        return true;
    }

    public boolean tarkastaVolume(int volume) {
        if (volume < 0) {
            virheet.add("Vuosikerta ei saa olla negatiivinen");
            return false;
        }
        return true;
    }

    private boolean tarkastaYear(String syote) {
        //TODO
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

    public ArrayList<String> getVirheet() {
        return virheet;
    }
}
