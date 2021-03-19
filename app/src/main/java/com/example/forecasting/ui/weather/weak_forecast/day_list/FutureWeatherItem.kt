package com.example.forecasting.ui.weather.weak_forecast.day_list

import com.example.forecasting.R
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_list.textView_condition


class FutureWeatherItem: Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
     /*   viewHolder.apply {
            textView_condition.text="clear"
        }*/
    }

    override fun getLayout() = R.layout.item_list
}