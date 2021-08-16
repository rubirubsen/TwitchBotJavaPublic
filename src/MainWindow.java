import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

public class MainWindow extends JFrame {

    public JLabel chatLabel;
    public JLabel funcLabel;
    public ImageIcon dayZ = new ImageIcon("dayz.png");
    public JTextArea chatOutput;
    public JTextField chatInput;
    public JButton btnDayZ;
    public JButton btnFort;
    public JButton btnSend;
    public JButton btnCSGO;


    public MainWindow(String titel, DataOutputStream os, DataInputStream is, String channel, String oauth, String realName, String username, String nickname, String hostname, int port, Socket client) throws IOException {

        setTitle(titel);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setResizable(false);

        initComponents(os, is);

        JScrollPane scrollArea = new JScrollPane(chatOutput);

        add(funcLabel);
        add(chatInput);
        add(btnSend);
        add(btnCSGO);
        add(btnDayZ);
        add(btnFort);
        add(scrollArea);
        scrollArea.setSize(600, 400);
        scrollArea.setAutoscrolls(true);
        add(chatLabel);
        chatOutput.setSize(480, 300);
        chatOutput.setLineWrap(true);

        setVisible(true);
        setLocationRelativeTo(null);

        chatInput.setColumns(60);
        Action action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    os.writeBytes("PRIVMSG #rubizockt : " + chatInput.getText() + "\n\r");
                    System.out.println(">> Kfeemaschine: " + chatInput.getText());
                    } catch (IOException ex) {
                            ex.printStackTrace();
                            }

