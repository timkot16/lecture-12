package by.itacademy.javaenterprise.kotkovski.dao.impl;

import by.itacademy.javaenterprise.kotkovski.dao.CustomerDAO;
import by.itacademy.javaenterprise.kotkovski.dao.config.ConnectionManager;
import by.itacademy.javaenterprise.kotkovski.dao.config.MyDataSource;
import by.itacademy.javaenterprise.kotkovski.entity.Customer;
import by.itacademy.javaenterprise.kotkovski.exception.DAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDAOImpl implements CustomerDAO<Long, Customer> {

    private DataSource dataSource;

    private final static Logger logger = LoggerFactory.getLogger(ConnectionManager.class);

    private final static String SAVE = "insert into Customer(first_name, last_name, tel_number) values(?, ?, ?)";
    private final static String UPDATE = "update Customer set first_name = ?, last_name = ?, tel_number = ? where id = ?";
    private final static String DELETE = "delete from Customer where id = ?";
    private final static String FIND_ALL_SQl = "select id, first_name, last_name, tel_number from Customer";
    private final static String FIND_BY_ID_SQL = FIND_ALL_SQl + " where id = ?";

    @Override
    public Customer save(Customer customer) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE)) {
            connection.setAutoCommit(false);
            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setString(3, customer.getTelNumber());
            statement.executeUpdate();
            connection.commit();
            return customer;
        } catch (SQLException e) {
            logger.error("Failed to save {}", customer, e);
            throw new DAOException(e);
        }
    }

    @Override
    public void update(Customer customer) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            connection.setAutoCommit(false);
            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setString(3, customer.getTelNumber());
            statement.setLong(4, customer.getId());
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            logger.error("Failed to update {}", customer, e);
            throw new DAOException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Failed to delete customer with current id {}", id, e);
            throw new DAOException(e);
        }
    }

    @Override
    public Optional<Customer> findById(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            Customer customer = null;
            if (rs.next()) {
                customer = buildCustomer(rs);
            }
            return Optional.ofNullable(customer);
        } catch (SQLException e) {
            logger.error("Failed to find customer with current id {}", id, e);
            throw new DAOException(e);
        }
    }

    @Override
    public List<Customer> findAll() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQl)) {
            ResultSet rs = preparedStatement.executeQuery();
            List<Customer> customers = new ArrayList<>();
            while (rs.next()) {
                customers.add(buildCustomer(rs));
            }
            return customers;
        } catch (SQLException e) {
            logger.error("Failed to find all customers", e);
            throw new DAOException(e);
        }
    }

    private Customer buildCustomer(ResultSet rs) throws SQLException {
        return new Customer(rs.getLong("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("tel_number"));
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
