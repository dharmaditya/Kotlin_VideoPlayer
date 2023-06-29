package com.example.kotlinvideoplayer

import android.content.ContentValues.TAG
import android.media.MediaPlayer
import android.util.Log
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.VideoView
import androidx.appcompat.view.menu.MenuView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.samle_item.view.*
import java.io.File

class TheAdapter(private val videoList: List<Images>) :
    RecyclerView.Adapter<TheAdapter.ItemViewHolder>() {

    public lateinit var videoView: VideoView

    private val videoViews = mutableListOf<VideoView>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.samle_item,parent,false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        //val current_item = img_list[position]
        videoView = holder.img
        //holder.img_textview.text = current_item.name
        if(videoList[position].url != null) {
            videoView.setVideoPath(videoList[position].url)

        }

//
//        videoView.setOnPreparedListener(MediaPlayer.OnPreparedListener {
//
//
//            val vid_ratio = it.videoWidth / it.videoHeight
//            val screen_ratio  = holder.img.width / holder.img.height
//
//            Log.d(TAG, "onBindViewHolder: "+vid_ratio+" "+screen_ratio)
//
////            val scale : Float= (vid_ratio/screen_ratio).toFloat()
////            if(scale >=1f){
////                holder.img.scaleX = scale
////            }
////            else{
////                holder.img.scaleY = scale
////            }
//            it.start()
//
//        })

//        videoView.setOnCompletionListener {
//            it.start()
//        }
    }



    
    override fun getItemCount(): Int {
        return videoList.size
    }

    fun getVideoViewAtPosition(position: Int): VideoView? {
        return if (position in videoViews.indices) {
            videoViews[position]
        } else {
            null
        }
    }

    override fun onViewAttachedToWindow(holder: ItemViewHolder) {
        super.onViewAttachedToWindow(holder)
        videoView = holder.img
        videoView.start()
    }

    override fun onViewDetachedFromWindow(holder: ItemViewHolder) {
        super.onViewDetachedFromWindow(holder)
        videoView = holder.img
        videoView.stopPlayback()
    }


    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       // val img_textview = itemView.textView_image
        val img : VideoView = itemView.imageView
    }

}