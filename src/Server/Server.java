package Server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;


public class Server {
    public static Vector<InjuredPoliceman> injuredPolicemen;
    public static Gson gson = new Gson();
    public static String json = "";
    public static InputOutput inputOutput = new InputOutput();
    public static GUIServer guiServer;
    public static GUILogin guiLogin;

    public static void main(String[] args) {

        String str = inputOutput.input();
        Type type = new TypeToken<Vector<InjuredPoliceman>>() {
        }.getType();
        injuredPolicemen = gson.fromJson(str, type);

        if (injuredPolicemen == null)
            injuredPolicemen = new Vector<InjuredPoliceman>();

        Collections.sort(injuredPolicemen, new InjuredPolicemanComparator());
        guiServer = new GUIServer();
        guiLogin = new GUILogin();


        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                guiLogin.createGUI();
            }
        });


        int port = 1235;
        try {
            ServerSocket ss;
            Socket socket;
            ss = new ServerSocket(port);
            System.out.println("Ожидаем поключения");
            while (true) {
                socket = ss.accept();
                Socket s = socket;
                System.out.println("Клиент подключился");

                new Thread(() -> connectClient(s)).start();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void connectClient(Socket socket) {
        Gson gson = new Gson();
        String json = "";

        //InputOutput inputOutput = new InputOutput();

        try {
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            DataInputStream in = new DataInputStream(inputStream);
            DataOutputStream out = new DataOutputStream(outputStream);

            Collections.sort(injuredPolicemen, new InjuredPolicemanComparator());
            json = gson.toJson(injuredPolicemen);
            //System.out.println(json);
            out.writeUTF(json);
            out.close();
            socket.close();

        } catch (Exception e) {
            System.out.println("Клиент отключился");
        }
    }
}

