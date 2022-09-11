package dehnavi.sajjad.foodapp.ui.detail.player

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import dehnavi.sajjad.foodapp.databinding.ActivityPlayerBinding
import dehnavi.sajjad.foodapp.utils.VIDEO_ID
import dehnavi.sajjad.foodapp.utils.YOUTUBE_API_KEY
import dehnavi.sajjad.foodapp.utils.showSnackBar

class PlayerActivity : YouTubeBaseActivity() {
    //binding
    private lateinit var binding: ActivityPlayerBinding

    //other
    private var pathId = ""
    private lateinit var mPlayer: YouTubePlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        //fullscreen
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(binding.root)
        //get Data
        intent.getStringExtra(VIDEO_ID)?.let {
            pathId = it
        }
        //Init Views
        binding.apply {
            player.initialize(YOUTUBE_API_KEY, object : YouTubePlayer.OnInitializedListener {
                override fun onInitializationSuccess(
                    p0: YouTubePlayer.Provider?,
                    p1: YouTubePlayer,
                    p2: Boolean
                ) {
                    Log.d("Sajjad", "onInitializationSuccess: $pathId")
                    mPlayer = p1
                    mPlayer.loadVideo(pathId)
                    mPlayer.play()
                }

                override fun onInitializationFailure(
                    p0: YouTubePlayer.Provider?,
                    p1: YouTubeInitializationResult?
                ) {
                    root.showSnackBar("ERROR")
                }

            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPlayer.release()
    }
}