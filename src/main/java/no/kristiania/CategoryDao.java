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

import static no.kristiania.ProductDao.createDataSource;

public class CategoryDao {

    private final DataSource dataSource;

    public CategoryDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public void save(Category category) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {

            try (PreparedStatement statement = connection.prepareStatement(
                    "insert into categories (category_name) values (?)",
                    Statement.RETURN_GENERATED_KEYS
            )) {
                statement.setString(1, category.getCategory_name());

                statement.executeUpdate();

                try (ResultSet rs = statement.getGeneratedKeys()) {
                    rs.next();
                    category.setId(rs.getLong("id"));
                }
            }
        }
    }

    public Category retrieve(long id) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "select * from categories where id = ?"
            )) {
                statement.setLong(1, id);

                try (ResultSet rs = statement.executeQuery()) {
                    rs.next();

                    return mapFromResultSet(rs);
                }
            }
        }
    }

    private Category mapFromResultSet(ResultSet rs) throws SQLException {
        Category category = new Category();
        category.setId(rs.getLong("id"));
        category.setCategory_name(rs.getString("category_name"));
        return category;
    }

    public List<Category> listByCategoryName(String categoryName) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "select * from categories where category_name = ?"
            )) {
                statement.setString(1, categoryName);
                try (ResultSet rs = statement.executeQuery()) {
                    ArrayList<Category> categories = new ArrayList<>();

                    while (rs.next()) {
                        categories.add(mapFromResultSet(rs));
                    }

                    return categories;
                }
            }
        }
    }

    public static void main(String[] args) throws SQLException {
        CategoryDao dao = new CategoryDao(createDataSource());

        System.out.println("Please enter a category name: ");

        Scanner scanner = new Scanner(System.in);
        String categoryName = scanner.nextLine().trim();

        System.out.println(dao.listByCategoryName(categoryName));

    }
}