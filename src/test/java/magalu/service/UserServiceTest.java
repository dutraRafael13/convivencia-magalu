package magalu.service;

import magalu.domain.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class UserServiceTest {

    @Test
    public void convertTestNotNull() {
        Map<Integer, User> users = new UserService().convert("src/test/resources/data_teste.txt");
        Assert.assertNotNull(users);
    }

    @Test
    public void convertTestOneOrderAndTwoProductsFromPalmer() {
        Map<Integer, User> users = new UserService().convert("src/test/resources/data_teste.txt");
        Assert.assertEquals("Palmer Prosacco", users.get(70).getName());
        Assert.assertEquals(1, users.get(70).getOrders().size());
        Assert.assertEquals(2, users.get(70).getOrders().get(0).getProducts().size());
    }

    @Test
    public void convertTestTwoOrdersFromBobbie() {
        Map<Integer, User> users = new UserService().convert("src/test/resources/data_teste.txt");
        Assert.assertEquals("Bobbie Batz", users.get(75).getName());
        Assert.assertEquals(2, users.get(75).getOrders().size());
    }

    @Test
    public void convertTestTwoOrdersFromKen() {
        Map<Integer, User> users = new UserService().convert("src/test/resources/data_teste.txt");
        Assert.assertEquals("Ken Wintheiser", users.get(49).getName());
        Assert.assertEquals(2, users.get(49).getOrders().size());
    }

}