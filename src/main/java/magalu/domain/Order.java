package magalu.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class Order {

    private Integer id;
    private Double total;
    private LocalDate date;
    List<Product> products;

}