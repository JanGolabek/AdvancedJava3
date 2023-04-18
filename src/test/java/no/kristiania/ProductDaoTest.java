package no.kristiania;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductDaoTest {

    private final ProductDao dao = new ProductDao(testDataSource());

    private DataSource testDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:productdb;DB_CLOSE_DELAY=-1");
        Flyway.configure().dataSource(dataSource).load().migrate();
        return dataSource;
    }

    @Test
    void shouldRetrieveSavedProductFromDatabase() throws SQLException {
        Product product = exampleProduct();
        dao.save(product);
        assertThat(dao.retrieve(product.getId()))
                .usingRecursiveComparison()
                .isEqualTo(product);
    }

    @Test
    void shouldListProductsByName() throws SQLException {
        Product matchingProduct = exampleProduct();
        matchingProduct.setProduct_name("Test product");
        dao.save(matchingProduct);
        Product anotherMatchingProduct = exampleProduct();
        anotherMatchingProduct.setProduct_name(matchingProduct.getProduct_name());
        dao.save(anotherMatchingProduct);

        Product nonMatchingProduct = exampleProduct();
        dao.save(nonMatchingProduct);


        assertThat(dao.listByName(matchingProduct.getProduct_name()))
                .extracting(Product::getId)
                .contains(matchingProduct.getId(), anotherMatchingProduct.getId())
                .doesNotContain(nonMatchingProduct.getId());
    }

    private Product exampleProduct() {
        Product product = new Product();
        product.setProduct_name(pickOne("Jakke", "Genser", "Bukse", "Lue", "Bokser"));
        product.setDescription(pickOne("Stor", "Liten", "Dongri", "Rød", "Kul"));
        product.setPrice(pickAnother(100, 200, 300, 400, 500));
        return product;
    }

    public static String pickOne(String... alternatives) {
        return alternatives[new Random().nextInt(alternatives.length)];
    }

    public static Integer pickAnother(Integer... alternatives) {
        return alternatives[new Random().nextInt(alternatives.length)];
    }


}