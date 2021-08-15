import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class MainWindow extends JFrame {

    public JLabel chatLabel;
    public JLabel funcLabel;

    public JTextArea chatOutput;
    public JTextField chatInput;
    public JButton btnDayZ;
    public JButton btnFort;
    public JButton btnSend;
    public JButton btnCSGO;


    public MainWindow(String titel, DataOutputStream os, DataInputStream is) throws IOException {

        setTitle(titel);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setSize(800, 600);
        setResizable(false);

        initComponents(os, is);

        add(funcLabel);
        add(chatInput);
        add(btnSend);
        add(btnCSGO);
        add(btnDayZ);
        add(btnFort);

        add(chatLabel);
        add(chatOutput);
        chatOutput.setSize(500,300);
        setVisible(true);
        setLocationRelativeTo(null);


    }

    private void initComponents(DataOutputStream os, DataInputStream is) {

        chatLabel = new JLabel("Twitch-Chat");
        funcLabel = new JLabel ("Kfeemaschine:");
        chatOutput = new JTextArea("Hier erscheint der Chat: "+"\n",10,50);
        chatInput = new JTextField(8);
        btnSend = new JButton(">>");
        btnCSGO = new JButton("CSGO");
        btnCSGO.setBackground(Color.gray);
        btnDayZ = new JButton("DayZ");
        btnDayZ.setBackground(Color.darkGray);
        btnDayZ.setForeground(Color.white);
        btnFort = new JButton("Fortnite");
        btnFort.setBackground(Color.cyan);


        btnSend.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    os.writeBytes("PRIVMSG #rubizockt : "+chatInput.getText()+"\n\r");
                    System.out.println(">> Kfeemaschine: "+chatInput.getText());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                chatInput.setText("");
            }
        });

        btnCSGO.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    os.writeBytes("PRIVMSG #rubizockt : !setgame csgo \n\r");
                    String confi ="CSGO wird eingestellt";
                    System.out.println(confi);
                    chatOutput.append(confi+"\n");
                }catch (IOException ex){
                    ex.printStackTrace();
                }
            }
        });
        btnFort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    os.writeBytes("PRIVMSG #rubizockt : !setgame Fortnite \n\r");
                    String confi = "Fortnite wird eingestellt";
                    System.out.println(confi);
                    chatOutput.append(confi+"\n");
                }catch (IOException ex){
                    ex.printStackTrace();
                }
            }
        });
        btnDayZ.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    os.writeBytes("PRIVMSG #rubizockt : !setgame DayZ \n\r");
                    String confi = "DayZ wird eingestellt";
                    System.out.println(confi);
                    chatOutput.append(confi+"\n");
                }catch (IOException ex){
                    ex.printStackTrace();
                }
            }
        });

    }
}
