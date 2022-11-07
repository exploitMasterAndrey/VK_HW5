import db_migration.DBCreator;
import manager.CreateManager;
import manager.DeleteManager;
import manager.ReadManager;
import manager.UpdateManager;
import report_creator.ReportCreator;

import java.sql.Timestamp;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        DBCreator.init();

        System.out.println("==========CREATING=========");
        CreateManager.ProductCreateManager.createProduct("productFromCode", 981);
        CreateManager.ProductCreateManager.createProduct("anotherProductFromCode", 991);

        CreateManager.OrganizationCreateManager.createOrganization("organizationFromCode", 1234567890l, 456);
        CreateManager.OrganizationCreateManager.createOrganization("anotherOrganizationFromCode", 1234567111l, 491);

        CreateManager.InvoiceCreateManager.createInvoice(111, new Timestamp(2022, 10, 9, 16, 30, 0, 0),1234567890l);
        CreateManager.InvoiceCreateManager.createInvoice(112, new Timestamp(2022, 11, 8, 16, 30, 0, 0),1234567111l);

        CreateManager.InvoicePositionCreateManager.createInvoicePosition(800, 981, 10, 111);
        CreateManager.InvoicePositionCreateManager.createInvoicePosition(900, 991, 7, 111);

        System.out.println("===========READING==========");
        System.out.println("========INVOICES========");
        System.out.println(ReadManager.InvoiceReadManager.readInvoice(111).toString());
        System.out.println(ReadManager.InvoiceReadManager.readAllInvoices());

        System.out.println("========PRODUCTS========");
        System.out.println(ReadManager.ProductReadManager.readProduct(981));
        System.out.println(ReadManager.ProductReadManager.readAllProducts());

        System.out.println("========ORGANIZATIONS========");
        System.out.println(ReadManager.OrganizationReadManager.readOrganization(1234567890l));
        System.out.println(ReadManager.OrganizationReadManager.readAllOrganizations());

        System.out.println("========INVOICEPOSITIONS========");
        System.out.println(ReadManager.InvoicePositionReadManager.readInvoicePosition(981, 111));
        System.out.println(ReadManager.InvoicePositionReadManager.readAllInvoicePositions());


        System.out.println("===========DELETING===========");
        DeleteManager.InvoicePositionDeleteManager.deleteInvoice(991, 111);

        DeleteManager.InvoiceDeleteManager.deleteInvoice(111);

        DeleteManager.ProductDeleteManager.deleteProduct(991);

        DeleteManager.OrganizationDeleteManager.deleteOrganization(1234567111l);


        System.out.println("===========UPDATING==========");
        CreateManager.ProductCreateManager.createProduct("productForUpdate", 404);
        System.out.println(UpdateManager.ProductUpdateManager.updateProduct("productNameAfterUpdate", 404));

        CreateManager.OrganizationCreateManager.createOrganization("organizationForUpdate", 1234117810l, 302);
        System.out.println(UpdateManager.OrganizationUpdateManager.updateOrganization("organizationNameAfterUpdate", 303, 1234117810l));

        CreateManager.InvoiceCreateManager.createInvoice(500, new Timestamp(2020, 10, 9, 16, 30, 0, 0),1234117810l);
        System.out.println(UpdateManager.InvoiceUpdateManager.updateInvoice(new Timestamp(2021, 10, 9, 16, 30, 0, 0), 1234117810l, 500));

        CreateManager.InvoicePositionCreateManager.createInvoicePosition(811, 404, 3, 500);
        System.out.println(UpdateManager.InvoicePositionUpdateManager.updateInvoicePosition(799, 4, 404, 500));


        System.out.println("=============REPORTS===========");
        System.out.println("First 10 suppliers: " + ReportCreator.findFirst10suppliers());
        System.out.println("Suppliers with product sum greater then input: " + ReportCreator.findSuppliersWithProductSumGreaterThanInput(Map.of(234234, 50, 289541, 60)));
        System.out.println("AVG: " + ReportCreator.calcAveragePriceDuringPeriod(234234, new Timestamp(2022 - 1900, 9 - 1, 6, 16, 30, 0, 0), new Timestamp(2022 - 1900, 10 - 1, 10, 16, 30, 0, 0)));
        System.out.println("Amount and sum everyday for every product during period: " + ReportCreator.calcAmountAndSumEverydayForEveryProductDuringPeriod(new Timestamp(2022 - 1900, 9 - 1, 6, 5, 50, 0, 0), new Timestamp(2022 - 1900, 11 - 1, 7, 7, 50, 0, 0)));
        System.out.println("Delivered by organizations products list in period: " + ReportCreator.getDeliveredByOrganizationsProductsListInPeriod(new Timestamp(2021 - 1900, 2 - 1, 6, 5, 50, 0, 0), new Timestamp(2022 - 1900, 12 - 1, 6, 5, 50, 0, 0)));

    }
}
