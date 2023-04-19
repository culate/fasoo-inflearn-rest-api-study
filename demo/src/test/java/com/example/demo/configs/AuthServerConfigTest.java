package com.example.demo.configs;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Set;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.accounts.Account;
import com.example.demo.accounts.AccountRole;
import com.example.demo.accounts.AccountService;
import com.example.demo.common.BaseTest;

public class AuthServerConfigTest extends BaseTest {

    @Autowired
    AccountService accountService;

//    @Autowired
//    AppProperties appProperties;

    @Test
    @DisplayName("인증 토큰을 발급 받는 테스트")
    public void getAuthToken() throws Exception {
//        this.mockMvc.perform(post("/oauth/token")
//                .with(httpBasic(appProperties.getClientId(), appProperties.getClientSecret()))
//                .param("username", appProperties.getUserUsername())
//                .param("password", appProperties.getUserPassword())
//                .param("grant_type", "password"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("access_token").exists());
    	
    	String username = "keeun@emake.com"; 
    	String password = "keesun";
    	Account keesun = Account.builder()
    			.email(username)
    			.password(password)
    			.roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
    			.build();
    	this.accountService.saveAccount(keesun);
    	
    	String clientId = "myApp";
    	String clientSecret = "pass";
    	this.mockMvc.perform(post("/oauth/token")
    			.with(httpBasic(clientId, clientSecret))
    			.param("username", username)
    			.param("password", password)
    			.param("grant_type", "password"))
    		.andDo(print())
    		.andExpect(status().isOk())
    		.andExpect(jsonPath("access_token").exists());
    }

}
