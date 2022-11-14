package model;

import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;
import java.util.Objects;

public class Invoice {
    private @NotNull Integer num;

    private @NotNull
    Timestamp creationDate;

    private @NotNull Long organizationINN;

    public Invoice(@NotNull Integer num, @NotNull Timestamp creationDate, @NotNull Long organizationINN) {
        this.num = num;
        this.creationDate = creationDate;
        this.organizationINN = organizationINN;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return num.equals(invoice.num) && creationDate.equals(invoice.creationDate) && organizationINN.equals(invoice.organizationINN);
    }

    @Override
    public int hashCode() {
        return Objects.hash(num, creationDate, organizationINN);
    }

    @NotNull
    public Integer getNum() {
        return num;
    }

    @NotNull
    public Timestamp getCreationDate() {
        return creationDate;
    }

    @NotNull
    public Long getOrganizationINN() {
        return organizationINN;
    }

    @Override
    public String toString() {
        return "num=" + num + " creationDate=" + creationDate + " organizationINN=" + organizationINN;
    }

    public static class InvoicePosition{
        private @NotNull Integer price;
        private @NotNull Integer productInnerCode;
        private @NotNull Integer amount;
        private @NotNull Integer invoiceNum;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            InvoicePosition that = (InvoicePosition) o;
            return price.equals(that.price) && productInnerCode.equals(that.productInnerCode) && amount.equals(that.amount) && invoiceNum.equals(that.invoiceNum);
        }

        @Override
        public int hashCode() {
            return Objects.hash(price, productInnerCode, amount, invoiceNum);
        }

        public InvoicePosition(@NotNull Integer price, @NotNull Integer productInnerCode, @NotNull Integer amount, @NotNull Integer invoiceNum) {
            this.price = price;
            this.productInnerCode = productInnerCode;
            this.amount = amount;
            this.invoiceNum = invoiceNum;
        }

        @NotNull
        public Integer getPrice() {
            return price;
        }

        @NotNull
        public Integer getProductInnerCode() {
            return productInnerCode;
        }

        @NotNull
        public Integer getAmount() {
            return amount;
        }

        @NotNull
        public Integer getInvoiceNum() {
            return invoiceNum;
        }

        @Override
        public String toString() {
            return "price=" + price + " productInnerCode=" + productInnerCode + " amount=" + amount + " invoiceNum=" + invoiceNum;
        }
    }
}
