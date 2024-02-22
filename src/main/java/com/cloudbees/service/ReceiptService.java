package com.cloudbees.service;

import com.cloudbees.model.Receipt;

public interface ReceiptService {

    public Receipt getReceiptById(String id);

    void saveReceipt(Receipt receipt);
}
