package mau.com.fred.platform.application.client.response;

import mau.com.fred.platform.domain.client.model.Client;

public record ClientResponse(Long id, String name, String email, boolean migrated) {
    public static ClientResponse from(Client client) {
        return new ClientResponse(
                client.getId(),
                client.getName(),
                client.getEmail(),
                client.isMigrated()
        );
    }
}
