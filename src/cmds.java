import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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

    public String user(String response) {
        String usrmsg = (response.substring(response.indexOf("!") + 1, response.indexOf("@")));
        return usrmsg;
    }

    public String nachricht(String response) {
        String msg = (response.substring(response.indexOf(":", 2) + 1));
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

    public void info() throws IOException {


        URL url = new URL("https://api.twitch.tv/kraken/users?login=rubizockt");
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        http.setRequestProperty("Accept", "application/vnd.twitchtv.v5+json");
        http.setRequestProperty("Client-ID", "o2aamy4s11aewdlmkb9vj4w11vzanv");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream(), "UTF-8"))) {

            for (String line; (line = reader.readLine()) != null;) {

                System.out.println(line);
            }
        }
    }
}

/*
NACHRICHTENFORMAT
:tunzie95!tunzie95@tunzie95.tmi.twitch.tv PRIVMSG #rubizockt :nachricht
"Client-Id", "o2aamy4s11aewdlmkb9vj4w11vzanv"
"Authorization", "Bearer n8jh5s2gne756s7hewval1u7r597bt"
oauth:og530u4hzcj0jvh5ru176ays6hun7u
 */