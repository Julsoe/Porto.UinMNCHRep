package pro202.exam.UinMNCH.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pro202.exam.UinMNCH.databinding.ActivityToursBinding

class ToursActivity : AppCompatActivity() {

    // initiation of the variables
    private var binding : ActivityToursBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // assigning layout to the activity
        binding = ActivityToursBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // onClick to launch the tour
        binding?.titleGameInfinite?.setOnClickListener {
            val intent = Intent(this, TourDescriptionActivity::class.java)
            startActivity(intent)
        }

        // onClick to open reward activity
        binding?.rewardsPage?.setOnClickListener{
            val intent = Intent(this, RewardActivity::class.java)
            startActivity(intent)
        }

        // onClick to open user's profile activity
        binding?.profilePage?.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}