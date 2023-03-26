package com.yty.bank.service;

import com.yty.bank.exceptions.MoneyNotEnoughException;
import com.yty.bank.exceptions.TransferException;

/**
 * @author yty
 * @version 1.0
 * @since 1.0
 */
public interface AccountService {
    /**
     *
     * @param fromActno
     * @param toActno
     * @param money
     */
    void transfer(String fromActno, String toActno, double money) throws MoneyNotEnoughException, TransferException;
}
