package com.example.collegedata.Config;

import com.example.collegedata.Author.AuditAware;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "getAuditorAwareImp")
public class modelMapper {
    @Bean
    public ModelMapper addMapper(){
        return new ModelMapper();
    }
    @Bean  //Now our Jpa is also attach with auditor.
    public AuditorAware<String> getAuditorAwareImp(){
        return new AuditAware();
    }
}
