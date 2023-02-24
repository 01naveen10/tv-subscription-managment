package nizontv;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainScreen extends JFrame {


    //Panel 1 : User Registration (all are global values)
    JPanel subscriberPanel;

    JTextField subName;
    JTextField subLastName;
    JTextField subMobile;
    JTextField subCity;

    JLabel nameLBL;
    JLabel lastLBL;
    JLabel mobileLBL;
    JLabel cityLBL;

    //Panel 2: Cycle
    JPanel cyclePanel;

    JTextField startCycleFLD;
    JTextField endCycleFLD;
    JTextField numberTVFLD;

    JLabel todayLBL;
    JLabel startCycleLBL;
    JLabel endCycleLBL;
    JLabel numberTVLBL;

    SimpleDateFormat df;
    Date currentDate;

    //Panel 3 : Packages
    JPanel packagesPanel;

    JCheckBox sportsCHKBX;
    JCheckBox docCHKBX;
    JCheckBox moviesCHKBX;

    //Panel 4 : Channels
    JPanel channelsPanel;

    JTextArea channelAreaS;
    JTextArea channelAreaM;
    JTextArea channelAreaD;

    //Panel 5: fee
    JPanel feePanel;
    JLabel installFeeLBL;
    JLabel packageFeeLBL;
    JLabel totalFeeLBL;

    //Panel 6 :subscription data
    JPanel subDataPanel;
    JTable table;
    DefaultTableModel tableModel;

    //Panel 7:Action Panel
    JPanel actionPanel;
    JButton saveBTN;
    JButton loadBTN;
    JButton newBTN;



    /*********************   classes & objects   **********************/

    Subscriber subscriber;
    Subscription subscription;
    int packagesTotalPrice;
    int totalPrice;

    /*********************   Arrays   **********************/

    ArrayList<Subscription> listToSave= new ArrayList<>();

    File file;





    //Constructor
    public MainScreen(){
        /********************************************** PANEL 1 ***********************************************/
        subscriberPanel = new JPanel();
        Border panel1Tile = BorderFactory.createTitledBorder("Subscriber Details");
        subscriberPanel.setBorder(panel1Tile);
        subscriberPanel.setBounds(15,15,300,200);
        subscriberPanel.setLayout(new GridLayout(4,2));

        //JLabel
        nameLBL = new JLabel("Name:");
        lastLBL = new JLabel("Last Name:");
        mobileLBL = new JLabel("Mobile:");
        cityLBL = new JLabel("City:");

        //TextField
        subName = new JTextField();
        subLastName = new JTextField();
        subMobile = new JTextField();
        subCity = new JTextField();

        //Adding components to panel1
        subscriberPanel.add(nameLBL);
        subscriberPanel.add(subName);
        subscriberPanel.add(lastLBL);
        subscriberPanel.add(subLastName);
        subscriberPanel.add(mobileLBL);
        subscriberPanel.add(subMobile);
        subscriberPanel.add(cityLBL);
        subscriberPanel.add(subCity);

        /********************************************** PANEL 2 ***********************************************/
        cyclePanel =new JPanel();
        Border panel2Tile = BorderFactory.createTitledBorder("Cycle Details");
        cyclePanel.setBorder(panel2Tile);
        cyclePanel.setLayout(new GridLayout(14,1));
        cyclePanel.setBounds(15,230,300,500);

        todayLBL =new JLabel();
        df = new SimpleDateFormat("dd/MM/yyyy");
        currentDate = new Date();
        todayLBL.setText("Today: "+df.format(currentDate));
        startCycleLBL = new JLabel("Start Cycle Date (DD/MM/YYYY)");
        startCycleFLD = new JTextField();
        endCycleLBL = new JLabel("End Cycle Date (DD/MM/YYYY)");
        endCycleFLD = new JTextField();
        numberTVLBL = new JLabel("Number of TV");
        numberTVFLD = new JTextField();

        cyclePanel.add(todayLBL);
        cyclePanel.add(startCycleLBL);
        cyclePanel.add(startCycleFLD);
        cyclePanel.add(endCycleLBL);
        cyclePanel.add(endCycleFLD);
        cyclePanel.add(numberTVLBL);
        cyclePanel.add(numberTVFLD);

        /********************************************** PANEL 3 ***********************************************/
        packagesPanel = new JPanel();
        packagesPanel.setLayout(new GridLayout(5,1));
        packagesPanel.setBounds(330,15,300,200);
        Border panel3Tile = BorderFactory.createTitledBorder("Package Details");
        packagesPanel.setBorder(panel3Tile);

        JLabel packagesLBL = new JLabel("Please select your Package");

        sportsCHKBX = new JCheckBox("Sports Package");
        moviesCHKBX = new JCheckBox("Movies Package");
        docCHKBX = new JCheckBox("Documentary Package");

        JButton subscribeBTN = new JButton("Subscribe");

        packagesPanel.add(packagesLBL);
        packagesPanel.add(sportsCHKBX);
        packagesPanel.add(moviesCHKBX);
        packagesPanel.add(docCHKBX);
        packagesPanel.add(subscribeBTN);

        //CheckBox Listeners

        sportsCHKBX.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(sportsCHKBX.isSelected()){
                    DisplaySportsChannels();
                }else{
                    channelAreaS.setText("");
                }
            }
        });

        moviesCHKBX.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(moviesCHKBX.isSelected()){
                    DisplayMoviesChannels();
                }else{
                    channelAreaM.setText("");

                }

            }
        });

        docCHKBX.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(docCHKBX.isSelected()){
                    DisplayDocumentaryChannels();
                }else{
                    channelAreaD.setText("");

                }
            }
        });

        subscribeBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    GetSubscriberData();
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        /********************************************** PANEL 4 ***********************************************/
        channelsPanel = new JPanel();
        channelsPanel.setBounds(330,230,300,500);
        channelsPanel.setLayout(new GridLayout(3,1));
        Border panel4Tile = BorderFactory.createTitledBorder("Channel Details");
        channelsPanel.setBorder(panel4Tile);

        channelAreaS =new JTextArea(5,1);
        channelAreaS.setOpaque(false);
        channelAreaS.setEditable(false);
        channelAreaS.setLineWrap(true);

        channelAreaM =new JTextArea(5,1);
        channelAreaM.setOpaque(false);
        channelAreaM.setEditable(false);
        channelAreaM.setLineWrap(true);

        channelAreaD =new JTextArea(5,1);
        channelAreaD.setOpaque(false);
        channelAreaD.setEditable(false);
        channelAreaD.setLineWrap(true);

        channelsPanel.add(channelAreaS);
        channelsPanel.add(channelAreaM);
        channelsPanel.add(channelAreaD);

        /********************************************** PANEL 5 ***********************************************/
        feePanel = new JPanel();
        feePanel.setBounds(645,15,200,200);
        feePanel.setLayout(new GridLayout(3,1));
        Border panel5Tile = BorderFactory.createTitledBorder("Fee Details");
        feePanel.setBorder(panel5Tile);

        installFeeLBL = new JLabel("Installation Fee: ");
        packageFeeLBL = new JLabel("Package Fee: ");
        totalFeeLBL = new JLabel("Total Fee: ");

        feePanel.add(installFeeLBL);
        feePanel.add(packageFeeLBL);
        feePanel.add(totalFeeLBL);

        /********************************************** PANEL 6 ***********************************************/
        subDataPanel = new JPanel();
        subDataPanel.setBounds(645,230,515,500);
        subDataPanel.setLayout(new GridLayout(3,1));
        Border panel6Tile = BorderFactory.createTitledBorder("Our Customers");
        subDataPanel.setBorder(panel6Tile);

        //table
        tableModel = new DefaultTableModel();

        table = new JTable(tableModel);
        tableModel.addColumn("First Name");
        tableModel.addColumn("Last Name");
        tableModel.addColumn("Phone Number");
        tableModel.addColumn("Start Cycle");
        tableModel.addColumn("End Cycle");
        tableModel.addColumn("Total Fee");
        JScrollPane scrollPane = new JScrollPane(table);
        subDataPanel.add(scrollPane);

        /********************************************** PANEL 7 ***********************************************/
        actionPanel = new JPanel();
        actionPanel.setBounds(860,15,300,200);
        actionPanel.setLayout(new GridLayout(4,1));
        Border panel7Tile = BorderFactory.createTitledBorder("Action");
        actionPanel.setBorder(panel7Tile);

        saveBTN = new JButton("Save Subscription");
        loadBTN = new JButton("Load Subscription");
        newBTN = new JButton("New Subscription");

        actionPanel.add(newBTN);
        actionPanel.add(saveBTN);
        actionPanel.add(loadBTN);

        newBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewSubscription();
            }
        });

        saveBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SaveSubscriptionToDisk();
            }
        });

        loadBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Subscription> savedList = LoadDataFromDisk();

            }
        });



        //JFame
        setLayout(null);
        add(subscriberPanel);
        add(cyclePanel);
        add(packagesPanel);
        add(channelsPanel);
        add(feePanel);
        add(subDataPanel);
        add(actionPanel);

    }




    /************************************* Methods  **********************************************/
    private int DisplaySportsChannels() {

        int packagePrice =0;

        SportsChannel m1 = new SportsChannel("AFN Sports","EN","SPRT", 5);
        SportsChannel m2 = new SportsChannel("beIN Sports","FR","SPRT", 3);
        SportsChannel m3 = new SportsChannel("Eleven Sports","EN","SPRT", 8);
        SportsChannel m4 = new SportsChannel("NBA TV","EN","SPRT", 6);
        SportsChannel m5 = new SportsChannel("NFL Network","AR","SPRT", 3);
        SportsChannel m6 = new SportsChannel("The Ski Channel","AR","SPRT", 1);

        ArrayList<SportsChannel> sportsChannels = new ArrayList<>();
        sportsChannels.add(m1);
        sportsChannels.add(m2);
        sportsChannels.add(m3);
        sportsChannels.add(m4);
        sportsChannels.add(m5);
        sportsChannels.add(m6);

        String sportsChannelString ="";
        for(int i=0;i< sportsChannels.size();i++){
            sportsChannelString+="       " + sportsChannels.get(i).getChannelName()
                    + "       " +sportsChannels.get(i).getLanguage()
                    + "       " +sportsChannels.get(i).getPrice()+"\n";
            packagePrice += sportsChannels.get(i).getPrice();

        }
        channelAreaS.setText(sportsChannelString);
        return  packagePrice;

    }

    private int DisplayMoviesChannels() {

        int packagePrice = 0;

        MovieChannel m1 = new MovieChannel("MBC Bundle","EN","MOV", 4);
        MovieChannel m2 = new MovieChannel("Cinema One","EN","MOV", 5);
        MovieChannel m3 = new MovieChannel("Cinema Pro","RU","MOV", 6);
        MovieChannel m4 = new MovieChannel("Cinema 1","AR","MOV", 2);
        MovieChannel m5 = new MovieChannel("Movie Home","GR","MOV", 4);
        MovieChannel m6 = new MovieChannel("Film4","FR","MOV", 2);

        ArrayList<MovieChannel> movieChannels = new ArrayList<>();
        movieChannels.add(m1);
        movieChannels.add(m2);
        movieChannels.add(m3);
        movieChannels.add(m4);
        movieChannels.add(m5);
        movieChannels.add(m6);

        String movChannelString ="";
        for(int i=0;i< movieChannels.size();i++){
            movChannelString+="       " + movieChannels.get(i).getChannelName()
                    + "       " +movieChannels.get(i).getLanguage()
                    + "       " +movieChannels.get(i).getPrice()+"\n";
            packagePrice += movieChannels.get(i).getPrice();
        }
        channelAreaM.setText(movChannelString);
        return packagePrice;
    }

    private int DisplayDocumentaryChannels() {
        int packagePrice = 0;
        DocumentaryChannel m1 = new DocumentaryChannel("NAT GEO","SP","DOC", 3);
        DocumentaryChannel m2 = new DocumentaryChannel("PBS America","EN","DOC", 2);
        DocumentaryChannel m3 = new DocumentaryChannel("Al Jazeera Documentary","IN","DOC", 3);
        DocumentaryChannel m4 = new DocumentaryChannel("Canal D","EN","DOC", 4);
        DocumentaryChannel m5 = new DocumentaryChannel("Discovery  Historia","AR","DOC", 5);
        DocumentaryChannel m6 = new DocumentaryChannel("World Documentary","GR","DOC", 1);

        ArrayList<DocumentaryChannel> documentaryChannels = new ArrayList<>();
        documentaryChannels.add(m1);
        documentaryChannels.add(m2);
        documentaryChannels.add(m3);
        documentaryChannels.add(m4);
        documentaryChannels.add(m5);
        documentaryChannels.add(m6);

        String docChannelString ="";
        for(int i=0;i< documentaryChannels.size();i++){
            docChannelString+="       " + documentaryChannels.get(i).getChannelName()
                    + "       " +documentaryChannels.get(i).getLanguage()
                    + "       " +documentaryChannels.get(i).getPrice()+"\n";
            packagePrice+= documentaryChannels.get(i).getPrice();
        }
        channelAreaD.setText(docChannelString);

        return  packagePrice;

    }

    private void GetSubscriberData() throws ParseException {
        Date currentDate = new Date();

        subscriber = new Subscriber(subName.getText(),subLastName.getText(),subCity.getText(),Integer.parseInt(subMobile.getText()));

        Date startCycle = df.parse(startCycleFLD.getText());
        Date endCycle = df.parse(endCycleFLD.getText());
        SubscriptionCycle subscriptionCycle = new SubscriptionCycle(df.format(startCycle), df.format(endCycle));

        subscription = new Subscription(Integer.parseInt(numberTVFLD.getText()),subscriber,subscriptionCycle,df.format(currentDate));

        installFeeLBL.setText("Installation Fee:"+ subscription.getTotalFee()+" $");

        showPrice();


    }

    private void showPrice(){
        if(sportsCHKBX.isSelected())
            packagesTotalPrice+=DisplaySportsChannels();
        else if(moviesCHKBX.isSelected())
            packagesTotalPrice+=DisplayMoviesChannels();
        else if(docCHKBX.isSelected())
            packagesTotalPrice+=DisplayDocumentaryChannels();


        packageFeeLBL.setText("Package Fee: "+ packagesTotalPrice + " $");
        totalPrice = subscription.getTotalFee()+packagesTotalPrice;
        totalFeeLBL.setText("Total Amount: "+totalPrice+" $");
    }

    private void NewSubscription() {
        subName.setText("");
        subLastName.setText("");
        subCity.setText("");
        subMobile.setText("");

        startCycleFLD.setText("");
        endCycleFLD.setText("");
        numberTVFLD.setText("");

        installFeeLBL.setText("");
        packageFeeLBL.setText("");
        totalFeeLBL.setText("");

        sportsCHKBX.setSelected(false);
        moviesCHKBX.setSelected(false);
        docCHKBX.setSelected(false);
    }

    private void SaveSubscriptionToDisk() {
        listToSave.add(subscription);
        file = new File("c:\\Users\\01naveen10\\Documents\\programming\\java_programming\\Section_03\\Tarzan\\myData.dat");

        try{
            OutputStream os = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(listToSave);
            oos.flush();
            oos.close();
            os.close();
        }catch (IOException e){
            e.printStackTrace();

        }
    }

    private ArrayList<Subscription> LoadDataFromDisk() {
        ArrayList<Subscription> subList = new ArrayList<>();
        try {
            file = new File("c:\\Users\\01naveen10\\Documents\\programming\\java_programming\\Section_03\\Tarzan\\myData.dat");
            InputStream is = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(is);
            subList = (ArrayList) ois.readObject();
            ois.close();
            is.close();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        for(Subscription sub : subList){
            DisplaySubscriptionInTable(sub);
        }

        return subList;
    }

    private void DisplaySubscriptionInTable(Subscription sub) {

        tableModel.addRow(new Object[]{
                sub.getSubscriber().getfName(),
                sub.getSubscriber().getlName(),
                sub.getSubscriber().getCity(),
                sub.getSubscriber().getPhone(),
                sub.getSubCycle().getStartDate(),
                sub.getSubCycle().getEndDate(),
                sub.getTotalFee()

        });
    }


    public static void main(String[] args){
        MainScreen mainScreen = new MainScreen();
        mainScreen.setBounds(100,10,1200,800);
        mainScreen.setVisible(true);
    }
}
