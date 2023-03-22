package com.ifs.iconizer.web.rest;

import foundation.icon.icx.*;
import foundation.icon.icx.data.*;
import score.*;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class Voting extends Score {

    private final Map<String, BigInteger> votes;

    public Voting(IconService iconService, Address address) {
        super(iconService, address);
        votes = new HashMap<>();
    }

    @Payable
    public void vote(String candidateName) {
        BigInteger count = votes.get(candidateName);
        if (count == null) {
            count = BigInteger.ZERO;
        }
        count = count.add(BigInteger.ONE);
        votes.put(candidateName, count);
    }

    @External(readonly=true)
    public BigInteger getVoteCount(String candidateName) {
        BigInteger count = votes.get(candidateName);
        if (count == null) {
            count = BigInteger.ZERO;
        }
        return count;
    }
}

