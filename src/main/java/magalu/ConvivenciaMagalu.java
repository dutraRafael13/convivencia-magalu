package magalu;

import magalu.service.ConverterJson;
import magalu.service.ConverterUser;

public class ConvivenciaMagalu {

    public static void main(String[] args) {
        ConverterJson convert = new ConverterJson(new ConverterUser());
        convert.convert("src/main/resources/data_1.txt", "src/main/resources/user1.json");
        convert.convert("src/main/resources/data_2.txt", "src/main/resources/user2.json");
    }
}
