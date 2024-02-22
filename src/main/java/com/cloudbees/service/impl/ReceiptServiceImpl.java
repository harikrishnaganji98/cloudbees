package com.cloudbees.service.impl;

import com.cloudbees.datahelper.Storage;
import com.cloudbees.enums.ReceiptStatus;
import com.cloudbees.model.Receipt;
import com.cloudbees.service.ReceiptService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReceiptServiceImpl implements ReceiptService {

    @Override
    public Receipt getReceiptById(String id) {
        return Storage.receipts.get(id);
    }

    @Override
    public void saveReceipt(Receipt receipt) {
        Storage.receipts.put(receipt.getId(), receipt);
    }

    @Override
    public Optional<Receipt> getReceiptByUserId(String userId) {
        return Storage.receipts.values().stream().
                filter(key -> key.getUser().getId().equals(userId) && key.getStatus().equals(ReceiptStatus.SUCCESS)).findAny();
    }
}
