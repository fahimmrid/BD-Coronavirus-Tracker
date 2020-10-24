package com.example.bangladeshcovid19.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.view.MenuInflater
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bangladeshcovid19.R
import com.example.bangladeshcovid19.model.BDtwoResponse
import com.example.bangladeshcovid19.model.Data
import com.example.bangladeshcovid19.viewModel.BDViewModel
import kotlinx.android.synthetic.main.activity_two.*
import java.util.*
import kotlin.collections.ArrayList

class SecondActivity : AppCompatActivity() {

    private lateinit var toolbar : Toolbar //the toolbar for the 2nd activity
    private lateinit var search_d : EditText
    lateinit var recycler_d : RecyclerView

    //var displayList:MutableList<Data> = mutableListOf()
    var displayList2 = ArrayList<Data>()
    var displayListnew = ArrayList<Data>()

   // lateinit var text_up: TextView //testing 2nd activity API

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two)

  //  recycler_d = findViewById(R.id.recycler_view)
        toolbar = findViewById(R.id.toolbarnew)
        toolbar.setTitle("Bangladesh Covid-19 Tracker")
        setSupportActionBar(toolbar)

     //   recycler_d.layoutManager = LinearLayoutManager(this)

     //   search_d = findViewById(R.id.et_search)
       // text_up = findViewById(R.id.tv_updated2)
/*
        search_d.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
           }
        })
*/

        //init step for getting the viewmodel in oncreate
        val viewModel: BDViewModel = ViewModelProvider(this,
            object : ViewModelProvider.Factory { //What creates the viewmodel
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return BDViewModel() as T
                }
            }).get(BDViewModel::class.java)

        viewModel.getMedistrict()
        viewModel.getDistrict().observe(this,
            Observer<BDtwoResponse> { bd2 ->
               //Log.d("red","death")
                bd2?.let {


                    val adapter = BDAdapter(it)
                   // val adapter2 = BDAdapter(displayList2)

                    //var list1:ArrayList<Data> =
                    recycler_view.layoutManager = LinearLayoutManager(this@SecondActivity)
                    recycler_view.adapter = adapter


                }
            })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {


        menuInflater.inflate(R.menu.menutwo, menu)
        val searchItem = menu!!.findItem(R.id.menu_search)
        if(searchItem != null){
            val searchView = searchItem.actionView as SearchView
            searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                  //  displayList2.clear()
                    //val search = newText!!.toLowerCase()
                    if (newText!!.isNotEmpty()) {
                        displayList2.clear()
                        val search = newText.toLowerCase(Locale.getDefault())
                        displayListnew.forEach {
                            if (it.name.toLowerCase(Locale.getDefault()).contains(search)){
                                displayList2.add(it)
                            }
                        }
                        recycler_view.adapter!!.notifyDataSetChanged()
                    }
               //     return true
                    else {
                        displayList2.clear()
                        displayList2.addAll(displayListnew)
                        recycler_view.adapter!!.notifyDataSetChanged()
                    }
                    return true

                }

            })
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
}




