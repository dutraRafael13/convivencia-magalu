package magalu.converter;

import com.google.gson.Gson;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import magalu.domain.User;
import magalu.service.UserService;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class JsonConverter {

    private final UserService service;

    public void convert(@NonNull final String file, @NonNull final String directory) {
        List<User> users = this.getUsers(file);
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
        Map<Integer, User> mapUser = this.service.convert(arquivo);
        return new ArrayList<>(mapUser.values());
    }

}