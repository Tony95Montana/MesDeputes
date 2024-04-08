package com.example.mesdeputes.services;

import com.example.mesdeputes.models.Deputy;

public interface SearchObserver {
    public void onReceiveDeputyInfo(Deputy deputy);
}
