
package Avaingenerointi;

import Database.Database;
import domain.Kentta;
import domain.Viite;

public class Avaingeneraattori {
    Database db;
    
    public Avaingeneraattori(Database db){
        this.db = db;
    }
    
    public String luoAvain(Viite viite){
        String avain = "";
        String nimi = "";
        String vuosi = viite.getKentanSisalto(Kentta.year);
        
        if (viite.getKentanSisalto(Kentta.author)==null){
            nimi = viite.getKentanSisalto(Kentta.editor);
        } else {
            nimi = viite.getKentanSisalto(Kentta.author);
        }
        
        avain += lyhennaNimet(nimi);
        avain += vuosi.substring(vuosi.length()-2);
        
        int samat = tarkistaSamat(lyhennaNimet(nimi), viite.getKentanSisalto(Kentta.year));
        if (samat>0) avain += "-" + samat;
                
        avain = avain.replaceAll("[, ]", "");
        return avain;
    }
    
    public String lyhennaNimet(String nimi){
        String lyhenne = "";
        String[] nimet = nimi.split(" and ");
        
        for (String s : nimet){
            s = poistaSopimattomatMerkit(s);
            if (s.contains(", ")){
                lyhenne += s.substring(0,1);
            } else {
                s = s.replaceAll("[a-z ]", "");
                lyhenne += s.substring(s.length()-1);
            }
        }
        
        return lyhenne;
    }
    
    private String poistaSopimattomatMerkit(String nimi){
        String[] eiSallitutMerkit = {"@", "\'","\"", "#", "}", "~", "%", "\\\\", "\\{"};
        for (String a : eiSallitutMerkit){
            nimi = nimi.replaceAll(a, "");
        }
        
        return nimi;
    }
    
    private int tarkistaSamat(String nimi, String vuosi){
        int samat = 0;
        for (int i=0; i<db.getSize(); i++){
            String author = db.getEntry(i).getKentanSisalto(Kentta.author);
            String year = db.getEntry(i).getKentanSisalto(Kentta.year);
            if (nimi.equals(lyhennaNimet(author)) && vuosi.equals(year)){
                samat++;
            }
        }
        return samat;
    }
}
