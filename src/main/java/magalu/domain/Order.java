package magalu.domain;

import java.util.ArrayList;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Order {

    private Integer id;
    private Double total;
    private String date;
    List<Product> products;

    public List<Product> getProducts() {
        if (Objects.isNull(products)) {
            this.products = new ArrayList<>();
        }
        return this.products;
    }

    public void verifyTotal() {
        this.products.forEach(product -> total += product.getValue());
    }

}