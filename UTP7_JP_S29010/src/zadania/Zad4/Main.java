package Zad4;
import java.sql.*;
public class Main {
    public static void main(String[] args) {
        new Main();
    }
    String sel = "SELECT a.NAZWISKO, p.TYTUL, p.ROK, p.CENA " +
            "FROM AUTOR a JOIN POZYCJE p ON a.AUTID = p.AUTID " +
            "WHERE p.ROK > 2000 AND p.CENA > 30";
    Connection con = null;
    public Main() {
        try {
            con = DriverManager.getConnection("jdbc:derby:C:\\DerbyDbs\\ksidb");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sel);

            while (rs.next()) {
                String nazwisko = rs.getString("NAZWISKO");
                String tytul = rs.getString("TYTUL");
                int rokWydania = rs.getInt("ROK");
                double cena = rs.getDouble("CENA");

                System.out.print("Autor: " + nazwisko);
                System.out.print("Tytuł: " + tytul);
                System.out.print("Rok wydania: " + rokWydania);
                System.out.print("Cena: " + cena);
                System.out.println();
            }

            stmt.close();
            con.close();
        } catch (SQLException exc) {
            System.out.println(exc.getMessage());
        }
    }
}
