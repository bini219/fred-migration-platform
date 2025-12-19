package mau.com.fred.platform.application.client.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Validated
@Data
public class SearchClientsRequest {
    @NotEmpty(message = "Query cannot be empty")
    private String query;
}
