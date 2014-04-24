package GUI;

import domain.Kentta;
import domain.Viitetyyppi;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Set;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ToolTipManager;
import javax.swing.text.JTextComponent;

public class NakymaBuilder {

    private static int x = 0;
    private static int y = 0;

    public static void teeNakymaLomakkeelle(JPanel lomake, Set<Kentta> pakollisetKentat, Set<Kentta> muutKentat, Viitetyyppi tyyppi, int xArvo, int yArvo, JScrollPane lomakeScroll, boolean muokkaus, ArrayList<String> tagit) {

        x = xArvo;
        y = yArvo;

        lomake.removeAll();
        lomake.setLayout(new GridBagLayout());

        //luodaan alkuun pakolliset kentät tähden kera
        lisaaLomakkeeseen(pakollisetKentat, true, tyyppi, lomake);

        lisaaLomakkeeseen(muutKentat, false, tyyppi, lomake);
        
        if (muokkaus) lisaaTagilista(lomake, tagit);
        //paivitetaan lomake-paneeli ja skrollaus
        lomake.validate();
        lomake.repaint();

        if (lomakeScroll != null) {
            lomakeScroll.validate();
        }
    }

    private static void lisaaLomakkeeseen(Set<Kentta> kentat, boolean pakollinen, Viitetyyppi tyyppi, JPanel lomake) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);

        //Kirjalla täytyy olla joko kirjoittaja tai editori, joten  käydään tämä erikoistapaus
        //läpi ensiksi
        if (tyyppi == Viitetyyppi.book && pakollinen) {
            JComboBox valinta = new JComboBox(new Kentta[]{Kentta.author, Kentta.editor});
            valinta.setName("kirja");
            gbc.anchor = GridBagConstraints.SOUTHEAST;
            lomake.add(valinta, gbc);

            x++;
            gbc.gridx = x;

            JTextArea tekstikentta = new JTextArea(1, 20);
            tekstikentta.setLineWrap(true);
            tekstikentta.setWrapStyleWord(true);
            tekstikentta.setMargin(new Insets(2, 2, 2, 2));
            //asetetaan tekstikentälle sama nimi kuin comboboxilla jotta ne voidaan tunnistaa myöhemmin
            tekstikentta.setName(valinta.getName());
            tekstikentta.setToolTipText("Anna nimet muodossa: 'Etunumi Sukunimi' tai 'Sukunimi, Etunimi' Kirjoita henkilöiden väliin ' and '");
            lomake.add(tekstikentta, gbc);

            x++;
            gbc.gridx = x;

            lomake.add(new JLabel("*"), gbc);

            y++;
            x = 0;

            //lisätään seuraavaksi muut kentät silmukassa joten poistetaan ensin nämä, etteivät tule kahdesti
            kentat.remove(Kentta.author);
            kentat.remove(Kentta.editor);
        }

        for (Kentta kentta : kentat) {
            gbc.gridx = x;
            gbc.gridy = y;

            //kenttien nimet tulevat tekstialueiden viereen
            gbc.anchor = GridBagConstraints.NORTHEAST;

            String nimi = kentta.toString();
            lomake.add(new JLabel(nimi), gbc);

            x++;

            JTextArea tekstikentta = new JTextArea(1, 20);
            tekstikentta.setLineWrap(true);
            tekstikentta.setWrapStyleWord(true);
            tekstikentta.setMargin(new Insets(2, 2, 2, 2));
            tekstikentta.setName(nimi);

            if (kentta == Kentta.pages) {
                tekstikentta.setToolTipText("Anna sivut muodossa: 21, 21-40 tai 21+");
            }

            if (kentta == Kentta.author || kentta == Kentta.editor) {
                tekstikentta.setToolTipText("Anna nimet muodossa: 'Etunumi Sukunimi' tai 'Sukunimi, Etunimi' Kirjoita henkilöiden väliin ' and '");
            }

            gbc.gridx = x;
            lomake.add(tekstikentta, gbc);

            if (pakollinen) {
                x++;
                gbc.gridx = x;

                lomake.add(new JLabel("*"), gbc);
            }

            y++;
            x = 0;
        }

        //tooltip-viesti näytetään heti, kun osoitin tekstikentän päällä
        ToolTipManager.sharedInstance().setInitialDelay(0);
    }

    public static EnumMap<Kentta, String> haeLomakkeenTiedot(JPanel lomake) {
        EnumMap<Kentta, String> lomakkeenSisalto = new EnumMap(Kentta.class);

        Component[] komponentit = lomake.getComponents();

        //näihin tallenetaan kirjalomakkeen comoboxissa valittu kenttä ja comboboxin nimi, jotta
        //voidaan yhdistää ne oikeaan tekstikenttään
        Kentta valittuKentta = null;
        String kirjaCombonNimi = "";

        for (Component komponentti : komponentit) {
            if (komponentti instanceof JComboBox) {
                //kirjan lomakkeessa on combobox, jossa valittuna editor tai author
                JComboBox authorEditor = (JComboBox) komponentti;
                valittuKentta = (Kentta) authorEditor.getSelectedItem();
                kirjaCombonNimi = authorEditor.getName();
            }

            if (komponentti instanceof JTextComponent) {
                Kentta kentta = null;
                String syote = "";

                JTextComponent tekstikentta = (JTextComponent) komponentti;

                if (tekstikentta.getName().equals(kirjaCombonNimi)) {
                    kentta = valittuKentta;
                } else {
                    kentta = Kentta.valueOf(tekstikentta.getName());
                }

                syote = tekstikentta.getText();

                if (!syote.isEmpty()) {
                    lomakkeenSisalto.put(kentta, syote);
                }
            }
        }
        System.out.println("haettiin lomakkeesta: " + lomakkeenSisalto);
        return lomakkeenSisalto;
    }

    public static void taytaLomakkeenTiedot(JPanel lomake, EnumMap<Kentta, String> kentat) {
        EnumMap<Kentta, String> lomakkeenSisalto = new EnumMap(Kentta.class);

        Component[] komponentit = lomake.getComponents();

        //näihin tallenetaan kirjalomakkeen comoboxissa valittu kenttä ja comboboxin nimi, jotta
        //voidaan yhdistää ne oikeaan tekstikenttään
        Kentta valittuKentta = null;
        String kirjaCombonNimi = "";

        for (Component komponentti : komponentit) {
            if (komponentti instanceof JComboBox) {
                //kirjan lomakkeessa on combobox, jossa valittuna editor tai author
                JComboBox authorEditor = (JComboBox) komponentti;
                if (kentat.containsKey(Kentta.editor)) {
                    authorEditor.setSelectedItem(Kentta.editor);
                } else {
                    authorEditor.setSelectedItem(Kentta.author);
                }
            }

            if (komponentti instanceof JTextArea) {
                String nimi = komponentti.getName();
                JTextArea tekstikentta = (JTextArea) komponentti;
                
                //Kirjan erikoistapaus authorille ja editorille
                if (nimi.equals("kirja")) {
                    String authorOrEditor = kentat.get(Kentta.author);
                    if (authorOrEditor == null) authorOrEditor = kentat.get(Kentta.editor);
                    
                    tekstikentta.setText(authorOrEditor);
                }
                else {
                    //Etsitään tekstikenttää vastaava kenttä
                    for (Kentta kentta : Kentta.values()) {
                        if (kentta.toString().equals(nimi) && kentat.containsKey(kentta))
                            tekstikentta.setText(kentat.get(kentta));
                    }
                }
            }
        }
    }
    
    public static void lisaaTagilista(JPanel lomake, ArrayList<String> tagit){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.gridx = x;
        gbc.gridy = y;
        
        JList tagilista = new JList();
        String[] sisalto = tagit.toArray(new String[tagit.size()]);
        tagilista.setListData(sisalto);
        
        //lisää lista lomakkeeseen
        lomake.add(new JLabel("tagit"), gbc);
        x++;
        gbc.gridx = x;
        lomake.add(tagilista, gbc);
    }

}
