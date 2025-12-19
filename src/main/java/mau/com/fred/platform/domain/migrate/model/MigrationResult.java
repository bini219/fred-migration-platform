package mau.com.fred.platform.domain.migrate.model;

import lombok.Value;
import mau.com.fred.platform.domain.client.model.Client;

import java.time.LocalDateTime;

@Value
public class MigrationResult {
    Client client;
    LocalDateTime migratedAt;
    String message;

    public static MigrationResult success(Client client) {
        return new MigrationResult(client, LocalDateTime.now(), "Client migrated successfully");
    }
}
