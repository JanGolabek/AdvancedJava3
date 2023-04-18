package no.kristiania;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryDaoTest {

    private final CategoryDao dao = new CategoryDao(testDataSource());

    private DataSource testDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:productdb;DB_CLOSE_DELAY=-1");
        Flyway.configure().dataSource(dataSource).load().migrate();
        return dataSource;
    }

    @Test
    void shouldRetrieveSavedCategoryFromDatabase() throws SQLException {
        Category category = exampleCategory();
        dao.save(category);
        assertThat(dao.retrieve(category.getId()))
                .usingRecursiveComparison()
                .isEqualTo(category);
    }

    @Test
    void shouldListCategoriesByName() throws SQLException {
        Category matchingCategory = exampleCategory();
        matchingCategory.setCategory_name("Test category");
        dao.save(matchingCategory);
        Category anotherMatchingCategory = exampleCategory();
        anotherMatchingCategory.setCategory_name(matchingCategory.getCategory_name());
        dao.save(anotherMatchingCategory);

        Category nonMatchingCategory = exampleCategory();
        dao.save(nonMatchingCategory);


        assertThat(dao.listByCategoryName(matchingCategory.getCategory_name()))
                .extracting(Category::getId)
                .contains(matchingCategory.getId(), anotherMatchingCategory.getId())
                .doesNotContain(nonMatchingCategory.getId());
    }

    private Category exampleCategory() {
        Category category = new Category();
        category.setCategory_name(pickOne("Jakker", "Gensere", "Bukser", "Luer", "Undertøy"));
        return category;
    }

    public static String pickOne(String... alternatives) {
        return alternatives[new Random().nextInt(alternatives.length)];
    }


}
