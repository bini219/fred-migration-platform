package mau.com.fred.platform.domain.client.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Client {
    private Long id;
    private String name;
    private String email;
    private boolean migrated;

    public void markAsMigrated() {
        if (this.migrated) {
            throw new IllegalStateException("Client is already migrated");
        }
        this.migrated = true;
    }
}
