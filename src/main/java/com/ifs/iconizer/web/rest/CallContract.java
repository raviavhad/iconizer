
package com.ifs.iconizer.web.rest;

import foundation.icon.icx.Call;
import foundation.icon.icx.IconService;
import foundation.icon.icx.data.Address;
import foundation.icon.icx.data.Bytes;
import foundation.icon.icx.data.TransactionResult;
import foundation.icon.icx.transport.jsonrpc.HttpProvider;
import foundation.icon.icx.transport.jsonrpc.Request;
import foundation.icon.icx.transport.jsonrpc.Response;
import foundation.icon.icx.transport.jsonrpc.RpcItem;
import foundation.icon.icx.transport.jsonrpc.RpcObject;
import score.Address;

import java.math.BigInteger;

public class CallContract {

    // IconService object to interact with the Icon network
    private static final IconService iconService = new IconService(new HttpProvider("https://bicon.net.solidwallet.io/api/v3"));

    // Address of the smart contract to call
    private static final Address contractAddress = new Address("0x2d4dd49ca6b0ad6c5f5d5a580fb5e5bde76b518d");

    public static void main(String[] args) throws Exception {

        // Call the "createToken" method of the smart contract
        RpcObject params = new RpcObject.Builder()
                .put("_name", new RpcItem("My Token"))
                .put("_symbol", new RpcItem("MYT"))
                .put("_decimals", new RpcItem(BigInteger.valueOf(18)))
                .put("initialSupply", new RpcItem(BigInteger.valueOf(1000000)))
                .build();

        Call<RpcObject> call = new Call.Builder()
                .from(new Address("hxe7af5fcfd8dfc67530a01a0e403882687528df3b"))
                .to(contractAddress)
                .method("createToken")
                .params(params)
                .build();

        RpcObject result = iconService.call(call).execute();
        TransactionResult txResult = new TransactionResult(result);

        if (!txResult.getStatus().equals("0x1")) {
            throw new Exception("Transaction failed: " + txResult.getFailure());
        }

        System.out.println("Token created successfully!");
    }
}


