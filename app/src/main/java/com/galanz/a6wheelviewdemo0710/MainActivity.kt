package com.galanz.a6wheelviewdemo0710

import android.app.Activity
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.galanz.a6wheelviewdemo0710.view.SingleWheelView
import com.galanz.a6wheelviewdemo0710.view.WheelView
import io.github.deweyreed.scrollhmspicker.ScrollHmsPicker
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        singleWheelView.setViewType(SingleWheelView.TEMP_CENTIGRADE, 50, 350, 5)
//        singleWheelView.setViewType(SingleWheelView.BROIL)
//        btnDialog.setOnClickListener {
//            val dialog = AlertDialog.Builder(this)
//                .setView(R.layout.dialog_picker)
//                .setPositiveButton(android.R.string.yes, null)
//                .show()
//            val picker: ScrollHmsPicker = dialog.findViewById<ScrollHmsPicker>(R.id.picker)!!
//
//            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
//                onHmsPick(picker.hours, picker.minutes)
//                dialog.dismiss()
//            }
//        }
//        btnGetTime.setOnClickListener {
//            onHmsPick(scrollHmsPicker.hours, scrollHmsPicker.minutes)
//        }
    }

    private fun onHmsPick(hours: Int, minutes: Int) {
        Toast.makeText(
            this,
            "hours: $hours, minutes: $minutes",
            Toast.LENGTH_SHORT
        ).show()
    }
}
