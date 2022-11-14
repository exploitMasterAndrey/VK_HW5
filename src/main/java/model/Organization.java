package model;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Organization {
    private @NotNull String name;
    private @NotNull Long INN;
    private @NotNull Integer paymentAccount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return name.equals(that.name) && INN.equals(that.INN) && paymentAccount.equals(that.paymentAccount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, INN, paymentAccount);
    }

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
