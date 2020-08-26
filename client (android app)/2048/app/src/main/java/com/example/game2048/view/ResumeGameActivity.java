package com.example.game2048.view;

public class ResumeGameActivity extends GamingActivity {
    /*@Override
    protected int newGame() {
        app = (MyApplication) getApplication();
        sharedPreferences = getSharedPreferences("DataGame", Context.MODE_PRIVATE);
        builder = new AlertDialog.Builder(this);

        String savedString = sharedPreferences.getString("saveGame", "");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        if (savedString.equals("")){
            newGame();
            return 0;
        }
        StringTokenizer st = new StringTokenizer(savedString, ",");
        score = Integer.parseInt(st.nextToken());
        for (int i = 0; i < __SIZE__; i++) {
            dataset.add(Integer.parseInt(st.nextToken()));
        }
        if (score > app.high_score){
            app.high_score = score;
        }
        return score;
    }*/
}
