package pro202.exam.UinMNCH.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import pro202.exam.UinMNCH.databinding.ActivityDetectiveBinding

class DetectiveActivity : AppCompatActivity() {

    // initiation of the variables
    private var binding : ActivityDetectiveBinding? = null
    private var btnNext : Button? = null
    private var btnRewards : ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // assigning layout to the activity
        binding = ActivityDetectiveBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // assigning xml elements to variables
        btnNext = binding?.buttonNext
        btnRewards = binding?.btnRewards

        // onClick to launch the first task activity
        btnNext?.setOnClickListener{
            val intent = Intent(this, LogicTaskActivity::class.java)
            startActivity(intent)
        }

        // onClick to launch rewards activity
        btnRewards?.setOnClickListener{
            val intent = Intent(this, RewardActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}