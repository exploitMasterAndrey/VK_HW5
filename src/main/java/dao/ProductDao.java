package dao;

import model.Product;
import org.jetbrains.annotations.NotNull;
import util.ConnectionProvider;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDao implements Dao<Product>{
    @Override
    public void create(@NotNull Product entity) {
        String query = "INSERT INTO product(prod_name, inner_code) VALUES (?,?)";
        try(var connection = ConnectionProvider.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setInt(2, entity.getInnerCode());
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Product read(@NotNull int innerCode) {
        String query_innerCode = "SELECT * FROM product WHERE inner_code = ?;";
        Product product = null;

        try(var connection = ConnectionProvider.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(query_innerCode);
            preparedStatement.setInt(1, innerCode);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            product = new Product(resultSet.getString("prod_name"), resultSet.getInt("inner_code"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return product;
    }

    @Override
    public void update(@NotNull Product entity) {
        String query = "UPDATE product SET prod_name = ? WHERE inner_code = ?";
        try(var connection = ConnectionProvider.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setInt(2, entity.getInnerCode());
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(@NotNull Product entity) {
        String query = "DELETE FROM product WHERE inner_code = ?";
        try(var connection = ConnectionProvider.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, entity.getInnerCode());
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @NotNull
    @Override
    public List<Product> all() {
        String query_all = "SELECT * FROM product;";
        List<Product> productList = new ArrayList<>();

        try(var connection = ConnectionProvider.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query_all);

            while (resultSet.next()){
                productList.add(new Product(resultSet.getString("prod_name"), resultSet.getInt("inner_code")));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return productList;
    }
}
