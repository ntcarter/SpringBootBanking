package com.carter.boot.banking.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.carter.boot.banking.entity.Account;
import com.carter.boot.banking.entity.Transaction;
import com.carter.boot.banking.service.AccountService;

@Controller
@RequestMapping("/api")
public class BankingController {

	private AccountService accountService;
	
	@Autowired
	public BankingController(AccountService theAccountService) {
		accountService = theAccountService;
	}
	
	// expose API
	@GetMapping("/accounts")
	public String findAll(Model theModel) {
		
		List<Account> accountList = accountService.findAll();
		theModel.addAttribute("accounts", accountList);
		return "accounts/accounts-list";
	}
	
	@GetMapping("/openAccountForm")
	public String openAccountForm(Model theModel) {
		Account newAccount = new Account();
		theModel.addAttribute("account", newAccount);
		return "accounts/open-account-form";
	}
	
	@GetMapping("/accounts/{accountId}")
	public Account getSingleAccount(@PathVariable int accountId) {
		Account theAccount = accountService.findById(accountId);
		// user id might not exist
		if(theAccount == null) {
			throw new RuntimeException("User id not found: " + accountId);
		}
		return theAccount;
	}
	
	@GetMapping("/updateAccount")
	public String updateAccount(@RequestParam("accountId") int theId, Model theModel) {
		
		Account theAccount = accountService.findById(theId);
		theModel.addAttribute(theAccount);
		
		return "accounts/open-Account-Form";
	}
	
	@PostMapping("/saveAccount")
	public String addAccount(@ModelAttribute("account") Account theAccount) {
		accountService.save(theAccount);
		return "redirect:/api/accounts";
	}
	
	@GetMapping("/accountTransaction")
	public String accountTransaction(@RequestParam("accountId") int theId, Model theModel) {
		
		Transaction theTransaction = new Transaction(0);
		theTransaction.setAccountId(theId);
		theModel.addAttribute("transaction", theTransaction);
		
		return "accounts/account-transaction-form";
	}
	
	@PostMapping("/saveTransaction")
	public String saveTransaction(@ModelAttribute("transaction") Transaction theTransaction) {
		// set account id to 0 to force a save 
		
		// get the account corresponding to the transaction
		Account theAccount = accountService.findById(theTransaction.getAccountId());
		
		// account not found
		if(theAccount == null) {
			return "redirect:/api/accounts";
		}
		
		theAccount.setBalance(theAccount.getBalance() + theTransaction.getTransactionAmount());
		theTransaction.setTransactionId(0);
		accountService.saveTransaction(theTransaction);
		accountService.save(theAccount);
		return "redirect:/api/accounts";
	}
	
	
	@PutMapping("/accounts")
	public Account updateAccount(@RequestBody Account theAccount) {
		accountService.save(theAccount);
		return theAccount;
	}
	
	@GetMapping("/deleteAccount")
	public String deleteAccount(@RequestParam("accountId") int accountId) {
		Account theAccount = accountService.findById(accountId);

		// check null
		// throw exception if null
		if (theAccount == null) {
			throw new RuntimeException("Account id not found - " +theAccount);
		}
		
		accountService.deleteById(accountId);
		return "redirect:/api/accounts";
	}
}
