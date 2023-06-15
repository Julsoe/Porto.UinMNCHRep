package pro202.exam.UinMNCH.Activities


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import pro202.exam.UinMNCH.Services.ApiService
import com.example.apitest.User
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import org.opencv.android.OpenCVLoader
import pro202.exam.UinMNCH.Fragments.MainAdapter
import pro202.exam.UinMNCH.R
import pro202.exam.UinMNCH.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    // initiation of the variables
    private var binding : ActivityMainBinding? = null

    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        OpenCVLoader.initDebug() // For OpenCV library that is used for image comparison

//LOGIN TABS


        // Binding the variables to their corresponding views in xml.
        tabLayout = findViewById(R.id.tab_layout)
        viewPager = findViewById(R.id.view_pager)


        // Adding tabs to tabLayout.
        tabLayout?.tabGravity = TabLayout.GRAVITY_FILL

        // Creating an adapter instance for tabs handling
        val adapter = MainAdapter(supportFragmentManager)


        // tabViewPager is set to adapter.
        viewPager?.adapter = adapter


        // Adding PageChangerListener to tabViewPager.
        viewPager?.addOnPageChangeListener(TabLayoutOnPageChangeListener(tabLayout))

        // Assigning and ViewPager Instance for switching between tabs
        tabLayout?.setupWithViewPager(viewPager)


//API
        // Create the Retrofit instance
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5000/User/")  // Replace with the base URL of the API
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Create the service interfaces
        val apiService = retrofit.create(ApiService::class.java)


        // Make the HTTP request for testing connection
        val callTestConnection: Call<String> = apiService.testConnection()
        callTestConnection.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    val date: String? = response.body()
                    if (!date.isNullOrBlank()) {
                        Log.d("API Response", "The date is: $date")
                    } else {
                        Log.e("API Response", "Empty or null response")
                    }
                } else {
                    val errorCode = response.code()
                    Log.e("API Response", "Request failed with status code: $errorCode")
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("API Response", "Request failed with error: ${t.message}")
            }
        })
        // Make the HTTP request for getting all users
        val callGetUsers: Call<ArrayList<User>> = apiService.getUsers()
        callGetUsers.enqueue(object : Callback<ArrayList<User>> {
            override fun onResponse(call: Call<ArrayList<User>>, response: Response<ArrayList<User>>) {
                if (response.isSuccessful) {
                    val userList: ArrayList<User>? = response.body()
                    Log.d("API Response", "The response is: $userList")
                } else {
                    val errorCode = response.code()
                    Log.e("API Response", "Request failed with status code: $errorCode")
                }
            }
            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                Log.e("API Response", "Request failed with error: ${t.message}")
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}