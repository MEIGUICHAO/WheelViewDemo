package io.github.deweyreed.scrollhmspicker

import android.content.Context
import android.graphics.Typeface
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.galanz.a6wheelviewdemo0710.R
import com.galanz.a6wheelviewdemo0710.view.NumberPickerView
import kotlinx.android.synthetic.main.shp_scrollhmspicker.view.*

/**
 * Created on 2018/2/13.
 */
@Suppress("MemberVisibilityCanBePrivate", "unused")
open class ScrollHmsPicker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var autoStep: Boolean = false
    private var enable99Hours = false

    var hours: Int
        get() = pickerHours.value
        set(value) = setSafeHours(value)

    var minutes: Int
        get() = pickerMinutes.value
        set(value) = setSafeMinutes(value)

    val font = "rubik_regular.ttf"


    init {
        orientation = HORIZONTAL
        LayoutInflater.from(context).inflate(R.layout.shp_scrollhmspicker, this)

        val res = resources

        val ta = context.obtainStyledAttributes(attrs, R.styleable.ScrollHmsPicker)

        val colorNormal = ta.getColor(
            R.styleable.ScrollHmsPicker_shp_normal_color,
            color(R.color.color333)
        )
        @ColorInt val colorSelected = ta.getColor(
            R.styleable.ScrollHmsPicker_shp_selected_color,
            color(R.color.colorSelected)
        )
        val hours = ta.getInteger(R.styleable.ScrollHmsPicker_shp_hours, 0)
        val minutes = ta.getInteger(R.styleable.ScrollHmsPicker_shp_minutes, 0)
        val autoStep = ta.getBoolean(R.styleable.ScrollHmsPicker_shp_auto_step, false)


        enable99Hours = ta.getBoolean(R.styleable.ScrollHmsPicker_shp_enable_99_hours, false)
        ta.recycle()

        pickerHours.maxValue = 99
        set99Hours(enable99Hours)

        pickerMinutes.maxValue = 59


        setSafeHours(hours)
        setSafeMinutes(minutes)
        setAutoStep(autoStep)

        arrayOf(pickerHours, pickerMinutes).forEach {
            it.setNormalTextColor(colorNormal)
            it.setSelectedTextColor(colorSelected)
            it.wrapSelectorWheel = false

        }

        textDes.setTypeface(Typeface.createFromAsset(context.assets, "font/$font"))

    }


    fun setColorNormal(@ColorRes res: Int) {
        setColorIntNormal(color(res))
    }

    fun setColorIntNormal(@ColorInt color: Int) {
        arrayOf(pickerHours, pickerMinutes).forEach {
            it.setNormalTextColor(color)
        }
    }



    fun setAutoStep(newValue: Boolean) {
        if (newValue != autoStep) {
            autoStep = newValue
            if (autoStep) {
                pickerMinutes.setOnValueChangeListenerInScrolling { _, oldVal, newVal ->
                    val hoursVal = pickerHours.value
                    if (oldVal == 59 && newVal == 0) {
                        pickerHours.smoothScrollToValue((hoursVal + 1) % (if (enable99Hours) 100 else 24))
                    } else if (oldVal == 0 && newVal == 59) {
                        pickerHours.smoothScrollToValue(if (hoursVal > 0) hoursVal - 1 else ((if (enable99Hours) 99 else 23)))
                    }
                }
            } else {
                pickerMinutes.setOnValueChangeListenerInScrolling(null)
            }
        }
    }



    fun set99Hours(enable: Boolean) {
        enable99Hours = enable
        pickerHours.setMinAndMaxShowIndex(0, if (enable) 99 else 23)
    }

    private fun setSafeHours(hours: Int) {
        if (hours in 0..(if (enable99Hours) 99 else 23)) scrollToValue(pickerHours, hours)
    }

    private fun setSafeMinutes(minutes: Int) {
        if (minutes in 0..59) scrollToValue(pickerMinutes, minutes)
    }


    private fun scrollToValue(picker: NumberPickerView, value: Int) {
//        if (animateToValue) {
//            post { picker.smoothScrollToValue(value) }
//        } else {
        picker.value = value
//        }
    }

    override fun onSaveInstanceState(): Parcelable {
        val parent = super.onSaveInstanceState()
        return (if (parent != null) SavedState(parent) else SavedState()).also { state ->
            state.hours = hours
            state.minutes = minutes
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is SavedState) {
            super.onRestoreInstanceState(state.superState)
            hours = state.hours
            minutes = state.minutes
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    private class SavedState : BaseSavedState {
        var hours: Int = 0
        var minutes: Int = 0
        var seconds: Int = 0

        constructor() : super(Parcel.obtain())

        constructor(superState: Parcelable) : super(superState)

        private constructor(source: Parcel?) : super(source) {
            source?.run {
                hours = source.readInt()
                minutes = source.readInt()
                seconds = source.readInt()
            }
        }

        override fun writeToParcel(out: Parcel?, flags: Int) {
            super.writeToParcel(out, flags)
            out?.run {
                writeInt(hours)
                writeInt(minutes)
                writeInt(seconds)
            }
        }

        private companion object {
            @JvmField
            val CREATOR = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(source: Parcel?): SavedState = SavedState(source)

                override fun newArray(size: Int): Array<SavedState?> = arrayOfNulls(size)
            }
        }
    }

    private fun View.color(@ColorRes id: Int) = ContextCompat.getColor(this.context, id)
}