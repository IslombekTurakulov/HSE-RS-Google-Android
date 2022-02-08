package com.iuturakulov.task15

import android.content.ContentValues
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.task_15.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.lang.StringBuilder

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val SAVED_STATE_KEY = "savedStateKey"
    private lateinit var fab: FloatingActionButton
    private lateinit var information: TextView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var showButton: Button
    private lateinit var editText: EditText
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        fab = findViewById(R.id.fab)
        fab.setOnClickListener(this)
        information = findViewById(R.id.info)
        showButton = findViewById(R.id.showButton)
        showButton.setOnClickListener(this)
        editText = findViewById(R.id.editText)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        information.text = sharedPreferences.getString(SAVED_STATE_KEY, "")
        dbHelper = DBHelper(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_clear_db -> {
                val db: SQLiteDatabase = dbHelper.writableDatabase ?: SQLiteDatabase.create(null)
                db.execSQL("delete from mytable;")
                db.execSQL("delete from sqlite_sequence where name='mytable';")
                db.close()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onClick(view: View) {
        if (view.id == fab.id) {
            val cv = ContentValues()
            val text: String = editText.text.toString()
            val db: SQLiteDatabase = dbHelper.writableDatabase ?: SQLiteDatabase.create(null)
            cv.put("info", text)
            // вставляем запись и получаем ее ID
            val rowID = db.insert("mytable", null, cv)
            Log.d("onClick", "row inserted, ID = $rowID")
            Toast.makeText(this@MainActivity, "Запись добавлена", Toast.LENGTH_SHORT).show()
        } else if (view.id == showButton.id) {
            val db: SQLiteDatabase = dbHelper.writableDatabase
            val c = db.query("mytable", null, null, null, null, null, null)
            val tmp = StringBuilder()
            when {
                c.moveToFirst() -> {
                    val idColIndex = c.getColumnIndex("id")
                    val infoColIndex = c.getColumnIndex("info")
                    do {
                        tmp.append("ID = ")
                            .append(c.getInt(idColIndex))
                            .append(", info = ")
                            .append(c.getString(infoColIndex))
                            .append("\n")
                    } while (c.moveToNext())
                }
            }
            c.close()
            Log.d("Records", tmp.toString())
            information.text = tmp.toString()
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString(SAVED_STATE_KEY, tmp.toString())
            editor.apply()
        }
    }
}