package Server;

import Client.Client;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

public class GUIEdit extends JFrame{

    public GUIEdit(){
        super("Изменение элемента коллекции");
    }

    JLabel lName;
    JLabel lYearOfBirth;
    JLabel lState;
    JLabel lCoordinates;
    JLabel lCoordinateX;
    JLabel lCoordinateY;
    JLabel lInjuredFacePart;
    JLabel lInjuredBodyPart;
    JLabel lColour;
    JLabel lError;
    JButton bEdit;
    JTextField tName;
    JTextField tYearOfBirth;
    JLabel lEmpty;
    JTextField tX;
    JTextField tY;
    ButtonGroup buttonGroupState;
    ButtonGroup buttonGroupFace;
    ButtonGroup buttonGroupBody;
    ButtonGroup buttonGroupColour;
    JLabel lDateOfCreation;
    JLabel lDate;
    int number = 0;

    private void addComponents(final Container pane){
        Font font1 = new Font("SansSerif", Font.BOLD, 25);

        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) GUIServer.tree.getLastSelectedPathComponent();
        InjuredPoliceman object = (InjuredPoliceman)selectedNode.getUserObject();
        for (int i=0; i< Server.injuredPolicemen.size();i++){
            if(object.id == Server.injuredPolicemen.get(i).id) {
                number = i;
            }
        }

        lName = new JLabel("Name:");
        lName.setHorizontalAlignment(lName.CENTER);
        lName.setFont(font1);

        lYearOfBirth = new JLabel("Year of birth:");
        lYearOfBirth.setHorizontalAlignment(lYearOfBirth.CENTER);
        lYearOfBirth.setFont(font1);

        lState = new JLabel("State:");
        lState.setHorizontalAlignment(lState.CENTER);
        lState.setFont(font1);

        lCoordinates = new JLabel("  Coordinates:");
        lCoordinates.setHorizontalAlignment(lCoordinates.LEFT);
        lCoordinates.setFont(font1);

        lCoordinateX = new JLabel("X[0;400]:");
        lCoordinateX.setHorizontalAlignment(lCoordinateX.CENTER);
        lCoordinateX.setFont(font1);

        lCoordinateY = new JLabel("Y[0;600]:");
        lCoordinateY.setHorizontalAlignment(lCoordinateY.CENTER);
        lCoordinateY.setFont(font1);

        lInjuredFacePart = new JLabel("Injured face part:");
        lInjuredFacePart.setHorizontalAlignment(lInjuredFacePart.CENTER);
        lInjuredFacePart.setFont(font1);

        lInjuredBodyPart = new JLabel("Injured body part:");
        lInjuredBodyPart.setHorizontalAlignment(lInjuredBodyPart.CENTER);
        lInjuredBodyPart.setFont(font1);

        lColour = new JLabel("Colour:");
        lColour.setHorizontalAlignment(lColour.CENTER);
        lColour.setFont(font1);

        lDateOfCreation = new JLabel("Date of creation:");
        lDateOfCreation.setHorizontalAlignment(lDateOfCreation.CENTER);
        lDateOfCreation.setFont(font1);

        lDate = new JLabel(String.valueOf(Server.injuredPolicemen.get(number).created));//todo странные даты создания
        lDate.setHorizontalAlignment(lDate.CENTER);
        lDate.setFont(font1);

        lError = new JLabel();
        lError.setForeground(new Color(255, 0, 51));
        lError.setHorizontalAlignment(lError.CENTER);
        lError.setFont(font1);

