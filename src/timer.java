import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;


public class timer extends Thread{


    public void run(){

        String hostname = "irc.chat.twitch.tv";
        int port = 6667;

        String nickname = "kfeemaschine";
        String username = "kfeemaschine";
        String realName = "kfeemaschine";
        String oauth = "oauth:z0m1idvt6qm11bshf3wzru26mypai6";
        String channel = "#rubizockt";


        try (Socket client = new Socket(hostname, port)) {
            DataInputStream is = new DataInputStream(client.getInputStream());
            DataOutputStream os = new DataOutputStream(client.getOutputStream());

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
                        }
                        System.out.println("2. Instanz gestartet!");
                        System.out.println("Timer gestartet - Nachrichten werden geladen...");
                        int i = 0;
                        while (true) werbung(os);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void werbung(DataOutputStream os) throws IOException, InterruptedException {
        sleep(10000);
        os.writeBytes("PRIVMSG #rubizockt : Falls ihr den Stream lautlos stellen möchtet, macht das bitte über den Tab im Browser (Rechtsklick-Tab stummschalten) anstatt direkt im Stream. Somit werdet ihr weiter als Viewer aufgeführt und helft euren Lieblingsstreamern ;) \n \r");
        sleep(60000);
        os.writeBytes("PRIVMSG #rubizockt : Prime-Subs sind übrigens kostenlos ;) \n\r");
        sleep(50000);
    }
}



