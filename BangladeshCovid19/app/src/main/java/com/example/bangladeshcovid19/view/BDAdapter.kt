package com.example.bangladeshcovid19.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bangladeshcovid19.R
import com.example.bangladeshcovid19.model.BDtwoResponse
import com.example.bangladeshcovid19.model.Data
import kotlinx.android.synthetic.main.item_layout_actwo.view.*


class BDAdapter (private val districtSet : BDtwoResponse)  :
    RecyclerView.Adapter<BDAdapter.BDViewHolder>() {

    /*var displayList:MutableList<Data> = mutableListOf()
        set(value){
            field = value
            notifyDataSetChanged()
        }*/

    class BDViewHolder(BDView: View) : RecyclerView.ViewHolder(BDView) {
        val tv_titleview: TextView =  BDView.tv_title
        val tv_gname: TextView =  BDView.tv_getname
        val tv_currentcase: TextView =  BDView.tv_curcase
        val tv_gcurcase: TextView =  BDView.tv_getcurcase
        val tv_prevcase: TextView =  BDView.tv_precase
        val tv_gprecase: TextView =  BDView.tv_getprecase

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BDViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout_actwo, parent, false)
        return BDViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return districtSet.data.size

    }

    override fun onBindViewHolder(holder: BDViewHolder, position: Int) {


        holder.tv_gname.text = districtSet.data[position].name
        holder.tv_gcurcase.text = districtSet.data[position].count.toString()
        holder.tv_gprecase.text = districtSet.data[position].prev_count.toString()
      //  holder.tv_gname.text = "District Name"

    }
}

