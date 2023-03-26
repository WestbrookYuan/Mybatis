package com.yty.bank.web;


import com.yty.bank.exceptions.MoneyNotEnoughException;
import com.yty.bank.exceptions.TransferException;
import com.yty.bank.service.AccountService;
import com.yty.bank.service.serviceImpl.AccountServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet(urlPatterns = {"/transfer"})
public class AccountServlet extends HttpServlet {
    //can be used in other method
    private AccountService accountService = new AccountServiceImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fromActno = request.getParameter("fromActno");
        String toActno = request.getParameter("toActno");
        double money = Double.parseDouble(request.getParameter("money"));
        try {
            accountService.transfer(fromActno, toActno, money);
            response.sendRedirect(request.getContextPath()+"/success.html");
        } catch (MoneyNotEnoughException e) {
            response.sendRedirect(request.getContextPath()+"/moneyNotEnough.html");
        } catch (TransferException e) {
            response.sendRedirect(request.getContextPath()+"/error.html");
        } catch (Exception e){
            response.sendRedirect(request.getContextPath()+"/error.html");
        }

    }
}

