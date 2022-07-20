package magalu.service;

import magalu.domain.Order;
import magalu.domain.Product;
import magalu.domain.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Converter {

    public Map<Integer, User> convert(String arquivo) {
        Map<Integer, User> mapUsers = new HashMap<>();
        try {
            Pattern pattern = Pattern.compile("^(\\d{10})(.{45})(\\d{10})(\\d{10})(\\d{12})(\\d{8})");
            Path path = Paths.get(arquivo);
            try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    Matcher matcher = pattern.matcher(line);
                    if(matcher.find()){
                        int i = 0;
                        User user = new User();
                        user.setId(Integer.valueOf(matcher.group(i++)));
                        user.setName(matcher.group(i++).trim());
                        Integer orderId = Integer.valueOf(matcher.group(i++));
                        Integer productId = Integer.valueOf(matcher.group(i++));
                        Double value = Double.valueOf(matcher.group(i++));
                        LocalDate date = LocalDate.parse(matcher.group(i));
                        user.setOrders(getOrders(user.getOrders(), orderId, productId, value, date));
                        this.getUsers(mapUsers, user);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mapUsers;
    }

    private List<Order> getOrders(List<Order> orders, Integer orderId, Integer productId, Double value, LocalDate date) {
        List<Order> newOrders = new ArrayList<>();
        if (orders != null && !orders.isEmpty() && verifyOrders(orders, orderId)) {
            orders.forEach(order -> {
                if (Objects.equals(order.getId(), orderId)) {
                    order.getProducts().add(new Product(productId, value));
                }
            });
            newOrders = orders;
        } else {
            List<Product> products = new ArrayList<>();
            products.add(new Product(productId, value));
            newOrders.add(new Order(orderId, date, products));
        }
        return newOrders;
    }

    private Boolean verifyOrders(List<Order> orders, Integer orderId) {
        for (Order order : orders) {
            if (Objects.equals(order.getId(), orderId)) {
                return true;
            }
        }
        return false;
    }

    private void getUsers(Map<Integer, User> mapUsers, User user) {
        if (mapUsers.containsKey(user.getId())) {
            mapUsers.replace(user.getId(), user);
        } else {
            mapUsers.put(user.getId(), user);
        }
    }

}