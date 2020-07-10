package com.galanz.a6wheelviewdemo0710

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var list = ArrayList<String>()
        for (i in 0 until 100) {
            if (i < 10) {
                list.add("20.03.0$i")
            } else {
                list.add("20.03.$i")
            }
        }
        wheelView.setItems(list, 20)
        wheelView.setCycleDisable(true)
        wheelView.setGravity(Gravity.CENTER)
//        wheelView2.setItems(list, 20)
//        wheelView2.setCycleDisable(true)
//        wheelView2.setGravity(Gravity.CENTER)

    }
}
