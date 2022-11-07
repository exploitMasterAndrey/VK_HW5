package manager;

import db_migration.DBCreator;
import model.Invoice;
import model.Organization;
import model.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ManagersTest {

    @BeforeAll
    static void reInitDb(){
        DBCreator.init();
    }

    @Test
    void CUDMethodsReturn1AfterExecutingRequest(){
        assertEquals(1, CreateManager.OrganizationCreateManager.createOrganization("test", 12311191l, 99911));
        assertEquals(1, CreateManager.OrganizationCreateManager.createOrganization("test2", 12411116l, 99912));
        assertEquals(1, CreateManager.OrganizationCreateManager.createOrganization("test3", 1245111l, 99913));
        assertEquals(1, CreateManager.OrganizationCreateManager.createOrganization("test4", 1245611l, 99914));
        assertEquals(1, CreateManager.ProductCreateManager.createProduct("test", 111222));
        assertEquals(1, CreateManager.ProductCreateManager.createProduct("test2", 111232));
        assertEquals(1, CreateManager.ProductCreateManager.createProduct("test3", 111234));
        assertEquals(1, CreateManager.ProductCreateManager.createProduct("test4", 111236));
        assertEquals(1, CreateManager.InvoiceCreateManager.createInvoice(100, Timestamp.valueOf(LocalDateTime.now()), 12311191l));
        assertEquals(1, CreateManager.InvoiceCreateManager.createInvoice(125, Timestamp.valueOf(LocalDateTime.now()), 12311191l));
        assertEquals(1, CreateManager.InvoicePositionCreateManager.createInvoicePosition(200, 111222, 11, 100));
        assertEquals(1, CreateManager.InvoicePositionCreateManager.createInvoicePosition(201, 111232, 12, 125));
        assertEquals(1, CreateManager.InvoicePositionCreateManager.createInvoicePosition(202, 111236, 120, 125));

        assertEquals(1, DeleteManager.ProductDeleteManager.deleteProduct(111234));
        assertEquals(1, DeleteManager.InvoicePositionDeleteManager.deleteInvoicePosition(111232, 125));
        assertEquals(1, DeleteManager.InvoiceDeleteManager.deleteInvoice(100));
        assertEquals(1, DeleteManager.OrganizationDeleteManager.deleteOrganization(12411116l));

        assertEquals(1, UpdateManager.ProductUpdateManager.updateProduct("updateTest", 111222));
        assertEquals(1, UpdateManager.InvoiceUpdateManager.updateInvoice(new Timestamp(2020, 10, 9, 16, 30, 0, 0), 1245111l, 125));
        assertEquals(1, UpdateManager.InvoicePositionUpdateManager.updateInvoicePosition(999, 15, 111236, 125));
        assertEquals(1, UpdateManager.OrganizationUpdateManager.updateOrganization("updateTest2", 999915, 1245611l));

    }

    @Test
    void readMethodReturnRightEntity(){
        Organization organization = new Organization("test11", 555555l, 441111);
        Organization organization1 = new Organization("test12", 555556l, 441119);
        CreateManager.OrganizationCreateManager.createOrganization(organization.getName(), organization.getINN(), organization.getPaymentAccount());
        CreateManager.OrganizationCreateManager.createOrganization(organization1.getName(), organization1.getINN(), organization1.getPaymentAccount());
        assertEquals(organization.toString(), ReadManager.OrganizationReadManager.readOrganization(555555l).toString());


        Product product = new Product("product11", 05115);
        Product product1 = new Product("product12", 05116);
        CreateManager.ProductCreateManager.createProduct(product.getName(), product.getInnerCode());
        CreateManager.ProductCreateManager.createProduct(product1.getName(), product1.getInnerCode());
        assertEquals(product.toString(), ReadManager.ProductReadManager.readProduct(05115).toString());


        Invoice invoice = new Invoice(404, new Timestamp(2020, 10, 9, 16, 30, 0, 0), 555555l);
        Invoice invoice1 = new Invoice(402, new Timestamp(2020, 10, 9, 16, 30, 0, 0), 555556l);
        CreateManager.InvoiceCreateManager.createInvoice(invoice.getNum(), invoice.getCreationDate(), invoice.getOrganizationINN());
        CreateManager.InvoiceCreateManager.createInvoice(invoice1.getNum(), invoice1.getCreationDate(), invoice1.getOrganizationINN());
        assertEquals(invoice.toString(), ReadManager.InvoiceReadManager.readInvoice(404).toString());


        Invoice.InvoicePosition invoicePosition = new Invoice.InvoicePosition(1234, 05115, 2, 404);
        Invoice.InvoicePosition invoicePosition1 = new Invoice.InvoicePosition(1235, 05115, 6, 402);
        CreateManager.InvoicePositionCreateManager.createInvoicePosition(invoicePosition.getPrice(), invoicePosition.getProductInnerCode(), invoicePosition.getAmount(), invoicePosition.getInvoiceNum());
        CreateManager.InvoicePositionCreateManager.createInvoicePosition(invoicePosition1.getPrice(), invoicePosition1.getProductInnerCode(), invoicePosition1.getAmount(), invoicePosition1.getInvoiceNum());
        assertEquals(invoicePosition.toString(), ReadManager.InvoicePositionReadManager.readInvoicePosition(05115, 404).toString());
    }

}