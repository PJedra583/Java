package Zad3;

import java.sql.*;

public class Ins1 {

    static public void main(String[] args) {
        new Ins1();
    }

    Statement stmt;

    Ins1()  {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:derby:C:\\DerbyDbs\\ksidb");
            stmt = con.createStatement();
        } catch (Exception exc)  {
            System.out.println(exc);
            System.exit(1);
        }
        // nazwy wydawców do wpisywania do tabeli
        String[] wyd =  { "PWN", "PWE", "Czytelnik", "Amber", "HELION", "MIKOM" };

        // pierwszy numer wydawcy do wpisywania do tabeli: PWN ma numer 15, PWE ma 16, ...
        int beginKey = 15;

        String[] ins = new String[wyd.length];// ? ... tablica instrukcji SQL do wpisywania rekordów do tabeli: INSERT ...
        int counter = 0;
        for (String s : wyd) {
            ins[counter] = "INSERT INTO WYDAWCA VALUES (" + beginKey + ", '" + s + "')";
            counter++;
            beginKey++;
        }
        int insCount = 0;   // ile rekordów wpisano
        try  {
            for (int i=0; i < ins.length; i++) // wpisywanie rekordów
            // ...
            {
                stmt.executeUpdate(ins[i]);
                insCount++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
//...
        //Wypisanie rekordów
        try {
            ResultSet rs = stmt.executeQuery("SELECT NAME FROM WYDAWCA");
                while (rs.next()) {
                    System.out.println(rs.getString("NAME"));
                }
        } catch (Exception e) {

        }

        beginKey = 15;
        int delCount =  0;
        try  {
            // przygotowanie instrukcji prekompilowanej
            // PreparedStatement deleteStmt = con.prepareStatement("DELETE FROM WYDAWCA WHERE ID = ?");
            stmt = con.prepareStatement("DELETE FROM wydawca WHERE wydid = ?");	// usunięcie z tabeli WYDAWCA rekordu o
            // podanej nazwie wydawcy z tablicy wyd lub o podanym numerze wydawcy zaczynającym się od beginKey
            for (int i=0; i < wyd.length; i++)   {
                ((PreparedStatement) stmt).setInt(1,beginKey);
                int rowsAffected = ((PreparedStatement) stmt).executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Rekord został pomyślnie usunięty. ID = " + beginKey);
                }
                beginKey++;
            }
            stmt.close();
            con.close();
        } catch(SQLException exc)  {
            System.err.println(exc);
        }

        // ...
    }
}