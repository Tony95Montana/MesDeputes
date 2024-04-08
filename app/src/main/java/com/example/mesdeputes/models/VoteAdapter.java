package com.example.mesdeputes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class VoteAdapter extends BaseAdapter {

    private ArrayList<Vote> votes;
    private final Context context;

    public VoteAdapter(ArrayList<Vote> votes, Context context) {
        this.votes = votes;
        this.context = context;
    }

    public void setVotes(ArrayList<Vote> votes) {
        this.votes = votes;
    }

    @Override
    public int getCount() {
        return votes.size();
    }

    @Override
    public Object getItem(int position) {
        return votes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return votes.get(position).getId();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Vote vote = votes.get(position);
        if (convertView == null) convertView = LayoutInflater.from(context).inflate(R.layout.item_vote, parent, false);
        TextView textViewTitre = convertView.findViewById(R.id.textViewTitre);
        textViewTitre.setText(vote.getTitre());
        TextView textViewDescription = convertView.findViewById(R.id.textViewDescription);
        String color = "grey";
        if (vote.getPosition().equals("pour")) color = "#008000";
        else if (vote.getPosition().equals("contre")) color = "red";
        String description = "La proposition de loi demander par <strong>"+vote.getDemandeur()+"</strong> le "+vote.getDate()+" à été voté <strong><font color=\""+color+"\">"+vote.getPosition().toUpperCase()+"</font></strong> par ce Député.<br>"+
                "Vote pour : "+vote.getNombrePours() +" contre "+ vote.getNombreContres() +" votes ("+ vote.getNombreVotants() +" votes).";
        textViewDescription.setText(Html.fromHtml(description, Html.FROM_HTML_MODE_COMPACT));
        ImageView imageViewPosition = convertView.findViewById(R.id.imageViewPosition);
        Log.println(Log.INFO, null, vote.getPosition());
        if (vote.getSort().equals("adopté")) imageViewPosition.setImageResource(R.mipmap.loi_green);
        else if (vote.getSort().equals("rejeté")) imageViewPosition.setImageResource(R.mipmap.loi_red);
        return convertView;
    }
}
