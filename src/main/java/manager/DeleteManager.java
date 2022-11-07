package manager;

import org.jetbrains.annotations.NotNull;
import util.ConnectionProvider;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteManager {

    public static class OrganizationDeleteManager{
        private static String query = "DELETE FROM organization WHERE inn = ?";

        public static void deleteOrganization(@NotNull Long INN){

            try(var connection = ConnectionProvider.getConnection()){
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setLong(1, INN);

                preparedStatement.executeUpdate();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static class ProductDeleteManager{
        private static String query = "DELETE FROM product WHERE inner_code = ?";

        public static void deleteProduct(@NotNull Integer innerCode){

            try(var connection = ConnectionProvider.getConnection()){
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, innerCode);

                preparedStatement.executeUpdate();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static class InvoiceDeleteManager{
        private static String query = "DELETE FROM invoice WHERE num = ?";

        public static void deleteInvoice(@NotNull Integer num){

            try(var connection = ConnectionProvider.getConnection()){
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, num);

                preparedStatement.executeUpdate();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static class InvoicePositionDeleteManager{
        private static String query = "DELETE FROM invoice_position WHERE product_inner_code = ? AND invoice_num = ?";

        public static void deleteInvoice(@NotNull Integer productInnerCode, @NotNull Integer invoiceNum){

            try(var connection = ConnectionProvider.getConnection()){
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, productInnerCode);
                preparedStatement.setInt(2, invoiceNum);

                preparedStatement.executeUpdate();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
