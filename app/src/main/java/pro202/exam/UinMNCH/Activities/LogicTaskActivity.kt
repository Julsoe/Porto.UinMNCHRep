package pro202.exam.UinMNCH.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import pro202.exam.UinMNCH.Constants.Constants
import pro202.exam.UinMNCH.databinding.ActivityLogicBinding

class LogicTaskActivity : AppCompatActivity() {

    // initiation of the variables
    private var binding : ActivityLogicBinding? = null
    private var btnNext : Button? = null
    private var btnRewards : ImageView? = null
    private var inputKiss : EditText? = null
    private var inputBeach : EditText? = null
    private var inputHorse : EditText? = null
    private var inputHouse : EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // assigning layout to the activity
        binding = ActivityLogicBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // assigning xml elements to variables
        btnNext = binding?.buttonNext
        btnRewards = binding?.btnRewards
        inputKiss = binding?.inputKiss
        inputBeach = binding?.inputBeach
        inputHorse = binding?.inputHorse
        inputHouse = binding?.inputHouse

        // check if user's answer is correct and if so, update score and launch the next task's activity
        btnNext?.setOnClickListener{
            val letterInput1 = inputKiss?.text.toString().lowercase()
            val letterInput2 = inputBeach?.text.toString().lowercase()
            val letterInput3 = inputHorse?.text.toString().lowercase()
            val letterInput4 = inputHouse?.text.toString().lowercase()

            val entireInput = letterInput1 + letterInput2 + letterInput3 + letterInput4

            if(entireInput == "krig"){
                Constants.updateTask("task1", true, 1)
                val intent = Intent(this, InterpretTaskActivity::class.java)
                startActivity(intent)
            }
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