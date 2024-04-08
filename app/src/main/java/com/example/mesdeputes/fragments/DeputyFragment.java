package com.example.mesdeputes.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.mesdeputes.R;
import com.example.mesdeputes.models.Deputy;
import com.example.mesdeputes.models.Vote;
import com.example.mesdeputes.models.VoteAdapter;
import com.example.mesdeputes.services.ApiServices;
import com.example.mesdeputes.services.VoteObserver;
import java.util.ArrayList;
import java.util.Objects;

public class DeputyFragment extends Fragment implements VoteObserver {

    private ImageView imageView, imageViewTwitter, imageViewFacebook, imageViewInstagram;
    private ArrayList<Vote> votes;
    private VoteAdapter adapterVote;
    private ListView listViewVote;
    private TextView textViewName, textViewCirco, textViewEmail, textViewGroupe, textViewDateNaissance, textViewLieuNaissance, textViewSexe;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View v = inflater.inflate(R.layout.fragment_deputy, null);
        super.onCreate(savedInstanceState);
        textViewName = v.findViewById(R.id.textViewDeputyName);
        textViewCirco = v.findViewById(R.id.textViewDeputyCirco);
        textViewGroupe = v.findViewById(R.id.textViewDeputyGroupe);
        textViewEmail = v.findViewById(R.id.textViewDeputyEmail);
        textViewDateNaissance = v.findViewById(R.id.textViewDeputyDateNaissance);
        textViewLieuNaissance = v.findViewById(R.id.textViewDeputyLieuNaissance);
        textViewSexe = v.findViewById(R.id.textViewDeputySexe);
        imageViewTwitter = v.findViewById(R.id.imageViewTwitter);
        imageViewFacebook = v.findViewById(R.id.imageViewFacebook);
        imageViewInstagram = v.findViewById(R.id.imageViewInsta);
        imageView = v.findViewById(R.id.imageViewDeputy);
        listViewVote = v.findViewById(R.id.listViewVote);
        votes = new ArrayList<>();
        adapterVote = new VoteAdapter(votes, getContext());
        listViewVote.setAdapter(adapterVote);
        return v;
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
    public void onSelectDeputy(Deputy deputy) {
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
        ApiServices.loadDeputyAvatar(getContext(), deputy.getNameForAvatar(), imageView);
        ApiServices.callVote(getContext(), deputy.getFirstname().replace(" ", "-")+'-'+deputy.getLastname().replace(" ", "-"), this);
    }
}
