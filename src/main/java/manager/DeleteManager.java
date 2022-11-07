package manager;

import org.jetbrains.annotations.NotNull;
import util.ConnectionProvider;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteManager {

    public static class OrganizationDeleteManager{
        private static String query = "DELETE FROM organization WHERE inn = ?";

        public static Integer deleteOrganization(@NotNull Long INN){
            int res = 0;

            try(var connection = ConnectionProvider.getConnection()){
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setLong(1, INN);

                res = preparedStatement.executeUpdate();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return res;
        }
    }

    public static class ProductDeleteManager{
        private static String query = "DELETE FROM product WHERE inner_code = ?";

        public static Integer deleteProduct(@NotNull Integer innerCode){
            int res = 0;

            try(var connection = ConnectionProvider.getConnection()){
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, innerCode);

                res = preparedStatement.executeUpdate();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return res;
        }
    }

    public static class InvoiceDeleteManager{
        private static String query = "DELETE FROM invoice WHERE num = ?";

        public static Integer deleteInvoice(@NotNull Integer num){
            int res = 0;

            try(var connection = ConnectionProvider.getConnection()){
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, num);

                res = preparedStatement.executeUpdate();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return res;
        }
    }

    public static class InvoicePositionDeleteManager{
        private static String query = "DELETE FROM invoice_position WHERE product_inner_code = ? AND invoice_num = ?";

        public static Integer deleteInvoicePosition(@NotNull Integer productInnerCode, @NotNull Integer invoiceNum){
            int res = 0;

            try(var connection = ConnectionProvider.getConnection()){
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, productInnerCode);
                preparedStatement.setInt(2, invoiceNum);

                res = preparedStatement.executeUpdate();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return res;
        }
    }
}
