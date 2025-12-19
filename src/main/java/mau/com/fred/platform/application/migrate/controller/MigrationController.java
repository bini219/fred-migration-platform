package mau.com.fred.platform.application.migrate.controller;

import lombok.RequiredArgsConstructor;
import mau.com.fred.platform.application.migrate.response.MigrationResponse;
import mau.com.fred.platform.domain.migrate.model.MigrationResult;
import mau.com.fred.platform.domain.migrate.service.MigrationService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/migrate")
@RequiredArgsConstructor
public class MigrationController {
    private final MigrationService migrationService;

    @PostMapping("/{id}")
    public MigrationResponse migrateClient(@PathVariable Long id) {
        MigrationResult result = migrationService.migrateClient(id);
        return MigrationResponse.from(result);
    }
}
