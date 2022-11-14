package dao;

import model.Invoice;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class InvoiceDaoTest {

    public static Dao dao;
    @BeforeAll
    static void beforeAll(){
        dao = new InvoiceDao();
    }

    @Test
    void create() {
        Invoice invoice = new Invoice(1, new Timestamp(1, 1, 1, 1, 1, 1, 0), 6925813936l);
        dao.create(invoice);
        assertEquals(invoice, dao.read(invoice.getNum()));
    }

    @Test
    void read() {
        assertEquals(new Invoice(56, new Timestamp(2022-1900, 8, 7, 5, 50, 0, 0), 6449013711l), dao.read(56));
    }

    @Test
    void update() {
        Invoice invoice0 = new Invoice(1, new Timestamp(1, 1, 1, 1, 1, 1, 0), 6925813936l);
        dao.create(invoice0);
        Invoice invoice = new Invoice(1, new Timestamp(1, 1, 1, 1, 1, 1, 0), 1234556556l);
        dao.update(invoice);
        assertEquals(invoice, dao.read(1));
    }

    @Test
    void delete() {
        Invoice invoice = new Invoice(1, new Timestamp(1, 1, 1, 1, 1, 1, 0), 1234556556l);
        dao.delete(invoice);
        assertNotEquals(invoice, dao.read(invoice.getNum()));
    }
}