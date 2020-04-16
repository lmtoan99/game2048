package com.example.game2048;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.game2048.model.Square;

import java.util.ArrayList;

public class SquareArrayAdapter extends ArrayAdapter<Integer> {
    private Context context;
    private ArrayList<Integer> dataset;
    public SquareArrayAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Integer> objects) {
        super(context, resource, objects);
        this.context = context;
        this.dataset = new ArrayList<>(objects);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_square,null);
        }

        if (dataset.size() > 0){
            int color = getColor(dataset.get(position));
            int back_color = getBackgroundColor(dataset.get(position));
            Square square = convertView.findViewById(R.id.textSquare);
            if (dataset.get(position) != 0) {
                square.setText(String.valueOf(dataset.get(position)));
            }else{
                square.setText("");
            }
            GradientDrawable gradientDrawable = (GradientDrawable) square.getBackground();
            gradientDrawable.setColor(color);
            Square background_square = convertView.findViewById(R.id.background_square);
            GradientDrawable gradientDrawable1 = (GradientDrawable) background_square.getBackground();
            gradientDrawable1.setColor(back_color);
        }
        return convertView;
    }

    private int getColor(Integer value){
        TypedArray typedArray = context.getResources().obtainTypedArray(R.array.colorSquare);
        int[] listColor = new int[typedArray.length()];
        for (int i=0;i<listColor.length;i++){
            listColor[i]=typedArray.getColor(i,0);
        }
        typedArray.recycle();
        if (value == 0){
            return Color.WHITE;
        }

        return listColor[(int)(Math.log(value)/Math.log(2))-1];
    }

    private int getBackgroundColor(Integer value){
        TypedArray typedArray = context.getResources().obtainTypedArray(R.array.backgroundColor);
        int[] listColor = new int[typedArray.length()];
        for (int i=0;i<listColor.length;i++){
            listColor[i]=typedArray.getColor(i,0);
        }
        typedArray.recycle();
        if (value == 0){
            return Color.GRAY;
        }

        return listColor[(int)(Math.log(value)/Math.log(2))-1];
    }

    protected void refresh(ArrayList<Integer> newdataset){
        this.dataset = newdataset;
        notifyDataSetChanged();
    }
}
