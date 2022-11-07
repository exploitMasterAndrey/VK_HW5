package report_creator;

import model.Organization;
import model.Product;
import org.flywaydb.core.internal.util.Pair;
import org.jetbrains.annotations.NotNull;
import util.ConnectionProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportCreator {

    public static List<Organization> findFirst10suppliers(){
        List<Organization> organizationList = new ArrayList<>();
        try(var connection = ConnectionProvider.getConnection()){
            final String query = "SELECT org_name, inn, payment_account, SUM(amount) as sum " +
                    "FROM invoice_position " +
                    "JOIN invoice i on i.num = invoice_position.invoice_num " +
                    "JOIN organization o on o.inn = i.organization_inn " +
                    "GROUP BY org_name, inn, payment_account " +
                    "ORDER BY sum DESC " +
                    "LIMIT 10";

            ResultSet resultSet = connection.createStatement().executeQuery(query);

            while (resultSet.next()){
                organizationList.add(new Organization(resultSet.getString("org_name"), resultSet.getLong("INN"), resultSet.getInt("payment_account")));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return organizationList;
    }

    public static List<Organization> findSuppliersWithProductSumGreaterThanInput(Map<Integer, Integer> productsAndCounts){
        List<Organization> organizationList = new ArrayList<>();

        StringBuilder query = new StringBuilder("SELECT org_name, inn, payment_account FROM " +
                "(SELECT org_name, inn, payment_account, product_inner_code, SUM(amount) as sum " +
                "FROM invoice_position " +
                "JOIN invoice i on i.num = invoice_position.invoice_num " +
                "JOIN organization o on o.inn = i.organization_inn " +
                "GROUP BY org_name, inn, payment_account, product_inner_code) as x " +
                "WHERE ");

        for (var entry: productsAndCounts.entrySet()){
            query.append("(x.product_inner_code = " + entry.getKey() + " AND x.sum > " + entry.getValue() + ") OR ");
        }

        query.delete(query.length() - 4, query.length());

        try(var connection = ConnectionProvider.getConnection()){
            ResultSet resultSet = connection.createStatement().executeQuery(query.toString());

            while (resultSet.next()){
                organizationList.add(new Organization(resultSet.getString("org_name"), resultSet.getLong("INN"), resultSet.getInt("payment_account")));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return organizationList;
    }

    public static Double calcAveragePriceDuringPeriod(@NotNull Integer innerCode, @NotNull Timestamp start, @NotNull Timestamp end){
        final String query = "SELECT AVG(price * invoice_position.amount) as avg " +
                "FROM invoice_position " +
                "JOIN invoice i on i.num = invoice_position.invoice_num " +
                "WHERE creation_date BETWEEN ? AND ? " +
                "GROUP BY product_inner_code " +
                "HAVING product_inner_code = ?";
        Double res = null;

        try(var connection = ConnectionProvider.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setTimestamp(1, start);
            preparedStatement.setTimestamp(2, end);
            preparedStatement.setInt(3, innerCode);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            res = resultSet.getDouble("avg");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return res;
    }



    public static Map<Date, List<Map<Integer, Map<String, Integer>>>> calcAmountAndSumEverydayForEveryProductDuringPeriod(@NotNull Timestamp start, @NotNull Timestamp end){
        final String query = "SELECT product_inner_code, creation_date::TIMESTAMP::DATE as creation_date, SUM(amount) AS amount, SUM(amount * price) AS price " +
                "FROM invoice_position " +
                "JOIN invoice i on i.num = invoice_position.invoice_num " +
                "WHERE creation_date BETWEEN ? AND ? " +
                "GROUP BY product_inner_code, creation_date::TIMESTAMP::DATE";

        Map<Date, List<Map<Integer, Map<String, Integer>>>> res = new HashMap<>();

        Map<Integer, Map<String, Integer>> periodResults = new HashMap<>();

        try(var connection = ConnectionProvider.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setTimestamp(1, start);
            preparedStatement.setTimestamp(2, end);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                if (res.containsKey(resultSet.getDate("creation_date"))){
                    List<Map<Integer, Map<String, Integer>>> creation_date = res.get(resultSet.getDate("creation_date"));
                    creation_date.add(Map.of(resultSet.getInt("product_inner_code"), Map.of("amount", resultSet.getInt("amount"), "price", resultSet.getInt("price"))));

                    //new
                    if (periodResults.containsKey(resultSet.getInt("product_inner_code"))){
                        Map<String, Integer> product_inner_code = periodResults.get(resultSet.getInt("product_inner_code"));
                        if (product_inner_code.get("amount") != null) {
                            product_inner_code.put("amount", product_inner_code.get("amount") + resultSet.getInt("amount"));
                            product_inner_code.put("price", product_inner_code.get("price") + resultSet.getInt("price"));
                        }
                    }else {
                        Map<String, Integer> product_inner_code = new HashMap<>();
                        product_inner_code.put("amount", resultSet.getInt("amount"));
                        product_inner_code.put("price",  resultSet.getInt("price"));

                        periodResults.put(resultSet.getInt("product_inner_code"), product_inner_code);
                    }
                }else {
                    List<Map<Integer, Map<String, Integer>>> creation_date = new ArrayList<>();
                    creation_date.add(Map.of(resultSet.getInt("product_inner_code"), Map.of("amount", resultSet.getInt("amount"), "price", resultSet.getInt("price"))));
                    res.put(resultSet.getDate("creation_date"), creation_date);

                    //new
                    if (periodResults.containsKey(resultSet.getInt("product_inner_code"))){
                        Map<String, Integer> product_inner_code = periodResults.get(resultSet.getInt("product_inner_code"));
                        if (product_inner_code.get("amount") != null) {
                            product_inner_code.put("amount", product_inner_code.get("amount") + resultSet.getInt("amount"));
                            product_inner_code.put("price", product_inner_code.get("price") + resultSet.getInt("price"));
                        }
                    }else {
                        Map<String, Integer> product_inner_code = new HashMap<>();
                        product_inner_code.put("amount",  resultSet.getInt("amount"));
                        product_inner_code.put("price",  resultSet.getInt("price"));

                        periodResults.put(resultSet.getInt("product_inner_code"), product_inner_code);
                    }
                }
            }

            System.out.println("TOTAL: " + periodResults);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return res;
    }

    public static Map<Organization, Product> getDeliveredByOrganizationsProductsListInPeriod(@NotNull Timestamp start, @NotNull Timestamp end){
        final String query = "SELECT DISTINCT org_name, inn, payment_account, prod_name, inner_code " +
                "FROM organization " +
                "LEFT JOIN invoice i on organization.inn = i.organization_inn " +
                "LEFT JOIN invoice_position ip on i.num = ip.invoice_num " +
                "LEFT JOIN product p on p.inner_code = ip.product_inner_code " +
                "WHERE (creation_date BETWEEN ? AND ?) OR (creation_date IS NULL);";

        Map<Organization, Product> res = new HashMap<>();

        try(var connection = ConnectionProvider.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setTimestamp(1, start);
            preparedStatement.setTimestamp(2, end);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Organization organization = new Organization(resultSet.getString("org_name"), resultSet.getLong("INN"), resultSet.getInt("payment_account"));
                if (resultSet.getString("prod_name") != null) res.put(organization, new Product(resultSet.getString("prod_name"), resultSet.getInt("inner_code")));
                else res.put(organization, null);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }
}
