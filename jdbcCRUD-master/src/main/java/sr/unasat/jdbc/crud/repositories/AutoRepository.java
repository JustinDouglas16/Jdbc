package sr.unasat.jdbc.crud.repositories;

import sr.unasat.jdbc.crud.entities.Auto;
import sr.unasat.jdbc.crud.entities.ContactInformatie;
import sr.unasat.jdbc.crud.entities.Land;
import sr.unasat.jdbc.crud.entities.Persoon;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AutoRepository {
    private Connection connection;

    public AutoRepository() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("De driver is geregistreerd!");

            String URL = "jdbc:mysql://localhost:3305/adres_boek";
            String USER = "root";
            String PASS = "root";
            connection = DriverManager.getConnection(URL, USER, PASS);
            System.out.println(connection);
        } catch (ClassNotFoundException ex) {
            System.out.println("Error: unable to load driver class!");
            System.exit(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Auto> findAllRecords() {
        List<Auto> autoList = new ArrayList<>();
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            String sql = "select auto.id, auto.model, p.id pid" +
                    " from auto" +
                    " join persoon p" +
                    " on p.id = auto.persoon_id";
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("resultset: " + rs);
            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                int id = rs.getInt("id");
                String model = rs.getString("model");

                int persoonId = rs.getInt("pid");
//                String persoonNaam = rs.getString("pnaam");
                Persoon persoon = new Persoon(persoonId);

//                int landId = rs.getInt("lid");
//                String landNaam = rs.getString("land_naam");
//                Land land = new Land(landId, landNaam);

                autoList.add(new Auto(id, model, persoon));
            }
            rs.close();


        } catch (SQLException e) {

        } finally {

        }
        return autoList;
    }

    public int insertOneRecord(Auto auto) {
        PreparedStatement stmt = null;
        int result = 0;
        try {
            String sql = "insert into auto (id, model, persoon) values(?, ?, ?)";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, auto.getId());
            stmt.setString(1, auto.getModel());
            stmt.setInt(1, auto.getPersoon().getId());
            result = stmt.executeUpdate();
            System.out.println("resultset: " + result);

        } catch (SQLException e) {

        } finally {

        }
        return result;
    }

    public int updateOneRecord(Auto auto) {
        PreparedStatement stmt = null;
        int result = 0;
        try {
            String sql = "update auto set auto.model = ?, ci.persoon_id = ? where auto.id = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, auto.getId());
            stmt.setString(2, auto.getModel());
            stmt.setInt(3, auto.getPersoon().getId());
            result = stmt.executeUpdate();
            System.out.println("resultset: " + result);

        } catch (SQLException e) {

        } finally {

        }
        return result;

    }

    public Auto findOneRecord(int id, String model) {
        Auto auto = null;
        PreparedStatement stmt = null;
        try {
            String sql = "select auto.id, auto.model, p.id pid" +
                    " from auto" +
                    " join persoon p" +
                    " on p.id = auto.persoon_id where auto.id = ? or auto.model = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.setString(2, model);
            ResultSet rs = stmt.executeQuery();
            System.out.println("resultset: " + rs);
            //STEP 5: Extract data from result set
            if (rs.next()) {
                //Retrieve by column name
                int idNummer = rs.getInt("id");
                String modelAuto = rs.getString("model");

                int persoonId = rs.getInt("pid");
                Persoon persoon = new Persoon(persoonId);

                int landId = rs.getInt("lid");
                String landNaam = rs.getString("land_naam");
                Land land = new Land(landId, landNaam);

                auto = new Auto(id, model, persoon);

            }

            rs.close();
        } catch (SQLException e) {

        } finally {

        }

        return auto;
    }

}
