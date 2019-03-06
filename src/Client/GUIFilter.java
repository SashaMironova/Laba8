package Client;

import Server.BodyPartEnum;
import Server.FacePartEnum;
import Server.InjuredPoliceman;
import Server.StateEnum;
import com.sun.org.apache.xpath.internal.SourceTree;
import com.sun.xml.internal.ws.client.sei.ResponseBuilder;
import sun.security.x509.IPAddressName;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.plaf.ColorUIResource;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Vector;

public class GUIFilter extends JFrame{
    JLabel lName;
    JFormattedTextField tName;
    JLabel lYearOfBirth;
    JTextField tYearOfBirth;
    JLabel lCoordinates;
    JSlider sX;
    JLabel lCoordinateX;
    JLabel lCoordinateY;
    JSlider sY;
    JLabel lState;
    JList<StateEnum> liState;
    JLabel lInjuredFacePart;
    JList<FacePartEnum> liInjuredFacePart;
    JLabel lInjuredBodyPart;
    JList<BodyPartEnum> liInjuredBodyPart;
    JLabel lColour;
    JList<String> liColour;
    JLabel lError;
    JButton bFilter;
    StateEnum[] states = {StateEnum.GOOD, StateEnum.NAUSEA, StateEnum.BAD, StateEnum.SMOOTH};
    BodyPartEnum[] bodyParts = {BodyPartEnum.LEFT_HAND, BodyPartEnum.RIGHT_HAND, BodyPartEnum.LEFT_LEG, BodyPartEnum.RIGHT_LEG, BodyPartEnum.HEAD, BodyPartEnum.RIGHT_FEET, BodyPartEnum.LEFT_FEET};
    FacePartEnum[] faceParts = {FacePartEnum.FOREHEAD, FacePartEnum.NOSE, FacePartEnum.RIGHT_EYE, FacePartEnum.LEFT_EYE, FacePartEnum.MOUTH, FacePartEnum.CHEEKS};
    String[] colours = {"red", "blue", "orange", "green", "yellow", "white", "black"};
    public static Vector<InjuredPoliceman> filteredInjuredPolicemen = new Vector<InjuredPoliceman>();
    static GUIFilter frame;
    JLabel value1;
    JLabel value2;

    public GUIFilter(){
        super("Фильтр");
    }

    public static void createGUI() {
        GUIFilter frame = new GUIFilter();
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.addComponents(frame.getContentPane());
        frame.pack();
        frame.setVisible(true);
        frame.setSize(600, 800);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/3*2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
    }

    private void addComponents(final Container pane){
        Font font1 = new Font("SansSerif", Font.BOLD, 25);

        bFilter = new JButton("Set filter");
        bFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                String name = tName.getText();
                int yearOfBirth = Integer.parseInt(tYearOfBirth.getText());
                StateEnum state = states[liState.getSelectedIndex()];
                FacePartEnum facePart = faceParts[liInjuredFacePart.getSelectedIndex()];
                BodyPartEnum bodyPart = bodyParts[liInjuredBodyPart.getSelectedIndex()];
                String color = colours[liColour.getSelectedIndex()];
                int x = sX.getValue();
                int y = sY.getValue();

                filteredInjuredPolicemen.clear();

                for (InjuredPoliceman policeman: Client.injuredPolicemen) {
                    if (policeman.name.equals(name) || policeman.yearOfBirth == yearOfBirth || policeman.stateEnum == state ||
                            policeman.injuredFacePart == facePart || policeman.bodyPartsEnum.get(0) == bodyPart || policeman.colour.equals(color))
                        filteredInjuredPolicemen.add(policeman);
                }
                dispose();
            }
        });
        value1 = new JLabel("200");
        value2 = new JLabel("300");

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

        lCoordinateX = new JLabel("X:");
        lCoordinateX.setHorizontalAlignment(lCoordinateX.CENTER);
        lCoordinateX.setFont(font1);

        lCoordinateY = new JLabel("Y:");
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

        lError = new JLabel();
        lError.setForeground(new Color(255, 0, 51));
        lError.setHorizontalAlignment(lError.CENTER);
        lError.setFont(font1);

        tName = new JFormattedTextField(Client.injuredPolicemen.get(0).name);

        //NumberFormat year = new DecimalFormat("####");
        //tYearOfBirth = new JFormattedTextField(new NumberFormatter(year));
        tYearOfBirth = new JTextField(String.valueOf(Client.injuredPolicemen.get(0).yearOfBirth));

        sX = new JSlider(0,400);

        sY = new JSlider(0,600);

        sX.addChangeListener((event) -> {value1.setText(String.valueOf(sX.getValue()));}) ;
        sY.addChangeListener((event) -> {value2.setText(String.valueOf(sY.getValue()));}) ;

        liState = new JList<StateEnum>(states);
        liState.setSelectedIndex(0);
        JScrollPane jScrollPaneState = new JScrollPane(liState);

        liInjuredBodyPart = new JList<BodyPartEnum>(bodyParts);
        liInjuredBodyPart.setSelectedIndex(0);
        JScrollPane jScrollPaneBody = new JScrollPane(liInjuredBodyPart);

        liInjuredFacePart = new JList<FacePartEnum>(faceParts);
        liInjuredFacePart.setSelectedIndex(0);
        JScrollPane jScrollPaneFace = new JScrollPane(liInjuredFacePart);

        liColour = new JList<String>(colours);
        liColour.setSelectedIndex(0);
        JScrollPane jScrollPaneColour = new JScrollPane(liColour);

        JLabel empty1 = new JLabel("");
        JLabel empty2 = new JLabel("");
        JLabel empty3 = new JLabel("");

        JPanel panel = new JPanel();
        GridLayout layout = new GridLayout(12,2, 5, 5);
        panel.setLayout(layout);
        panel.add(lName);
        panel.add(tName);
        panel.add(lYearOfBirth);
        panel.add(tYearOfBirth);
        panel.add(lCoordinates);
        panel.add(empty1);
        panel.add(lCoordinateX);
        panel.add(sX);
        panel.add(empty2);
        panel.add(value1);
        panel.add(lCoordinateY);
        panel.add(sY);
        panel.add(empty3);
        panel.add(value2);
        panel.add(lState);
        panel.add(jScrollPaneState);
        panel.add(lInjuredFacePart);
        panel.add(jScrollPaneFace);
        panel.add(lInjuredBodyPart);
        panel.add(jScrollPaneBody);
        panel.add(lColour);
        panel.add(jScrollPaneColour);
        panel.add(lError);
        panel.add(bFilter);
        pane.add(panel);
    }
}
