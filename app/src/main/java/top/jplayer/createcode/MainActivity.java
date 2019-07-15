package top.jplayer.createcode;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import java.util.LinkedHashMap;
import java.util.Map;

import top.jplayer.codelib.AutoModel;
import top.jplayer.codelib.BindFieldView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @AutoModel()
    public Map<String, Integer> auto() {
        return new LinkedHashMap<>();
    }
}
