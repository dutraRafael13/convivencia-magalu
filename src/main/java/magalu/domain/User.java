package magalu.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Integer id;
    private String name;
    private List<Order> orders;

    public void calculateTotal() {
        this.orders.forEach(order -> order.verifyTotal());
    }

}