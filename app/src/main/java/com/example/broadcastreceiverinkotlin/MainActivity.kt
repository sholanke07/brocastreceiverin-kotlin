 package com.example.broadcastreceiverinkotlin

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.BatteryManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

 class MainActivity : AppCompatActivity() {
    private val myBroadCastReceiver: BroadcastReceiver = MyBroadCastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    override fun onResume() {
        super.onResume()
        var filter = IntentFilter()
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        filter.addAction(Intent.ACTION_BATTERY_CHANGED)
        filter.addAction(Intent.ACTION_POWER_CONNECTED)
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED)
        filter.addAction(Intent.ACTION_TIME_TICK)
        val intent = Intent(this, MyBroadCastReceiver::class.java)
        intent.putExtra("name", "Lateef")
        intent.putExtra("age", "20")
        registerReceiver(myBroadCastReceiver, filter)
    }
   /* override fun onStart() {
        super.onStart()
        var filter = IntentFilter()
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
         filter.addAction(Intent.ACTION_BATTERY_CHANGED)
        filter.addAction(Intent.ACTION_POWER_CONNECTED)
        registerReceiver(myBroadCastReceiver, filter)
    }*/

   /* override fun onStop() {
        super.onStop()
        unregisterReceiver(myBroadCastReceiver)
    }*/
    override fun onPause() {
        super.onPause()
         unregisterReceiver(myBroadCastReceiver)
    }
 }
 private fun getCurrentTimeStamp(): String {
     val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)
     val now = Date()
     return simpleDateFormat.format(now)
 }
class MyBroadCastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (ConnectivityManager.CONNECTIVITY_ACTION == intent!!.action) {
            val noConnectivity: Boolean = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)
            if (noConnectivity) {
                Toast.makeText(context, "Disconnected", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Connected", Toast.LENGTH_SHORT).show()
            }
        }
        if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)) {
            Toast.makeText(context, "Power Connected", Toast.LENGTH_SHORT).show()

            val name00 = intent.getStringExtra("name")
            val age00 = intent.getStringExtra("age")

            Toast.makeText(context, "$name00  $age00", Toast.LENGTH_SHORT).show()
        }
        if (intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED)){
            Toast.makeText(context, "Power Disconnected", Toast.LENGTH_SHORT).show()
        }
        val level : Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)

        if (level != 0) {
            Toast.makeText(context, "$level", Toast.LENGTH_LONG).show()
        }
        if (intent?.action == Intent.ACTION_TIME_TICK) {

           val info = getCurrentTimeStamp()
                    Toast.makeText(context, "$info", Toast.LENGTH_SHORT).show()
        }
        //if(intent.getAction().equals(Intent.ACTION_TIME_TICK)){
            //Toast.makeText(context, )
       // }
    }
}