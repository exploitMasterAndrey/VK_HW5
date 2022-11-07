package model;

import org.jetbrains.annotations.NotNull;

public class Product {
    private @NotNull String name;
    private @NotNull Integer innerCode;

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
