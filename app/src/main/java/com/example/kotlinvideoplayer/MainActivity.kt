package com.example.kotlinvideoplayer

import android.Manifest
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.VideoView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    val image_iist = ArrayList<Images>()

    val PERMISSION_REQUEST_CODE = 1001
    private lateinit var fileName:StringBuilder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_CODE)
        }


        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED){
        val path = Environment.getExternalStorageDirectory().toString()+"/Download"
        Log.d("FilePath", " path - $path")
        val directory = File(path)

        if(!directory.canRead()){
            Log.d("FilePath","Not Readable")
        }
        if(directory.exists() && directory.isDirectory) {

            val files = directory.listFiles { file ->
                file.isFile && file.extension in listOf("mp4", "mkv")
            }
            fileName = StringBuilder()
            for (file in files!!) {
                Log.d("FilePath", file.absolutePath)
                val uri = Uri.fromFile(file)
                val url = uri.toString()
                image_iist.add(Images(url,file.name,this))
//                val imageview  = imageView
//                Glide.with(this)
//                    .load(File(file.absolutePath))
//                    .into(imageView)
                fileName.append(file.name)
                fileName.append(" - ")
            }
            val recyclerView  = recycler
            recycler.adapter = TheAdapter(image_iist)
//            val onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
//                override fun onPageSelected(position: Int) {
//                    // Call your method here
//                    // This method will be triggered when the ViewPager2 is slid
//                    Log.d(TAG, "onPageSelected: "+image_iist[position].name+" "+position)
//                    //recycler.adapter = TheAdapter(image_iist[position].url,position,image_iist.size)
//                    //changeAdapter(position)
//
//                }
//            }

            val pageChangeListener = VideoPlayerPageChangeListener(recyclerView)
            recyclerView.registerOnPageChangeCallback(pageChangeListener)

//            recycler.registerOnPageChangeCallback(onPageChangeCallback)
//            //textview.text = fileName
        }
        }


    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission is granted, access external storage

                } else {
                    finish()
                    // Permission is denied, show an explanation or disable functionality that depends on the permission
                }
            }
        }
    }
    private fun playVideoAtPosition(position: Int) {

        val videoView = (recycler.adapter as? TheAdapter)?.getVideoViewAtPosition(position)
        videoView?.start()
    }

    public fun changeAdapter(position: Int){
        recycler.adapter = TheAdapter(image_iist)
    }

    private fun pauseCurrentVideo() {
        val currentVideoView = (recycler.adapter as? TheAdapter)?.getVideoViewAtPosition(recycler.currentItem)
        currentVideoView?.pause()
    }

}