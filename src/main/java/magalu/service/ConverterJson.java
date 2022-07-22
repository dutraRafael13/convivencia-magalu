package magalu.service;

import com.google.gson.Gson;
import magalu.domain.User;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConverterJson {

    private final ConverterUser convertUser;

    public ConverterJson(ConverterUser convertUser) {
        this.convertUser = convertUser;
    }

    public void convert(String arquivo, String directory) {
        List<User> users = this.getUsers(arquivo);
        if (users != null && !users.isEmpty()) {
            try (FileWriter writer = new FileWriter(directory)) {
                Gson gson = new Gson();
                String json = gson.toJson(users);
                writer.write(json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected List<User> getUsers(String arquivo) {
        Map<Integer, User> mapUser = this.convertUser.convert(arquivo);
        return new ArrayList<>(mapUser.values());
    }

}