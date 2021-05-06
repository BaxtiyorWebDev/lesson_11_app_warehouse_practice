package uz.pdp.online.lesson_11_app_warehouse_practice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.online.lesson_11_app_warehouse_practice.entity.Client;
import uz.pdp.online.lesson_11_app_warehouse_practice.payload.Result;
import uz.pdp.online.lesson_11_app_warehouse_practice.repository.ClientRepos;

import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    ClientRepos clientRepos;

    public Result addClient(Client client) {
        boolean exists = clientRepos.existsByNameAndPhoneNumber(client.getName(), client.getPhoneNumber());
        if (exists)
            return new Result("Bunday mijoz mavjud", false);
        clientRepos.save(client);
        return new Result("Mijoz qo'shildi", true);
    }

    public Page<Client> getClientsList(int page) {
        Pageable pageable = PageRequest.of(page, 20);
        Page<Client> clients = clientRepos.findAll(pageable);
        return clients;
    }

    public Client getClient(Integer id) {
        Optional<Client> optionalClient = clientRepos.findById(id);
        if (!optionalClient.isPresent())
            return new Client();
        return optionalClient.get();
    }

    public Result editClient(Integer id, Client client) {
        boolean exists = clientRepos.existsByNameAndPhoneNumberAndIdNot(client.getName(), client.getPhoneNumber(), id);
        if (exists)
            return new Result("Bunday mijoz mavjud, qaytadan urinib ko'ring",false);
        Optional<Client> optionalClient = clientRepos.findById(id);
        if (!optionalClient.isPresent())
            return new Result("Bunday mijoz mavjud emas",false);
        Client editingClient = optionalClient.get();
        editingClient.setName(client.getName());
        editingClient.setPhoneNumber(client.getPhoneNumber());
        editingClient.setActive(client.isActive());
        clientRepos.save(editingClient);
        return new Result("Ma'lumot tahrirlandi", true);
    }

    public Result deleteClient(Integer id) {
        Optional<Client> optionalClient = clientRepos.findById(id);
        if (!optionalClient.isPresent())
            return new Result("Bunday mijoz mavjud emas",false);
        clientRepos.delete(optionalClient.get());
        return new Result("Ma'lumot o'chirildi",true);
    }
}
