package mau.com.fred.platform.domain.client.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mau.com.fred.platform.domain.client.model.Client;
import mau.com.fred.platform.domain.client.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    public List<Client> getLegacyClients() {
        return clientRepository.findAllLegacy();
    }

    public List<Client> getMigratedClients() {
        return clientRepository.findAllMigrated();
    }

}
