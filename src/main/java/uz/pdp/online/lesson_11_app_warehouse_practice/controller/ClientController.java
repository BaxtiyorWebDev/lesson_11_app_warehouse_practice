package uz.pdp.online.lesson_11_app_warehouse_practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.web.bind.annotation.*;
import uz.pdp.online.lesson_11_app_warehouse_practice.entity.Client;
import uz.pdp.online.lesson_11_app_warehouse_practice.payload.Result;
import uz.pdp.online.lesson_11_app_warehouse_practice.service.ClientService;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    ClientService clientService;

    @PostMapping
    public Result addClient(@RequestBody Client client) {
        Result result = clientService.addClient(client);
        return result;
    }

    @GetMapping
    public Page<Client> getClientsList(@RequestParam int page) {
        Page<Client> clientsList = clientService.getClientsList(page);
        return clientsList;
    }

    @GetMapping("/{id}")
    public Client getClient(@PathVariable Integer id) {
        Client client = clientService.getClient(id);
        return client;
    }

    @PutMapping("/{id}")
    public Result editClient(@PathVariable Integer id, @RequestBody Client client) {
        Result result = clientService.editClient(id, client);
        return result;
    }

    @DeleteMapping("/{id}")
    public Result deleteClient(@PathVariable Integer id) {
        Result result = clientService.deleteClient(id);
        return result;
    }
}
