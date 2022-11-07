package manager;

import model.Invoice;
import model.Organization;
import model.Product;
import org.jetbrains.annotations.NotNull;
import util.ConnectionProvider;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CreateManager {

    public static class OrganizationCreateManager{
        private static String query = "INSERT INTO organization(org_name, INN, payment_account) VALUES (?,?,?)";

        public static Organization createOrganization(@NotNull String name, @NotNull Long INN, @NotNull Integer paymentAccount){
            Organization organization = new Organization(name, INN, paymentAccount);

            try(var connection = ConnectionProvider.getConnection()){
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, name);
                preparedStatement.setLong(2, INN);
                preparedStatement.setInt(3, paymentAccount);

                preparedStatement.executeUpdate();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            return organization;
        }
    }

    public static class ProductCreateManager{
        private static String query = "INSERT INTO product(prod_name, inner_code) VALUES (?,?)";

        public static Product createProduct(@NotNull String name, @NotNull Integer innerCode){
            Product product = new Product(name, innerCode);

            try(var connection = ConnectionProvider.getConnection()){
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, name);
                preparedStatement.setInt(2, innerCode);

                preparedStatement.executeUpdate();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            return product;
        }
    }

    public static class InvoiceCreateManager{
        private static String query = "INSERT INTO invoice(num, creation_date, organization_INN) VALUES (?,?,?)";

        public static Invoice createInvoice(@NotNull Integer num, @NotNull Timestamp date, @NotNull Long organizationINN){
            Invoice invoice = new Invoice(num, date, organizationINN);

            try(var connection = ConnectionProvider.getConnection()){
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, num);
                preparedStatement.setTimestamp(2, date);
                preparedStatement.setLong(3, organizationINN);

                preparedStatement.executeUpdate();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            return invoice;
        }
    }

    public static class InvoicePositionCreateManager{
        private static String query = "INSERT INTO invoice_position(price, product_inner_code, amount, invoice_num) VALUES (?,?,?,?)";

        public static void createInvoicePosition(@NotNull Integer price, @NotNull Integer productInnerCode, @NotNull Integer amount, @NotNull Integer invoiceNum){
            Invoice.InvoicePosition invoicePosition = new Invoice.InvoicePosition(price, productInnerCode, amount, invoiceNum);

            try(var connection = ConnectionProvider.getConnection()){
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, price);
                preparedStatement.setInt(2, productInnerCode);
                preparedStatement.setInt(3, amount);
                preparedStatement.setInt(4, invoiceNum);

                preparedStatement.executeUpdate();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
