package com.example.weatherapp.ui.weather.listCities

import android.os.Bundle
import android.view.*
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.data.api.WeatherApiService
import com.example.weatherapp.data.db.City
import com.example.weatherapp.databinding.ListCitiesWeatherFragmentBinding

class ListCitiesWeatherFragment : Fragment() {
 private lateinit var binding: ListCitiesWeatherFragmentBinding
 private lateinit var adapter: ListCitiesWeatherAdapter
 val weatherApiService = WeatherApiService()
    companion object {
        fun newInstance() = ListCitiesWeatherFragment()
    }

    private lateinit var viewModel: ListCitiesWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ListCitiesWeatherFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        setHasOptionsMenu(true)
        viewModel = ViewModelProviders.of(this, ListCitiesWeatherViewModeFactory(
            context!!
        )
        ).get(ListCitiesWeatherViewModel::class.java)

        adapter = ListCitiesWeatherAdapter(context!!, viewModel)
        binding.recyclerView.layoutManager = LinearLayoutManager(context!!)
        binding.recyclerView.adapter = adapter

        viewModel.getListCities()
        viewModel.getListCitiesObsserver().observe(this,{

            adapter.setCitiesWeather(it)
        })

       // add city to list
        binding.addActionButton.setOnClickListener{

            val builder = AlertDialog.Builder(context!!)
            val inflater: LayoutInflater = layoutInflater
            val dialogLayout: View = inflater.inflate(R.layout.layout_add_city,null)
            val editText = dialogLayout.findViewById<EditText>(R.id.addCityEditText)

            with(builder) {
                setTitle("add City")
               setPositiveButton("Ok"){dialog,whitch ->
                   viewModel.insertCity(City(name = editText.text.toString()))
               }
                setNegativeButton("Cancel"){dialog, whitch ->
                    dialog.cancel()
                }
                setView(dialogLayout)
                show()
            }

        }


    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu,menu)

        val item = menu.findItem(R.id.action_search)
        val searchView = item.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                    if (query != null) {
                        if (query.isEmpty()) {
                            viewModel.getListCities()
                        } else
                             viewModel.searchCities(query)
                    }

                return false
            }

        })



    }



}