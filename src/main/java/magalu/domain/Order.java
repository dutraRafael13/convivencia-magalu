package magalu.domain;

import java.time.LocalDate;
import java.util.List;

public class Order {

    private Integer id;
    private Double total;
    private LocalDate date;
    List<Product> products;

    public Order(Integer id, LocalDate date, List<Product> products) {
        this.id = id;
        this.date = date;
        this.products = products;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

}