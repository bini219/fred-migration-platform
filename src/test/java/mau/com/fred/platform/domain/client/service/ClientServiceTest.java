package mau.com.fred.platform.domain.client.service;

import mau.com.fred.platform.application.client.request.SearchClientsRequest;
import mau.com.fred.platform.domain.client.model.Client;
import mau.com.fred.platform.domain.client.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    @Test
    void shouldReturnLegacyClientsWhenCallingGetLegacyClients() {
        Client client1 = new Client(1L, "Client 1", "client1@test.com", false);
        Client client2 = new Client(2L, "Client 2", "client2@test.com", false);
        List<Client> legacyClients = List.of(client1, client2);

        when(clientRepository.findAllLegacy()).thenReturn(legacyClients);

        List<Client> result = clientService.getLegacyClients();

        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyInAnyOrder(client1, client2);
    }

    @Test
    void shouldReturnMigratedClientsWhenCallingGetMigratedClients() {
        Client client1 = new Client(1L, "Client 1", "client1@test.com", true);
        Client client2 = new Client(2L, "Client 2", "client2@test.com", true);
        List<Client> migratedClients = List.of(client1, client2);

        when(clientRepository.findAllMigrated()).thenReturn(migratedClients);

        List<Client> result = clientService.getMigratedClients();

        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyInAnyOrder(client1, client2);
    }
}
