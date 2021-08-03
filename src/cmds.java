import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class cmds {
    StringBuffer responseContent = new StringBuffer();

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

    public static String nachricht(String response) {
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
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestProperty("Accept", "application/vnd.twitchtv.v5+json");
        http.setRequestProperty("Client-ID", "o2aamy4s11aewdlmkb9vj4w11vzanv");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream(), "UTF-8"))) {

            for (String line; (line = reader.readLine()) != null; ) {

                System.out.println(line);
            }
        }
    }

    public void channelSearch() throws IOException {
        HttpURLConnection connection;
        StringBuffer responseContent = new StringBuffer();

        try {
            URL url = new URL("https://api.twitch.tv/kraken/streams/?language=de&offset=700&limit=5");
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

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"))) {

                for (String line; (line = reader.readLine()) != null; ) {

                    String linefine = line.substring(line.indexOf("["), line.length() - 1);
                    responseContent.append(linefine);
                    /* System.out.println(linefine); */
                    parse(linefine);
                }
                reader.close();
            }
            /* parse(responseContent.toString()); */
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
            String zeile = null;
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
                } catch (IOException e) {
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
