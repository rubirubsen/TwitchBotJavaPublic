import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

class main {

    public static void main(String[] args) throws IOException {
        String hostname = "irc.chat.twitch.tv";
        int port = 6667;

        String nickname = "kfeemaschine";
        String username = "kfeemaschine";
        String realName = "kfeemaschine";
        String oauth = "oauth:z0m1idvt6qm11bshf3wzru26mypai6";
        String channel = "#rubizockt";


        System.out.println("Server: " + hostname + "\nPort: " + port
                + "\nNickname: " + nickname + "\nUsername: " + username
                + "\nReal Name: " + realName);
        System.out.println();

        Socket client;
        DataInputStream is;
        DataOutputStream os;

        client = new Socket(hostname, port);
        os = new DataOutputStream(client.getOutputStream());
        is = new DataInputStream(client.getInputStream());

        try {



            if (client != null && os != null && is != null) {

                os.writeBytes("PASS " + oauth + "\r\n");
                os.writeBytes("NICK " + nickname + "\r\n");
                os.writeBytes("USER " + username + " 0 * :" + realName + "\r\n");
                os.flush();

                String response;
                String usrmsg;


                while ((response = is.readLine()) != null) {

                    if (response.contains("004")) {
                        System.out.println("Log in erfolgreich!");
                        // We are now logged in.
                        os.writeBytes("JOIN " + channel + "\r\n");
                        os.writeBytes("PRIVMSG #rubizockt : KonCha \r\n");
                        }



                    else if (response.contains("433")) {

                        System.out.println("Nickname is already in use.");

                        return;

                    }

                    usrmsg = "";

                    if (response.startsWith("PING")) {
                        String pong = "PONG" + response.substring(4);
                        os.writeBytes(pong + "\r\n");
                        System.out.println(pong);
                    }else /* hier Test Wortanzahl (==1 -> command) einfügen */ if (response.contains("cool")) {
                        cmds c = new cmds();
                        c.user(response);
                        c.cool(os, response);

                    } else if (response.contains("bot")) {
                        cmds c = new cmds();
                        c.nobot(os, response, usrmsg);
                        System.out.println(c.nachricht(response) + ">>" + c.user(response));

                    }else if (response.contains("test")){
                        cmds c = new cmds();
                        c.info();


                }else {
                        System.out.println(response);
                    }
                    os.flush();
                }
                }
                os.close();
                is.close();
                client.close();
            } catch (IOException e) {
               e.printStackTrace();
           }
    }


}

/*

prefixchar = "!" ;
prefixchar + msgcontent (ab dem 2. :) = command

if msgcontent == prefixchar + 1 word{
command
}else sout msgcontent;


 */