                chatOutput.append("KFEEMASCHINE >> " + chatInput.getText() + "\n");
                JScrollBar scrollbar1 = scrollArea.getVerticalScrollBar();
                scrollbar1.setValue(scrollbar1.getMaximum());
                chatInput.setText("");

            }
        };

        btnSend.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    os.writeBytes("PRIVMSG #rubizockt : " + chatInput.getText() + "\n\r");
                    System.out.println(">> Kfeemaschine: " + chatInput.getText());
                    chatOutput.append("KFEEMASCHINE >> " + chatInput.getText() + "\n");
                    JScrollBar scrollbar1 = scrollArea.getVerticalScrollBar();
                    scrollbar1.setValue(scrollbar1.getMaximum());
                    chatInput.setText("");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        btnCSGO.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    os.writeBytes("PRIVMSG #rubizockt : !setgame csgo \n\r");
                    String confi = "CSGO wird eingestellt";
                    System.out.println(confi);
                    chatOutput.append(confi + "\n");
                    JScrollBar scrollbar1 = scrollArea.getVerticalScrollBar();
                    scrollbar1.setValue(scrollbar1.getMaximum());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnFort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    os.writeBytes("PRIVMSG #rubizockt : !setgame Fortnite \n\r");
                    String confi = "Fortnite wird eingestellt";
                    System.out.println(confi);
                    chatOutput.append(confi + "\n");
                    JScrollBar scrollbar1 = scrollArea.getVerticalScrollBar();
                    scrollbar1.setValue(scrollbar1.getMaximum());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnDayZ.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    os.writeBytes("PRIVMSG #rubizockt : !setgame DayZ \n\r");
                    String confi = "DayZ wird eingestellt";
                    System.out.println(confi);
                    chatOutput.append(confi + "\n");
                    JScrollBar scrollbar1 = scrollArea.getVerticalScrollBar();
                    scrollbar1.setValue(scrollbar1.getMaximum());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        chatInput.addActionListener(action);
        {
            os.writeBytes("PRIVMSG #rubizockt : " + chatInput.getText() + "\n\r");
            System.out.println(">> Kfeemaschine: " + chatInput.getText());
            chatOutput.append("KFEEMASCHINE >> " + chatInput.getText() + "\n");
            JScrollBar scrollbar1 = scrollArea.getVerticalScrollBar();
            scrollbar1.setValue(scrollbar1.getMaximum());
            chatInput.setText("");
        }

        System.out.println(
                "Server: " + hostname + "\n" +
                        "Port: " + port + "\n" +
                        "Nickname: " + nickname + "\n" +
                        "Username: " + username + "\n" +
                        "Real Name: " + realName + "\n");

        System.out.println("-------------------------");




        try {

            if (client != null && os != null && is != null) {

                os.writeBytes("PASS " + oauth + "\r\n");
                os.writeBytes("NICK " + nickname + "\r\n");
                os.writeBytes("USER " + username + " 0 * :" + realName + "\r\n");
                os.flush();

                is.readLine();
                String respons;
                String usrmsg = "";


                while ((respons = is.readLine()) != null) {
                    byte[] rtext = respons.getBytes("ISO_8859_1");
                    String response = new String(rtext,"UTF-8");

                    if (response.contains("004")) {
                        cmds c = new cmds();
                        c.joinChannel(os,channel);

                    } else if (response.contains("433")) {
                        System.out.println("Nickname is already in use.");
                        return;
                    }

                    if (response.startsWith("PING")) {
                        cmds c = new cmds();
                        c.pong(os,response);

                    } else if (response.contains("@")) {

                        cmds control = new cmds();

                        if(cmds.nachricht(response).startsWith("!")) {
                            System.out.println("! - Befehl erkannt");

                            if (cmds.nachricht(response).equals("!da")) {
                                System.out.println("- BOT ONLINE-");
                                os.writeBytes("PRIVMSG #rubizockt : da! \r\n");

                            } else if (cmds.nachricht(response).equals("!ts3")) {
                                cmds c = new cmds();
                                String benutzer = c.user(response);
                                System.out.println(benutzer + " >> !ts3");
                                os.writeBytes("PRIVMSG #rubizockt :@" + benutzer + " - Die Teamspeak-'Adresse' lautet rubizockt  \n \r");

                            } else if (cmds.nachricht(response).equals("!lurk")) {
                                cmds c = new cmds();
                                String benutzer = c.user(response);
                                System.out.println(benutzer + " >> !lurk");
                                os.writeBytes("PRIVMSG #rubizockt :" + benutzer + " ist jetzt im Lurk. Bis denn dann! KonCha \n\r");

                            } else if (cmds.nachricht(response).equals("!liebe")) {
                                cmds c = new cmds();
                                String usr = c.user(response);
                                System.out.println(usr + " >> !liebe");
                                os.writeBytes("PRIVMSG #rubizockt : TwitchUnity TwitchUnity TwitchUnity TwitchUnity TwitchUnity TwitchUnity \n\r");

                            } else if (cmds.nachricht(response).equals("!lautlos")) {
                                os.writeBytes("PRIVMSG #rubizockt : Falls ihr den Stream lautlos stellen moechtet, macht das bitte ueber den Tab im Browser (Rechtsklick-Tab stummschalten) anstatt direkt im Stream. Somit werdet ihr weiter als Viewer aufgefuehrt und helft euren Lieblingsstreamern ;) \n \r");

                            } else if (cmds.nachricht(response).equals("!discord")) {
                                cmds c = new cmds();
                                String usr = c.user(response);
                                System.out.println(usr + " >> !discord");
                                os.writeBytes("PRIVMSG #rubizockt : @" + usr + " PurpleStar https://discord.gg/f4QwWb2 PurpleStar \n\r");

                            } else if (cmds.nachricht(response).equals("!dc")) {
                                cmds c = new cmds();
                                String usr = c.user(response);
                                System.out.println(usr + " >> !dc");
                                os.writeBytes("PRIVMSG #rubizockt : @" + usr + " PurpleStar https://discord.gg/f4QwWb2 PurpleStar \n\r");

                            } else if (cmds.nachricht(response).equals("!insta")) {
                                cmds c = new cmds();
                                String usr = c.user(response);
                                System.out.println(usr + " >> !insta");
                                os.writeBytes("PRIVMSG #rubizockt : @" + usr + " TTours https://instagram.com/rubizockt/ TTours \n\r");

                            }   else if (cmds.nachricht(response).equals("!f1")) {
                                cmds c = new cmds();
                                String usr = c.user(response);
                                System.out.println(usr + " >> !f1");
                                os.writeBytes("PRIVMSG #rubizockt : @" + usr + " Rubi faehrt die MyTeam Karriere mit einer 79er KI , ohne Fahrhilfen (Traktionshilfe Mittel) und mit mittlerer Länge. Sein Lenkrad ist ein Xbox360 Controller.  \n\r");

                            }  else if (cmds.nachricht(response).equals("!rubi")) {
                                cmds c = new cmds();
                                String phrase = c.aktuell();
                                System.out.println(phrase);
                                os.writeBytes("PRIVMSG #rubizockt :"+phrase+" \n\r");

                            }else if (cmds.nachricht(response).equals("!kaffeesucht")){
                                db db = new db();
                                db.kaffeeAnzahl(os);
                            }else if (cmds.nachricht(response).equals("!kaffee++")){
                                db db = new db();
                                db.mehrKaffee(os);
                            }else if (cmds.nachricht(response).equals("!kaffee--")){
                                db db = new db();
                                db.wenigerKaffee(os);
                            }else if(cmds.nachricht(response).equals("!zaehlmal")){
                                cmds c = new cmds();
                                c.userZaehlen(os);
                            }else if (cmds.nachricht(response).equals("!chat")){
                                os.writeBytes("PRIVMSG #rubizockt : !setgame Just Chatting \n\r");
                            }else if (cmds.nachricht(response).equals("!wows")){
                                os.writeBytes("PRIVMSG #rubizockt : !setgame World of Warships \n\r");
                            }else if (cmds.nachricht(response).equals("!sleep")){
                                os.writeBytes("PRIVMSG #rubizockt : !setgame I'm only Sleeping \n\r");
                            }else if (cmds.nachricht(response).equals("!csgo")){
                                os.writeBytes("PRIVMSG #rubizockt : !setgame csgo \n\r");
                            }else if (cmds.nachricht(response).equals("!pubg")){
                                os.writeBytes("PRIVMSG #rubizockt : !setgame Playerunknown's Battleground \n\r");
                            }else if (cmds.nachricht(response).equals("!arma")){
                                os.writeBytes("PRIVMSG #rubizockt : !setgame ARMA 3 \n\r");
                            }else if (cmds.nachricht(response).equals("!poker")){
                                os.writeBytes("PRIVMSG #rubizockt : !setgame Poker \n\r");
                            }else if (cmds.nachricht(response).equals("!dayz")){
                                os.writeBytes("PRIVMSG #rubizockt : !setgame DayZ \n\r");
                            }else if (cmds.nachricht(response).equals("!fortnite")){
                                os.writeBytes("PRIVMSG #rubizockt : !setgame Fortnite \n\r");
                            }else if (cmds.nachricht(response).equals("!elite")){
                                os.writeBytes("PRIVMSG #rubizockt : !setgame Elite Dangerous: Odyssey \n\r");
                            }else if (cmds.nachricht(response).equals("!alwayson")){
                                os.writeBytes("PRIVMSG #rubizockt : !setgame Always On \n\r");
                            }
                            else if (cmds.nachricht(response).equals("!thread")) {
                                os.writeBytes("JOIN #derhamsta \n\r");
                                os.writeBytes("PRIVMSG #derhamsta : Nur kurz zu Besuch KonCha \n\r");
                            }
                            // Ende der Befehle

                            // Auslöser durch Worterwähnungen (contains)
                        } else if (response.contains(";)")) {
                            cmds c = new cmds();
                            c.user(response);
                            c.cool(os, response);

                        } else if (response.contains("bot")) {
                            cmds c = new cmds();
                            if(c.user(response).equals("wizebot")){
                                System.out.println("wizebot war wieder da");
                            }else{
                                c.nobot(os, response);
                            }
                            System.out.println(c.user(response) + ": " + cmds.nachricht(response));

                        }else if (response.contains("taest")) {
                            cmds c = new cmds();
                            c.channelSearch();

                        } else if (response.contains("goh")) {
                            cmds c = new cmds();
                            c.sturmAngriff();

                        }else if (response.contains("laehnge")) {
                            cmds c = new cmds();
                            int laenge = c.defLaenge(response);
                            System.out.println(laenge);

                        } else if ((response.toLowerCase().contains("hallo")) || (response.toLowerCase().contains("hey"))){
                            cmds c= new cmds();
                            String usr = c.user(response);
                            os.writeBytes("PRIVMSG #rubizockt : Hallo " + usr + "! KonCha \n\r");
                            System.out.println(c.user(response) + ": " + cmds.nachricht(response));

                        }else if (response.toLowerCase().contains("guten abend")) {
                            cmds c = new cmds();
                            String usr = c.user(response);
                            os.writeBytes("PRIVMSG #rubizockt : Dir auch einen guten Abend, "+usr+"\n\r");
                            System.out.println(c.user(response) + ": " + cmds.nachricht(response));

                        }else if (response.toLowerCase().contains("gute nacht")) {
                            cmds c = new cmds();
                            String usr = c.user(response);
                            os.writeBytes("PRIVMSG #rubizockt : Schlaf gut, "+usr+", bis neulich <3 KonCha \n\r");
                            System.out.println(c.user(response) + ": " + cmds.nachricht(response));

                        }else if (response.toLowerCase().contains("guten morgen")) {
                            cmds c = new cmds();
                            String usr = c.user(response);
                            os.writeBytes("PRIVMSG #rubizockt : Coffee Guten Morgen, "+usr+", schon wach? Coffee \n\r");
                            System.out.println(c.user(response) + ": " + cmds.nachricht(response));
                        }
                        // falls nichts zutrifft, hier einfach ausgabe user : nachricht
                        else {
                            cmds c = new cmds();
                            System.out.println(c.user(response) + ": " + cmds.nachricht(response));
                            chatOutput.append(c.user(response)+ " >> " + cmds.nachricht(response)+"\n");
                            JScrollBar scrollbar1 = scrollArea.getVerticalScrollBar();
                            scrollbar1.setValue(scrollbar1.getMaximum());
                        }
                    } else {
                        System.out.println(response);
                        chatOutput.append(response+"\n");
                        JScrollBar scrollbar1 = scrollArea.getVerticalScrollBar();
                        scrollbar1.setValue(scrollbar1.getMaximum());
                    }
                }
                os.flush();


                os.close();
                is.close();
                client.close();
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

    }
        private void initComponents(DataOutputStream os, DataInputStream is) throws IOException {

            chatLabel = new JLabel("Twitch-Chat");
            funcLabel = new JLabel("Kfeemaschine:");
            chatOutput = new JTextArea("Hier erscheint der Chat: " + "\n", 10, 50);
            chatInput = new JTextField(8);
            btnSend = new JButton(">>");

            ImageIcon csgo = new ImageIcon("csgo.png");
            btnCSGO = new JButton("",csgo);
            btnCSGO.setBackground(Color.white);

            ImageIcon dayZ = new ImageIcon("DayZ.png");
            btnDayZ = new JButton("",dayZ);
            btnDayZ.setForeground(Color.white);

            ImageIcon fortn = new ImageIcon("fn.png");
            btnFort = new JButton("",fortn);
            btnFort.setBackground(Color.blue);
            chatOutput.append("Bitte warten...");
    }
}
