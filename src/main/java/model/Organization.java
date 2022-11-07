package model;

import org.jetbrains.annotations.NotNull;

public class Organization {
    private @NotNull String name;
    private @NotNull Long INN;
    private @NotNull Integer paymentAccount;

    public Organization(@NotNull String name, @NotNull Long INN, @NotNull Integer paymentAccount) {
        this.name = name;
        this.INN = INN;
        this.paymentAccount = paymentAccount;
    }

    @NotNull
    public String getName() {
        return name;
    }

    @NotNull
    public Long getINN() {
        return INN;
    }

    @NotNull
    public Integer getPaymentAccount() {
        return paymentAccount;
    }

    @Override
    public String toString() {
        return "name=" + name + " INN=" + INN + " paymentAccount=" + paymentAccount;
    }
}
