package com.ifs.iconizer.web.rest;

import foundation.icon.icx.Contract;
import foundation.icon.icx.IconService;
import foundation.icon.icx.data.TransactionResult;
import foundation.icon.icx.transport.jsonrpc.HttpProvider;
import foundation.icon.icx.transport.jsonrpc.RpcObject;
import foundation.icon.icx.transport.jsonrpc.RpcValue;
import score.Address;
import score.Context;
import score.DictDB;
import score.VarDB;

public class TokenContract extends Contract {

    // Variables to store token information
    private final VarDB<String> name = Context.newVarDB("name", String.class);
    private final VarDB<String> symbol = Context.newVarDB("symbol", String.class);
    private final VarDB<Integer> decimals = Context.newVarDB("decimals", Integer.class);
    private final VarDB<Address> owner = Context.newVarDB("owner", Address.class);
    private final VarDB<Integer> totalSupply = Context.newVarDB("totalSupply", Integer.class);

    // Database to store token balances
    private final DictDB<Address, Integer> balances = Context.newDictDB("balances", Integer.class);

    public TokenContract(RpcObject rpcObject, IconService iconService) {
        super(rpcObject);
    }

    // Method to create a new token with a given name, symbol, and decimals
    public void createToken(String _name, String _symbol, int _decimals, int initialSupply) {
        // Check if the caller is the contract owner
        if (!Context.getCaller().equals(Context.getOwner())) {
            Context.revert("Only the contract owner can create a new token.");
        }

        // Set token information
        name.set(_name);
        symbol.set(_symbol);
        decimals.set(_decimals);
        owner.set(Context.getCaller());
        totalSupply.set(initialSupply);

        // Mint initial supply to the contract owner
        balances.set(owner.get(), initialSupply);
    }

    // Method to get the name of the token
    public String getName() {
        return name.get();
    }

    // Method to get the symbol of the token
    public String getSymbol() {
        return symbol.get();
    }

    // Method to get the number of decimals of the token
    public int getDecimals() {
        return decimals.get();
    }

    // Method to get the total supply of the token
    public int getTotalSupply() {
        return totalSupply.get();
    }

    // Method to get the balance of a given address
    public int getBalance(Address _address) {
        return balances.get(_address);
    }

    // Method to transfer tokens from the sender to a given address
    public void transfer(Address _to, int _value) {
        // Check if the sender has sufficient balance
        if (balances.get(Context.getCaller()) < _value) {
            Context.revert("Insufficient balance.");
        }

        // Transfer tokens to the given address
        balances.set(Context.getCaller(), balances.get(Context.getCaller()) - _value);
        balances.set(_to, balances.get(_to) + _value);
    }
}


