package by.itacademy.javaenterprise.kotkovski.dao.impl;

import by.itacademy.javaenterprise.kotkovski.dao.CarDAO;
import by.itacademy.javaenterprise.kotkovski.dao.CustomerDAO;
import by.itacademy.javaenterprise.kotkovski.dao.config.ConnectionManager;
import by.itacademy.javaenterprise.kotkovski.dao.config.MyDataSource;
import by.itacademy.javaenterprise.kotkovski.entity.Car;
import by.itacademy.javaenterprise.kotkovski.entity.Customer;
import by.itacademy.javaenterprise.kotkovski.exception.DAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CarDAOImpl implements CarDAO<Long, Car> {

    private final static Logger logger = LoggerFactory.getLogger(ConnectionManager.class);

    private DataSource dataSource;

    private final CustomerDAO<Long, Customer> customerDAO = new CustomerDAOImpl();

    private final static String FIND_BY_ID_SQL = "select id, car, vin, number, year, customer_id from Cars where id = ?";

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public Car save(Car car) {
        return null;
    }

    @Override
    public void update(Car car) {
    }

    @Override
    public List<Car> findAll() {
        return null;
    }

    @Override
    public Optional<Car> findById(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Car car = null;
            if (resultSet.next()) {
                car = buildCar(resultSet);
            }
            return Optional.ofNullable(car);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private Car buildCar(ResultSet resultSet) throws SQLException {
        return new Car(
                resultSet.getLong("id"),
                resultSet.getString("car"),
                resultSet.getString("vin"),
                resultSet.getString("number"),
                resultSet.getInt("year"),
                customerDAO.findById(resultSet.getLong("id")).orElse(null)
        );
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
