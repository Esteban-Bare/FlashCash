package com.example.flascash.repositories;

import com.example.flascash.entities.Transaction;
import com.example.flascash.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Procedure(name = "findByUser")
    List<Transaction> findByUser(Long user_id);
}
