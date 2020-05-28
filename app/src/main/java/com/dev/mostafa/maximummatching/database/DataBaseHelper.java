package com.dev.mostafa.maximummatching.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.dev.mostafa.maximummatching.model.GraphDM;
import com.dev.mostafa.maximummatching.tool.Constant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {


    private static final String CREATE_TABLE_ALGORITHM = "CREATE TABLE "
            + Constant.ALGORITHM_TABLE_NAME + "(" + Constant.KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + Constant.KEY_NAME + " TEXT,"
            + Constant.KEY_DOCUMENT + " TEXT,"
            + Constant.KEY_PSEUDO_CODE + " TEXT );";

    private static final String CREATE_TABLE_GRAPH = "CREATE TABLE "
            + Constant.GRAPH_TABLE_NAME + "(" + Constant.KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + Constant.KEY_NAME + " TEXT );";

    private static final String CREATE_TABLE_NODE = "CREATE TABLE "
            + Constant.NODE_TABLE_NAME + "(" + Constant.KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + Constant.KEY_NAME + " TEXT,"
            + Constant.KEY_X + " FLOAT,"
            + Constant.KEY_Y + " FLOAT,"
            + Constant.KEY_GRAPH_ID + " INTEGER,"
            + " FOREIGN KEY (" + Constant.KEY_GRAPH_ID + ") REFERENCES "
            + Constant.GRAPH_TABLE_NAME + "(" + Constant.KEY_ID + ")"
            + ");";

    private static final String CREATE_TABLE_EDGE = "CREATE TABLE "
            + Constant.EDGE_TABLE_NAME + "(" + Constant.KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + Constant.KEY_NAME + " TEXT,"
            + Constant.KEY_WEIGHT + " INTEGER,"
            + Constant.KEY_GRAPH_ID + " INTEGER,"
            + Constant.KEY_START_NODE + " INTEGER,"
            + Constant.KEY_END_NODE + " INTEGER,"
            + " FOREIGN KEY (" + Constant.KEY_GRAPH_ID + ") REFERENCES "
            + Constant.GRAPH_TABLE_NAME + "(" + Constant.KEY_ID + "), "
            + " FOREIGN KEY (" + Constant.KEY_START_NODE + ") REFERENCES "
            + Constant.NODE_TABLE_NAME + "(" + Constant.KEY_ID + "), "
            + " FOREIGN KEY (" + Constant.KEY_END_NODE + ") REFERENCES "
            + Constant.NODE_TABLE_NAME + "(" + Constant.KEY_ID + ")"
            + ");";


    public DataBaseHelper(Context context) {
        super(context, Constant.DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ALGORITHM);
        Log.i("graphh" , "table algorithm created");
        db.execSQL(CREATE_TABLE_GRAPH);
        Log.i("graphh" , "table graph created");
        db.execSQL(CREATE_TABLE_NODE);
        Log.i("graphh" , "table node created");
        db.execSQL(CREATE_TABLE_EDGE);
        Log.i("graphh" , "table edge created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //-------------------- Algorithm Table Function --------------------//

    //-------------------- Graph Table Function --------------------//
    public List<GraphDM> getAllGraph() {
        List<GraphDM> graphDMS = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + Constant.GRAPH_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                GraphDM graphDM = new GraphDM(c.getInt(c.getColumnIndex(Constant.KEY_ID))
                        , c.getString(c.getColumnIndex(Constant.KEY_NAME)));

                // adding to Students list
                graphDMS.add(graphDM);
            } while (c.moveToNext());
        }
        return graphDMS;
    }

    public long addGraph(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constant.KEY_NAME, name);
        return db.insert(Constant.GRAPH_TABLE_NAME, null, values);
    }

    //-------------------- Node Table Function --------------------//

    //-------------------- Edge Table Function --------------------//
}
