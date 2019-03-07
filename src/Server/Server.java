package Server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.org.apache.xpath.internal.SourceTree;
import org.omg.CORBA.PUBLIC_MEMBER;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class Server {
    public static Vector<InjuredPoliceman> injuredPolicemen;
    public static Gson gson = new Gson();
    public static String json = "";
    public static InputOutput inputOutput = new InputOutput();
    public static GUIServer guiServer;
    public static GUILogin guiLogin;

    public static void main(String[] args) {
//        String str = inputOutput.input();
//        Type type = new TypeToken<Vector<InjuredPoliceman>>() {
//        }.getType();
//        injuredPolicemen = gson.fromJson(str, type);
        if (injuredPolicemen == null)
            injuredPolicemen = new Vector<InjuredPoliceman>();

        Collections.sort(injuredPolicemen, new InjuredPolicemanComparator());
        Server server = new Server();
        server.getAllPolicemen();
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

    private void getAllPolicemen() {
        try {
            //Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/postgres";
            String login = "postgres";
            String password = "postgres";
            Connection con = DriverManager.getConnection(url, login, password);
            try {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM INJURED_POLICEMAN");
                while (rs.next()) {
                    InjuredPoliceman injuredPoliceman = new InjuredPoliceman();
                    injuredPoliceman.id = rs.getInt("ID");
                    injuredPoliceman.name = rs.getString("NAME");
                    injuredPoliceman.yearOfBirth = rs.getInt("YEAR_OF_BIRTH");
                    switch(rs.getString("STATE")){
                        case "SMOOTH":
                            injuredPoliceman.stateEnum = StateEnum.SMOOTH;
                            break;
                        case "GOOD":
                            injuredPoliceman.stateEnum = StateEnum.GOOD;
                            break;
                        case "NAUSEA":
                            injuredPoliceman.stateEnum = StateEnum.NAUSEA;
                            break;
                        case "BAD":
                            injuredPoliceman.stateEnum = StateEnum.BAD;
                            break;
                    }
                    injuredPoliceman.colour = rs.getString("colour");
                    injuredPoliceman.x = rs.getInt("X");
                    injuredPoliceman.y = rs.getInt("Y");
                    ZoneOffset zoneOffset = ZoneOffset.ofHours(3);
                    LocalDateTime localDateTime =  rs.getTimestamp("CREATED").toLocalDateTime();
                    injuredPoliceman.created = localDateTime.atOffset(zoneOffset);
                     switch(rs.getString("INJURED_FACE_PART")){
                        case "FOREHEAD":
                            injuredPoliceman.injuredFacePart = FacePartEnum.FOREHEAD;
                            break;
                        case "CHEEKS":
                            injuredPoliceman.injuredFacePart = FacePartEnum.CHEEKS;
                            break;
                        case "NOSE":
                            injuredPoliceman.injuredFacePart = FacePartEnum.NOSE;
                            break;
                        case "RIGHT_EYE":
                            injuredPoliceman.injuredFacePart = FacePartEnum.RIGHT_EYE;
                            break;
                        case "LEFT_EYE":
                            injuredPoliceman.injuredFacePart = FacePartEnum.LEFT_EYE;
                            break;
                        case "MOUTH":
                            injuredPoliceman.injuredFacePart = FacePartEnum.MOUTH;
                            break;
                    }
                    switch (rs.getString("INJURED_BODY_PART")){
                        case "LEFT_FEET":
                            injuredPoliceman.bodyPartsEnum.add(BodyPartEnum.LEFT_FEET);
                            break;
                        case "RIGHT_FEET":
                            injuredPoliceman.bodyPartsEnum.add(BodyPartEnum.RIGHT_FEET);
                            break;
                        case "LEFT_HAND":
                            injuredPoliceman.bodyPartsEnum.add(BodyPartEnum.LEFT_HAND);
                            break;
                        case "RIGHT_HAND":
                            injuredPoliceman.bodyPartsEnum.add(BodyPartEnum.RIGHT_HAND);
                            break;
                        case "HEAD":
                            injuredPoliceman.bodyPartsEnum.add(BodyPartEnum.HEAD);
                            break;
                        case "LEFT_LEG":
                            injuredPoliceman.bodyPartsEnum.add(BodyPartEnum.LEFT_LEG);
                            break;
                        case "RIGHT_LEG":
                            injuredPoliceman.bodyPartsEnum.add(BodyPartEnum.RIGHT_LEG);
                            break;
                    }
                    injuredPolicemen.add(injuredPoliceman);
                }
                rs.close();
                stmt.close();
            } finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void connectClient(Socket socket) {

        Gson gson = new Gson();
        String json = "";

        InputOutput inputOutput = new InputOutput();

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

