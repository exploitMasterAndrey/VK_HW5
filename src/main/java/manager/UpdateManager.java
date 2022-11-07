package manager;

import model.Invoice;
import model.Organization;
import model.Product;
import org.jetbrains.annotations.NotNull;
import util.ConnectionProvider;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class UpdateManager {

    public static class OrganizationUpdateManager{
        private static String query = "UPDATE organization SET org_name = ?, payment_account = ? WHERE inn = ?";

        public static Organization updateOrganization(@NotNull String newOrganizationName, @NotNull Integer newPaymentAccount, @NotNull Long INN){
            Organization organization = null;

            try(var connection = ConnectionProvider.getConnection()){
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, newOrganizationName);
                preparedStatement.setInt(2, newPaymentAccount);
                preparedStatement.setLong(3, INN);

                preparedStatement.executeUpdate();

                organization = new Organization(newOrganizationName, INN, newPaymentAccount);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return organization;
        }
    }

    public static class ProductUpdateManager{
        private static String query = "UPDATE product SET prod_name = ? WHERE inner_code = ?";

        public static Product updateProduct(@NotNull String newProductName, @NotNull Integer innerCode){
            Product product = null;

            try(var connection = ConnectionProvider.getConnection()){
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, newProductName);
                preparedStatement.setInt(2, innerCode);

                preparedStatement.executeUpdate();

                product = new Product(newProductName, innerCode);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return product;
        }
    }

    public static class InvoiceUpdateManager{
        private static String query = "UPDATE invoice SET creation_date = ?, organization_inn = ? WHERE num = ?";

        public static Invoice updateInvoice(@NotNull Timestamp newCreationDate, @NotNull Long newOrganizationINN, @NotNull Integer num){
            Invoice invoice = null;

            try(var connection = ConnectionProvider.getConnection()){
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setTimestamp(1, newCreationDate);
                preparedStatement.setLong(2, newOrganizationINN);
                preparedStatement.setInt(3, num);

                preparedStatement.executeUpdate();

                invoice = new Invoice(num, newCreationDate, newOrganizationINN);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return invoice;
        }
    }

    public static class InvoicePositionUpdateManager{
        private static String query = "UPDATE invoice_position SET price = ?, amount = ? WHERE product_inner_code = ? AND invoice_num = ?";

        public static Invoice.InvoicePosition updateInvoicePosition(@NotNull Integer newPrice, @NotNull Integer newAmount, @NotNull Integer productInnerCode, @NotNull Integer invoiceNum){
            Invoice.InvoicePosition invoicePosition = null;

            try(var connection = ConnectionProvider.getConnection()){
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, newPrice);
                preparedStatement.setInt(2, newAmount);
                preparedStatement.setInt(3, productInnerCode);
                preparedStatement.setInt(4, invoiceNum);

                preparedStatement.executeUpdate();

                invoicePosition = new Invoice.InvoicePosition(newPrice, productInnerCode, newAmount, invoiceNum);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return invoicePosition;
        }
    }
}
