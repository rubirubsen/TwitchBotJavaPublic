import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


class main {

    public static void main(String[] args) throws IOException {

        String hostname = "irc.chat.twitch.tv";
        String nickname = "kfeemaschine";
        String username = "kfeemaschine";
        String realName = "kfeemaschine";
        String oauth = "oauth:z0m1idvt6qm11bshf3wzru26mypai6";
        String channel = "#rubizockt";
        int port = 6667;

        Socket client = new Socket(hostname, port);
        DataInputStream is = new DataInputStream(client.getInputStream());
        DataOutputStream os =  new DataOutputStream(client.getOutputStream());

        MainWindow GuiHaupt = new MainWindow("Rubis Bot",os,is);
        connect.run(client,is,os,hostname,nickname,username,realName,oauth,channel,port);
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