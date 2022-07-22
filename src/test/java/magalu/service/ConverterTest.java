package magalu.service;

import magalu.domain.Order;
import magalu.domain.Product;
import magalu.domain.User;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.*;

public class ConverterTest {

    @Test
    public void conveterTest() {
        Map<Integer, User> users = new Converter().convert("src/test/resources/data_teste.txt");
        Assert.assertNotNull(users);
        Assert.assertEquals(2, users.get(70).getOrders().get(0).getProducts().size());
        Assert.assertEquals(2, users.get(75).getOrders().size());
    }

    @Test
    public void getDateTest() {
        LocalDate date = new Converter().getDate("20220721");
        Assert.assertNotNull(date);
    }

    @Test
    public void getOrdersTestAddProduct() {
        List<Order> orders = this.getOrders(1,1,1.0);
        orders = new Converter().getOrders(orders, 1, 2, 2.0, LocalDate.now());
        Assert.assertEquals(2, orders.get(0).getProducts().size());
    }

    @Test
    public void getOrdersTestNewOrder() {
        List<Order> orders = new ArrayList<>();
        orders = new Converter().getOrders(orders, 1, 2, 2.0, LocalDate.now());
        Assert.assertFalse(orders.isEmpty());
    }

    @Test
    public void getOrdersTestAddOrder() {
        List<Order> orders = this.getOrders(1,1,1.0);
        orders = new Converter().getOrders(orders, 2, 2, 2.0, LocalDate.now());
        Assert.assertEquals(2, orders.size());
    }

    @Test
    public void getUsersTestUpdateUser() {
        Map<Integer, User> mapUsers = new HashMap<>();
        List<Order> orders = this.getOrders(1,1,1.0);
        mapUsers.put(1, new User(1, "Rafael", orders));
        orders.addAll(this.getOrders(2,2,2.0));
        mapUsers = new Converter().getUsers(mapUsers, new User(1, "Rafael", orders));
        Assert.assertEquals(2, mapUsers.get(1).getOrders().size());
    }

    @Test
    public void getUsersTestNewUser() {
        Map<Integer, User> mapUsers = new HashMap<>();
        List<Order> orders = this.getOrders(1,1,1.0);
        mapUsers.put(1, new User(1, "Rafael", orders));
        orders.addAll(this.getOrders(2,2,2.0));
        mapUsers = new Converter().getUsers(mapUsers, new User(2, "João", orders));
        Assert.assertEquals(2, mapUsers.size());
    }

    @Test
    public void verifyTotal() {
        Map<Integer, User> mapUsers = new HashMap<>();
        List<Order> orders = this.getOrders(1,1,1.0);
        orders.get(0).getProducts().add(new Product(2,2.0));
        mapUsers.put(1, new User(1, "Rafael", orders));
        mapUsers = new Converter().verifyTotal(mapUsers);
        Assert.assertEquals(Double.valueOf(3.0), mapUsers.get(1).getOrders().get(0).getTotal());
    }

    @Test
    public void verifyTotalTwoUsers() {
        Map<Integer, User> mapUsers = new HashMap<>();
        List<Order> order1 = this.getOrders(1,1,1.0);
        order1.get(0).getProducts().add(new Product(2,2.0));
        mapUsers.put(1, new User(1, "Rafael", order1));
        List<Order> order2 = this.getOrders(1,3,3.0);
        mapUsers.put(2, new User(2, "João", order2));
        mapUsers = new Converter().verifyTotal(mapUsers);
        Assert.assertEquals(Double.valueOf(3.0), mapUsers.get(1).getOrders().get(0).getTotal());
        Assert.assertEquals(Double.valueOf(3.0), mapUsers.get(2).getOrders().get(0).getTotal());
    }

    private List<Order> getOrders(Integer orderId, Integer productId, Double value) {
        List<Order> orders = new ArrayList<>();
        List<Product> products = new ArrayList<>();
        products.add(new Product(productId,value));
        orders.add(new Order(orderId, 0.0, LocalDate.now(), products));
        return orders;
    }

}