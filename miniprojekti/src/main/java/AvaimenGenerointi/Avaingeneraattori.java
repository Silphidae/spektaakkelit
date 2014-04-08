
package AvaimenGenerointi;

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
        
        if (viite.getKentanSisalto(Kentta.author)==null){
            nimi = viite.getKentanSisalto(Kentta.editor);
            avain += lyhennaNimi(nimi);
        } else {
            nimi = viite.getKentanSisalto(Kentta.author);
            avain += lyhennaNimi(nimi);
        }
        avain += viite.getKentanSisalto(Kentta.year);
        
        int samat = tarkistaSamat(nimi, viite.getKentanSisalto(Kentta.year));
        if (samat>0) avain += "-" + samat;
        
        return avain;
    }
    
    public String lyhennaNimi(String nimi){
        int i = 0;
        while (i<nimi.length() && nimi.charAt(i)!=' '){
            i++;
        }
        String lyhenne = nimi.substring(Math.min(i+1, nimi.length()), Math.min(i+3, nimi.length()));
        
        lyhenne += nimi.substring(0, Math.min(2, nimi.length()));
        
        return lyhenne;
    }
    
    private int tarkistaSamat(String nimi, String vuosi){
        int samat = 0;
        for (int i=0; i<db.getSize(); i++){
            String author = db.getEntry(i).getKentanSisalto(Kentta.author);
            String year = db.getEntry(i).getKentanSisalto(Kentta.year);
            if (nimi.equals(author) && vuosi.equals(year)){
                samat++;
            }
        }
        return samat;
    }
}
