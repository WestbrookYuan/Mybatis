package com.yty.bank.dao;


import com.yty.bank.pojo.Account;

/**
 * @author yty
 * @version 1.0
 * @since 1.0
 * responsible for data from t_act's crud
 */
public interface AccountDao {
    /**
     *
     * @param actno
     * @return account info
     */
    Account selectByActno(String actno);

    /**
     * update database
     * @return
     */
    int updateByActno(Account act);
}
