import java.io.DataOutputStream;
import java.io.IOException;

public class cmds {

    public void cool(DataOutputStream os, String response) {
        try {
            os.writeBytes("PRIVMSG #rubizockt :;)\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Cooler Typ, der " + user(response));
    }

    public void nobot(DataOutputStream os, String response, String usrmsg) {

        try {
            os.writeBytes("PRIVMSG #rubizockt :Hier gibt es keine Bots, " + user(response) + "\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String user(String response){
        String usrmsg = (response.substring(response.indexOf("!")+1, response.indexOf("@")));
        return usrmsg;
    }

    public String nachricht(String response){
        String msg = (response.substring(response.indexOf(":",2)+1));
        return msg;
    }

    public static String wahl(final String command) {
        switch (command) {
            case "cool":
                return "Stein";
            case "bot":
                return "Schere";
            case "kaffee":
                return "Papier";
            default:
                return "Kafee";
        }
    }
}


/*
NACHRICHTENFORMAT
:tunzie95!tunzie95@tunzie95.tmi.twitch.tv PRIVMSG #rubizockt :nachricht

 */