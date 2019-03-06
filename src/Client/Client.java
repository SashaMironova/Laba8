package Client;

import java.awt.*;
import java.lang.reflect.Type;
import java.net.*;
import java.io.*;
import java.util.*;

import Server.InjuredPoliceman;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;

import static javax.swing.JComponent.getDefaultLocale;

public class Client{

    static String str;
    static String address = "127.0.0.1";
    static int serverPort = 1235;
    static Locale currentLocale = getDefaultLocale();
    static Display display = new Display();
    static Filter filter = new Filter();

    public static ArrayList<InjuredPoliceman> injuredPolicemen;

    public static void main(String[] args) {
        getCollection();
        createGUI();
    }

    public static void getCollection(){
        try {
            InetAddress ipAddress = InetAddress.getByName(address);
            Socket socket = new Socket(ipAddress, serverPort);

            InputStream inputStream = socket.getInputStream();

            DataInputStream in = new DataInputStream(inputStream);

            Gson gson = new Gson();

            Type type = new TypeToken<ArrayList<InjuredPoliceman>>() {
            }.getType();

            str = in.readUTF();
            injuredPolicemen = gson.fromJson(str, type);

            in.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public static void createGUI(){
        JFrame frame = new JFrame("Клиент");
        GridLayout gridLayout = new GridLayout(1,2);
        frame.setResizable(false);
        frame.setLayout(gridLayout);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(800,600);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/3*2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
        //Display display = new Display();
        frame.add(display);
//        for(int i = 0 ;i<injuredPolicemen.size();i++){
//            new JLabel()
//            JLabel label = new JLabel(injuredPolicemen.get(i).name);
//            label.setLocation((int)injuredPolicemen.get(i).x, (int)injuredPolicemen.get(i).y);
//            label.setSize(40,40);
//            display.add(label);
//        }
        frame.add(filter);
    }

    public static void UpdateGUI(){
        display.revalidate();
        display.repaint();
        System.out.println("reload");
        filter = new Filter();
        filter.revalidate();
        filter.repaint();
    }
}
