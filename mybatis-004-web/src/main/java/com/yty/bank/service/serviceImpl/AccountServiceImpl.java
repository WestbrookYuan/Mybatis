package com.yty.bank.service.serviceImpl;

import com.yty.bank.dao.AccountDao;
import com.yty.bank.dao.Impl.AccountDaoImpl;
import com.yty.bank.exceptions.MoneyNotEnoughException;
import com.yty.bank.exceptions.TransferException;
import com.yty.bank.pojo.Account;
import com.yty.bank.service.AccountService;
import com.yty.bank.utils.GenerateDaoProxy;
import com.yty.bank.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;

public class AccountServiceImpl implements AccountService {

//    private AccountDao accountDao = new AccountDaoImpl();
//    private AccountDao accountDao = (AccountDao) GenerateDaoProxy.generate(AccountDao.class, SqlSessionUtil.openSession());

    private AccountDao accountDao = SqlSessionUtil.openSession().getMapper(AccountDao.class);


    @Override
    public void transfer(String fromActno, String toActno, double money) throws MoneyNotEnoughException, TransferException {
        SqlSession sqlSession = SqlSessionUtil.openSession();

        // 1. balance enough
        Account fromAct = accountDao.selectByActno(fromActno);
        // 2. if not enough: alert
        if (fromAct.getBalance() < money){
            throw new MoneyNotEnoughException("not enough exception");
        }
        // 3. update balance
        Account toAct = accountDao.selectByActno(toActno);
        toAct.setBalance(toAct.getBalance() + money);
        fromAct.setBalance(fromAct.getBalance() - money);
        int count = accountDao.updateByActno(fromAct);
//        String s = null;
//        s.toString();

        count += accountDao.updateByActno(toAct);
        if (count != 2){
            throw new TransferException("error");
        }
        sqlSession.commit();
        SqlSessionUtil.close(sqlSession);


    }
}
