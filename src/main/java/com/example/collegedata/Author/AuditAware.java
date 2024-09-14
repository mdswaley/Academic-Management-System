package com.example.collegedata.Author;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("MD Swaley");
    }
}
