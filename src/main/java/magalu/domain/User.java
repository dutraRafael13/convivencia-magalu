package magalu.domain;

import java.util.ArrayList;
import java.util.List;

public class User {

    private Integer id;
    private String name;
    private List<Order> orders;

    public User() {
        this.id = 0;
        this.name = "";
        this.orders = new ArrayList<>();
    }

    public User(Integer id, String name, List<Order> orders) {
        this.id = id;
        this.name = name;
        this.orders = orders;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

}