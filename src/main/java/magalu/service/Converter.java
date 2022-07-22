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
            Pattern pattern = Pattern.compile("^(\\d{10})(.{45})(\\d{10})(\\d{10})(.{12})(\\d{8})");
            Path path = Paths.get(arquivo);
            try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    Matcher matcher = pattern.matcher(line);
                    if(matcher.find()){
                        int i = 1;
                        User user = new User();
                        user.setId(Integer.valueOf(matcher.group(i++)));
                        user.setName(matcher.group(i++).trim());
                        Integer orderId = Integer.valueOf(matcher.group(i++));
                        Integer productId = Integer.valueOf(matcher.group(i++));
                        Double value = Double.valueOf(matcher.group(i++));
                        LocalDate date = this.getDate(matcher.group(i));
                        user.setOrders(getOrders(mapUsers.get(user.getId()) != null ?
                                mapUsers.get(user.getId()).getOrders() : new ArrayList<>(), orderId, productId, value, date));
                        mapUsers = this.getUsers(mapUsers, user);
                    }
                }
                mapUsers = this.verifyTotal(mapUsers);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mapUsers;
    }

    protected LocalDate getDate(String date) {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int day = Integer.parseInt(date.substring(6,8));
        return LocalDate.of(year, month, day);
    }

    protected List<Order> getOrders(List<Order> orders, Integer orderId, Integer productId, Double value, LocalDate date) {
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

    protected Boolean verifyOrders(List<Order> orders, Integer orderId) {
        for (Order order : orders) {
            if (Objects.equals(order.getId(), orderId)) {
                return true;
            }
        }
        return false;
    }

    protected Map<Integer, User> getUsers(Map<Integer, User> mapUsers, User user) {
        if (mapUsers.containsKey(user.getId())) {
            mapUsers.replace(user.getId(), user);
        } else {
            mapUsers.put(user.getId(), user);
        }
        return mapUsers;
    }

    protected Map<Integer, User> verifyTotal(Map<Integer, User> mapUsers) {
        for (User user : mapUsers.values()) {
            user.getOrders().forEach(order -> order.getProducts().forEach(product -> order.setTotal(order.getTotal() + product.getValue())));
            mapUsers.replace(user.getId(), user);
        }
        return mapUsers;
    }

}