package magalu.service;

import magalu.domain.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class UserServiceTest {

    @Test
    public void convertTest() {
        Map<Integer, User> users = new UserService().convert("src/test/resources/data_teste.txt");
        Assert.assertNotNull(users);
        Assert.assertEquals(2, users.get(70).getOrders().get(0).getProducts().size());
        Assert.assertEquals(2, users.get(75).getOrders().size());
    }

}