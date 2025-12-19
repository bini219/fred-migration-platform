package mau.com.fred.platform.domain.migrate.service;

import mau.com.fred.platform.domain.client.model.Client;
import mau.com.fred.platform.domain.client.repository.ClientRepository;
import mau.com.fred.platform.domain.migrate.exception.ClientNotFoundException;
import mau.com.fred.platform.domain.migrate.model.MigrationResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MigrationServiceTest {

    @InjectMocks
    private MigrationService migrationService;

    @Mock
    private ClientRepository clientRepository;

    @Test
    void shouldMigrateClientSuccessfully() {
        Long clientId = 1L;
        Client client = new Client(clientId, "Client 1", "client1@test.com", false);

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

        MigrationResult result = migrationService.migrateClient(clientId);

        assertThat(result).isNotNull();
        assertThat(result.getClient()).isEqualTo(client);
        assertThat(result.getClient().isMigrated()).isTrue();
        assertThat(result.getMessage()).isEqualTo("Client migrated successfully");

        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void shouldThrowClientNotFoundExceptionWhenClientDoesNotExist() {
        Long clientId = 1L;
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> migrationService.migrateClient(clientId))
                .isInstanceOf(ClientNotFoundException.class)
                .hasMessage("Client with id 1 not found");

        verify(clientRepository, never()).save(any());
    }

    @Test
    void shouldThrowIllegalStateExceptionWhenClientIsAlreadyMigrated() {
        Long clientId = 1L;
        Client client = new Client(clientId, "Client 1", "client1@test.com", true);

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

        assertThatThrownBy(() -> migrationService.migrateClient(clientId))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Client is already migrated");

        verify(clientRepository, never()).save(any());
    }
}
