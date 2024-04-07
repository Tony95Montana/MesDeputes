package com.example.mesdeputes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Objects;

public class DeputyActivity extends AppCompatActivity implements VoteObserver {

    private ImageView imageView, imageViewTwitter, imageViewFacebook, imageViewInstagram;
    private ArrayList<Vote> votes;
    private VoteAdapter adapterVote;
    private ListView listViewVote;
    private TextView textViewName, textViewCirco, textViewEmail, textViewGroupe, textViewDateNaissance, textViewLieuNaissance, textViewSexe;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deputy);
        textViewName = findViewById(R.id.textViewDeputyName);
        textViewCirco = findViewById(R.id.textViewDeputyCirco);
        textViewGroupe = findViewById(R.id.textViewDeputyGroupe);
        textViewEmail = findViewById(R.id.textViewDeputyEmail);
        textViewDateNaissance = findViewById(R.id.textViewDeputyDateNaissance);
        textViewLieuNaissance = findViewById(R.id.textViewDeputyLieuNaissance);
        textViewSexe = findViewById(R.id.textViewDeputySexe);
        imageViewTwitter = findViewById(R.id.imageViewTwitter);
        imageViewFacebook = findViewById(R.id.imageViewFacebook);
        imageViewInstagram = findViewById(R.id.imageViewInsta);
        imageView= findViewById(R.id.imageViewDeputy);
        listViewVote = findViewById(R.id.listViewVote);
        votes = new ArrayList<>();
        adapterVote = new VoteAdapter(votes, this);
        listViewVote.setAdapter(adapterVote);
    }
    @Override
    public void onReceiveVoteDeputy(Vote vote) {
        if(!votes.contains(vote)){
            votes.add(vote);
            adapterVote.setVotes(votes);
            adapterVote.notifyDataSetChanged();
        }
    }
    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();
        Deputy deputy = (Deputy) getIntent().getSerializableExtra("deputy");
        textViewName.setText(Objects.requireNonNull(deputy).getFirstname()+" "+deputy.getLastname());
        textViewCirco.setText(deputy.getDepartment()+", "+
                deputy.getNameCirco()+ " "+ deputy.getNumCirco()+
                (deputy.getNumCirco()==1? "er": "eme")+" circoncription");
        textViewGroupe.setText(deputy.getGroupe());
        textViewEmail.setText(deputy.getEmail());
        textViewDateNaissance.setText(deputy.getDateNaissance());
        textViewLieuNaissance.setText(deputy.getLieuNaissance());
        textViewSexe.setText(deputy.getSexe());
        if (deputy.getTwitter() != null) imageViewTwitter.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(deputy.getTwitter()));
            startActivity(browserIntent);
        });
        else {
            imageViewTwitter.setImageResource(R.color.white);
        }
        if (deputy.getInstagram() != null) imageViewInstagram.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(deputy.getInstagram()));
            startActivity(browserIntent);
        });
        else {
            imageViewInstagram.setImageResource(R.color.white);
        }
        if (deputy.getFacebook() != null) imageViewFacebook.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(deputy.getFacebook()));
            startActivity(browserIntent);
        });
        else {
            imageViewFacebook.setImageResource(R.color.white);
        }
        ApiServices.loadDeputyAvatar(this, deputy.getNameForAvatar(), imageView);
        ApiServices.callVote(this, deputy.getFirstname().replace(" ", "-")+'-'+deputy.getLastname().replace(" ", "-"), this);
    }
}
