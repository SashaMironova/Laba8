package Server;

// Использование JTree, TreeModel

//import com.sun.scenario.effect.impl.sw.java.JSWBlend_COLOR_BURNPeer;
import org.omg.PortableServer.SERVANT_RETENTION_POLICY_ID;

//import org.eclipse.swt.SWT;
//import org.eclipse.swt.widgets.Shell;
//import org.eclipse.swt.widgets.Button;
//import org.eclipse.swt.widgets.Display;
//
//// import org.eclipse.swt.layout.GridData;
//import org.eclipse.swt.layout.GridLayout;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import java.awt.event.ActionEvent;
import java.lang.*;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;


public class GUIServer extends JFrame {
    JButton bAdd;
    JButton bEdit;
    JButton bDelete;
    JButton bDeleteLower;
    JButton bClear;
    JLabel lMessage;
    public static JTree tree;

    public GUIServer() {
        super("Сервер");
    }

    private void addComponents(final Container pane){
        bAdd = new JButton("Add");
        bAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAddActionPerformed(evt);
            }
        });

        bEdit = new JButton("Edit");
        bEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bEditActionPerformed(evt);
            }
        });

        bDelete = new JButton("Delete");
        bDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDeleteActionPerformed(evt);
            }
        });

        bDeleteLower = new JButton("Delete lower than");
        bDeleteLower.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDeleteLowerActionPerformed(evt);
            }
        });

        bClear = new JButton("Clear collection");
        bClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bClearActionPerformed(evt);
            }
        });

        lMessage = new JLabel("");
        lMessage.setForeground(new Color(255, 0, 51));
        lMessage.setPreferredSize(new Dimension(200,50));

        tree = new JTree(Server.injuredPolicemen);

        JPanel buttonsPanel = new JPanel();
        JPanel treePanel = new JPanel();

        GridLayout gridButtons = new GridLayout(6,1);
        gridButtons.setVgap(10);
        gridButtons.setHgap(10);
        buttonsPanel.setLayout(gridButtons);

        GridLayout gridTree = new GridLayout(1,1);
        treePanel.setLayout(gridTree);

        buttonsPanel.add(bAdd);
        buttonsPanel.add(bEdit);
        buttonsPanel.add(bDelete);
        buttonsPanel.add(bDeleteLower);
        buttonsPanel.add(bClear);
        buttonsPanel.add(lMessage);
        treePanel.add(tree);
        pane.add(buttonsPanel, BorderLayout.WEST);
        pane.add(treePanel, BorderLayout.CENTER);
    }

    private void bDeleteActionPerformed(ActionEvent evt) {
        lMessage.setText("");
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        if(selectedNode!=null){
            int id =0;
            InjuredPoliceman object = (InjuredPoliceman)selectedNode.getUserObject();
            for (int i=0; i< Server.injuredPolicemen.size();i++){
                if(object.id == Server.injuredPolicemen.get(i).id){
                    id = object.id;
                    Server.injuredPolicemen.remove(i);}
            }
            deletePoliceman(id);
            model.removeNodeFromParent(selectedNode);
            Server.json = Server.gson.toJson(Server.injuredPolicemen);
            Server.inputOutput.output(Server.json);
        }else{
            lMessage.setText("<html><center>You should select policeman<br>to delete</html>");
        }
    }

    private void bDeleteLowerActionPerformed(ActionEvent evt) {
        lMessage.setText("");
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        if(selectedNode!=null){
            DefaultMutableTreeNode root = (DefaultMutableTreeNode)selectedNode.getRoot();
            int number=0;
            InjuredPoliceman object = (InjuredPoliceman)selectedNode.getUserObject();
            int id = 0;
            for (int i=0; i< Server.injuredPolicemen.size();i++){
                if(object.id == Server.injuredPolicemen.get(i).id){
                    id = object.id;
                    number = i;}
            }
            deleteLowerPoliceman(id);
            ArrayList<DefaultMutableTreeNode> nodes = new ArrayList<DefaultMutableTreeNode>();
            Enumeration e = root.breadthFirstEnumeration();
            while(e.hasMoreElements()){
                DefaultMutableTreeNode nodeForCollection = (DefaultMutableTreeNode)e.nextElement();
                nodes.add(nodeForCollection);
            }
            for (int i=0; i<Server.injuredPolicemen.size();i++) {
//                id = Server.injuredPolicemen.get(0).id;
//                Server.injuredPolicemen.remove(0);
                for(int j =0;j<nodes.size();j++){
                    if(!nodes.get(j).isRoot()) {
                        InjuredPoliceman object1 = (InjuredPoliceman) nodes.get(j).getUserObject();
                        if (object1.id < id) {
                            model.removeNodeFromParent(nodes.get(j));
                        }
                    }
                }
            }

            //Server.json = Server.gson.toJson(Server.injuredPolicemen);
            //Server.inputOutput.output(Server.json);
        }else{
            lMessage.setText("<html><center>You should select policeman<br>to delete policemen with lower index</html>");
        }
    }

    private void bAddActionPerformed(ActionEvent evt) {
        GUIAdd.createGUI();
    }

    private void bEditActionPerformed(ActionEvent evt){
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        if (selectedNode!=null) {
            lMessage.setText("");
            GUIEdit.createGUI(selectedNode);
        }else
            lMessage.setText("You should select policeman to edit");
    }

    private void bClearActionPerformed(ActionEvent evt) {
        lMessage.setText("");
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode)tree.getModel().getRoot();
        root.removeAllChildren();
        deleteAll();
        model.reload();
        Server.injuredPolicemen = new Vector<InjuredPoliceman>();
        Server.json = Server.gson.toJson(Server.injuredPolicemen);
        Server.inputOutput.output(Server.json);
    }

    public static void createGUI(){
        GUIServer frame = new GUIServer();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addComponents(frame.getContentPane());
        frame.pack();
        frame.setVisible(true);
        frame.setSize(400,600);
    }

    private void deletePoliceman(int id) {
        try {
            //Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/postgres";
            String login = "postgres";
            String password = "postgres";
            Connection con = DriverManager.getConnection(url, login, password);
            //String date= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss+02").format(Timestamp.valueOf(injuredPoliceman.created.toLocalDateTime()));
            String query = "DELETE FROM INJURED_POLICEMAN WHERE ID = " + id + ";";
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

    private void deleteLowerPoliceman(int id) {
        try {
            //Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/postgres";
            String login = "postgres";
            String password = "postgres";
            Connection con = DriverManager.getConnection(url, login, password);
            //String date= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss+02").format(Timestamp.valueOf(injuredPoliceman.created.toLocalDateTime()));
            String query = "DELETE FROM INJURED_POLICEMAN WHERE ID < " + id + ";";
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

    private void deleteAll() {
        try {
            //Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/postgres";
            String login = "postgres";
            String password = "postgres";
            Connection con = DriverManager.getConnection(url, login, password);
            //String date= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss+02").format(Timestamp.valueOf(injuredPoliceman.created.toLocalDateTime()));
            String query = "DELETE FROM INJURED_POLICEMAN;";
            //String date2= new SimpleDateFormat("YYYY-MM-DD hh:mm:ss.000000").format(date1).toString();
            try {
                Statement stmt = con.createStatement();
                ResultSet rs;
                        stmt.executeQuery(query);

                //123rs.close();
                stmt.close();
            } finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}