package GUI;

import domain.Kentta;
import domain.Viitetyyppi;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Set;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ToolTipManager;

public class NakymaBuilder {
    
    private static int x = 0;
    private static int y = 0;
    
    public static void teeNakymaLomakkeelle(JPanel lomake, Set<Kentta> pakollisetKentat, Set<Kentta> muutKentat, Viitetyyppi tyyppi, int xArvo, int yArvo, JScrollPane lomakeScroll) {
        
        x = xArvo;
        y = yArvo;
        
        lomake.removeAll();
        lomake.setLayout(new GridBagLayout());

        //luodaan alkuun pakolliset kentät tähden kera
        lisaaLomakkeeseen(pakollisetKentat, true, tyyppi, lomake);

        lisaaLomakkeeseen(muutKentat, false, tyyppi, lomake);
        //paivitetaan lomake-paneeli ja skrollaus
        lomake.validate();
        lomake.repaint();
        
        if (lomakeScroll != null) lomakeScroll.validate();
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
            tekstikentta.setToolTipText("Erottele henkilöt pilkulla");
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
                tekstikentta.setToolTipText("Erottele henkilöt pilkulla");
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

}
