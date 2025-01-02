package com.example.flascash.controller;

import com.example.flascash.entities.Account;
import com.example.flascash.entities.Transaction;
import com.example.flascash.entities.User;
import com.example.flascash.service.AccountService;
import com.example.flascash.service.SessionService;
import com.example.flascash.service.TransactionService;
import com.example.flascash.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private SessionService sessionService;

    @GetMapping(path = "/send/{friendId}")
    public String sendTransaction(@PathVariable Long friendId, Model model) {
        User friend = userService.findById(friendId);
        List<Account> friendAccounts = friend.getAccounts();
        List<Account> userAccounts = sessionService.sessionUser().getAccounts();
        model.addAttribute("friendAccounts", friendAccounts);
        model.addAttribute("userAccounts", userAccounts);
        model.addAttribute("transaction", new Transaction());
        return "send-transaction";
    }

    @PostMapping(path = "/send")
    public String processTransaction(@ModelAttribute Transaction transaction) {
        transaction.setDate(LocalDateTime.now());
        accountService.addMoney(transaction.getAmount(), transaction.getReceiverAccount());
        accountService.removeMoney(transaction.getAmount(), transaction.getSenderAccount());
        transactionService.save(transaction);
        return "redirect:/";
    }
}
