package mau.com.fred.platform.domain.client.repository;

import mau.com.fred.platform.domain.client.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    List<Client> findAllLegacy();

    List<Client> findAllMigrated();

    Optional<Client> findById(Long id);

    void save(Client client);

    List<Client> searchClients(String query);
}
