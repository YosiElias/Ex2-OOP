import api.DirectedWeightedGraphAlgorithms;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;



public class GuiGraph extends JFrame implements ActionListener {
    private DWGalgo _algo;

    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu funcMenu;
    private JMenu editMenu;
    private JMenuItem loadItem;
    private JMenuItem saveItem;
    private JMenuItem exitItem;
    private JMenuItem cleanScreen;
    private JMenuItem isConnected;
    private JMenuItem shortestPathDist;
    private JMenuItem shortestPath;
    private JMenuItem center;
    private JMenuItem  tsp;
    private JMenuItem  addNode;
    private JMenuItem  deletNode;
    private JMenuItem  addEdge;
    private JMenuItem  deletEdge;
    private ImageIcon loadIcon;
    private ImageIcon saveIcon;
    private ImageIcon exitIcon;
    private GraphPanel panel;


    GuiGraph(DWGalgo algo){
        this();
        this._algo = algo;
        panel.set_graph((DirectedWeightedGraphClass) algo.getGraph());
        showGraph();
        this.setVisible(true);
    }

    GuiGraph(){
        super();
        _algo = new DWGalgo();
        panel = new GraphPanel(_algo, _algo.getGraph());
        this.add(panel);
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setUndecorated(true);    //pack() xor this line
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }



        loadIcon = new ImageIcon("./resources/load.jpg");
        saveIcon = new ImageIcon("./resources/save.png");
        exitIcon = new ImageIcon("./resources/exit.jpg");
//        cleanScreen =  new ImageIcon("./resources/load.jpg");
//        isConnected
//        shortestPathDist;
//        shortestPath;
//        center;
//        tsp;
//        addNode;
//        deletNode;
//        addEdge;
//        deletEdge;

        loadIcon = scaleImageIcon(loadIcon,20,20);
        saveIcon = scaleImageIcon(saveIcon,20,20);
        exitIcon = scaleImageIcon(exitIcon,20,20);

        menuBar = new JMenuBar();

        fileMenu = new JMenu("File");
        funcMenu = new JMenu("Function");
        editMenu = new JMenu("Edit");

        loadItem = new JMenuItem("Load Graph");
        saveItem = new JMenuItem("Save Graph");
        exitItem = new JMenuItem("Exit");
        cleanScreen = new JMenuItem("clean Screen");

        loadItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);
        cleanScreen.addActionListener(this);


        loadItem.setIcon(loadIcon);
        saveItem.setIcon(saveIcon);
        exitItem.setIcon(exitIcon);

        isConnected = new JMenuItem("isConnected");
        shortestPathDist = new JMenuItem("shortestPathDist");
        shortestPath = new JMenuItem("shortestPath");
        center = new JMenuItem("center");
        tsp = new JMenuItem("tsp");

        isConnected.addActionListener(this);
        shortestPath.addActionListener(this);
        shortestPathDist.addActionListener(this);
        center.addActionListener(this);
        tsp.addActionListener(this);

        fileMenu.setMnemonic(KeyEvent.VK_F); // Alt + f for file
        funcMenu.setMnemonic(KeyEvent.VK_A); // Alt + e for func
        editMenu.setMnemonic(KeyEvent.VK_E); // Alt + h for edit
        loadItem.setMnemonic(KeyEvent.VK_L); // Alt + f + l for load
        saveItem.setMnemonic(KeyEvent.VK_S); // Alt + f + s for save
        exitItem.setMnemonic(KeyEvent.VK_E); // Alt + f + e for exit
        cleanScreen.setMnemonic(KeyEvent.VK_D); // Alt + f + d for clean screen

        addNode = new JMenuItem("Add Node");
        deletNode = new JMenuItem("Remove Node");
        addEdge = new JMenuItem("Add Edge");
        deletEdge = new JMenuItem("Remove Edge");

        addNode.addActionListener(this);
        deletNode.addActionListener(this);
        addEdge.addActionListener(this);
        deletEdge.addActionListener(this);

        fileMenu.add(loadItem);
        fileMenu.add(saveItem);
        fileMenu.add(cleanScreen);
        fileMenu.add(exitItem);

        funcMenu.add(isConnected);
        funcMenu.add(shortestPath);
        funcMenu.add(shortestPathDist);
        funcMenu.add(center);
        funcMenu.add(tsp);

        editMenu.add(addNode);
        editMenu.add(deletNode);
        editMenu.add(addEdge);
        editMenu.add(deletEdge);

        menuBar.add(fileMenu);
        menuBar.add(funcMenu);
        menuBar.add(editMenu);


        this.setJMenuBar(menuBar);

        this.setVisible(true);
    }


    public static ImageIcon scaleImageIcon(ImageIcon imageIcon, int width, int height){
        Image image = imageIcon.getImage(); // transform it
        Image newimg = image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        return new ImageIcon(newimg);  // transform it back
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            //File Menu:
            if (e.getSource() == loadItem) {
                loadFile();
                showGraph();
            }
            if (e.getSource() == saveItem) {
                saveFile();
            }
            if (e.getSource() == cleanScreen) {
                clean();
            }
            if (e.getSource() == exitItem) {
                System.exit(0);
            }
            //Function:
            if (e.getSource() == isConnected) {
                isConnectedFunc();
            }
            if (e.getSource() == shortestPath) {
                shortestPathFunc();
            }
            if (e.getSource() == shortestPathDist) {
                shortestPathDistFunc();
            }
            if (e.getSource() == center) {
                centerFunc();
            }
            if (e.getSource() == tsp) {
                tspFunc();
            }
            //Edit:
            if (e.getSource() == addNode) {
                addNodeFunc();
            }
            if (e.getSource() == deletNode) {
                deletNodeFunc();
            }
            if (e.getSource() == addEdge) {
                addEdgeFunc();
            }
            if (e.getSource() == deletEdge) {
                deletEdgeFunc();
            }
        }
        catch(Exception er){
            infoBox("Eror: \nAn error occurred while executing the operation.\n" +
                    "Please make sure you enter proper input and try again.", "Function Eror", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletEdgeFunc() {
        JFrame textWind = new JFrame();
        String getMessage = JOptionPane.showInputDialog(textWind, "Enter source (node id) of edge to delete:");
        int src = Integer.parseInt(getMessage);
        getMessage = JOptionPane.showInputDialog(textWind, "Enter destination (node id) of edge to delete:");
        int dest = Integer.parseInt(getMessage);
        panel.deletEdgeFunc(src, dest);
    }

    private void addEdgeFunc() {
        JFrame textWind = new JFrame();
        String getMessage = JOptionPane.showInputDialog(textWind, "Enter source (node id) of edge to add:");
        int src = Integer.parseInt(getMessage);
        getMessage = JOptionPane.showInputDialog(textWind, "Enter destination (node id) of edge to add:");
        int dest = Integer.parseInt(getMessage);
        getMessage = JOptionPane.showInputDialog(textWind, "Enter weight of the new node:");
        double w = Double.parseDouble(getMessage);
        panel.addEdgeFunc(src, dest, w);
    }

    private void deletNodeFunc() {
        JFrame textWind = new JFrame();
        String getMessage = JOptionPane.showInputDialog(textWind, "Enter node id to delete:");
        int id = Integer.parseInt(getMessage);
        panel.deletNodeFunc(id);
    }

    private void addNodeFunc() {
        JFrame textWind = new JFrame();
        String getMessage = JOptionPane.showInputDialog(textWind, "Enter x coordinate of new node:");
        double x = Double.parseDouble(getMessage);
        getMessage = JOptionPane.showInputDialog(textWind, "Enter y coordinate of new node:");
        double y = Double.parseDouble(getMessage);
        double z = 0;
        getMessage = JOptionPane.showInputDialog(textWind, "Enter id of new node:");
        int id = Integer.parseInt(getMessage);
        panel.addNodeFunc(x, y, z, id);
    }

    private void tspFunc() {
        List<Integer> citiesID = new ArrayList<>();
        String getMessage = "";
        JFrame textWind = new JFrame();
        while (!getMessage.equals("z") && !getMessage.equals("Z")) {
            getMessage = JOptionPane.showInputDialog(textWind, "Enter Node id, to end enter Z");
            try {
                if (!getMessage.equals("z") && !getMessage.equals("Z"))
                    citiesID.add(Integer.parseInt(getMessage));
            }
            catch (NumberFormatException e){
                infoBox("Eror: \nThe input you entered is invalid !\nTo end the input enter Z", "TSP - input eror", JOptionPane.ERROR_MESSAGE);
            }
        }
        panel.tspFunc(citiesID);
    }

    private void centerFunc() {
        panel.centerFunc();
    }

    private void shortestPathDistFunc() {
        JFrame textWind = new JFrame();
        String getMessage = JOptionPane.showInputDialog(textWind, "Enter source Node id");
        int src = Integer.parseInt(getMessage);
        getMessage = JOptionPane.showInputDialog(textWind, "Enter dest Node id");
        int dest = Integer.parseInt(getMessage);
        panel.shortestPathDistFunc(src, dest);
    }

    private void shortestPathFunc() {
        JFrame textWind = new JFrame();
        String getMessage = JOptionPane.showInputDialog(textWind, "Enter source Node id");
        int src = Integer.parseInt(getMessage);
        getMessage = JOptionPane.showInputDialog(textWind, "Enter dest Node id");
        int dest = Integer.parseInt(getMessage);
        panel.shortestPathFunc(src, dest);
    }

    private void isConnectedFunc() {
        if (_algo.isConnected())
            infoBox("True: \nThere is a valid path from each node to each other node", "    isConnected  ->  True", JOptionPane.PLAIN_MESSAGE);
        else
            infoBox("False: \nNot every node to another node has a legal way", "    isConnected  ->  False", JOptionPane.PLAIN_MESSAGE);
    }

    private void clean() {
        panel.set_clean(true);
        panel.reset();
        this.repaint();
    }

    private void showGraph() {
        panel.set_clean(false);
        panel.set_graph((DirectedWeightedGraphClass) _algo.getGraph());
        panel.reset();
        this.repaint();
    }

    private void saveFile() {
        infoBox("Please select the Json file to save the graph in it", "Message", JOptionPane.PLAIN_MESSAGE);
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {    // user selects a file
            File selectedFile = fileChooser.getSelectedFile();
//            System.out.println("Selected file: " + selectedFile.getAbsolutePath());   //for self testing
            if (_algo.save(selectedFile.getAbsolutePath()))
                infoBox("Saving graph is complete :)", "Message", JOptionPane.PLAIN_MESSAGE);
            else
                infoBox("Saving  of the graph is failed !", "Message", JOptionPane.ERROR_MESSAGE);
        }
        else
            infoBox("Eror: \nAn error occurred while executing the operation.\n" +
                    "Please make sure you enter proper input and try again.", "Function Eror", JOptionPane.ERROR_MESSAGE);
    }

    private void loadFile() {
        infoBox("Please select a Json file to load a graph from", "Message", JOptionPane.PLAIN_MESSAGE);
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {    // user selects a file
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            if (_algo.load(selectedFile.getAbsolutePath()))
                infoBox("Loading graph is complete :)", "Message", JOptionPane.PLAIN_MESSAGE);
            else
                infoBox("Loading of the graph is failed !", "Message", JOptionPane.ERROR_MESSAGE);
        }
        else
            infoBox("Eror: \nAn error occurred while executing the operation.\n" +
                    "Please make sure you enter proper input and try again.", "Function Eror", JOptionPane.ERROR_MESSAGE);
    }


    public static void infoBox(String infoMessage, String titleBar,int typeOfMassage)
    {
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, typeOfMassage);
    }

    public void plotGraph(DirectedWeightedGraphAlgorithms alg) {
        this._algo = (DWGalgo) alg;
        panel.set_algo((DirectedWeightedGraphClass) alg.getGraph());
        showGraph();
    }

    public static void main(String[] args) {
        new GuiGraph(); //for self testing
    }


}
