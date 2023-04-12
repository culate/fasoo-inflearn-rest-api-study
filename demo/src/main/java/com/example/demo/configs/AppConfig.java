package com.example.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.accounts.Account;
import com.example.demo.accounts.AccountRole;
import com.example.demo.accounts.AccountService;

import java.util.Set;

import org.modelmapper.ModelMapper;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return new ApplicationRunner() {

            @Autowired
            AccountService accountService;

//            @Autowired
//            AppProperties appProperties;

            @Override
            public void run(ApplicationArguments args) throws Exception {
                Account keesun = Account.builder()
                      .email("keesun@email.com")
                      .password("keesun")
                      .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
                      .build();
                accountService.saveAccount(keesun);
//                Account admin = Account.builder()
////                        .email(appProperties.getAdminUsername())
////                        .password(appProperties.getAdminPassword())
//                        .email("keesun@email.com")
//                        .password("keesun")
//                        .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
//                        .build();
//                accountService.saveAccount(admin);
//
//                Account user = Account.builder()
////                        .email(appProperties.getUserUsername())
////                        .password(appProperties.getUserPassword())
//                        .email("")
//                        .password("")
//                        .roles(Set.of(AccountRole.USER))
//                        .build();
//                accountService.saveAccount(user);
            }
        };
    }

}
