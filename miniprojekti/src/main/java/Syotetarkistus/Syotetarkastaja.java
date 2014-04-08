package Syotetarkistus;

import domain.Kentta;
import java.util.ArrayList;
import java.util.List;

public class Syotetarkastaja {

    private ArrayList<String> virheet = new ArrayList<>();

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

    public boolean tarkastaChapter(String syote) {
        //TODO
        return true;
    }

    public boolean tarkastaCrossref(String syote) {
        //TODO
        return true;
    }

    public boolean tarkastaEdition(String syote) {
        //TODO
        return true;
    }

    public boolean tarkastaEditor(String syote) {
        //TODO
        return true;
    }

    public boolean tarkastaEprint(String syote) {
        //TODO
        return true;
    }

    public boolean tarkastaHowpublished(String syote) {
        //TODO
        return true;
    }

    public boolean tarkastaInstitution(String syote) {
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

    public boolean tarkastaKey(String syote) {
        //TODO
        return true;
    }

    public boolean tarkastaMonth(String syote) {
        //TODO
        return true;
    }

    public boolean tarkastaNote(String syote) {
        //TODO
        return true;
    }

    public boolean tarkastaNumber(String syote) {
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

    public boolean tarkastaOrganization(String syote) {
        //TODO
        return true;
    }

    public boolean tarkastaPages(String syote) {
        if (syote.matches("[0-9]+-[0-9]+")) {
            String[] sivut = syote.split("-");
            int eka = Integer.parseInt(sivut[0]);
            int toka = Integer.parseInt(sivut[1]);
            if (toka < eka) {
                virheet.add("Jälkimmäisen sivunumeron tulee olla ensimmäistä suurempi.");
                return false;
            }
            return true;
        }
        if (syote.matches("[0-9]+\\+||[0-9]+")) {
            return true;
        }
        virheet.add("Sivujen tulee olla muotoa 123-456, 78+ tai 90");
        return false;
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

    public boolean tarkastaSchool(String syote) {
        //TODO
        return true;
    }

    public boolean tarkastaSeries(String syote) {
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

    public boolean tarkastaType(String syote) {
        //TODO
        return true;
    }

    public boolean tarkastaUrl(String syote) {

        return true;
    }

    public boolean tarkastaVolume(String syote) {
        try {
            int volume = Integer.parseInt(syote);
            if (volume < 0) {
                virheet.add("Volume ei saa olla negatiivinen");
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            virheet.add("Volumen tulee olla positiivinen kokonaisluku.");
            return false;
        }
    }

    public boolean tarkastaYear(String syote) {
        try {
            int year = Integer.parseInt(syote);
            return true;
        } catch (NumberFormatException e) {
            virheet.add("Vuoden tulee olla kokonaisluku.");
            return false;
        }
    }

    public ArrayList<String> getVirheet() {
        return virheet;
    }
}
