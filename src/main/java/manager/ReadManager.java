package manager;

import model.Invoice;
import model.Organization;
import model.Product;
import org.jetbrains.annotations.NotNull;
import util.ConnectionProvider;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReadManager {

    public static class OrganizationReadManager{
        private static String query_all = "SELECT * FROM organization;";
        private static String query_INN = "SELECT * FROM organization WHERE INN = ?;";

        public static Organization readOrganization(@NotNull Long INN){
            Organization organization = null;

            try(var connection = ConnectionProvider.getConnection()){
                PreparedStatement preparedStatement = connection.prepareStatement(query_INN);
                preparedStatement.setLong(1, INN);

                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();

                organization = new Organization(resultSet.getString("org_name"), resultSet.getLong("INN"), resultSet.getInt("payment_account"));

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return organization;
        }

        public static List<Organization> readAllOrganizations(){
            List<Organization> organizationList = new ArrayList<>();

            try(var connection = ConnectionProvider.getConnection()){
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query_all);

                while (resultSet.next()){
                    organizationList.add(new Organization(resultSet.getString("org_name"), resultSet.getLong("INN"), resultSet.getInt("payment_account")));
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            return organizationList;
        }
    }

    public static class ProductReadManager{
        private static String query_all = "SELECT * FROM product;";
        private static String query_innerCode = "SELECT * FROM product WHERE inner_code = ?;";

        public static Product readProduct(@NotNull Integer innerCode){
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

        public static List<Product> readAllProducts(){
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

    public static class InvoiceReadManager{
        private static String query_all = "SELECT * FROM invoice;";
        private static String query_num = "SELECT * FROM invoice WHERE num = ?;";

        public static Invoice readInvoice(@NotNull Integer num){
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

        public static List<Invoice> readAllInvoices(){
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

    public static class InvoicePositionReadManager{
        private static String query_all = "SELECT * FROM invoice_position;";
        private static String query_product_inner_code_invoice_num = "SELECT * FROM invoice_position WHERE product_inner_code = ? AND invoice_num = ?;";

        public static Invoice.InvoicePosition readInvoicePosition(@NotNull Integer productInnerCode, @NotNull Integer invoiceNum){
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

        public static List<Invoice.InvoicePosition> readAllInvoicePositions(){
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
}
