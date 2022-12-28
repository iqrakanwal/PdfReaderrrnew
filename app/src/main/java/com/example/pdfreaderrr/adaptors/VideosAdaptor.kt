package com.example.pdfreaderrr.adaptors

import android.R.attr.button
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.pdfreaderrr.PdfModel
import com.example.pdfreaderrr.R
import com.example.pdfreaderrr.interfaces.OptionMenuClickListener
import com.example.pdfreaderrr.interfaces.PdfClickedListener
import com.example.pdfreaderrr.utills.PreferencesUtility


class VideosAdaptor(
    var itemclicked:OptionMenuClickListener,
    var context: Context,
    var arrayList: ArrayList<PdfModel>,
    var buttonClick: PdfClickedListener
) :
    RecyclerView.Adapter<VideosAdaptor.VideoItem>() {
    inner class VideoItem(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.namePDF)
        var size: TextView = view.findViewById(R.id.sizePDF)
        var options: ImageView = view.findViewById(R.id.imageoption)
        // var fav: ImageView = view.findViewById(R.id.fav)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoItem {
        var view: View
        if (PreferencesUtility.getInstance(context).isAlbumsInGrid()) {

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_folder_grid, null)

        } else {

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

        holder.options?.setOnClickListener {
            val popupMenu = PopupMenu(context, holder.options)
            popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu())
            popupMenu.setOnMenuItemClickListener { menuItem ->

                if ((menuItem.title).equals("Info")) {
                    itemclicked.info(arrayList.get(position))
                    Toast.makeText(
                        context,
                        "You Clicked " + menuItem.title,
                        Toast.LENGTH_SHORT
                    ).show()

                } else if ((menuItem.title).equals("Delete")) {
                    itemclicked.info(arrayList.get(position))
                    Toast.makeText(
                        context,
                        "You Clicked " + menuItem.title,
                        Toast.LENGTH_SHORT
                    ).show()
                } else if ((menuItem.title).equals("Rename")) {
                    itemclicked.info(arrayList.get(position))
                    Toast.makeText(
                        context,
                        "You Clicked " + menuItem.title,
                        Toast.LENGTH_SHORT
                    ).show()

                }

                // Toast message on menu item clicked

                true
            }
            // Showing the popup menu
            // Showing the popup menu
            popupMenu.show()


        }


    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
}