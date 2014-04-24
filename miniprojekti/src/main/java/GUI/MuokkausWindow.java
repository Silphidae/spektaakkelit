package GUI;

import Engine.IEngine;
import domain.Kentta;
import domain.Viitetyyppi;
import java.awt.Component;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;

/**
 *
 * @author otsku
 */
public class MuokkausWindow extends javax.swing.JFrame {

    private IEngine engine;
    private String ckey;
    private Viitetyyppi viitetyyppi;
    private MainWindow mainWindow;

    /**
     * Creates new form MuokkausWindow
     */
    public MuokkausWindow(String ckey, IEngine engine, MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.ckey = ckey;
        this.engine = engine;
        EnumMap<Kentta, String> kentat = engine.getKentat(ckey);
        initComponents();

        viitetyyppi = engine.getViitetyyppi(ckey);
        ArrayList tagit = engine.getTagsByViite(ckey);//kesken!!!
        NakymaBuilder.teeNakymaLomakkeelle(lomake, engine.getPakollisetKentat(viitetyyppi), engine.getEiPakollisetKentat(viitetyyppi), viitetyyppi, 0, 0, null, true, tagit);
        NakymaBuilder.taytaLomakkeenTiedot(lomake, kentat);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lomake = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout lomakeLayout = new javax.swing.GroupLayout(lomake);
        lomake.setLayout(lomakeLayout);
        lomakeLayout.setHorizontalGroup(
            lomakeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 431, Short.MAX_VALUE)
        );
        lomakeLayout.setVerticalGroup(
            lomakeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 448, Short.MAX_VALUE)
        );

        jButton1.setText("Peruuta");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Tallenna");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Poista tagi");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lomake, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lomake, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        setVisible(false);
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        EnumMap<Kentta, String> kentat = NakymaBuilder.haeLomakkeenTiedot(lomake);

        //Otetaan tagit talteen ennen poistoa
        ArrayList<String> tagit = engine.getTagsByViite(ckey);

        //Otetaan viite talteen ennen muokkausta
        EnumMap<Kentta, String> vanhaViite = engine.getKentat(ckey);
        engine.poistaViite(ckey);

        ArrayList<String> virheet = engine.lisaaViite(viitetyyppi, kentat);

        String uusiCkey = ckey;

        if (virheet != null) {
            String virheviesti = "";

            for (String virhe : virheet) {
                virheviesti += virhe + "\n";
            }

            JOptionPane.showMessageDialog(this, virheviesti);

            engine.lisaaViite(viitetyyppi, vanhaViite);

            uusiCkey = engine.getViimeksiLisatynCkey();
        } else {
            JOptionPane.showMessageDialog(this, "Viitettä muokattiin onnistuneesti");
            //Sulkee muokkausikkunan
            setVisible(false);
            dispose();
            mainWindow.paivitaViitelista();
        }

        //Lisätään tagit takaisin
        for (String tag : tagit) {
            engine.addTagi(ckey, tag);
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        ArrayList<String> tagit = new ArrayList<>();

        Component[] lomakkeenSisalto = lomake.getComponents();
        JList tagilista = null;

        for (Component kentta : lomakkeenSisalto) {
            if (kentta instanceof JList) {
                tagilista = (JList) kentta;
                for (Object o : tagilista.getSelectedValuesList()) {
                    tagit.add(o.toString());

                }
            }
        }

        int valinta = 0;
        if (tagit.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ei valittuja tageja");
        } else {
            valinta = JOptionPane.showConfirmDialog(this, "Poistetaanko tagit?");
        }

        if (valinta == JOptionPane.YES_OPTION) {
            for (String tagi : tagit) {
                engine.removeTagi(ckey, tagi);
            }
            paivitaTagit(tagilista);
            mainWindow.paivitaTagit();
        }
    }//GEN-LAST:event_jButton3ActionPerformed
    public void paivitaTagit(JList lista) {
        ArrayList<String> tagit = engine.getTagsByViite(ckey);
        String[] sisalto = tagit.toArray(new String[tagit.size()]);
        lista.setListData(sisalto);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JPanel lomake;
    // End of variables declaration//GEN-END:variables
}
