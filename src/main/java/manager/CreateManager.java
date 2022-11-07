package manager;

import org.jetbrains.annotations.NotNull;
import util.ConnectionProvider;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CreateManager {

    public static class OrganizationCreateManager{
        private static String query = "INSERT INTO organization(org_name, INN, payment_account) VALUES (?,?,?)";

        public static Integer createOrganization(@NotNull String name, @NotNull Long INN, @NotNull Integer paymentAccount){
            int res = 0;

            try(var connection = ConnectionProvider.getConnection()){
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, name);
                preparedStatement.setLong(2, INN);
                preparedStatement.setInt(3, paymentAccount);

                res =  preparedStatement.executeUpdate();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            return res;
        }
    }

    public static class ProductCreateManager{
        private static String query = "INSERT INTO product(prod_name, inner_code) VALUES (?,?)";

        public static Integer createProduct(@NotNull String name, @NotNull Integer innerCode){
            int res = 0;

            try(var connection = ConnectionProvider.getConnection()){
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, name);
                preparedStatement.setInt(2, innerCode);

                res = preparedStatement.executeUpdate();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            return res;
        }
    }

    public static class InvoiceCreateManager{
        private static String query = "INSERT INTO invoice(num, creation_date, organization_INN) VALUES (?,?,?)";

        public static Integer createInvoice(@NotNull Integer num, @NotNull Timestamp date, @NotNull Long organizationINN){
            int res = 0;

            try(var connection = ConnectionProvider.getConnection()){
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, num);
                preparedStatement.setTimestamp(2, date);
                preparedStatement.setLong(3, organizationINN);

                res = preparedStatement.executeUpdate();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            return res;
        }
    }

    public static class InvoicePositionCreateManager{
        private static String query = "INSERT INTO invoice_position(price, product_inner_code, amount, invoice_num) VALUES (?,?,?,?)";

        public static Integer createInvoicePosition(@NotNull Integer price, @NotNull Integer productInnerCode, @NotNull Integer amount, @NotNull Integer invoiceNum){
            int res = 0;

            try(var connection = ConnectionProvider.getConnection()){
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, price);
                preparedStatement.setInt(2, productInnerCode);
                preparedStatement.setInt(3, amount);
                preparedStatement.setInt(4, invoiceNum);

                res = preparedStatement.executeUpdate();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            return res;
        }
    }
}
