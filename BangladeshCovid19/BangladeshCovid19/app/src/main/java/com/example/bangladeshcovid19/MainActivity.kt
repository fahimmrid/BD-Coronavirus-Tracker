package com.example.bangladeshcovid19

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.Typeface.BOLD
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.graphics.toColorInt
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bangladeshcovid19.model.*
import com.example.bangladeshcovid19.view.SecondActivity
import com.example.bangladeshcovid19.viewModel.BDViewModel
import org.w3c.dom.Text
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder


class MainActivity : AppCompatActivity() {


    lateinit var text_update : TextView
    lateinit var text_up: TextView //testing 2nd activity API

    //image buttons
    lateinit var btn_newacc : Button
    lateinit var image_dead : ImageView
    lateinit var image_rec : ImageView
    lateinit var image_pos : ImageView
    lateinit var image_test : ImageView

    //toolbar
    private lateinit var toolbar : Toolbar
    val TAG  : String ="TAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        text_update = findViewById(R.id.tv_updated)

        btn_newacc = findViewById(R.id.button_newacc)

        image_dead = findViewById(R.id.image_death)
        image_rec = findViewById(R.id.image_recover)
        image_pos = findViewById(R.id.image_positive)
        image_test = findViewById(R.id.image_tested)

        toolbar = findViewById(R.id.toolbarnew)
       // toolbar.setTitle("Bangladesh Covid-19 Tracker")
        setSupportActionBar(toolbar)

