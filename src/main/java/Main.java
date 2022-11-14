import db_migration.DBCreator;
import report_creator.ReportCreator;

import java.sql.Timestamp;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        DBCreator.init();

        System.out.println("=============REPORTS===========");
        System.out.println("First 10 suppliers: " + ReportCreator.findFirst10suppliers());
        System.out.println("Suppliers with product sum greater then input: " + ReportCreator.findSuppliersWithProductSumGreaterThanInput(Map.of(234234, 50, 289541, 60)));
        System.out.println("AVG: " + ReportCreator.calcAveragePriceDuringPeriod(234234, new Timestamp(2022 - 1900, 9 - 1, 6, 16, 30, 0, 0), new Timestamp(2022 - 1900, 10 - 1, 10, 16, 30, 0, 0)));
        System.out.println("Amount and sum everyday for every product during period: " + ReportCreator.calcAmountAndSumEverydayForEveryProductDuringPeriod(new Timestamp(2022 - 1900, 9 - 1, 6, 5, 50, 0, 0), new Timestamp(2022 - 1900, 11 - 1, 7, 7, 50, 0, 0)));
        System.out.println("Delivered by organizations products list in period: " + ReportCreator.getDeliveredByOrganizationsProductsListInPeriod(new Timestamp(2021 - 1900, 2 - 1, 6, 5, 50, 0, 0), new Timestamp(2022 - 1900, 12 - 1, 6, 5, 50, 0, 0)));

    }
}
