package pro202.exam.UinMNCH.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pro202.exam.UinMNCH.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private var binding : ActivityProfileBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}