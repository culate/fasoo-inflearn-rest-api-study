package com.example.demo.accounts;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Set;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.common.BaseTest;

public class AccountServiceTest extends BaseTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
    @Autowired
    AccountService accountService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void findByUsername() {
        // Given
        String password = "culate";
        String username = "culate@email.com";
        Account account = Account.builder()
                .email(username)
                .password(password)
                .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
                .build();
        this.accountService.saveAccount(account);

        // When
        UserDetailsService userDetailsService = accountService;
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Then
        assertThat(this.passwordEncoder.matches(password, userDetails.getPassword())).isTrue();
    }

    @Test
    public void findByUsernameFail() {
    	String username= "random@email.com";
//    	try {
//    		accountService.loadUserByUsername(username);
//    	} catch (UsernameNotFoundException e) {
//    		//assertThat(e instanceof UsernameNotFoundException).isTrue();
//    		assertThat(e.getMessage()).containsSequence(username);
//    	}
    	
    	expectedException.expect(UsernameNotFoundException.class);
    	expectedException.expectMessage(Matchers.containsString(username));
    	accountService.loadUserByUsername(username);
    	
//        assertThrows(UsernameNotFoundException.class, () -> accountService.loadUserByUsername("random@email.com"));
    }

}
