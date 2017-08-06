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

import com.snv.common.CrudService;
import com.snv.guard.Profile;
import com.snv.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

/**
 * Class To test the Account service
 */
@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @InjectMocks
    private final AccountService AccountService = new AccountServiceImpl();

    @Mock
    private CrudService<Account> crudService;

    private Account account;

    @Before
    public void setUp() {
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
    public void should_return_Account_with_information() {
        Mockito.when(this.crudService.create(Matchers.any(Account.class))).thenReturn(this.account);
        Account actual = this.AccountService.create(this.account);
        verify(this.crudService, Mockito.times(1)).create(Matchers.any(Account.class));
        this.match(actual);
    }

    @Test
    public void should_return_Account_list_when_call_getAll() {
        Mockito.when(this.crudService.getAll()).thenReturn(Arrays.asList(this.account));
        List<Account> actual = this.AccountService.getAll();
        verify(this.crudService, Mockito.times(1)).getAll();
        assertFalse(actual.isEmpty());
        assertTrue(actual.size() == 1);
        this.match(actual.get(0));
    }

    @Test
    public void should_return_udpated_Account_when_call_put() {
        Mockito.when(this.crudService.update(Matchers.any(Account.class))).thenReturn(account);
        Account actual = this.AccountService.put(this.account);
        verify(this.crudService, Mockito.times(1)).update(Matchers.any(Account.class));
        this.match(actual);
    }

    @Test
    public void should_return_true_when_delete_Account() {
        Mockito.when(this.crudService.delete(Matchers.anyInt())).thenReturn(Boolean.TRUE);
        boolean actual = this.AccountService.delete(this.account.getId());
        verify(this.crudService, Mockito.times(1)).delete(Matchers.anyInt());
        assertTrue(actual);
    }

    private void match(Account actual) {
        assertNotNull("Account created can not be null !", actual);
        assertEquals("Accounts not same !", actual, this.account);
        assertEquals("Account's id is not same", actual.getId(), this.account.getId());
        assertEquals("Account's Bank address is not same", actual.getBankAddress(), this.account.getBankAddress());
        assertEquals("Account's bank desk number is not same", actual.getBankDeskNumber(), this.account.getBankDeskNumber());
        assertEquals("Account's bank number is not same", actual.getBankNumber(), this.account.getBankNumber());
        assertEquals("Account's account number is not same", actual.getAccountNumber(), this.account.getAccountNumber());
        assertEquals("Account's bank name is not same", actual.getBankName(), this.account.getBankName());
        assertEquals("Account's bank website is not same", actual.getBankWebSite(), this.account.getBankWebSite());
        assertEquals("Account's currency is not same", actual.getCurrency(), this.account.getCurrency());
    }
}
