package com.example.game2048;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

public class MainActivity extends Activity {
    private Context context = this;
    private ArrayList<Integer> dataset = new ArrayList<>();
    private Random random = new Random();
    final Integer __SIZE__ = 16;
    Integer side = (int)Math.sqrt(__SIZE__);
    private GridView boardGame;
    private SquareArrayAdapter adapter;
    private float X, Y;
    private int[][] data_on_board_game = new int[side][side];
    private TextView viewScore;
    private TextView highScore;
    private int highscore;
    private int score = 0;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        setDataset();

        gaming();
    }

    private void newGame(){
        for (int i = 0; i < __SIZE__; i++) {
            dataset.add(0);
        }

        int positionGenerate1 = random.nextInt(__SIZE__);
        dataset.set(positionGenerate1,2);
        int positionGenerate2 = random.nextInt(__SIZE__);
        while (positionGenerate1 == positionGenerate2){
            positionGenerate2 = random.nextInt(__SIZE__);
        }
        dataset.set(positionGenerate2,2);
        score = 0;
    }

    private void resumeGame(){
        String savedString = sharedPreferences.getString("saveGame", "");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        if (savedString.equals("")){
            newGame();
            return;
        }
        StringTokenizer st = new StringTokenizer(savedString, ",");
        score = Integer.parseInt(st.nextToken());
        for (int i = 0; i < __SIZE__; i++) {
            dataset.add(Integer.parseInt(st.nextToken()));
        }
    }

    private void init(){
        boardGame = findViewById(R.id.boardGame);
        viewScore = findViewById(R.id.score);
        highScore = findViewById(R.id.highscore);
        sharedPreferences = getSharedPreferences("DataGame", Context.MODE_PRIVATE);
        highscore = sharedPreferences.getInt("highScore",0);
        highScore.setText(String.valueOf(highscore));
        Intent intent = getIntent();
        int status = intent.getIntExtra("status",0);

        if (status==0){
            newGame();
        }else{
            resumeGame();
        }
        viewScore.setText(String.valueOf(score));
        for (int i=0; i<__SIZE__;i++){
            data_on_board_game[i/side][i%side]=dataset.get(i);
        }
    }

    private void setDataset(){
        adapter = new SquareArrayAdapter(this,0,dataset);
        boardGame.setAdapter(adapter);

    }

    private void autoGenerate() {
        ArrayList<Integer> zeroSquare = new ArrayList<>();
        for (int i = 0; i < dataset.size(); i++) {
            if (dataset.get(i) == 0) {
                zeroSquare.add(i);
            }
        }
        if (zeroSquare.size()==0){
            endGame();
            return;
        }
        int positionGenerate = random.nextInt(zeroSquare.size());
        dataset.set(zeroSquare.get(positionGenerate),2);
        data_on_board_game[zeroSquare.get(positionGenerate)/side][zeroSquare.get(positionGenerate)%side] = 2;
        adapter.refresh(dataset);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void gaming(){
        View.OnTouchListener onTouchListener = new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        X = event.getX();
                        Y = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        boolean flagGenerate;
                        if (Math.abs(event.getX() - X) > Math.abs(event.getY() - Y)){
                            if (event.getX() > X){
                                flagGenerate = gamingRight();
                            }else{
                                flagGenerate = gamingLeft();
                            }
                        }

                        else{
                            if (event.getY() > Y){
                                flagGenerate = gamingDown();
                            }else{
                                flagGenerate = gamingUp();
                            }
                        }
                        if (flagGenerate){
                            adapter.refresh(dataset);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    // Actions to do after 10 seconds
                                    autoGenerate();
                                    if (checkEndGame()){
                                        endGame();
                                    }
                                }
                            }, 100);
                        }
                        break;
                }
                return true;
            }
        };

        boardGame.setOnTouchListener(onTouchListener);
    }

    private boolean gamingUp(){
        boolean flag = false;
        for (int i=0;i<side;i++){
            boolean _continue;
            do {
                _continue = false;
                for (int j = 0; j < side - 1; j++) {
                    if (data_on_board_game[j][i]==0){
                        boolean push = false;
                        for (int k=j+1;k<side;k++){
                            if (data_on_board_game[k][i]!=0){
                                push = true;
                            }
                        }
                        if (push){
                            for (int k=j;k<side-1;k++){
                                data_on_board_game[k][i]=data_on_board_game[k+1][i];
                            }
                            data_on_board_game[side-1][i] = 0;
                            _continue = true;
                            flag = true;
                        }
                    }
                }
            }while(_continue);
            for (int j=0;j<side-1;j++){
                if (data_on_board_game[j][i]==0){
                    continue;
                }
                if (data_on_board_game[j][i] == data_on_board_game[j+1][i]) {
                    flag = true;
                    combine(data_on_board_game[j][i]);
                    data_on_board_game[j][i] *= 2;
                    for (int k=j+1;k<side-1;k++){
                        data_on_board_game[k][i]=data_on_board_game[k+1][i];
                    }
                    data_on_board_game[side-1][i] = 0;
                }
            }
        }
        dataset.clear();
        for (int i=0;i<__SIZE__;i++){
            dataset.add(data_on_board_game[i/side][i%side]);
        }
        return flag;
    }

    private boolean gamingDown(){
        boolean flag = false;
        for (int i=0;i<side;i++){
            boolean _continue;
            do {
                _continue = false;
                for (int j = side - 1; j > 0; j--) {
                    if (data_on_board_game[j][i]==0){
                        boolean push = false;
                        for (int k=j-1;k>=0;k--){
                            if (data_on_board_game[k][i]!=0){
                                push = true;
                            }
                        }
                        if (push){
                            for (int k=j;k>0;k--){
                                data_on_board_game[k][i]=data_on_board_game[k-1][i];
                            }
                            data_on_board_game[0][i] = 0;
                            _continue = true;
                            flag = true;
                        }
                    }
                }
            }while(_continue);
            for (int j=side-1;j>0;j--){
                if (data_on_board_game[j][i]==0){
                    continue;
                }
                if (data_on_board_game[j][i] == data_on_board_game[j-1][i]) {
                    flag = true;
                    combine(data_on_board_game[j][i]);
                    data_on_board_game[j][i] *= 2;
                    for (int k=j-1;k>0;k--){
                        data_on_board_game[k][i]=data_on_board_game[k-1][i];
                    }
                    data_on_board_game[0][i] = 0;
                }
            }
        }
        dataset.clear();
        for (int i=0;i<__SIZE__;i++){
            dataset.add(data_on_board_game[i/side][i%side]);
        }
        return flag;
    }

    private boolean gamingLeft(){
        boolean flag = false;
        for (int i=0;i<side;i++){
            boolean _continue;
            do {
                _continue = false;
                for (int j = 0; j < side - 1; j++) {
                    if (data_on_board_game[i][j]==0){
                        boolean push = false;
                        for (int k=j+1;k<side;k++){
                            if (data_on_board_game[i][k]!=0){
                                push = true;
                            }
                        }
                        if (push){
                            for (int k=j;k<side-1;k++){
                                data_on_board_game[i][k]=data_on_board_game[i][k+1];
                            }
                            data_on_board_game[i][side-1] = 0;
                            _continue = true;
                            flag = true;
                        }
                    }
                }
            }while(_continue);
            for (int j=0;j<side-1;j++){
                if (data_on_board_game[i][j]==0){
                    continue;
                }
                if (data_on_board_game[i][j] == data_on_board_game[i][j + 1]) {
                    flag = true;
                    combine(data_on_board_game[i][j]);
                    data_on_board_game[i][j] *= 2;
                    for (int k=j+1;k<side-1;k++){
                        data_on_board_game[i][k]=data_on_board_game[i][k+1];
                    }
                    data_on_board_game[i][side-1] = 0;
                }
            }
        }
        dataset.clear();
        for (int i=0;i<__SIZE__;i++){
            dataset.add(data_on_board_game[i/side][i%side]);
        }
        return flag;
    }

    private boolean gamingRight(){
        boolean flag = false;
        for (int i=0;i<side;i++){
            boolean _continue;
            do {
                _continue = false;
                for (int j = side - 1; j > 0; j--) {
                    if (data_on_board_game[i][j]==0){
                        boolean push = false;
                        for (int k=j-1;k>=0;k--){
                            if (data_on_board_game[i][k]!=0){
                                push = true;
                            }
                        }
                        if (push){
                            for (int k=j;k>0;k--){
                                data_on_board_game[i][k]=data_on_board_game[i][k-1];
                            }
                            data_on_board_game[i][0] = 0;
                            _continue = true;
                            flag = true;
                        }
                    }
                }
            }while(_continue);
            for (int j=side-1;j>0;j--){
                if (data_on_board_game[i][j]==0){
                    continue;
                }
                if (data_on_board_game[i][j] == data_on_board_game[i][j - 1]) {
                    flag = true;
                    combine(data_on_board_game[i][j]);
                    data_on_board_game[i][j] *= 2;
                    for (int k=j-1;k>0;k--){
                        data_on_board_game[i][k]=data_on_board_game[i][k-1];
                    }
                    data_on_board_game[i][0] = 0;
                }
            }
        }
        dataset.clear();
        for (int i=0;i<__SIZE__;i++){
            dataset.add(data_on_board_game[i/side][i%side]);
        }
        return flag;
    }

    private void combine(int number) {
        score += number;
        viewScore.setText(String.valueOf(score));
        if (score>highscore){
            highscore = score;
            highScore.setText(String.valueOf(highscore));
        }
    }

    private boolean checkEndGame(){
        for (int i=0;i<side-1;i++){
            for (int j=0;j<side-1;j++){
                if (data_on_board_game[i][j]==0){
                    return false;
                }
                if (data_on_board_game[i][j]==data_on_board_game[i][j+1]){
                    return false;
                }
                if (data_on_board_game[i][j]==data_on_board_game[i+1][j]){
                    return false;
                }
            }
            if (data_on_board_game[i][side-1]==0){
                return false;
            }
            if (data_on_board_game[i][side-1]==data_on_board_game[i+1][side-1]){
                return false;
            }
        }
        for (int j=0;j<side-1;j++){
            if (data_on_board_game[side-1][j]==0){
                return false;
            }
            if (data_on_board_game[side-1][j]==data_on_board_game[side-1][j+1]){
                return false;
            }
        }
        if (data_on_board_game[side-1][side-1]==0){
            return false;
        }
        return true;
    }

    private void endGame(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("highScore",highscore);
        editor.apply();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialog_view = inflater.inflate(R.layout.endgame_dialog,null);
        builder.setCancelable(false)
                .setView(dialog_view);
        TextView end_game_msg = dialog_view.findViewById(R.id.endgame_msg);
        end_game_msg.setText("End game!!! Your score is " + score + "!");
        Button new_game_action = dialog_view.findViewById(R.id.new_game_btn);
        new_game_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MainActivity.class);
                intent.putExtra("status",0);
                startActivity(intent);
                finish();
            }
        });
        Button exit_action = dialog_view.findViewById(R.id.exit_btn);
        exit_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        builder.create().show();
    }

    private void saveGame(){
        StringBuilder str = new StringBuilder();
        str.append(score).append(",");
        for (int i = 0; i < __SIZE__; i++) {
            str.append(dataset.get(i)).append(",");
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("saveGame", str.toString());
        editor.putInt("highScore",highscore);
        editor.apply();
    }

    @Override
    protected void onDestroy() {
        saveGame();
        super.onDestroy();
    }
}
