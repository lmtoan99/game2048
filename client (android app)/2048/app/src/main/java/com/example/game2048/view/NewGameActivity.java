package com.example.game2048.view;

import java.util.Random;

public class NewGameActivity extends GamingActivity {
    Random random = new Random();
    /*@Override
    protected int newGame(){
        app = (MyApplication) getApplication();
        sharedPreferences = getSharedPreferences("DataGame", Context.MODE_PRIVATE);
        builder = new AlertDialog.Builder(this);
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
        return 0;
    }*/
}
