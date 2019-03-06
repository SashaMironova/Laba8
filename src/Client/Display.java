package Client;

import javax.swing.*;
import java.awt.*;

public class Display extends JPanel {

    //private boolean showed = false;
    public void paintComponent(Graphics g){

        super.paintComponent(g);
        setBorder(BorderFactory.createLineBorder(Color.black));
        setBackground(Color.WHITE);
        g.setColor(Color.RED);
        g.drawRect(410,40,10,10);

        for (int i =0;i<Client.injuredPolicemen.size();i++){
            String colour = Client.injuredPolicemen.get(i).colour;
            switch (colour){
                case ("red"):
                    g.setColor(Color.RED);
                    break;
                case ("blue"):
                    g.setColor(Color.BLUE);
                    break;
                case ("yellow"):
                    g.setColor(Color.YELLOW );
                    break;
                case ("green"):
                    g.setColor(Color.GREEN);
                    break;
                case("orange"):
                    g.setColor(Color.ORANGE);
                    break;
                case("white"):
                    g.setColor(Color.WHITE);
                    break;
                case("black"):
                    g.setColor(Color.BLACK);
                    break;
            }
            int x = (int)Client.injuredPolicemen.get(i).x;
            int y = (int)Client.injuredPolicemen.get(i).y;
            int size = (int)Client.injuredPolicemen.get(i).size;
            g.fillOval(x,y,size,size);
//            if (!showed) {
////                JLabel label = new JLabel(Client.injuredPolicemen.get(i).name);
////                label.setSize(50, 40);
////                label.setLocation(x + 15, y - 10);
//                //label.setToolTipText(Client.injuredPolicemen.get(i).name);
//                //this.add(label);
//            }
            //g.setToolTipText("cvbnmk,");
        }
        //showed = true;
    }
}

