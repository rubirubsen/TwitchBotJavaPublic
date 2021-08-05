import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.*;


public class db {
    cmds c = new cmds();

    public void sqltester(DataOutputStream os, String response) {
// Datenbankadresse und Anmeldedaten
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String pass = "";


        try {
            // Verbindung aufbauen
            Connection con = DriverManager.getConnection(url, user, pass);
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM info");
            System.out.println("SQL-Verbindung erfolgreich hergestellt");
            os.writeBytes("PRIVMSG #rubizockt :Verbindung steht ;-)\r\n");
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
