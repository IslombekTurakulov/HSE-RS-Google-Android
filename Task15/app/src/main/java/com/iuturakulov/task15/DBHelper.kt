package com.iuturakulov.task15

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBHelper(context: Context?) :
    SQLiteOpenHelper(context, "db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        Log.d("SQLiteDatabase", "--- onCreate database ---")
        // создаем таблицу с полями
        db.execSQL(
            "create table mytable ("
                    + "id integer primary key autoincrement,"
                    + "info text"
                    + ");"
        )
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {}
}
