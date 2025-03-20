package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;
    
    public AccountService() {
        this.accountDAO = new AccountDAO();
    }

    public Account registerUser(Account account) {
        if (account.getUsername().isEmpty() || account.getPassword().length()<4) {
            return null;
        }
        return accountDAO.registerUser(account);
    }

    public Account loginUser(Account account) {
        return accountDAO.loginUser(account);
    }
}
