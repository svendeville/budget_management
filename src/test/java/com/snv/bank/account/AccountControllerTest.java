/*
 * @Copyright 2016 Sylvain Vendeville.
 * This file is part of Budget Management.
 * Budget Management is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Budget Management is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Budget Management. If not, see <http://www.gnu.org/licenses/>.
 */
package com.snv.bank.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snv.guard.AuthenticationService;
import com.snv.guard.Profile;
import com.snv.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest {

    private static final String CONTROLLER_URL = "http://localhost:8080/api/bank/accounts";
    private final ObjectMapper mapper = new ObjectMapper();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;
    @InjectMocks
    private AccountController accountController;
    @Mock
    private AccountService accountService;
    @Mock
    private AuthenticationService authenticationService;
    private Account account;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.accountController)
                .build();
        this.account = new Account();
        this.account.setId(1234L);
        this.account.setBankAddress("bank address");
        this.account.setBankDeskNumber(1234L);
        this.account.setBankNumber(5678L);
        this.account.setAccountNumber(12345678L);
        this.account.setBankName("Test");
        this.account.setBankWebSite("http://www.exemple.com");
        this.account.setCurrency(Currency.getInstance(Locale.FRANCE));
        User user = new User();
        user.setFirstName("toto");
        user.setLastName("tata");
        user.setEmail("toto.tata@lol.com");
        user.setProfile(Profile.ADMIN);
        user.setLogin("test");
        user.setPassword("pass");
        this.account.setUsers(Arrays.asList(user));
    }

    @Test
    public void should_return_BadRequest_when_call_without_account() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(CONTROLLER_URL)
                .content(this.mapper.writeValueAsString(new Account()))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void should_return_BadRequest_when_call_with_empty_account_values() throws Exception {
        Account emptyAccount = new Account();

        emptyAccount.setBankAddress("");
        emptyAccount.setBankName("");
        emptyAccount.setBankWebSite("");

        mockMvc.perform(MockMvcRequestBuilders.post(CONTROLLER_URL)
                .content(this.mapper.writeValueAsString(emptyAccount))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void should_return_account_with_information_on_create() throws Exception {
        when(this.accountService.create(Matchers.any(Account.class))).thenReturn(this.account);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(CONTROLLER_URL + "/create")
                .content(this.mapper.writeValueAsString(account))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();

        verify(this.accountService, times(1)).create(Matchers.any(Account.class));

        assertFalse(result.getResponse().getContentAsString().isEmpty());
        Account actual = objectMapper.readValue(result.getResponse().getContentAsString(), Account.class);
        assertNotNull("account created can not be null !", actual);
        assertEquals("accounts not same !", actual, this.account);
        assertEquals("account's BankAddress name is not same", actual.getBankAddress(), this.account.getBankAddress());
        assertEquals("account's BankDeskNumber name is not same", actual.getBankDeskNumber(), this.account.getBankDeskNumber());
        assertEquals("account's BankNumber name is not same", actual.getBankNumber(), this.account.getBankNumber());
        assertEquals("account's AccountNumber name is not same", actual.getAccountNumber(), this.account.getAccountNumber());
        assertEquals("account's BankName name is not same", actual.getBankName(), this.account.getBankName());
        assertEquals("account's BankWebSite name is not same", actual.getBankWebSite(), this.account.getBankWebSite());
        assertEquals("account's Currency name is not same", actual.getCurrency(), this.account.getCurrency());
        assertEquals("account's User name is not same", actual.getUsers(), this.account.getUsers());
    }

    @Test
    public void should_return_account_with_information_on_update() throws Exception {
        when(this.accountService.put(Matchers.any(Account.class))).thenReturn(this.account);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(CONTROLLER_URL)
                .content(this.mapper.writeValueAsString(account))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();

        verify(this.accountService, times(1)).put(Matchers.any(Account.class));

        assertFalse(result.getResponse().getContentAsString().isEmpty());
        Account actual = objectMapper.readValue(result.getResponse().getContentAsString(), Account.class);
        assertNotNull("account created can not be null !", actual);
        assertEquals("accounts not same !", actual, this.account);
        assertEquals("account's BankAddress name is not same", actual.getBankAddress(), this.account.getBankAddress());
        assertEquals("account's BankDeskNumber name is not same", actual.getBankDeskNumber(), this.account.getBankDeskNumber());
        assertEquals("account's BankNumber name is not same", actual.getBankNumber(), this.account.getBankNumber());
        assertEquals("account's AccountNumber name is not same", actual.getAccountNumber(), this.account.getAccountNumber());
        assertEquals("account's BankName name is not same", actual.getBankName(), this.account.getBankName());
        assertEquals("account's BankWebSite name is not same", actual.getBankWebSite(), this.account.getBankWebSite());
        assertEquals("account's Currency name is not same", actual.getCurrency(), this.account.getCurrency());
        assertEquals("account's User name is not same", actual.getUsers(), this.account.getUsers());
    }

    @Test
    public void should_return_list_of_account_on_getall() throws Exception {
        List<Account> accounts = Arrays.asList(this.account);
        when(this.accountService.getAll()).thenReturn(accounts);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(CONTROLLER_URL)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();

        verify(this.accountService, times(1)).getAll();


        assertFalse(result.getResponse().getContentAsString().isEmpty());
        List<Account> actual = objectMapper.readValue(result.getResponse().getContentAsString(), List.class);

        assertNotNull("List of accounts can not be null !", actual);
        assertFalse("List of accounts can not be empty", actual.isEmpty());
    }

    @Test
    public void should_return_account_on_get() throws Exception {
        when(this.accountService.get(Matchers.anyLong())).thenReturn(this.account);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(CONTROLLER_URL + "/1")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();

        verify(this.accountService, times(1)).get(Matchers.anyLong());


        assertFalse(result.getResponse().getContentAsString().isEmpty());
        Account actual = objectMapper.readValue(result.getResponse().getContentAsString(), Account.class);
        assertNotNull("account created can not be null !", actual);
        assertEquals("accounts not same !", actual, this.account);
        assertEquals("account's BankAddress name is not same", actual.getBankAddress(), this.account.getBankAddress());
        assertEquals("account's BankDeskNumber name is not same", actual.getBankDeskNumber(), this.account.getBankDeskNumber());
        assertEquals("account's BankNumber name is not same", actual.getBankNumber(), this.account.getBankNumber());
        assertEquals("account's AccountNumber name is not same", actual.getAccountNumber(), this.account.getAccountNumber());
        assertEquals("account's BankName name is not same", actual.getBankName(), this.account.getBankName());
        assertEquals("account's BankWebSite name is not same", actual.getBankWebSite(), this.account.getBankWebSite());
        assertEquals("account's Currency name is not same", actual.getCurrency(), this.account.getCurrency());
        assertEquals("account's User name is not same", actual.getUsers(), this.account.getUsers());
    }

    @Test
    public void should_return_boolean_true_on_delete_success() throws Exception {
        when(this.accountService.delete(Matchers.anyLong())).thenReturn(Boolean.TRUE);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(CONTROLLER_URL + "/" + this.account.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();

        verify(this.accountService, times(1)).delete(Matchers.anyLong());


        Boolean actual = objectMapper.readValue(result.getResponse().getContentAsString(), Boolean.class);
        assertNotNull("result on delete can not be null !", actual);
        assertTrue("result on delete is not true !", actual);
    }
}
