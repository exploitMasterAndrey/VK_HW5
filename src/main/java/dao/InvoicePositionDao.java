package dao;

import model.Invoice;
import org.jetbrains.annotations.NotNull;
import util.ConnectionProvider;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class InvoicePositionDao implements Dao<Invoice.InvoicePosition>{
    @Override
    public void create(@NotNull Invoice.InvoicePosition entity) {
        String query = "INSERT INTO invoice_position(price, product_inner_code, amount, invoice_num) VALUES (?,?,?,?)";
        try(var connection = ConnectionProvider.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, entity.getPrice());
            preparedStatement.setInt(2, entity.getProductInnerCode());
            preparedStatement.setInt(3, entity.getAmount());
            preparedStatement.setInt(4, entity.getInvoiceNum());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Invoice.InvoicePosition read(@NotNull int id) {
        return null;
    }

    public Invoice.InvoicePosition read(@NotNull Integer productInnerCode, @NotNull Integer invoiceNum){
        String query_product_inner_code_invoice_num = "SELECT * FROM invoice_position WHERE product_inner_code = ? AND invoice_num = ?;";
        Invoice.InvoicePosition invoicePosition = null;

        try(var connection = ConnectionProvider.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(query_product_inner_code_invoice_num);
            preparedStatement.setInt(1, productInnerCode);
            preparedStatement.setInt(2, invoiceNum);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            invoicePosition = new Invoice.InvoicePosition(resultSet.getInt("price"), resultSet.getInt("product_inner_code"), resultSet.getInt("amount"), resultSet.getInt("invoice_num"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return invoicePosition;
    }

    @Override
    public void update(@NotNull Invoice.InvoicePosition entity) {
        String query = "UPDATE invoice_position SET price = ?, amount = ? WHERE product_inner_code = ? AND invoice_num = ?";

        try(var connection = ConnectionProvider.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, entity.getPrice());
            preparedStatement.setInt(2, entity.getAmount());
            preparedStatement.setInt(3, entity.getProductInnerCode());
            preparedStatement.setInt(4, entity.getInvoiceNum());
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(@NotNull Invoice.InvoicePosition entity) {
        String query = "DELETE FROM invoice_position WHERE product_inner_code = ? AND invoice_num = ?";
        try(var connection = ConnectionProvider.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, entity.getProductInnerCode());
            preparedStatement.setInt(2, entity.getInvoiceNum());

            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @NotNull
    @Override
    public List<Invoice.InvoicePosition> all() {
        String query_all = "SELECT * FROM invoice_position;";
        List<Invoice.InvoicePosition> invoicePositionList = new ArrayList<>();

        try(var connection = ConnectionProvider.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query_all);

            while (resultSet.next()){
                invoicePositionList.add(new Invoice.InvoicePosition(resultSet.getInt("price"), resultSet.getInt("product_inner_code"), resultSet.getInt("amount"), resultSet.getInt("invoice_num")));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return invoicePositionList;
    }
}
