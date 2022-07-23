package magalu.converter;

import magalu.converter.JsonConverter;
import magalu.domain.Order;
import magalu.domain.Product;
import magalu.domain.User;
import magalu.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class JsonConverterTest {

    @Mock
    private UserService service;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void convertTest() {
        JsonConverter convert = new JsonConverter(service);
        Mockito.when(service.convert("")).thenReturn(this.getUsers());
        convert.convert("", "src/test/resources/user.json");
        File file = new File("src/test/resources/user.json");
        Assert.assertTrue(file.exists());
    }

    private Map<Integer, User> getUsers() {
        Map<Integer, User> mapUser = new HashMap<>();
        List<Order> orders = new ArrayList<>();
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, 1.0));
        orders.add(new Order(1, 1.0, "20220722", products));
        mapUser.put(1, new User(1, "Rafael", orders));
        mapUser.put(2, new User(2, "João", orders));
        mapUser.put(3, new User(3, "Chico", orders));
        return mapUser;
    }

}