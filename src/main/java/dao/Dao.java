package dao;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Dao<T> {
    void create(@NotNull T entity);
    T read(@NotNull int id);
    void update(@NotNull T entity);
    void delete(@NotNull T entity);

    @NotNull List<T> all();
}
