package com.example.forecasting.ui.weather.weak_forecast.details


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.forecasting.R
import kotlinx.android.synthetic.main.fragment_future_details.*

class FutureDetailsFragment : Fragment(R.layout.fragment_details) {

    private val args:FutureDetailsFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textView_temperature.text=args.date.toString()
    }
}