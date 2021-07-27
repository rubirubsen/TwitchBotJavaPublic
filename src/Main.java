import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.*;

class main {
    public static final String delim = ":";

    public static void main(String[] args) throws FileNotFoundException, IOException {
        String hostname = "irc.freenode.org";
        int port = 6667;

        String nickname = "kfeemaschine";
        String username = "kfeemaschine";
        String realName = "kfeemaschine";
        String[] channel = {"#rubizockt" , "#rubizo2"};


        System.out.println("Server: " + hostname + "\nPort: " + port
                + "\nNickname: " + nickname + "\nUsername: " + username
                + "\nReal Name: " + realName);
        System.out.println();

        Socket client = null;
        DataInputStream is = null;
        DataOutputStream os = null;

        client = new Socket(hostname, port);
        os = new DataOutputStream(client.getOutputStream());
        is = new DataInputStream(client.getInputStream());

        try {



            if (client != null && os != null && is != null) {

                os.writeBytes("NICK " + nickname + "\r\n");
                os.writeBytes("USER " + username + " 0 * :" + realName + "\r\n");
                os.flush();

                String response = "";



                while ((response = is.readLine()) != null) {

                    if (response.contains("004")) {
                        System.out.println("Log in erfolgreich!");
                        // We are now logged in.
                        for(int i=0;i<channel.length;i++){
                            os.writeBytes("JOIN " + channel[i] + "\r\n");
                            os.flush();
                        }

                    }

                    else if (response.contains("433")) {

                        System.out.println("Nickname is already in use.");

                        return;

                    }

                    if (response.substring(0, 4).equals("PING")) {
                        String pong = "PONG" + response.substring(4, response.length());
                        os.writeBytes(pong + "\r\n");
                        System.out.println(pong);
                    }else if (response.contains("cool")) {
                        cool(os, response);
                    } else {
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

    private static void cool(DataOutputStream os,String response) {
        String usrmsg = (response.substring(response.indexOf(":")+1, response.indexOf("!")));
        try {
            os.writeBytes("PRIVMSG #rubizockt :;)\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Cooler Typ, der " + usrmsg);
    }
}