package magalu;

import magalu.converter.JsonConverter;
import magalu.service.UserService;

public class ConvivenciaMagalu {

    public static void main(String[] args) {
        JsonConverter convert = new JsonConverter(new UserService());
        convert.convert("src/main/resources/data_1.txt", "src/main/resources/user1.json");
        convert.convert("src/main/resources/data_2.txt", "src/main/resources/user2.json");
    }
}
