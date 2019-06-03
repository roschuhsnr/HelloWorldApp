package de.hsnr.helloworldapp;

import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DB extends SQLiteOpenHelper {

    private static final String Tag = DB.class.getSimpleName();
//gffff

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);
    }
}
