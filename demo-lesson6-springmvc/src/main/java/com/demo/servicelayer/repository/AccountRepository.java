package com.demo.servicelayer.repository;

import com.demo.servicelayer.model.Account;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AccountRepository {

    private final List<Account> accounts = new ArrayList<>();

    public Account findByName(String name) {
        return accounts.stream()
                .filter(account -> name.equals(account.getUserName()))
                .findFirst()
                .orElse(null);
    }

    public void save(Account account) {
        accounts.add(account);
    }
}
