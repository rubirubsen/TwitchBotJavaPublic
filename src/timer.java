import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class timer extends Thread{


    public void run(){

        String hostname = "irc.chat.twitch.tv";
        int port = 6667;

        String nickname = "kfeemaschine";
        String username = "kfeemaschine";
        String realName = "kfeemaschine";
        String oauth = "oauth:z0m1idvt6qm11bshf3wzru26mypai6";
        String channel = "#derHamsta";


        try (Socket client = new Socket(hostname, port)) {
            DataInputStream is = new DataInputStream(client.getInputStream());
            DataOutputStream os = new DataOutputStream(client.getOutputStream());

            try {

                if (client != null && os != null && is != null) {

                    os.writeBytes("PASS " + oauth + "\r\n");
                    os.writeBytes("NICK " + nickname + "\r\n");
                    os.writeBytes("USER " + username + " 0 * :" + realName + "\r\n");
                    os.flush();

                    String response;
                    String usrmsg = "";


                    while ((response = is.readLine()) != null) {

                        if (response.contains("004")) {
                            os.writeBytes("JOIN " + channel + "\r\n");
                        } else if (response.contains("433")) {
                            System.out.println("Nickname is already in use.");
                            return;
                        }


                        if (response.startsWith("PING")) {
                            String pong = "PONG" + response.substring(4);
                            os.writeBytes(pong + "\r\n");
                            System.out.println(pong);
                        }

                        if(response.contains("da")){
                            os.writeBytes("PRIVMSG #derHamsta: Ja, Hallo :)");
                        }
                        System.out.println(">>"+response);

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}