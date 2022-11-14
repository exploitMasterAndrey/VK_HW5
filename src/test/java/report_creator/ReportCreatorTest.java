package report_creator;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReportCreatorTest {

    @Test
    void findFirst10suppliers() {
        assertEquals("[name=2_company INN=5654123843 paymentAccount=645235, name=4_company INN=8744114677 paymentAccount=978900, name=11_company INN=5555512343 paymentAccount=909871, name=8_company INN=6752341122 paymentAccount=645652, name=7_company INN=6333112465 paymentAccount=123, name=10_company INN=1234556556 paymentAccount=888246, name=3_company INN=6925813936 paymentAccount=789444, name=6_company INN=1235437561 paymentAccount=876867, name=12_company INN=9753461234 paymentAccount=78235, name=1_company INN=6449013711 paymentAccount=123123]", ReportCreator.findFirst10suppliers().toString());
    }

    @Test
    void findSuppliersWithProductSumGreaterThanInput() {
        assertEquals("[name=2_company INN=5654123843 paymentAccount=645235, name=7_company INN=6333112465 paymentAccount=123, name=11_company INN=5555512343 paymentAccount=909871, name=4_company INN=8744114677 paymentAccount=978900]", ReportCreator.findSuppliersWithProductSumGreaterThanInput(Map.of(234234, 50, 289541, 60)).toString());
    }

    @Test
    void calcAveragePriceDuringPeriod() {
        assertEquals(650d, ReportCreator.calcAveragePriceDuringPeriod(234234, new Timestamp(2022 - 1900, 9 - 1, 6, 16, 30, 0, 0), new Timestamp(2022 - 1900, 10 - 1, 10, 16, 30, 0, 0)));
    }

    @Test
    void calcAmountAndSumEverydayForEveryProductDuringPeriod() {
        assertEquals("{2022-09-07=[{234234={price=77600, amount=121}}, {275124={price=6000, amount=10}}], 2022-10-07=[{275124={price=23970, amount=51}}], 2022-11-07=[{275124={price=30000, amount=60}}]}", ReportCreator.calcAmountAndSumEverydayForEveryProductDuringPeriod(new Timestamp(2022 - 1900, 9 - 1, 6, 5, 50, 0, 0), new Timestamp(2022 - 1900, 11 - 1, 7, 7, 50, 0, 0)).toString());
    }

    @Test
    void getDeliveredByOrganizationsProductsListInPeriod() {
        assertEquals("{name=1_company INN=6449013711 paymentAccount=123123=[name=1_product innerCode=234234], name=6_company INN=1235437561 paymentAccount=876867=[name=4_product innerCode=888563], name=8_company INN=6752341122 paymentAccount=645652=[name=2_product innerCode=275124], name=5_company INN=6542345576 paymentAccount=334466=[name=3_product innerCode=289541], name=10_company INN=1234556556 paymentAccount=888246=[name=4_product innerCode=888563], name=12_company INN=9753461234 paymentAccount=78235=[name=2_product innerCode=275124], name=7_company INN=6333112465 paymentAccount=123=[name=3_product innerCode=289541], name=3_company INN=6925813936 paymentAccount=789444=[name=2_product innerCode=275124], name=11_company INN=5555512343 paymentAccount=909871=[name=1_product innerCode=234234], name=9_company INN=4411123611 paymentAccount=125467=null, name=2_company INN=5654123843 paymentAccount=645235=[name=1_product innerCode=234234, name=2_product innerCode=275124], name=4_company INN=8744114677 paymentAccount=978900=[name=3_product innerCode=289541]}", ReportCreator.getDeliveredByOrganizationsProductsListInPeriod(new Timestamp(2021 - 1900, 2 - 1, 6, 5, 50, 0, 0), new Timestamp(2022 - 1900, 12 - 1, 6, 5, 50, 0, 0)).toString());
    }
}