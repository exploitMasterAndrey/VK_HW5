package dao;

import model.Organization;
import org.jetbrains.annotations.NotNull;
import util.ConnectionProvider;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrganizationDao implements Dao<Organization>{
    @Override
    public void create(@NotNull Organization organization) {
        String query = "INSERT INTO organization(org_name, INN, payment_account) VALUES (?,?,?)";
        try(var connection = ConnectionProvider.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, organization.getName());
            preparedStatement.setLong(2, organization.getINN());
            preparedStatement.setInt(3, organization.getPaymentAccount());
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Organization read(@NotNull int INN) {
        String query_INN = "SELECT * FROM organization WHERE INN = ?;";
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

    @Override
    public void update(@NotNull Organization organization) {
        String query = "UPDATE organization SET org_name = ?, payment_account = ? WHERE inn = ?";

        try(var connection = ConnectionProvider.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, organization.getName());
            preparedStatement.setInt(2, organization.getPaymentAccount());
            preparedStatement.setLong(3, organization.getINN());
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(Organization organization) {
        String query = "DELETE FROM organization WHERE inn = ?";

        try(var connection = ConnectionProvider.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, organization.getINN());
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Organization> all(){
        String query_all = "SELECT * FROM organization;";

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
