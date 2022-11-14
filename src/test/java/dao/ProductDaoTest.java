package dao;

import model.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductDaoTest {
    private static Dao dao;

    @BeforeAll
    static void beforeAll() {
        dao = new ProductDao();
    }

    @Test
    void create() {
        Product product = new Product("test", 666);
        dao.create(product);
        assertEquals(product, dao.read(product.getInnerCode()));
        dao.delete(product);
    }

    @Test
    void read() {
        Product product = new Product("1_product", 234234);
        assertEquals(product, dao.read(product.getInnerCode()));
    }

    @Test
    void update() {
        Product product = new Product("test", 666);
        dao.create(product);
        Product product1 = new Product("test2", 666);
        dao.update(product1);
        assertEquals(product1, dao.read(product1.getInnerCode()));
        dao.delete(product1);
    }

    @Test
    void delete() {
        Product product = new Product("test", 666);
        dao.create(product);
        dao.delete(product);
        assertNotEquals(product, dao.read(product.getInnerCode()));
    }
}