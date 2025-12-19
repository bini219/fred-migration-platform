package mau.com.fred.platform.domain.migrate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mau.com.fred.platform.domain.client.model.Client;
import mau.com.fred.platform.domain.client.repository.ClientRepository;
import mau.com.fred.platform.domain.migrate.exception.ClientNotFoundException;
import mau.com.fred.platform.domain.migrate.model.MigrationResult;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MigrationService {
    private final ClientRepository clientRepository;

    public MigrationResult migrateClient(Long clientId) {
        log.info("Starting migration for client: {}", clientId);

        Client client = clientRepository.findById(clientId)
                                        .orElseThrow(() -> new ClientNotFoundException(clientId));

        client.markAsMigrated();
        clientRepository.save(client);

        log.info("Successfully migrated client: {}", clientId);
        return MigrationResult.success(client);
    }
}
