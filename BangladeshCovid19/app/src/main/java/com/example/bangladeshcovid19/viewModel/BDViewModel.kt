package com.example.bangladeshcovid19.viewModel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bangladeshcovid19.model.BDCovidAPI
import com.example.bangladeshcovid19.model.BDtwoResponse
import com.example.bangladeshcovid19.model.BdApiResponse
import retrofit2.Callback
import retrofit2.Response

class BDViewModel : ViewModel() {
    private val dataSet: MutableLiveData<BdApiResponse> = MutableLiveData()

    private val dataDistrict: MutableLiveData<BDtwoResponse> = MutableLiveData()


    fun getData(): LiveData<BdApiResponse> {
        return dataSet
    }

    fun getDistrict(): LiveData<BDtwoResponse> {
        return dataDistrict
    }

    fun getMestats(){
        BDCovidAPI.initRetrofit()
            .getMestats()
            .enqueue(object : Callback<BdApiResponse> {
                override fun onFailure(call: retrofit2.Call<BdApiResponse>, t: Throwable) {
                    Log.e("BDViewModel", t.message?:"N/A")
                    // Toast.makeText(this@BDViewModel, "server not available", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(
                    call: retrofit2.Call<BdApiResponse>,
                    response: Response<BdApiResponse>
                ) {
                    if(response.isSuccessful) //onreponse modify the livedata...trigger an emission observed by view
                        dataSet.value = response.body()//like obserbable
                }
            })
    }

    fun getMedistrict (){  //called from the main acc.
        BDCovidAPI.initRetrofit()
            .getMedistrict()
            .enqueue(object : Callback<BDtwoResponse> {
                override fun onFailure(call: retrofit2.Call<BDtwoResponse>, t: Throwable) {
                    Log.e("BDViewModel", t.message?:"N/A")
                   // Toast.makeText(this@BDViewModel, "server not available", Toast.LENGTH_SHORT).show()

                }

                override fun onResponse(
                    call: retrofit2.Call<BDtwoResponse>,
                    response: Response<BDtwoResponse>
                ) {
                    if(response.isSuccessful) //onreponse modify the livedata...trigger an emission observed by view
                        dataDistrict.value = response.body()//like obserbable
                }
            })
    }



}
