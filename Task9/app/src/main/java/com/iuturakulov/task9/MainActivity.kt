package com.iuturakulov.task9

import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.material.button.MaterialButton
import java.util.*


class MainActivity : AppCompatActivity() {

    companion object {
        const val NOTIFICATION_ID = 101
        const val CHANNEL_ID = "HW_11"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mediaPlayer = MediaPlayer.create(this, R.raw.playground)
        val startButton: Button = findViewById(R.id.startMedia)
        val stopButton: Button = findViewById(R.id.stopMedia)
        startButton.setOnClickListener{
            mediaPlayer.start()
            startButton.visibility = View.GONE
            stopButton.visibility = View.VISIBLE
        }

        stopButton.setOnClickListener{
            mediaPlayer.pause()
            stopButton.visibility = View.GONE
            startButton.visibility = View.VISIBLE
        }
        val textView: TextView = findViewById(R.id.textView_context)
        registerForContextMenu(textView)
        findViewById<MaterialButton>(R.id.notificationButton).setOnClickListener {
            val ringURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(CHANNEL_ID, CHANNEL_ID, importance)

                val audioAttributes = AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build()

                channel.setSound(ringURI, audioAttributes)
                // Register the channel with the system
                val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }


            val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Календарь")
                .setTicker("Новое уведомление!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(NotificationCompat.BigTextStyle()
                    .bigText("ЗАВТРА ВОСКРЕСЕНЬЕ!"))
                .setSound(ringURI)

            with(NotificationManagerCompat.from(this)) {
                notify(NOTIFICATION_ID, builder.build())
            }
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val textView: TextView = findViewById(R.id.textView_context)
        when (item.itemId) {
            R.id.option_1 -> {
                Toast.makeText(this, "Option 1 selected", Toast.LENGTH_LONG).show()
                textView.setTextColor(Color.BLUE)
                return true
            }
            R.id.option_2 -> {
                Toast.makeText(this, "Option 2 selected", Toast.LENGTH_LONG).show()
                textView.setTextColor(Color.GREEN)
                return true
            }
            R.id.option_3 -> {
                Toast.makeText(this, "Option 3 selected", Toast.LENGTH_LONG).show()
                textView.setTextColor(Color.CYAN)
                return true
            }
            else -> {
                return super.onContextItemSelected(item)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val textView: TextView = findViewById(R.id.textView_context)
        when (item.itemId) {
            R.id.item1 -> {
                val date = Date()
                Toast.makeText(this, "$date", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.item2 -> {
                Toast.makeText(this, "MAKAROV SERGEY LVOVICH", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.item3 -> {
                when (textView.visibility) {
                    View.VISIBLE -> {
                        textView.visibility = View.INVISIBLE
                    }
                    else -> {
                        textView.visibility = View.VISIBLE;
                    }
                }
                return true
            }
            R.id.subitem1 -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Сложно выбрать!!")
                    .setMessage("Выбери пищу")
                    .setCancelable(true)
                    .setPositiveButton("Вкусная пища") { dialog, id ->
                        Toast.makeText(
                            this, "Вы сделали правильный выбор",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    .setNegativeButton(
                        "Здоровая пища"
                    ) { dialog, id ->
                        Toast.makeText(
                            this, "Возможно вы правы",
                            Toast.LENGTH_LONG
                        ).show()
                    }.show()
                return true
            }
            R.id.subitem2 -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Выход")
                    .setMessage("Выйти с приложения?")
                    .setCancelable(true)
                    .setPositiveButton("Кто здесь это поместил?") { dialog, id ->
                        Toast.makeText(
                            this, "Ладннооо, не буду выключать",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    .setNegativeButton(
                        "ДА ДАВАЙ ОТКЛЮЧАЙ"
                    ) { dialog, id ->
                        this.finish()
                    }.show()
                return true
            }
            R.id.subitem3 -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Сложно выбрать!!")
                    .setMessage("У меня проблема с выбором еды, помоги!")
                    .setCancelable(true)
                    .setPositiveButton("Пицца с газировкой") { dialog, id ->
                        Toast.makeText(
                            this, "Ам ням ням вкуснаааа",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    .setNegativeButton(
                        "Брокколи с кетчупом"
                    ) { dialog, id ->
                        Toast.makeText(
                            this, "Сумасшедший!!((",
                            Toast.LENGTH_LONG
                        ).show()
                    }.show()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater: MenuInflater = menuInflater
        menuInflater.inflate(R.menu.top_menu, menu)
        return true
    }

}