        bEdit = new JButton("Edit");
        bEdit.setFont(font1);
        bEdit.setSize(150,15);
        bEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bEditActionPerformed(evt);
            }
        });

        tName = new JTextField();
        tName.setText(Server.injuredPolicemen.get(number).name);
        tYearOfBirth = new JTextField();
        tYearOfBirth.setText(Integer.toString(Server.injuredPolicemen.get(number).yearOfBirth));
        lEmpty = new JLabel("");
        tX = new JTextField(Double.toString(Server.injuredPolicemen.get(number).x));
        tY = new JTextField(Double.toString(Server.injuredPolicemen.get(number).y));

        JRadioButton rbState1 = new JRadioButton("SMOOTH");
        JRadioButton rbState2 = new JRadioButton("NAUSEA");
        JRadioButton rbState3 = new JRadioButton("GOOD");
        JRadioButton rbState4 = new JRadioButton("BAD");
        JPanel radioButtonsState = new JPanel();
        radioButtonsState.add(rbState1);
        radioButtonsState.add(rbState2);
        radioButtonsState.add(rbState3);
        radioButtonsState.add(rbState4);
        buttonGroupState = new ButtonGroup();
        buttonGroupState.add(rbState1);
        buttonGroupState.add(rbState2);
        buttonGroupState.add(rbState3);
        buttonGroupState.add(rbState4);
        if (Server.injuredPolicemen.get(number).stateEnum == StateEnum.SMOOTH)
            rbState1.setSelected(true);
        if (Server.injuredPolicemen.get(number).stateEnum == StateEnum.NAUSEA)
            rbState2.setSelected(true);
        if (Server.injuredPolicemen.get(number).stateEnum == StateEnum.GOOD)
            rbState3.setSelected(true);
        if (Server.injuredPolicemen.get(number).stateEnum == StateEnum.BAD)
            rbState4.setSelected(true);

        JRadioButton rbFace1 = new JRadioButton("FOREHEAD");
        JRadioButton rbFace2 = new JRadioButton("NOSE");
        JRadioButton rbFace3 = new JRadioButton("MOUTH");
        JRadioButton rbFace4 = new JRadioButton("RIGHT_EYE");
        JRadioButton rbFace5 = new JRadioButton("LEFT_EYE");
        JRadioButton rbFace6 = new JRadioButton("CHEEKS");
        JPanel radioButtonsFace = new JPanel();
        radioButtonsFace.add(rbFace1);
        radioButtonsFace.add(rbFace2);
        radioButtonsFace.add(rbFace3);
        radioButtonsFace.add(rbFace4);
        radioButtonsFace.add(rbFace5);
        radioButtonsFace.add(rbFace6);
        buttonGroupFace = new ButtonGroup();
        buttonGroupFace.add(rbFace1);
        buttonGroupFace.add(rbFace2);
        buttonGroupFace.add(rbFace3);
        buttonGroupFace.add(rbFace4);
        buttonGroupFace.add(rbFace5);
        buttonGroupFace.add(rbFace6);
        if (Server.injuredPolicemen.get(number).injuredFacePart == FacePartEnum.FOREHEAD)
            rbFace1.setSelected(true);
        if (Server.injuredPolicemen.get(number).injuredFacePart == FacePartEnum.NOSE)
            rbFace2.setSelected(true);
        if (Server.injuredPolicemen.get(number).injuredFacePart == FacePartEnum.MOUTH)
            rbFace3.setSelected(true);
        if (Server.injuredPolicemen.get(number).injuredFacePart == FacePartEnum.RIGHT_EYE)
            rbFace4.setSelected(true);
        if (Server.injuredPolicemen.get(number).injuredFacePart == FacePartEnum.LEFT_EYE)
            rbFace5.setSelected(true);
        if (Server.injuredPolicemen.get(number).injuredFacePart == FacePartEnum.CHEEKS)
            rbFace6.setSelected(true);

        JRadioButton rbBody1 = new JRadioButton("HEAD");
        JRadioButton rbBody2 = new JRadioButton("LEFT_HAND");
        JRadioButton rbBody3 = new JRadioButton("RIGHT_HAND");
        JRadioButton rbBody4 = new JRadioButton("LEFT_FEET");
        JRadioButton rbBody5 = new JRadioButton("RIGHT_FEET");
        JRadioButton rbBody6 = new JRadioButton("LEFT_LEG");
        JRadioButton rbBody7 = new JRadioButton("RIGHT_LEG");
        JPanel radioButtonsBody = new JPanel();
        radioButtonsBody.add(rbBody1);
        radioButtonsBody.add(rbBody2);
        radioButtonsBody.add(rbBody3);
        radioButtonsBody.add(rbBody4);
        radioButtonsBody.add(rbBody5);
        radioButtonsBody.add(rbBody6);
        radioButtonsBody.add(rbBody7);
        buttonGroupBody = new ButtonGroup();
        buttonGroupBody.add(rbBody1);
        buttonGroupBody.add(rbBody2);
        buttonGroupBody.add(rbBody3);
        buttonGroupBody.add(rbBody4);
        buttonGroupBody.add(rbBody5);
        buttonGroupBody.add(rbBody6);
        buttonGroupBody.add(rbBody7);
        if (Server.injuredPolicemen.get(number).bodyPartsEnum.get(0) == BodyPartEnum.HEAD)
            rbBody1.setSelected(true);
        if (Server.injuredPolicemen.get(number).bodyPartsEnum.get(0) == BodyPartEnum.LEFT_HAND)
            rbBody2.setSelected(true);
        if (Server.injuredPolicemen.get(number).bodyPartsEnum.get(0) == BodyPartEnum.RIGHT_HAND)
            rbBody3.setSelected(true);
        if (Server.injuredPolicemen.get(number).bodyPartsEnum.get(0) == BodyPartEnum.LEFT_FEET)
            rbBody4.setSelected(true);
        if (Server.injuredPolicemen.get(number).bodyPartsEnum.get(0) == BodyPartEnum.RIGHT_FEET)
            rbBody5.setSelected(true);
        if (Server.injuredPolicemen.get(number).bodyPartsEnum.get(0) == BodyPartEnum.LEFT_LEG)
            rbBody6.setSelected(true);
        if (Server.injuredPolicemen.get(number).bodyPartsEnum.get(0) == BodyPartEnum.RIGHT_LEG)
            rbBody7.setSelected(true);

        JRadioButton rbColour1 = new JRadioButton("red");
        JRadioButton rbColour2 = new JRadioButton("blue");
        JRadioButton rbColour3 = new JRadioButton("yellow");
        JRadioButton rbColour4 = new JRadioButton("green");
        JRadioButton rbColour5 = new JRadioButton("orange");
        JRadioButton rbColour6 = new JRadioButton("white");
        JRadioButton rbColour7 = new JRadioButton("black");
        JPanel radioButtonsColour = new JPanel();
        radioButtonsColour.add(rbColour1);
        radioButtonsColour.add(rbColour2);
        radioButtonsColour.add(rbColour3);
        radioButtonsColour.add(rbColour4);
        radioButtonsColour.add(rbColour5);
        radioButtonsColour.add(rbColour6);
        radioButtonsColour.add(rbColour7);
        buttonGroupColour = new ButtonGroup();
        buttonGroupColour.add(rbColour1);
        buttonGroupColour.add(rbColour2);
        buttonGroupColour.add(rbColour3);
        buttonGroupColour.add(rbColour4);
        buttonGroupColour.add(rbColour5);
        buttonGroupColour.add(rbColour6);
        buttonGroupColour.add(rbColour7);
        if (Server.injuredPolicemen.get(number).colour.equals("red"))
            rbColour1.setSelected(true);
        if (Server.injuredPolicemen.get(number).colour.equals("blue"))
            rbColour2.setSelected(true);
        if (Server.injuredPolicemen.get(number).colour.equals("yellow"))
            rbColour3.setSelected(true);
        if (Server.injuredPolicemen.get(number).colour.equals("green"))
            rbColour4.setSelected(true);
        if (Server.injuredPolicemen.get(number).colour.equals("orange"))
            rbColour5.setSelected(true);
        if (Server.injuredPolicemen.get(number).colour.equals("white"))
            rbColour6.setSelected(true);
        if (Server.injuredPolicemen.get(number).colour.equals("black"))
            rbColour7.setSelected(true);

        JPanel panel = new JPanel();
        GridLayout layout = new GridLayout(11,2, 5, 5);
        panel.setLayout(layout);
        panel.add(lName);
        panel.add(tName);
        panel.add(lYearOfBirth);
        panel.add(tYearOfBirth);
        panel.add(lState);
        panel.add(radioButtonsState);
        panel.add(lCoordinates);
        panel.add(lEmpty);
        panel.add(lCoordinateX);
        panel.add(tX);
        panel.add(lCoordinateY);
        panel.add(tY);
        panel.add(lInjuredFacePart);
        panel.add(radioButtonsFace);
        panel.add(lInjuredBodyPart);
        panel.add(radioButtonsBody);
        panel.add(lColour);
        panel.add(radioButtonsColour);
        panel.add(lDateOfCreation);
        panel.add(lDate);
        panel.add(lError);
        panel.add(bEdit);
        pane.add(panel);
    }

    private void bEditActionPerformed(ActionEvent evt) {
        lError.setText("");
        try{
            double x = Double.parseDouble(tY.getText());
        } catch (Exception e){
            lError.setText("Wrong format of \"Coordinate Y\"");
        }
        try{
            double x = Double.parseDouble(tX.getText());
        } catch (Exception e){
            lError.setText("Wrong format of \"Coordinate X\"");
        }
        try {
            int year = Integer.parseInt(tYearOfBirth.getText());
        } catch (Exception e){
            lError.setText("Wrong format of \"Year of birth\"");
        }
        if ((tName.getText().equals(""))||(tYearOfBirth.getText().equals(""))||(tX.getText().equals(""))||(tY.getText().equals("")))
            lError.setText("Text fields are empty");
        else {
            InjuredPoliceman injuredPoliceman = new InjuredPoliceman();
            injuredPoliceman.name = tName.getText();
            injuredPoliceman.yearOfBirth = Integer.parseInt(tYearOfBirth.getText());
            injuredPoliceman.x = Double.parseDouble(tX.getText());
            injuredPoliceman.y = Double.parseDouble(tY.getText());
            switch (getSelectedButtonText(buttonGroupState)) {
                case ("SMOOTH"):
                    injuredPoliceman.stateEnum = StateEnum.SMOOTH;
                    break;
                case ("NAUSEA"):
                    injuredPoliceman.stateEnum = StateEnum.NAUSEA;
                    break;
                case ("GOOD"):
                    injuredPoliceman.stateEnum = StateEnum.GOOD;
                    break;
                case ("BAD"):
                    injuredPoliceman.stateEnum = StateEnum.BAD;
                    break;
            }
            switch (getSelectedButtonText(buttonGroupFace)) {
                case ("FOREHEAD"):
                    injuredPoliceman.injuredFacePart = FacePartEnum.FOREHEAD;
                    break;
                case ("NOSE"):
                    injuredPoliceman.injuredFacePart = FacePartEnum.NOSE;
                    break;
                case ("RIGHT_EYE"):
                    injuredPoliceman.injuredFacePart = FacePartEnum.RIGHT_EYE;
                    break;
                case ("LEFT_EYE"):
                    injuredPoliceman.injuredFacePart = FacePartEnum.LEFT_EYE;
                    break;
                case ("MOUTH"):
                    injuredPoliceman.injuredFacePart = FacePartEnum.MOUTH;
                    break;
                case ("CHEEKS"):
                    injuredPoliceman.injuredFacePart = FacePartEnum.CHEEKS;
                    break;
            }
            switch (getSelectedButtonText(buttonGroupBody)) {
                case ("LEFT_FEET"):
                    injuredPoliceman.bodyPartsEnum.add(BodyPartEnum.LEFT_FEET);
                    break;
                case ("RIGHT_FEET"):
                    injuredPoliceman.bodyPartsEnum.add(BodyPartEnum.RIGHT_FEET);
                    break;
                case ("LEFT_HAND"):
                    injuredPoliceman.bodyPartsEnum.add(BodyPartEnum.LEFT_HAND);
                    break;
                case ("RIGHT_HAND"):
                    injuredPoliceman.bodyPartsEnum.add(BodyPartEnum.RIGHT_HAND);
                    break;
                case ("LEFT_LEG"):
                    injuredPoliceman.bodyPartsEnum.add(BodyPartEnum.LEFT_LEG);
                    break;
                case ("RIGHT_LEG"):
                    injuredPoliceman.bodyPartsEnum.add(BodyPartEnum.RIGHT_LEG);
                    break;
                case ("HEAD"):
                    injuredPoliceman.bodyPartsEnum.add(BodyPartEnum.HEAD);
                    break;
            }
            injuredPoliceman.colour = getSelectedButtonText(buttonGroupColour);
            injuredPoliceman.id = Server.injuredPolicemen.get(number).id;
            Server.injuredPolicemen.set(number, injuredPoliceman);
            updatePoliceman(injuredPoliceman);
            //Server.json = Server.gson.toJson(Server.injuredPolicemen);
            //Server.inputOutput.output(Server.json);
            DefaultTreeModel model = (DefaultTreeModel) Server.guiServer.tree.getModel();
            model.removeNodeFromParent(selectedNode1);
            DefaultMutableTreeNode root = (DefaultMutableTreeNode) Server.guiServer.tree.getModel()
                    .getRoot();
            DefaultMutableTreeNode child = new DefaultMutableTreeNode(injuredPoliceman);
            root.add(child);
            model.reload();
            dispose();
        }
    }

    public String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return button.getText();
            }
        }

        return null;
    }

    public static  DefaultMutableTreeNode selectedNode1;

    public static void createGUI(DefaultMutableTreeNode selectedNode){
        selectedNode1 = selectedNode;
        GUIEdit frame = new GUIEdit();
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.addComponents(frame.getContentPane());
        frame.pack();
        frame.setVisible(true);
        frame.setSize(800,800);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
    }

    private void updatePoliceman(InjuredPoliceman injuredPoliceman) {
        try {
            //Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/postgres";
            String login = "postgres";
            String password = "postgres";
            Connection con = DriverManager.getConnection(url, login, password);
            String date= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss+02").format(Timestamp.valueOf(injuredPoliceman.created.toLocalDateTime()));
            String query = "UPDATE INJURED_POLICEMAN SET NAME ='" + injuredPoliceman.name + "', YEAR_OF_BIRTH = " + injuredPoliceman.yearOfBirth + ", STATE = '" + injuredPoliceman.stateEnum + "', COLOUR = '" + injuredPoliceman.colour + "', X = " + injuredPoliceman.x + ", Y = " + injuredPoliceman.y + ", INJURED_FACE_PART = '" + injuredPoliceman.injuredFacePart + "', INJURED_BODY_PART = '" + injuredPoliceman.bodyPartsEnum.get(0) + "' WHERE ID=" + injuredPoliceman.id + ";";
            //String date2= new SimpleDateFormat("YYYY-MM-DD hh:mm:ss.000000").format(date1).toString();
            try {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                rs.close();
                stmt.close();
            } finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
