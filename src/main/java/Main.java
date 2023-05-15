import configurations.FlywayConfigurations;
import entity.Client;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        new FlywayConfigurations()
                .setup()
                .migrate();


        ClientService clientService = new ClientServiceImpl();
        long id = clientService.create("New User");
        System.out.println(id);
        String name = clientService.getById(id);
        System.out.println(name);
        clientService.setName(id, "Updated name user");
        System.out.println(clientService.getById(id));
        clientService.deleteById(id);
        System.out.println(clientService.getById(id));
        List<Client> clientList = clientService.listAll();
        for (Client client:clientList){
            System.out.println(client);
        }
    }
}
