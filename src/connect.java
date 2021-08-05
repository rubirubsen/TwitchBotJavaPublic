import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class connect {
    private static Throwable ee;

    public static void connect() throws IOException {

        StringBuffer response2Content = new StringBuffer();
        String host2 = "irc.chat.twitch.tv";
        int port2 = 6667;

        String nickname = "kfeemaschine";
        String username = "kfeemaschine";
        String realName = "kfeemaschine";
        String oauth = "oauth:z0m1idvt6qm11bshf3wzru26mypai6";
        String channel = "#rubizockt";


        System.out.println(
                "Server: " + host2 + "\n" +
                        "Port: " + port2 + "\n" +
                        "Nickname: " + nickname + "\n" +
                        "Username: " + username + "\n" +
                        "Real Name: " + realName + "\n");

        System.out.println("-------------------------");

        Socket cli2ent = new Socket(host2, port2);
        DataOutputStream o2s = new DataOutputStream(cli2ent.getOutputStream());
        DataInputStream i2s = new DataInputStream(cli2ent.getInputStream());

        try {

            if (cli2ent != null && o2s != null && i2s != null) {
                o2s.writeBytes("PASS " + oauth + "\r\n");
                o2s.writeBytes("NICK " + nickname + "\r\n");
                o2s.writeBytes("USER " + username + " 0 * :" + realName + "\r\n");

                String res2ponse = i2s.readLine();
                String usr2msg = "";

                while ((res2ponse = i2s.readLine()) != null) {

                    if (res2ponse.contains("004")) {
                        o2s.writeBytes("JOIN " + channel + "\r\n");
                        o2s.writeBytes("PRIVMSG #rubizockt : KonCha \r\n");
                    } else if (res2ponse.contains("433")) {
                        System.out.println("Nickname is already in use.");
                        return;
                    }

                    if (res2ponse.startsWith("PING")) {
                        String pong = "PONG" + res2ponse.substring(4);
                        o2s.writeBytes(pong + "\r\n");
                        System.out.println(pong);

                    } else if (res2ponse.contains("@")) {
                        cmds c = new cmds();
                        System.out.println(c.user(res2ponse) + ">>" + c.nachricht(res2ponse));
                    }
                }
            }
        } catch (IOException ee) {
            ee.printStackTrace();
        }
    }
}