package com.example.game2048.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.game2048.R;
import com.example.game2048.model.TopRank;

import java.util.ArrayList;

public class TopRankArrayAdapter extends ArrayAdapter<TopRank> {
    private Context context;
    private ArrayList<TopRank> dataset;
    public TopRankArrayAdapter(@NonNull Context context, int resource, @NonNull ArrayList<TopRank> objects) {
        super(context, resource, objects);
        this.context = context;
        this.dataset = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.rank_item,null);
        }

        if (dataset.size() > 0){
            TextView rank = convertView.findViewById(R.id.ranking_stt);
            TextView name = convertView.findViewById(R.id.ranking_name);
            TextView score = convertView.findViewById(R.id.ranking_score);

            rank.setText(String.valueOf(dataset.get(position).getRank()));
            name.setText(String.valueOf(dataset.get(position).getDisplayName()));
            score.setText(String.valueOf(dataset.get(position).getScore()));
        }

        return convertView;
    }
}
