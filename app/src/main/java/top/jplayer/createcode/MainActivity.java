package top.jplayer.createcode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import top.jplayer.codelib.BindFieldView;

public class MainActivity extends AppCompatActivity {
    @BindFieldView(id =123123)
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
