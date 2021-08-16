import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class cmds {

    public String aktuell() throws IOException {

        StringBuilder responseContent = new StringBuilder();
        URL url = new URL("https://api.twitch.tv/kraken/search/streams?query=rubizockt");
        HttpURLConnection connection;
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Accept", "application/vnd.twitchtv.v5+json");
        connection.setRequestProperty("Client-ID", "o2aamy4s11aewdlmkb9vj4w11vzanv");

        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        String result ="";
        int status = connection.getResponseCode();
        if (status == 200) {
            System.out.println(status + " - OK");
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {

            for (String line; (line = reader.readLine()) != null; ) {
                String linefine = line.substring(line.indexOf("["), line.length() - 1);
                responseContent.append(linefine);
                JSONArray infoakt = new JSONArray(linefine);

                for (int i = 0; i < infoakt.length(); i++) {

                    JSONObject rubi = infoakt.getJSONObject(i);
                    String RubiGame = rubi.getString("game");
                    String channelstatus = rubi.getJSONObject("channel").getString("status");
                    int Follower = rubi.getJSONObject("channel").getInt("followers");
                    int views = rubi.getJSONObject("channel").getInt("views");
                    result = "/me TwitchSings " + RubiGame + " TwitchSings | Rubi hat momentan " + Follower + " Follower und haette gern 500 | <3 "+views+" Views <3 | GlitchCat  Jetzt live: '"+channelstatus+"' GlitchCat  \n \r";
                }
            }
        }return result;
    }

    public void channelSearch() throws IOException {
        HttpURLConnection connection;
        StringBuilder responseContent = new StringBuilder();

        try {
            URL url = new URL("https://api.twitch.tv/kraken/streams?language=de&game=fortnite&offset=400&limit=100");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Accept", "application/vnd.twitchtv.v5+json");
            connection.setRequestProperty("Client-ID", "o2aamy4s11aewdlmkb9vj4w11vzanv");

            //Anfrage-Modell
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();
            if (status == 200) {
                System.out.println(status + " - OK");
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {

                for (String line; (line = reader.readLine()) != null; ) {

                    String linefine = line.substring(line.indexOf("["), line.length() - 1);
                    responseContent.append(linefine);
                    parse(linefine);
                }
            }
            /* parse(responseContent.toString()); */
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cool(DataOutputStream os, String response) {
        try {
            os.writeBytes("PRIVMSG #rubizockt :;)\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Cooler Typ, der " + user(response));
    }

    public int defLaenge(String response) {
        return cmds.nachricht(response).length();
    }

    public void nobot(DataOutputStream os, String response) {

        try {
            os.writeBytes("PRIVMSG #rubizockt :Hier gibt es keine Bots, " + user(response) + "\r\n");
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public String user(String response) {
        return (response.substring(response.indexOf("!") + 1, response.indexOf("@")));
    }

    public static String nachricht(String response) {
        return (response.substring(response.indexOf(":", 2) + 1));
    }

    public void info() throws IOException {


        URL url = new URL("https://api.twitch.tv/kraken/users?login=rubizockt");
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestProperty("Accept", "application/vnd.twitchtv.v5+json");
        http.setRequestProperty("Client-ID", "o2aamy4s11aewdlmkb9vj4w11vzanv");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream(),
                StandardCharsets.UTF_8))) {

            for (String line; (line = reader.readLine()) != null; ) {

                System.out.println(line);
            }
        }
    }

    public static String parse(String responseBody) {

        JSONArray channels = new JSONArray(responseBody);
        for (int i = 0; i < channels.length(); i++) {

            JSONObject channel = channels.getJSONObject(i);
            int viewers = channel.getInt("viewers");
            String channelName = channel.getJSONObject("channel").getString("display_name");
            System.out.println("Der Kanal " + channelName + " hat gerade " + viewers + " Viewer(s). Stabil!");
            PrintWriter pWriter = null;

            try {
                pWriter = new PrintWriter(new BufferedWriter(new FileWriter("channels.txt", true)));
                pWriter.print(channelName + "\n");
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } finally {
                if (pWriter != null) {
                    pWriter.flush();
                    pWriter.close();
                }
            }
        }
        return null;
    }

    public void sturmAngriff() {

        File file = new File("channels.txt");

        if (!file.canRead() || !file.isFile())
            System.exit(0);

        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader("channels.txt"));
            int anzahl = 0;
            String zeile;
            while ((zeile = in.readLine()) != null) {
                System.out.println(zeile);
                anzahl++;
            }
            System.out.println("Insgesamt waren das " + anzahl + " Channels. Gern geschehen!");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException ignored) {
                }
        }
    }

    public void msgcontent(String response) {
        cmds c = new cmds();
        System.out.println(c.user(response) + ">>" + nachricht(response));
    }

    public void userZaehlen(DataOutputStream os) throws IOException {
        URL url = new URL("http://tmi.twitch.tv/group/user/rubizockt/chatters");
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        http.setRequestProperty("Accept", "application/vnd.twitchtv.v5+json");


        String result = "";

        int status = http.getResponseCode();
        if (status == 200) {
            System.out.println(status + " - OK");
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream(), StandardCharsets.UTF_8))) {
            String line = reader.readLine();
            JSONObject usersAnzahl = new JSONObject(line);
            int chatters = usersAnzahl.getInt("chatter_count");
            String chatterino = chatters + " Chatter anwesend.";
            System.out.println(chatterino);
            os.writeBytes("PRIVMSG #rubizockt : oddDrums "+chatterino+" oddDrums \n\r");
        }
        http.disconnect();
    }

    public void joinChannel(DataOutputStream os,String channel) throws IOException {
        os.writeBytes("JOIN " + channel + "\r\n");
        os.writeBytes("PRIVMSG #rubizockt : KonCha \r\n");
    }

    public void pong(DataOutputStream os,String response) throws IOException {
        String pong = "PONG" + response.substring(4);
        os.writeBytes(pong + "\r\n");
        System.out.println(pong);
    }

}

/*
NACHRICHTENFORMAT
:tunzie95!tunzie95@tunzie95.tmi.twitch.tv PRIVMSG #rubizockt :nachricht
 */

/*
deutsche streams suchen mit offset und limit:
curl -H 'Accept: application/vnd.twitchtv.v5+json' \
-H 'Client-ID: o2aamy4s11aewdlmkb9vj4w11vzanv' \
-X GET 'https://api.twitch.tv/kraken/streams?language=de&offset=600&limit=100'

 */