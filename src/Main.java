import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

class main {

    StringBuffer responseContent = new StringBuffer();


    public static void main(String[] args) throws IOException {
        String hostname = "irc.chat.twitch.tv";
        int port = 6667;

        String nickname = "kfeemaschine";
        String username = "kfeemaschine";
        String realName = "kfeemaschine";
        String oauth = "oauth:z0m1idvt6qm11bshf3wzru26mypai6";
        String channel = "#rubizockt";


        System.out.println(
                        "Server: " + hostname + "\n" +
                        "Port: " + port + "\n" +
                        "Nickname: " + nickname + "\n" +
                        "Username: " + username + "\n" +
                        "Real Name: " + realName + "\n");

        System.out.println("-------------------------");

        Socket client = new Socket(hostname, port);
        DataInputStream is = new DataInputStream(client.getInputStream());
        DataOutputStream os =  new DataOutputStream(client.getOutputStream());

        try {

            if (client != null && os != null && is != null) {

                os.writeBytes("PASS " + oauth + "\r\n");
                os.writeBytes("NICK " + nickname + "\r\n");
                os.writeBytes("USER " + username + " 0 * :" + realName + "\r\n");
                os.flush();

                String response = is.readLine();
                String usrmsg = "";


                while ((response = is.readLine()) != null) {

                    if (response.contains("004")) {
                        os.writeBytes("JOIN " + channel + "\r\n");
                        os.writeBytes("PRIVMSG #rubizockt : KonCha \r\n");
                    } else if (response.contains("433")) {
                        System.out.println("Nickname is already in use.");
                        return;
                    }

                    if (response.startsWith("PING")) {
                        String pong = "PONG" + response.substring(4);
                        os.writeBytes(pong + "\r\n");
                        System.out.println(pong);
                    // überprüfung ob nachricht im channel gesendet wurde via "@"
                    } else if (response.contains("@")) {
                            cmds control = new cmds();
                            // Überprüfung ob es sich um einen Befehl handelt via "starts with !"
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
                                    os.writeBytes("PRIVMSG #rubizockt : TwitchUnity TwitchUnity TwitchUnity " + usr + " TwitchUnity TwitchUnity TwitchUnity \n\r");

                                } else if (cmds.nachricht(response).equals("!lautlos")) {
                                    os.writeBytes("PRIVMSG #rubizockt : Falls ihr den Stream lautlos stellen möchtet, macht das bitte über den Tab im Browser (Rechtsklick-Tab stummschalten) anstatt direkt im Stream. Somit werdet ihr weiter als Viewer aufgeführt und helft euren Lieblingsstreamern ;) \n \r");

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
                                }  else if (cmds.nachricht(response).equals("!thread")) {
                                   timer ti = new timer();
                                   ti.start();
                                }
                                // Ende der Befehle

                            // Auslöser durch Worterwähnungen (contains)
                            } else if (response.contains(";)")) {
                                        cmds c = new cmds();
                                        c.user(response);
                                        c.cool(os, response);

                            } else if (response.contains("bot")) {
                                        cmds c = new cmds();
                                        c.nobot(os, response);
                                        System.out.println(c.user(response) + ": " + cmds.nachricht(response));

                            }else if (response.contains("taest")) {
                                        cmds c = new cmds();
                                        c.channelSearch();

                            } else if (response.contains("goh")) {
                                cmds c = new cmds();
                                c.sturmAngriff();

                            } else if (response.contains("sql")) {
                                db db = new db();
                                db.sqltester(os, response);

                            } else if (response.contains("laehnge")) {
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
                }
                    } else {
                        System.out.println(response);
                    }
                }
                os.flush();


                os.close();
                is.close();
                client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}





/*
LOGIKGEDANKEN:

prefixchar = "!" ;
prefixchar + msgcontent (ab dem 2. :) = command

if msgcontent == prefixchar + 1 word{
command
}else sout msgcontent;


 */