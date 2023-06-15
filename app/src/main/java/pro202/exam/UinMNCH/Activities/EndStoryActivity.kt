package pro202.exam.UinMNCH.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.viewbinding.ViewBinding
import pro202.exam.UinMNCH.R
import pro202.exam.UinMNCH.databinding.ActivityEnd1Binding
import pro202.exam.UinMNCH.databinding.ActivityEnd2Binding
import pro202.exam.UinMNCH.databinding.ActivityEnd3Binding

class EndStoryActivity : AppCompatActivity() {

    // initiation of the variables
    private var binding: ViewBinding? = null
    private var currentLayoutIndex = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // assigning layout to the activity
        binding = ActivityEnd1Binding.inflate(layoutInflater)
        setContentView(binding?.root)

        // assigning xml element to the variable
        val buttonNext = binding?.root?.findViewById<Button>(R.id.buttonNext)

        // onClick to change layout to the next one (for clicking through "story tabs")
        buttonNext?.setOnClickListener {

            // Increment the layout index
            currentLayoutIndex++

            // Update the binding instance based on the layout index
            binding = when (currentLayoutIndex) {
                2 -> {
                    // Inflate the activity_end_2 layout
                    val end2Binding = ActivityEnd2Binding.inflate(layoutInflater)

                    // onClick to change the layout to the next one
                    end2Binding.buttonNext.setOnClickListener {

                        // Increment the layout index
                        currentLayoutIndex++

                        // Update the binding instance to the next layout
                        binding = ActivityEnd3Binding.inflate(layoutInflater)


                        binding?.let { binding ->
                            // onClick to go over to tours activity
                            binding.root.findViewById<Button>(R.id.buttonNext)?.setOnClickListener {
                                // Start the ToursActivity
                                val intent = Intent(this@EndStoryActivity, ToursActivity::class.java)
                                startActivity(intent)
                            }
                        }

                        // change the layout to the next one
                        binding?.let {
                            setContentView(it.root)
                        }
                    }

                    end2Binding
                }
                3 -> ActivityEnd3Binding.inflate(layoutInflater)
                else -> ActivityEnd1Binding.inflate(layoutInflater)
            }

            // change the layout to the next one
            binding?.let {
                setContentView(it.root)
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
