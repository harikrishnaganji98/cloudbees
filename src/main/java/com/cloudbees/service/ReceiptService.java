package com.cloudbees.service;

import com.cloudbees.model.Receipt;

import java.util.Optional;

public interface ReceiptService {

    public Receipt getReceiptById(String id);

    void saveReceipt(Receipt receipt);

    public Optional<Receipt> getReceiptByUserId(String userId);
}
