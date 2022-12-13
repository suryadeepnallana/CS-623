import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class Project{

    private final String url = "jdbc:postgresql://localhost/postgres";
    private final String user = "postgres";
    private final String password = "postgres";
    Connection conn = null;

    public Project(){}

    public Connection connect() throws SQLException{
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /* print data from Depot table */
    public void getDepot() {
        String selectDepotSQL = "SELECT * FROM depot";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(selectDepotSQL);

            System.out.println("Dep_id"  + "\t \t"+ "Addr"  + "\t \t \t" + "Volume");
            // display Depot information
            while (rs.next()) {
                System.out.println(rs.getString("dep_id") + "\t "
                        + rs.getString("addr") + "\t \t"
                        + rs.getString("volume"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /* insert into Depot table */
    public void insertDepot(String deptno, String city, int volume) {
        String insertDepotSQL = "INSERT INTO depot(dep_id, addr, volume) "
                + "VALUES(?,?,?)";
        try {
            PreparedStatement statement = conn.prepareStatement(insertDepotSQL);
            statement.setString(1, deptno);
            statement.setString(2, city);
            statement.setInt(3, volume);

            statement.executeUpdate();
            statement.close();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /* Get all rows in the Stock table */
    public void getStock() {
        String selectStockSQL = "SELECT * FROM stock";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(selectStockSQL);
            System.out.println("Prod_id"  + "\t \t"+ "Dep_id"  + "\t \t" + "Quantity");

            // display stock information
            while (rs.next()) {
                System.out.println(rs.getString("prod_id") + "\t"
                        + rs.getString("dep_id") + "\t \t"
                        + rs.getInt("quantity"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /* insert into Stock table */
    public void insertStock(String prodid, String depid, int quantity) {
        String insertStockSQL = "INSERT INTO stock (prod_id, dep_id, quantity) "
                + "VALUES(?,?,?)";
        try {
            PreparedStatement statement = conn.prepareStatement(insertStockSQL);
            statement.setString(1, prodid);
            statement.setString(2, depid);
            statement.setInt(3, quantity);

            statement.executeUpdate();
            statement.close();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        Project app = new Project();
		try {
            app.connect();
            /* add a depot in Depot table */
            System.out.println("----------------------Print Depot table-------------------------");
            app.getDepot();
            System.out.println("----------------------Insert into Depot table-------------------");
            app.insertDepot("d100", "Chicago", 100);
            System.out.println("----------------------Print Depot table-------------------------");
            app.getDepot();

            /* add a stock in Stock table */
            System.out.println("---------------------Print Stock table-------------------------");
		    app.getStock();
            System.out.println("---------------------Insert into Stock table-------------------");
            app.insertStock("p1", "d100", 100);
            System.out.println("---------------------Print Stock table-------------------------");
            app.getStock();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}