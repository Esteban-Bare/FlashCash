package com.example.flascash.service;

import com.example.flascash.entities.Transaction;
import com.example.flascash.entities.User;
import com.example.flascash.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Transactional
    public List<Transaction> findByUser(Long userId) {
        return transactionRepository.findByUser(userId);
    }
}
