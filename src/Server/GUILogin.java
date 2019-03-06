package Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GUILogin extends JFrame {
    JLabel label;
    JTextField textField;
    JButton enter;
    JLabel error;
    String password = "1234";
    GUIServer guiServer;

    public GUILogin() {
        super("Логин");
    }

    private void addComponents(final Container pane){
        JPanel panel = new JPanel();
        label = new JLabel("Enter password");
        textField = new JTextField();
        enter = new JButton("Enter");
        enter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enterActionPerformed(evt);
            }
        });
        error = new JLabel();
        error.setForeground(new Color(255, 0, 51));
        error.setHorizontalAlignment(error.CENTER);
        GridLayout gridLayout = new GridLayout(4,1, 5, 5);
        panel.setLayout(gridLayout);
        panel.add(label);
        panel.add(textField);
        panel.add(enter);
        panel.add(error);
        pane.add(panel);
    }

    private void enterActionPerformed(ActionEvent evt) {
        if(!textField.getText().equals(password)){
            error.setText("Wrong password");
        } else {
            error.setText("");
            Server.guiServer.createGUI();
            dispose();
        }
    }

    public static void createGUI(){
        GUILogin frame = new GUILogin();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addComponents(frame.getContentPane());
        frame.pack();
        frame.setVisible(true);
        frame.setSize(400,200);
    }
}
