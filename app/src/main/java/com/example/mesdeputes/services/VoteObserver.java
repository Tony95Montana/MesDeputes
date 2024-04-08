package com.example.mesdeputes.services;

import com.example.mesdeputes.models.Vote;

public interface VoteObserver {
    public void onReceiveVoteDeputy(Vote vote);
}
