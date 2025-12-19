package mau.com.fred.platform.application.client.controller;

import lombok.RequiredArgsConstructor;
import mau.com.fred.platform.application.client.response.ClientResponse;
import mau.com.fred.platform.domain.client.service.ClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @GetMapping("/legacy/client")
    public List<ClientResponse> getLegacyClients() {
        return clientService.getLegacyClients().stream()
                            .map(ClientResponse::from)
                            .toList();
    }

    @GetMapping("/new/client")
    public List<ClientResponse> getNewClients() {
        return clientService.getMigratedClients().stream()
                            .map(ClientResponse::from)
                            .toList();
    }
}
