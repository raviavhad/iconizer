package com.ifs.iconizer.web.rest;

import foundation.icon.icx.Contract;
import foundation.icon.icx.IconService;
import foundation.icon.icx.data.TransactionResult;
import foundation.icon.icx.transport.jsonrpc.HttpProvider;
import foundation.icon.icx.transport.jsonrpc.RpcObject;
import foundation.icon.icx.transport.jsonrpc.RpcValue;
import score.Address;
import score.Context;

public class FundTransferContract extends Contract {

    public FundTransferContract(RpcObject rpcObject, IconService iconService) {
        super(rpcObject);
    }

    // Method to transfer funds from the contract to a given address
    public void transfer(Address to, long value) {
        // Check if the caller is the contract owner
        if (!Context.getCaller().equals(Context.getOwner())) {
            Context.revert("Only the contract owner can transfer funds.");
        }

        // Check if the contract balance is sufficient
        if (Context.getBalance().compareTo(value) < 0) {
            Context.revert("Insufficient contract balance.");
        }

        // Transfer funds to the given address
        Context.transfer(to, value);
    }
}
