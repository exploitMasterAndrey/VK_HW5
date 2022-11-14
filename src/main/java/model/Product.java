package model;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Product {
    private @NotNull String name;
    private @NotNull Integer innerCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return name.equals(product.name) && innerCode.equals(product.innerCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, innerCode);
    }

    public Product(@NotNull String name, @NotNull Integer innerCode) {
        this.name = name;
        this.innerCode = innerCode;
    }

    @NotNull
    public String getName() {
        return name;
    }

    @NotNull
    public Integer getInnerCode() {
        return innerCode;
    }

    @Override
    public String toString() {
        return "name=" + name + " innerCode=" + innerCode;
    }
}
