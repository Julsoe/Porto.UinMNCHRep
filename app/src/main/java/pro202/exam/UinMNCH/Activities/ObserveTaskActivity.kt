package pro202.exam.UinMNCH.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import pro202.exam.UinMNCH.Constants.Constants
import pro202.exam.UinMNCH.Models.WriteTaskModel
import pro202.exam.UinMNCH.databinding.ActivityObserveBinding

class ObserveTaskActivity : AppCompatActivity() {

    // initiation of the variables
    private var binding : ActivityObserveBinding? = null
    private var btnNext : Button? = null
    private var btnRewards : ImageView? = null
    private var tvTaskTitle : TextView? = null
    private var tvTaskSubTitle : TextView? = null
    private var tvTaskContent1 : TextView? = null
    private var tvTaskContent2 : TextView? = null
    private var inputAnswer : EditText? = null

    // initiation of the task variables
    private var taskList : ArrayList<WriteTaskModel>? = null
    private var currentTaskPosition = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // assigning layout to the activity
        binding = ActivityObserveBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // assigning xml elements to variables
        btnNext = binding?.buttonNext
        btnRewards = binding?.btnRewards
        inputAnswer = binding?.inputAnswer

        // assigning Constants' write task list to the variable
        taskList = Constants.writeTaskList(this)

        // setting up the task view
        setupTaskView()

        // check if user's answer is correct and if so, update score and launch the next task's activity
        btnNext?.setOnClickListener {
            val answer = inputAnswer?.text.toString().trim().lowercase()
            if (answer == taskList!![currentTaskPosition].getAnswer()) {
                Constants.updateTask("task4", true, 1)
                val intent = Intent(this, RiddleTaskActivity::class.java)
                startActivity(intent)
            }
        }

        // onClick to launch rewards activity
        btnRewards?.setOnClickListener{
            val intent = Intent(this, RewardActivity::class.java)
            startActivity(intent)
        }
    }

    // setting up the task view
    private fun setupTaskView() {

        // assigning xml elements to corresponding variables
        tvTaskTitle = binding?.tvTaskTitle
        tvTaskSubTitle = binding?.tvTaskSubtitle
        tvTaskContent1 = binding?.tvTaskContent1
        tvTaskContent2 = binding?.tvTaskContent2

        // assigning the task's arguments to the layout variables
        tvTaskTitle?.text = taskList!![currentTaskPosition].getTitle().uppercase()
        tvTaskSubTitle?.text = taskList!![currentTaskPosition].getSubTitle().uppercase()
        tvTaskContent1?.text = taskList!![currentTaskPosition].getContent1()
        tvTaskContent2?.text = taskList!![currentTaskPosition].getContent2()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}