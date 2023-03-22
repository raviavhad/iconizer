package com.ifs.iconizer.web.rest;

import foundation.icon.icx.Contract;
import foundation.icon.icx.IconService;
import foundation.icon.icx.data.TransactionResult;
import foundation.icon.icx.transport.jsonrpc.HttpProvider;
import score.Address;
import score.Context;
import score.DictDB;
import score.VarDB;

public class NFTContract extends Contract {

    // Variables to store NFT information
    private final VarDB<Address> owner = Context.newVarDB("owner", Address.class);
    private final VarDB<Integer> totalSupply = Context.newVarDB("totalSupply", Integer.class);

    // Database to store NFTs
    private final DictDB<String, Integer> nftDb = Context.newDictDB("nftDb", Integer.class);

    public NFTContract(Address address) {
        super(address);
    }

    // Method to create a new NFT with a given token ID
    public void createNFT(int tokenId) {
        // Check if NFT with this token ID already exists
        if (nftDb.get(Integer.toString(tokenId)) != null) {
            Context.revert("NFT with this token ID already exists.");
        }

        // Set NFT information
        owner.set(Context.getCaller());
        totalSupply.set(1);
        nftDb.set(Integer.toString(tokenId), 1);
    }

    // Method to get the owner of an NFT with a given token ID
    public Address getOwner(int tokenId) {
        // Check if NFT with this token ID exists
        if (nftDb.get(Integer.toString(tokenId)) == null) {
            Context.revert("NFT with this token ID does not exist.");
        }

        return owner.get();
    }

    // Method to get the total supply of an NFT with a given token ID
    public int getTotalSupply(int tokenId) {
        // Check if NFT with this token ID exists
        if (nftDb.get(Integer.toString(tokenId)) == null) {
            Context.revert("NFT with this token ID does not exist.");
        }

        return totalSupply.get();
    }
}

