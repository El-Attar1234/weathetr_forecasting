package com.example.forecasting.ui.weather.weak_forecast.day_list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.forecasting.R
import com.example.forecasting.data.repository.WeatherRepository
import com.example.forecasting.ui.helpers.ScopedFragment
import kotlinx.android.synthetic.main.future_list_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class FutureListFragment  : ScopedFragment(), KodeinAware,ListAdapter.OnItemClickListener{

    override val kodein by closestKodein()
    private val viewModelFactory: FutureListViewModelFactory by instance()

    private lateinit var viewModel: FutureListViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.future_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(FutureListViewModel::class.java)
    //     val adapter =ListAdapter(viewModel)
      //initializeUi(adapter)
    }
    private fun initializeUi(adapter:ListAdapter){
        recyclerView.adapter=adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)
        viewModel.specidicWeather.observe( this@FutureListFragment.viewLifecycleOwner
        ) {
            val detailsAction=FutureListFragmentDirections.detailAction(it.dt)
              findNavController().navigate(detailsAction)

        }

        launch {
          //  val futureWeather = viewModel.fetchFutureWeather.await()
            val currentWeather = viewModel.fetchCurrentWeather.await()
            currentWeather.observe(
                this@FutureListFragment.viewLifecycleOwner,
                Observer {
                    if (it == null) return@Observer
                    updateLocationTitle(it.zonedDateTime.zone.normalized().toString())

                })


           /* futureWeather.observe(  this@FutureListFragment.viewLifecycleOwner, Observer {
                if (it == null) return@Observer
                group_loading.visibility = View.GONE
                adapter.setList(it)
                updateSubtitle()
            //    initRecyclerView(weatherEntries.toFutureWeatherItems())
            })*/
        }

    }

    private fun updateLocationTitle(location: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateSubtitle() {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Next Week"
    }

    override fun onItemClick(dt: Long) {
        val detailsAction=FutureListFragmentDirections.detailAction(dt)
        findNavController().navigate(detailsAction)
    }

    /* override fun onItemClick(position: Int) {
         val repo=WeatherRepository()
     val dt=
     }*/

}