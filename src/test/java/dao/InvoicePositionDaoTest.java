package dao;

import model.Invoice;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvoicePositionDaoTest {
    private static InvoicePositionDao dao;

    @BeforeAll
    static void beforeAll(){
        dao = new InvoicePositionDao();
    }

    @Test
    void create() {
        Invoice.InvoicePosition invoicePosition = new Invoice.InvoicePosition(491, 234234, 19, 60);
        dao.create(invoicePosition);
        assertEquals(invoicePosition, dao.read(234234, 60));
        dao.delete(invoicePosition);
    }

    @Test
    void read() {
        Invoice.InvoicePosition invoicePosition = new Invoice.InvoicePosition(700, 234234, 50, 56);
        assertEquals(invoicePosition, dao.read(234234, 56));
    }

    @Test
    void update() {
        Invoice.InvoicePosition invoicePosition = new Invoice.InvoicePosition(491, 234234, 19, 67);
        dao.create(invoicePosition);
        Invoice.InvoicePosition invoicePosition1 = new Invoice.InvoicePosition(491, 234234, 21, 67);
        dao.update(invoicePosition1);
        assertEquals(invoicePosition1, dao.read(234234, 67));
        dao.delete(invoicePosition1);
    }

    @Test
    void delete() {
        Invoice.InvoicePosition invoicePosition = new Invoice.InvoicePosition(491, 234234, 21, 67);
        dao.create(invoicePosition);
        dao.delete(invoicePosition);
        assertNotEquals(invoicePosition, dao.read(234234, 67));
    }
}