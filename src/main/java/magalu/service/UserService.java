package magalu.service;

import lombok.NonNull;
import magalu.domain.Order;
import magalu.domain.Product;
import magalu.domain.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserService {

    private static final String REGEX = "^(\\d{10})(.{45})(\\d{10})(\\d{10})(.{12})(\\d{8})";

    public Map<Integer, User> convert(@NonNull final String arquivo) {
        Map<Integer, User> mapUsers = new HashMap<>();
        Pattern pattern = Pattern.compile(REGEX);
        Path path = Paths.get(arquivo);
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    int i = 1;
                    User user = new User();
                    user.setId(Integer.valueOf(matcher.group(i++)));
                    user.setName(matcher.group(i++).trim());
                    final var orderId = Integer.valueOf(matcher.group(i++));
                    final var productId = Integer.valueOf(matcher.group(i++));
                    final var value = Double.valueOf(matcher.group(i++));
                    final var date = matcher.group(i);
                    user.setOrders(getOrders(mapUsers.get(user.getId()) != null ?
                            mapUsers.get(user.getId()).getOrders() : new ArrayList<>(), orderId, productId, value, date));
                    mapUsers = this.getUsers(mapUsers, user);
                }
            }
            mapUsers.values().forEach(User::calculateTotal);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mapUsers;
    }

    private List<Order> getOrders(final List<Order> orders, final Integer orderId, final Integer productId, final Double value, final String date) {
        if (!orders.isEmpty() && Boolean.TRUE.equals(verifyOrders(orders, orderId))) {
            orders.forEach(order -> {
                if (Objects.equals(order.getId(), orderId)) {
                    order.getProducts().add(new Product(productId, value));
                    return;
                }
            });
        } else {
            List<Product> products = new ArrayList<>();
            products.add(new Product(productId, value));
            orders.add(new Order(orderId, 0.0, date, products));
        }
        return orders;
    }

    private Boolean verifyOrders(final List<Order> orders, final Integer orderId) {
        return orders.stream().anyMatch(order -> Objects.equals(order.getId(), orderId));
    }

    private Map<Integer, User> getUsers(final Map<Integer, User> mapUsers, final User user) {
        if (mapUsers.containsKey(user.getId())) {
            mapUsers.replace(user.getId(), user);
        } else {
            mapUsers.put(user.getId(), user);
        }
        return mapUsers;
    }

}