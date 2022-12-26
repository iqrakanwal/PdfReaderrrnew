package com.example.pdfreaderrr.adaptors

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pdfreaderrr.PdfModel
import com.example.pdfreaderrr.R
import com.example.pdfreaderrr.interfaces.PdfClickedListener
import com.example.pdfreaderrr.utills.PreferencesUtility

class VideosAdaptor(
    var context: Context,
    var arrayList: ArrayList<PdfModel>,
    var buttonClick: PdfClickedListener
) :
    RecyclerView.Adapter<VideosAdaptor.VideoItem>() {
    inner class VideoItem(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.namePDF)
        var size: TextView = view.findViewById(R.id.sizePDF)
       // var options: ImageView = view.findViewById(R.id.option)
       // var fav: ImageView = view.findViewById(R.id.fav)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoItem {
        var view: View
          if (PreferencesUtility.getInstance(context).isAlbumsInGrid()){

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_folder_grid, null)

        }
        else{

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_pdf, null)
   }
        return VideoItem(view)
    }

    override fun onBindViewHolder(holder: VideoItem, position: Int) {
        holder.name.text = arrayList.get(position).name
        holder.size.text = arrayList.get(position).size.toString()
        holder.name?.setOnClickListener {
            buttonClick.onPdfCLicked(Uri.parse(arrayList?.get(position).path))
        }


    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
}