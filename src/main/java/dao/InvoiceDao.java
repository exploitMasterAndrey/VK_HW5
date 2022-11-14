package dao;

import model.Invoice;
import model.Product;
import org.jetbrains.annotations.NotNull;
import util.ConnectionProvider;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDao implements Dao<Invoice>{
    @Override
    public void create(@NotNull Invoice entity) {
        String query = "INSERT INTO invoice(num, creation_date, organization_INN) VALUES (?,?,?)";
        try(var connection = ConnectionProvider.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, entity.getNum());
            preparedStatement.setTimestamp(2, entity.getCreationDate());
            preparedStatement.setLong(3, entity.getOrganizationINN());
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public Invoice read(@NotNull int num) {
        String query_num = "SELECT * FROM invoice WHERE num = ?;";
        Invoice invoice = null;

        try(var connection = ConnectionProvider.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(query_num);
            preparedStatement.setInt(1, num);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            invoice = new Invoice(resultSet.getInt("num"), resultSet.getTimestamp("creation_date"), resultSet.getLong("organization_INN"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return invoice;
    }

    @Override
    public void update(@NotNull Invoice entity) {
        String query = "UPDATE invoice SET creation_date = ?, organization_inn = ? WHERE num = ?";
        try(var connection = ConnectionProvider.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setTimestamp(1, entity.getCreationDate());
            preparedStatement.setLong(2, entity.getOrganizationINN());
            preparedStatement.setInt(3, entity.getNum());
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public void delete(@NotNull Invoice entity) {
        String query = "DELETE FROM invoice WHERE num = ?";
        try(var connection = ConnectionProvider.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, entity.getNum());
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @NotNull
    @Override
    public List<Invoice> all() {
        String query_all = "SELECT * FROM invoice;";
        List<Invoice> invoiceList = new ArrayList<>();

        try(var connection = ConnectionProvider.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query_all);

            while (resultSet.next()){
                invoiceList.add(new Invoice(resultSet.getInt("num"), resultSet.getTimestamp("creation_date"), resultSet.getLong("organization_INN")));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return invoiceList;
    }
}
