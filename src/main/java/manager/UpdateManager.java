package manager;

import org.jetbrains.annotations.NotNull;
import util.ConnectionProvider;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class UpdateManager {

    public static class OrganizationUpdateManager{
        private static String query = "UPDATE organization SET org_name = ?, payment_account = ? WHERE inn = ?";

        public static Integer updateOrganization(@NotNull String newOrganizationName, @NotNull Integer newPaymentAccount, @NotNull Long INN){
            int res = 0;

            try(var connection = ConnectionProvider.getConnection()){
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, newOrganizationName);
                preparedStatement.setInt(2, newPaymentAccount);
                preparedStatement.setLong(3, INN);

                res = preparedStatement.executeUpdate();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return res;
        }
    }

    public static class ProductUpdateManager{
        private static String query = "UPDATE product SET prod_name = ? WHERE inner_code = ?";

        public static Integer updateProduct(@NotNull String newProductName, @NotNull Integer innerCode){
            int res = 0;

            try(var connection = ConnectionProvider.getConnection()){
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, newProductName);
                preparedStatement.setInt(2, innerCode);

                res = preparedStatement.executeUpdate();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return res;
        }
    }

    public static class InvoiceUpdateManager{
        private static String query = "UPDATE invoice SET creation_date = ?, organization_inn = ? WHERE num = ?";

        public static Integer updateInvoice(@NotNull Timestamp newCreationDate, @NotNull Long newOrganizationINN, @NotNull Integer num){
            int res = 0;

            try(var connection = ConnectionProvider.getConnection()){
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setTimestamp(1, newCreationDate);
                preparedStatement.setLong(2, newOrganizationINN);
                preparedStatement.setInt(3, num);

                res = preparedStatement.executeUpdate();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return res;
        }
    }

    public static class InvoicePositionUpdateManager{
        private static String query = "UPDATE invoice_position SET price = ?, amount = ? WHERE product_inner_code = ? AND invoice_num = ?";

        public static Integer updateInvoicePosition(@NotNull Integer newPrice, @NotNull Integer newAmount, @NotNull Integer productInnerCode, @NotNull Integer invoiceNum){
            int res = 0;

            try(var connection = ConnectionProvider.getConnection()){
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, newPrice);
                preparedStatement.setInt(2, newAmount);
                preparedStatement.setInt(3, productInnerCode);
                preparedStatement.setInt(4, invoiceNum);

                res = preparedStatement.executeUpdate();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return res;
        }
    }
}
