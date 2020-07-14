package com.galanz.a6wheelviewdemo0710.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.galanz.a6wheelviewdemo0710.R
import kotlinx.android.synthetic.main.layout_single_wheelview.view.*
import java.util.*

class SingleWheelView @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attributes, defStyleAttr) {

    val calendar: Calendar = Calendar.getInstance()
    @SuppressLint("SimpleDateFormat")
    var sdf = java.text.SimpleDateFormat("yy.MM.dd")
    private val font = "alibaba_puhuiti_regular.ttf"

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_single_wheelview, this)
        calendar.set(2020,Calendar.JANUARY,1)
    }

    fun setViewType(viewType: Int, startValue: Int=0, endValue: Int=0, stepValue: Int=0,lable: String?=null) {
        when (viewType) {
            TEMP_CENTIGRADE -> {
                setArrayValues(endValue, startValue, stepValue)
                setLable("°C")
            }
            TEMP_CENTIGRADE_NO_TEXT -> {
                setArrayValues(endValue, startValue, stepValue)
                setLable("°C")
                setNoTextStytle()
            }
            TEMP_FAHRENHEIT -> {
                setArrayValues(endValue, startValue, stepValue)
                setLable("°F")
            }
            TEMP_FAHRENHEIT_NO_TEXT -> {
                setArrayValues(endValue, startValue, stepValue)
                setLable("°F")
                setNoTextStytle()
            }
            DATE_YMD -> {
                var values: Array<String?> = arrayOfNulls<String>(3650)
                for (index in 0 until 3650) {
                    calendar.add(Calendar.DATE,index)
                    values[index] = sdf.format(calendar.getTime())
                }
                setSelectedColor(R.color.wheelCurrentTimeSelecte)
                setDesContent("设置当前的日期")
                wheelView.refreshByNewDisplayedValues(values)
            }
            TEXT_WITH_LABLE -> {
                if (!lable.isNullOrEmpty()) {
                    setLable(lable)
                }
                setArrayValues(endValue, startValue, stepValue)
            }
            TEXT_NO_LABEL -> {
                setArrayValues(endValue, startValue, stepValue)
            }
            BROIL -> {
                val stringArray = resources.getStringArray(R.array.broil_level)
                wheelView.setTTFFont(font)
                wheelView.mTextSizeSelected = wheelView.dp2px(context, 50f)
                wheelView.setSelectedTextColor(Color.parseColor("#FE4013"))
                wheelView.mTextSizeNormal = wheelView.dp2px(context, 40f)
                wheelView.updateContentAndIndex(stringArray)
            }

        }

    }

    private fun setArrayValues(endValue: Int, startValue: Int, stepValue: Int) {
        var values: Array<String?> = arrayOfNulls<String>(((endValue - startValue) / stepValue) + 1)
        var index = 0
        for (value in startValue..endValue step stepValue) {
            values[index] = value.toString()
            index++
        }
        wheelView.refreshByNewDisplayedValues(values)
    }

    fun setLable(lable: String) {
        wheelView.setHintText(lable)
    }

    fun setDesContent(content: String) {
        textDes.setText(content)
    }

    fun setSelectedColor(color: Int) {
        wheelView.setSelectedTextColor(resources.getColor(color))
    }

    fun setNoTextStytle() {
        bgHasText.setImageDrawable(resources.getDrawable(R.mipmap.rectangular_mask_no))
        textDes.visibility = View.GONE
        wheelView.setPadding(0, 130, 0, 0)
    }

    companion object {
        const val TEMP_CENTIGRADE = 1
        const val TEMP_CENTIGRADE_NO_TEXT = 2
        const val TEMP_FAHRENHEIT = 3
        const val TEMP_FAHRENHEIT_NO_TEXT = 4
        const val DATE_YMD = 5
        const val TEXT_WITH_LABLE = 6
        const val TEXT_NO_LABEL = 7
        const val BROIL = 8
    }


}