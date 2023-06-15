package pro202.exam.UinMNCH.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import pro202.exam.UinMNCH.databinding.ActivityTourEndBinding

class TourEndActivity : AppCompatActivity() {

    // initiation of the variables
    private var binding : ActivityTourEndBinding? = null
    private var btnNext : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // assigning layout to the activity
        binding = ActivityTourEndBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // assigning the xml element to the variable
        btnNext = binding?.buttonNext

        // onClick to launch end story activity
        btnNext?.setOnClickListener{
            val intent = Intent(this, EndStoryActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}