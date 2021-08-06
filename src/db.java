import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.*;


public class db {
    cmds c = new cmds();

    public void kaffeeAnzahl(DataOutputStream os) {
// Datenbankadresse und Anmeldedaten
        String url = "jdbc:mysql://localhost:3306/twitchbot";
        String user = "root";
        String pass = "";


        try {
            // Verbindung aufbauen
            Connection con = DriverManager.getConnection(url, user, pass);
            Statement stm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                                ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stm.executeQuery("SELECT * FROM count");
            System.out.println("SQL-Verbindung erfolgreich hergestellt");

            rs.absolute(1);
            String counter_name= rs.getString("count_name");
            int counter_number = rs.getInt("count_no");
            System.out.println(counter_name);

            //ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
            //String title = rsmd.getColumnLabel(1);
            //System.out.println(title);
            os.writeBytes("PRIVMSG #rubizockt : CoffeeTime Rubi hatte bereits "+counter_number+" "+counter_name +" CoffeeTime \r\n");


        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }

    }
    public void mehrKaffee(DataOutputStream os) throws SQLException, IOException {
        String url = "jdbc:mysql://localhost:3306/twitchbot";
        String user = "root";
        String pass = "";

        Connection con = DriverManager.getConnection(url, user, pass);
        Statement stm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                            ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stm.executeQuery("SELECT * FROM count");
        rs.absolute(1);
        int counter_number = rs.getInt("count_no");

        rs.updateInt("count_no",(counter_number+1));
        rs.updateRow();

        int counter_number2 = rs.getInt("count_no");
        System.out.println(counter_number2);
        os.writeBytes("PRIVMSG #rubizockt : CoffeeTime "+counter_number2+" CoffeeTime \r\n");
    }

    public void wenigerKaffee(DataOutputStream os) throws SQLException, IOException {
        String url = "jdbc:mysql://localhost:3306/twitchbot";
        String user = "root";
        String pass = "";

        Connection con = DriverManager.getConnection(url, user, pass);
        Statement stm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stm.executeQuery("SELECT * FROM count");
        rs.absolute(1);
        int counter_number = rs.getInt("count_no");

        rs.updateInt("count_no",(counter_number-1));
        rs.updateRow();

        int counter_number2 = rs.getInt("count_no");
        System.out.println(counter_number2);
        os.writeBytes("PRIVMSG #rubizockt : CoffeeTime "+counter_number2+" CoffeeTime \r\n");
    }
}
