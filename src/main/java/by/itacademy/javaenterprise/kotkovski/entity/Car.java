package by.itacademy.javaenterprise.kotkovski.entity;

import java.util.Optional;

public class Car {
    private Long id;
    private String car;
    private String vin;
    private String number;
    private int year;
    private Customer customer;

    public Car() {
    }

    public Car(Long id, String car, String vin, String number, int year, Customer customer) {
        this.id = id;
        this.car = car;
        this.vin = vin;
        this.number = number;
        this.year = year;
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", car='" + car + '\'' +
                ", vin='" + vin + '\'' +
                ", number='" + number + '\'' +
                ", year=" + year +
                ", customer=" + customer +
                '}';
    }
}
