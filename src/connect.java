import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

public class connect {
    private static Throwable ee;

    public static void run(Socket client, DataInputStream is, DataOutputStream os, String hostname, String nickname, String username, String realName, String oauth, String channel, int port) {

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
                String response;
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
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
}


}