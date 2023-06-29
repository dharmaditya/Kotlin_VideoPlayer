package com.example.kotlinvideoplayer
import android.media.MediaPlayer
import android.widget.VideoView
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.samle_item.view.*

class VideoPlayerPageChangeListener(private val viewPager: ViewPager2) : ViewPager2.OnPageChangeCallback() {

    private var currentVideoView: VideoView? = null

    override fun onPageSelected(position: Int) {
        val newVideoView = viewPager.getChildAt(0)?.imageView
        if (newVideoView != currentVideoView) {
            // Pause or stop the previous video player
            currentVideoView?.let {
                if (it.isPlaying) {
                    it.pause() // Pause the video
                    // If you want to stop the video completely, use it.stopPlayback()
                }
            }

            // Start or resume the video player for the currently selected position
            newVideoView?.let {
                if (!it.isPlaying) {
                    it.start() // Start or resume the video
                }
                currentVideoView = it
            }
        }
    }
}
