package mau.com.fred.platform.infrastructure.persistence;

import mau.com.fred.platform.domain.client.model.Client;
import mau.com.fred.platform.domain.client.repository.ClientRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryClientRepository implements ClientRepository {
    private final Map<Long, Client> storage = new ConcurrentHashMap<>();

    public InMemoryClientRepository() {
        initializeSampleData();
    }

    private void initializeSampleData() {
        save(new Client(
                1L,
                "Acme Corporation",
                "contact@acme.com",
                false
        ));
        save(new Client(
                2L,
                "TechStart Inc",
                "info@techstart.com",
                false
        ));
        save(new Client(
                3L,
                "Global Solutions",
                "hello@global.com",
                false
        ));
        save(new Client(
                4L,
                "Innovation Labs",
                "team@innovation.com",
                false
        ));
    }

    @Override
    public List<Client> findAllLegacy() {
        return storage.values().stream()
                      .filter(client -> !client.isMigrated())
                      .toList();
    }

    @Override
    public List<Client> findAllMigrated() {
        return storage.values().stream()
                      .filter(Client::isMigrated)
                      .toList();
    }

    @Override
    public Optional<Client> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public void save(Client client) {
        storage.put(client.getId(), client);
    }
}
