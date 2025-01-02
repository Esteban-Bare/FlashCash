package com.example.flascash.controller;

import com.example.flascash.entities.Account;
import com.example.flascash.entities.Transaction;
import com.example.flascash.entities.User;
import com.example.flascash.service.*;
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
    @Autowired
    private FriendshipService friendshipService;

    @GetMapping(path = "/send/{friendId}")
    public String sendTransaction(@PathVariable Long friendId, Model model) {
        try {
            User friend = userService.findById(friendId);
            List<Account> friendAccounts = friend.getAccounts();
            List<Account> userAccounts = sessionService.sessionUser().getAccounts();

            List<Long> friendIds = friendshipService.getFriendIds(sessionService.sessionUser().getId());
            System.out.println(friendIds);
            if (!friendIds.contains(friendId)) {
                return "redirect:/dashboard";
            }

            model.addAttribute("friendAccounts", friendAccounts);
            model.addAttribute("userAccounts", userAccounts);
            model.addAttribute("transaction", new Transaction());
            return "send-transaction";
        } catch (Exception e) {
            return "redirect:/dashboard";
        }


    }

    @PostMapping(path = "/send")
    public String processTransaction(@ModelAttribute Transaction transaction, Model model) {
        try {
            if (transaction.getSenderAccount().equals(transaction.getReceiverAccount())) {
                throw new Exception("You can't send money to yourself");
            }
            if (transaction.getAmount() <= 0) {
                throw new Exception("Amount must be greater than 0");
            }
            if (transaction.getAmount() > accountService.findById(transaction.getSenderAccount().getId()).getBalance()) {
                throw new Exception("Insufficient funds");
            }
            transaction.setDate(LocalDateTime.now());
            accountService.addMoney(transaction.getAmount(), transaction.getReceiverAccount());
            accountService.removeMoney(transaction.getAmount(), transaction.getSenderAccount());
            transactionService.save(transaction);
            return "redirect:/";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            model.addAttribute("error", e.getMessage());
            model.addAttribute("success", false);
            return "redirect:/transactions/send/" + transaction.getReceiverAccount().getUser().getId();
        }
    }
}
