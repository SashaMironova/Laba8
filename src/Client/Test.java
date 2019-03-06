package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.SortedMap;

public class Test {
    public  static void main(String[] args) throws SQLException, InterruptedException{


        //Window window = new Window();

        //Locale currentLocale = new Locale("en", "US");
        //Locale currentLocale = new Locale("fr", "CA", "UNIX");
        //ResourceBundle introLabels = ResourceBundle.getBundle("ButtonLabel", currentLocale);
//        ResourceBundle bundleDefault = ResourceBundle.getBundle("resources");
//        //ResourceBundle bundleen = ResourceBundle.getBundle("resources", new Locale("en"));
//        ResourceBundle bundlecs = ResourceBundle.getBundle("resources", new Locale("cs"));
//        ResourceBundle bundleit = ResourceBundle.getBundle("resources", new Locale("it"));
//        ResourceBundle bundlees = ResourceBundle.getBundle("resources", new Locale("es"));
//
//        String a = translate(bundleDefault.getString("startbutton"));

        Locale currentLocale = new Locale("it");

        String x = translate((ResourceBundle.getBundle("resources", currentLocale).getString("startbutton")));
        System.out.println(x);
        currentLocale = new Locale("cs");
        x = translate((ResourceBundle.getBundle("resources", currentLocale).getString("startbutton")));
        System.out.println(x);


        //JButton bStart = new JButton(bundleDefault.getString("startbutton"));



        //String a = translate(bundleDefault.getString("startbutton"));
//        String b = translate(bundlecs.getString("startbutton"));
//        System.out.println(a);
        //System.out.println(b);
//        System.out.println(bundlees.getString("startbutton"));
//        System.out.println(bundleit.getString("startbutton"));

        //JButton bStart = new JButton(bundleit.getString("startbutton"));
    }

    public static String translate(String stroka){
        Charset cset = Charset.forName("UTF-8");
        ByteBuffer buf = cset.encode(stroka);
        byte[] b = buf.array();
        String str = new String(b);
        return str;
    }

    public static class Window extends JFrame{

        JButton russian = new JButton("Russian");
        JButton chezh = new JButton("Chezh");

        Locale currentLocale = new Locale("");

        public Window(){
            JFrame frame = new JFrame();
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800,600);
            frame.setVisible(true);
            JPanel panel= new JPanel();
            panel.add(russian);
            panel.add(chezh);
            frame.add(panel);

            russian.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    russianActionPerformed(evt);
                }
            });

        }

        private void russianActionPerformed(ActionEvent evt){
            currentLocale = new Locale("");
            russian.setText(translate((ResourceBundle.getBundle("resources", currentLocale).getString("startbutton"))));
        }


    }
}
