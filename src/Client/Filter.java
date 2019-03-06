package Client;

import Server.InjuredPoliceman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.ResourceBundle;

public class Filter extends JPanel {
    ResourceBundle bundleDefault = ResourceBundle.getBundle("resources");
    ResourceBundle bundlecs = ResourceBundle.getBundle("resources", new Locale("cs"));
    ResourceBundle bundleit = ResourceBundle.getBundle("resources", new Locale("it"));
    ResourceBundle bundlees = ResourceBundle.getBundle("resources", new Locale("es"));

    JButton bStart;
    JButton bStop;
    JButton bFilter;
    JButton bReload;
    JButton bRussian;
    JButton bChezh;
    JButton bSpanish;
    JButton bItalian;

    static Locale currentLocale = getDefaultLocale();
    private void createButtons() {
        bStart = new JButton(translate((ResourceBundle.getBundle("resources", Client.currentLocale).getString("startbutton"))));
        bStop = new JButton(translate((ResourceBundle.getBundle("resources", Client.currentLocale).getString("stopbutton"))));
        bFilter = new JButton(translate((ResourceBundle.getBundle("resources", Client.currentLocale).getString("filterbutton"))));
        bReload = new JButton(translate((ResourceBundle.getBundle("resources", Client.currentLocale).getString("reloadbutton"))));
        bRussian = new JButton("Русский");
        bChezh = new JButton("Česky");
        bSpanish = new JButton("Español");
        bItalian = new JButton("Italiano");
    }

    Timer timer;
    final int timerDelay = 1000;
    final int growTime = 5000;
    final int shrinkTime = 3000;

    public Filter() {
        //bStart.setText(translate((ResourceBundle.getBundle("resources", currentLocale).getString("startbutton"))));
        System.out.println(Client.currentLocale);
        createButtons();
        GridLayout gridLayout = new GridLayout(8, 1);
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
        for (InjuredPoliceman injuredPoliceman : Client.injuredPolicemen) {
            injuredPoliceman.size = 75;
        }
    }

    private void bReloadActionPerformed(ActionEvent evt) {
        Client.getCollection();
        Client.UpdateGUI();
    }

    private void bFilterActionPerformed(ActionEvent evt) {
        GUIFilter.createGUI();
    }

    private void bRussianActionPerformed(ActionEvent evt) {
        Client.currentLocale = getDefaultLocale();
        //updateFilter();
        updateButtons();
    }

    private void bChezhActionPerformed(ActionEvent evt) {
        Client.currentLocale = new Locale("cs");
        //System.out.println(Client.currentLocale);
        //repaint();
        //updateFilter();
        updateButtons();
        //System.out.println(Client.currentLocale);
    }

    private void bSpanishActionPerformed(ActionEvent evt) {
        Client.currentLocale = new Locale("es");
        //updateFilter();
        //Client.UpdateGUI();
        updateButtons();
    }

    private void bItalianActionPerformed(ActionEvent evt) {
        Client.currentLocale = new Locale("it");
        //updateFilter();
        updateButtons();
    }

    public void updateFilter() {
        this.revalidate();
        this.repaint();
    }

    public void updateButtons(){
        bStart.setText(translate((ResourceBundle.getBundle("resources", Client.currentLocale).getString("startbutton"))));
        bStop.setText(translate((ResourceBundle.getBundle("resources", Client.currentLocale).getString("stopbutton"))));
        bFilter.setText(translate((ResourceBundle.getBundle("resources", Client.currentLocale).getString("filterbutton"))));
        bReload.setText(translate((ResourceBundle.getBundle("resources", Client.currentLocale).getString("reloadbutton"))));
    }

    public static String translate(String stroka) {
        Charset cset = Charset.forName("UTF-8");
        ByteBuffer buf = cset.encode(stroka);
        byte[] b = buf.array();
        String str = new String(b);
        return str;
    }
}

