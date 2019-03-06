package Client;

import Server.InjuredPoliceman;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class Filter extends JPanel{
    ResourceBundle bundleDefault = ResourceBundle.getBundle("resources");
    ResourceBundle bundlecs = ResourceBundle.getBundle("resources", new Locale("cs"));
    ResourceBundle bundleit = ResourceBundle.getBundle("resources", new Locale("it"));
    ResourceBundle bundlees = ResourceBundle.getBundle("resources", new Locale("es"));

    Locale currentLocale = getDefaultLocale();

    JButton bStart = new JButton(translate((ResourceBundle.getBundle("resources", currentLocale).getString("startbutton"))));
    JButton bStop = new JButton(translate((ResourceBundle.getBundle("resources", currentLocale).getString("stopbutton"))));
    JButton bFilter = new JButton (translate((ResourceBundle.getBundle("resources", currentLocale).getString("filterbutton"))));
    JButton bReload = new JButton(translate((ResourceBundle.getBundle("resources", currentLocale).getString("reloadbutton"))));
    JButton bRussian = new JButton("Русский");
    JButton bChezh = new JButton("Česky");
    JButton bSpanish = new JButton("Español");
    JButton bItalian = new JButton("Italiano");
    Timer timer;
    final int timerDelay = 1000;
    final int growTime = 5000;
    final int shrinkTime = 3000;

    public Filter(){
        bStart.setText(translate((ResourceBundle.getBundle("resources", currentLocale).getString("startbutton"))));
        System.out.println(currentLocale);
        GridLayout gridLayout = new GridLayout(8,1);
        setLayout(gridLayout);
        add(bStart);
        add(bStop);
        add(bFilter);
        add(bReload);
        add(bRussian);
        add(bChezh);
        add(bSpanish);
        add(bItalian);
        bStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bStartActionPerformed(evt);
            }
        });
        bStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bStopActionPerformed(evt);
            }
        });
        bFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bFilterActionPerformed(evt);
            }
        });
        bReload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bReloadActionPerformed(evt);
            }
        });
        bRussian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bRussianActionPerformed(evt);
            }
        });
        bChezh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bChezhActionPerformed(evt);
            }
        });
        bSpanish.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSpanishActionPerformed(evt);
            }
        });
        bItalian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bItalianActionPerformed(evt);
            }
        });
    }

    boolean isActive = true;

    private void bStartActionPerformed(ActionEvent evt) {
        timer = new Timer(timerDelay, new ActionListener() {
            long startTime = System.currentTimeMillis();
            float shrinkPixels = 3;//(shrinkTime/timerDelay) / (Client.injuredPolicemen.get(0).size/2); поменяла 5 на 3
            float growPixels = 3;//(growTime/timerDelay) / (Client.injuredPolicemen.get(0).size/2);

            public void actionPerformed(ActionEvent ev) {
                for (InjuredPoliceman policeman : GUIFilter.filteredInjuredPolicemen) {
                    if (System.currentTimeMillis() - startTime < shrinkTime) {
                        policeman.size = policeman.size - shrinkPixels;
                    } else if (System.currentTimeMillis() - startTime > shrinkTime && System.currentTimeMillis() - startTime < shrinkTime + growTime) {
                        policeman.size = policeman.size + growPixels;
                    } else {
                        startTime = System.currentTimeMillis();
                    }
                }
            }
        });
        timer.start();
    }

    private void bStopActionPerformed(ActionEvent evt) {
        timer.stop();
        for (InjuredPoliceman injuredPoliceman : Client.injuredPolicemen        ) {
            injuredPoliceman.size = 75;
        }
    }

    private void bReloadActionPerformed(ActionEvent evt){
        Client.getCollection();
        Client.UpdateGUI();
    }

    private void bFilterActionPerformed(ActionEvent evt) {
        GUIFilter.createGUI();
    }

    private  void bRussianActionPerformed(ActionEvent evt){
        currentLocale = getDefaultLocale();
        updateFilter();
    }

    private  void bChezhActionPerformed(ActionEvent evt){
        currentLocale = new Locale("cs");
        System.out.println(currentLocale);
        //repaint();
        updateFilter();

        System.out.println(currentLocale);
    }

    private  void bSpanishActionPerformed(ActionEvent evt){
        currentLocale = new Locale("es");
        //updateFilter();
        Client.UpdateGUI();
    }

    private  void bItalianActionPerformed(ActionEvent evt){
        currentLocale = new Locale("it");
        updateFilter();
    }

    public void updateFilter(){
        revalidate();
        repaint();
    }

    public static String translate(String stroka){
        Charset cset = Charset.forName("UTF-8");
        ByteBuffer buf = cset.encode(stroka);
        byte[] b = buf.array();
        String str = new String(b);
        return str;
    }
}

