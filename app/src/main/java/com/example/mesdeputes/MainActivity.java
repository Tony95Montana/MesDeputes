package com.example.mesdeputes;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mesdeputes.fragments.DeputyFragment;
import com.example.mesdeputes.fragments.SearchFragment;
import com.example.mesdeputes.models.Deputy;
import com.example.mesdeputes.services.SearchObserver;

public class MainActivity extends AppCompatActivity implements SearchObserver {
    private SearchFragment searchFragment;
    private DeputyFragment deputyFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchFragment = new SearchFragment();
        searchFragment.setListener(this);
        deputyFragment = new DeputyFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frameLayout, searchFragment)
                .add(R.id.frameLayout, deputyFragment)
                .hide(deputyFragment).commit();
    }

    @Override
    public void onReceiveDeputyInfo(Deputy deputy) {
        deputyFragment.onSelectDeputy(deputy);
        getSupportFragmentManager().beginTransaction().hide(searchFragment).show(deputyFragment).commit();
    }
}
