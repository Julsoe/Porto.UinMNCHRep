package pro202.exam.UinMNCH.Activities

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi
import pro202.exam.UinMNCH.Services.ApiService
import pro202.exam.UinMNCH.Constants.Constants
import pro202.exam.UinMNCH.R
import pro202.exam.UinMNCH.Models.ScanTaskModel
import pro202.exam.UinMNCH.databinding.ActivityRewardBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RewardActivity : AppCompatActivity() {
    private var binding: ActivityRewardBinding? = null
    private lateinit var apiService: ApiService
    private var taskList : ArrayList<ScanTaskModel>? = null
    private var tvFreeCoffeeCoupon : TextView? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRewardBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // FETCH POINTS FROM DB TO UNLOCK REWARDS ACCORDINGLY
        fetchPointSum(1)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun fetchPointSum(userId: Int) {
        taskList = Constants.scanTaskList(this)
        tvFreeCoffeeCoupon = binding?.freeCoffeeCoupon
        val typefaceUnlocked = resources.getFont(R.font.anton)
        val typefaceLocked = resources.getFont(R.font.alumni_sans)
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5000/User/")  // Replace with the base URL of the API
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiService = retrofit.create(ApiService::class.java)
        val call = apiService.getPointSum(userId)

        call.enqueue(object : Callback<Int> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                if (response.isSuccessful) {
                    val pointSum = response.body()
                    pointSum?.let {
                        // Check if point sum equals task list size
                            if (it == taskList!!.size) {
                                // Unlock the reward by changing the textView
                                tvFreeCoffeeCoupon?.text = "@string/coupon_free_coffee"
                                tvFreeCoffeeCoupon?.textSize = 12F
                                tvFreeCoffeeCoupon?.typeface = typefaceUnlocked
                            } else {
                                tvFreeCoffeeCoupon?.text = "@string/locked_infinite_tour"
                                tvFreeCoffeeCoupon?.textSize = 18F
                                tvFreeCoffeeCoupon?.typeface = typefaceLocked
                            }
                    }
                } else {
                    val errorCode = response.code()
                    Log.i("Point fetching error", "$errorCode")
                }
            }

            override fun onFailure(call: Call<Int>, t: Throwable) {
                Log.i("Point fetching failure", "${t.message}")
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
