package no.kristiania;

import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductDao {

    private final DataSource dataSource;

    public ProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static DataSource createDataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost/products_database");
        dataSource.setUser("dbuser");
        dataSource.setPassword("bbXG``\\ju$7<VmX,!\\");

        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();

        return dataSource;
    }

    public void save(Product product) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {

            try (PreparedStatement statement = connection.prepareStatement(
                    "insert into product_table (product_name, description, price) values (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            )) {
                statement.setString(1, product.getProduct_name());
                statement.setString(2, product.getDescription());
                statement.setInt(3, product.getPrice());

                statement.executeUpdate();

                try (ResultSet rs = statement.getGeneratedKeys()) {
                    rs.next();
                    product.setId(rs.getLong("id"));
                }
            }
        }
    }

    public Product retrieve(long id) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "select * from product_table where id = ?"
            )) {
                statement.setLong(1, id);

                try (ResultSet rs = statement.executeQuery()) {
                    rs.next();

                    return mapFromResultSet(rs);
                }
            }
        }
    }

    private Product mapFromResultSet(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getLong("id"));
        product.setProduct_name(rs.getString("product_name"));
        product.setDescription(rs.getString("description"));
        product.setPrice(rs.getInt("price"));
        product.setCategory_id(rs.getInt("category_id"));
        return product;
    }

    public List<Product> listByName(String productName) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "select * from product_table where product_name = ?"
            )) {
                statement.setString(1, productName);
                try (ResultSet rs = statement.executeQuery()) {
                    ArrayList<Product> product = new ArrayList<>();

                    while (rs.next()) {
                        product.add(mapFromResultSet(rs));
                    }

                    return product;
                }
            }
        }
    }

    public static void main(String[] args) throws SQLException {
        ProductDao dao = new ProductDao(createDataSource());

        System.out.println("Please enter a product name: ");

        Scanner scanner = new Scanner(System.in);
        String productName = scanner.nextLine().trim();

        System.out.println(dao.listByName(productName));

    }
}