        //init step for getting the viewmodel in oncreate
        val viewModel: BDViewModel = ViewModelProvider(this,
            object : ViewModelProvider.Factory { //What creates the viewmodel
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return BDViewModel() as T
                }
            }).get(BDViewModel::class.java)

        viewModel.getMestats()


        viewModel.getData().observe(this,
            object : Observer<BdApiResponse> {
                override fun onChanged(bd: BdApiResponse?) {
                   //Log.d("red","death")
                    bd?.let {
                     // val obj = BdApiResponse(t)
                        text_update.text = ("last updated: " + bd.updated_on )

                        btn_newacc.setOnClickListener {
                            val intent = Intent(this@MainActivity, SecondActivity::class.java)
                            startActivity(intent)
                        }

                     //   val stringBuilder = StringBuilder()
                     //   var arrayCategories : Array<String> = arrayOf()
                        image_dead.setOnClickListener {
                            lateinit var textview_n : TextView
                            textview_n = findViewById(R.id.tv_new)
                            textview_n.setVisibility(View.VISIBLE)
                            var text: String = "Death Total: "
                            var mSpannableString = SpannableString(text)
                            //ForegroundColorspan red = new ForegroundCol(Color.RED)
                            //var red: ForegroundColorSpan = ForegroundColorSpan(Color.RED)//WORKS
                            var mRed = ForegroundColorSpan(Color.BLACK)
                            mSpannableString.setSpan(mRed, 0, mSpannableString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                            //mSpannableString.setSpan(mRed, 0, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE) //you can seletec start and end!
                            mSpannableString.setSpan(UnderlineSpan(), 0, mSpannableString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                            mSpannableString.setSpan(StyleSpan(BOLD),0, mSpannableString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                            textview_n.text = (mSpannableString)

                            var mSpannApi = SpannableString(bd.death.total.toString())
                            mSpannApi.setSpan(StyleSpan(BOLD),0, mSpannApi.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                            mSpannApi.setSpan(RelativeSizeSpan(1.5f),0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                            textview_n.append(mSpannApi)
                            textview_n.append("\n")

                            var text_24hrs: String = " Last 24hrs: "
                            var mSpan24hrs = SpannableString(text_24hrs)
                            var mRed2 = ForegroundColorSpan(Color.BLACK)
                            mSpan24hrs.setSpan(mRed2, 0, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                            mSpan24hrs.setSpan(UnderlineSpan(), 1, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                            textview_n.append(mSpan24hrs)
                            textview_n.append("\n")

                            var mSpannApi2 = SpannableString(bd.death.last24.toString())
                            mSpannApi2.setSpan(StyleSpan(BOLD),0, mSpannApi2.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                            textview_n.append(mSpannApi2)
                       /*     val r:Int = bd.death.total
                            val n:Int = bd.death.last24
                            val res: Double = r/n as Double
                            textview_n.append(res.toString())*/
                            //textview_n.setText(bd.death.total)   //android:textStyle="bold"
                            //textview_n.text = bd.death.total.toString()

                        }

                         image_rec.setOnClickListener {
                             lateinit var textview_n : TextView
                             textview_n = findViewById(R.id.tv_new)
                             textview_n.setVisibility(View.VISIBLE)

                             var text: String = "Recovered:"
                             var mSpannableString = SpannableString(text)
                             var mRed = ForegroundColorSpan(Color.BLACK)
                             mSpannableString.setSpan(mRed, 0, mSpannableString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                             mSpannableString.setSpan(UnderlineSpan(), 0, mSpannableString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                             mSpannableString.setSpan(StyleSpan(BOLD),0, mSpannableString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                             textview_n.text = (mSpannableString)

                             var mSpannApi = SpannableString(bd.recovered.total.toString())
                             mSpannApi.setSpan(StyleSpan(BOLD),0, mSpannApi.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                             mSpannApi.setSpan(RelativeSizeSpan(1.5f),0, mSpannApi.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                             textview_n.append(mSpannApi)
                             textview_n.append("\n")

                             var text_24hrs: String = " Last 24hrs:"
                             var mSpan24hrs = SpannableString(text_24hrs)
                             var mRed2 = ForegroundColorSpan(Color.BLACK)
                             mSpan24hrs.setSpan(mRed2, 0, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                             mSpan24hrs.setSpan(UnderlineSpan(), 1, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                             textview_n.append(mSpan24hrs)
                             textview_n.append("\n")

                             var mSpannApi2 = SpannableString(bd.recovered.last24.toString())
                             mSpannApi2.setSpan(StyleSpan(BOLD),0, mSpannApi2.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                             textview_n.append(mSpannApi2)


                         }
                        image_pos.setOnClickListener {
                            lateinit var textview_n: TextView
                            textview_n = findViewById(R.id.tv_new)
                            textview_n.setVisibility(View.VISIBLE)


                            var text: String = "Positive Case:"
                            var mSpannableString = SpannableString(text)
                            var mRed = ForegroundColorSpan(Color.BLACK)
                            mSpannableString.setSpan(mRed, 0, mSpannableString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                            mSpannableString.setSpan(UnderlineSpan(), 0, mSpannableString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                            mSpannableString.setSpan(StyleSpan(BOLD),0, mSpannableString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                            textview_n.text = (mSpannableString)

                            var mSpannApi = SpannableString(bd.positive.total.toString())
                            mSpannApi.setSpan(StyleSpan(BOLD),0, mSpannApi.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                            mSpannApi.setSpan(RelativeSizeSpan(1.5f),0, mSpannApi.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                            textview_n.append(mSpannApi)
                            textview_n.append("\n")

                            var text_24hrs: String = " Last 24hrs:"
                            var mSpan24hrs = SpannableString(text_24hrs)
                            var mRed2 = ForegroundColorSpan(Color.BLACK)
                            mSpan24hrs.setSpan(mRed2, 0, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                            mSpan24hrs.setSpan(UnderlineSpan(), 1, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                            textview_n.append(mSpan24hrs)
                            textview_n.append("\n")

                            var mSpannApi2 = SpannableString(bd.positive.last24.toString())
                            mSpannApi2.setSpan(StyleSpan(BOLD),0, mSpannApi2.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                            textview_n.append(mSpannApi2)

                        }

                        image_test.setOnClickListener {
                            lateinit var textview_n: TextView
                            textview_n = findViewById(R.id.tv_new)
                            textview_n.setVisibility(View.VISIBLE)

                            var text: String = "Total Tested:"
                            var mSpannableString = SpannableString(text)
                            var mRed = ForegroundColorSpan(Color.BLACK)
                            mSpannableString.setSpan(mRed, 0, mSpannableString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                            mSpannableString.setSpan(UnderlineSpan(), 0, mSpannableString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                            mSpannableString.setSpan(StyleSpan(BOLD),0, mSpannableString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                            textview_n.text = (mSpannableString)

                            var mSpannApi = SpannableString(bd.test.total.toString())
                            mSpannApi.setSpan(StyleSpan(BOLD),0, mSpannApi.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                            mSpannApi.setSpan(RelativeSizeSpan(1.5f),0, mSpannApi.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                            textview_n.append(mSpannApi)
                            textview_n.append("\n")

                            var text_24hrs: String = " Last 24hrs:"
                            var mSpan24hrs = SpannableString(text_24hrs)
                            var mRed2 = ForegroundColorSpan(Color.BLACK)
                            mSpan24hrs.setSpan(mRed2, 0, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                            mSpan24hrs.setSpan(UnderlineSpan(), 1, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                            textview_n.append(mSpan24hrs)
                            textview_n.append("\n")

                            var mSpannApi2 = SpannableString(bd.test.last24.toString())
                            mSpannApi2.setSpan(StyleSpan(BOLD),0, mSpannApi2.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                            textview_n.append(mSpannApi2)



                        }



                    }
                }
            })

    }

    /*btn_save_word.setOnClickListener {
            if(!listener!!.isWordValid(
                    view.til_word.editText?.text.toString()))
                Toast.makeText(activity,
                    "No empty words",
                    Toast.LENGTH_SHORT)
                    .show()
            else {
                listener!!.saveWord(
                    view.til_word.editText?.text.toString()
                )
                view.til_word.editText?.text?.clear()
                activity?.supportFragmentManager?.popBackStackImmediate()
            }
        }*/

/*
    fun init(){

        BDCovidAPI.initRetrofit()
            .getMestats()
            .enqueue(object : Callback<BdApiResponse> {
                override fun onFailure(call: retrofit2.Call<BdApiResponse>, t: Throwable) {
                    Log.e("BDViewModel", t.message?:"N/A")
                }

                override fun onResponse(
                    call: retrofit2.Call<BdApiResponse>,
                    response: Response<BdApiResponse>
                ) {
                    if(response.isSuccessful)
                        Log.d(TAG, "Response code is: "+response.code())
                           textview_n.text = response.body().toString()
                }
            })
    }
*/



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu, menu)
      /*  val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout_jokes, parent, false)*/
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
}