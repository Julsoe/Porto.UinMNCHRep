package pro202.exam.UinMNCH.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pro202.exam.UinMNCH.databinding.ActivityTourDescriptionBinding

class TourDescriptionActivity : AppCompatActivity() {

    // initiation of the variables
    private var binding : ActivityTourDescriptionBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // assigning layout to the activity
        binding = ActivityTourDescriptionBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // onClick to start the tour
        binding?.btnStart?.setOnClickListener {
            val intent = Intent(this, DetectiveActivity::class.java)
            startActivity(intent)
        }

        // onClick to go back to tour description activity
        binding?.ivBack?.setOnClickListener {
            val intent = Intent(this, ToursActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}