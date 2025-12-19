package mau.com.fred.platform.application.migrate.response;

import mau.com.fred.platform.application.client.response.ClientResponse;
import mau.com.fred.platform.domain.migrate.model.MigrationResult;

import java.time.LocalDateTime;

public record MigrationResponse(ClientResponse client, LocalDateTime migratedAt, String message) {
    public static MigrationResponse from(MigrationResult result) {
        return new MigrationResponse(
                ClientResponse.from(result.getClient()),
                result.getMigratedAt(),
                result.getMessage()
        );
    }
}