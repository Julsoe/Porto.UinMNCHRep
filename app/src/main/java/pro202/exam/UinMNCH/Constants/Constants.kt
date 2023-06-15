package pro202.exam.UinMNCH.Constants

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log


import androidx.annotation.RequiresApi
import pro202.exam.UinMNCH.Services.ApiService
import com.example.apitest.UpdateScoreboardInfinitev1
import pro202.exam.UinMNCH.Models.ScanTaskModel
import pro202.exam.UinMNCH.Models.WriteTaskModel
import pro202.exam.UinMNCH.R
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object Constants {

    // Image URL - the image's ID value is in the middle of the query
    const val URL_1 = "https://emuseum.oslo.kommune.no/apis/iiif/image/v2/" // The first part of Munch API's image url
    const val URL_2 = "/full/full/0/default.jpg" // The first part of Munch API's image url

    const val REQUEST_CODE = 200 // For the camera launching function

    // Create a list of tasks of type ScanTaskModel
    fun scanTaskList(context: Context): ArrayList<ScanTaskModel>{
        val scanTaskList = ArrayList<ScanTaskModel>()
        val task1 = ScanTaskModel(
            4460, // ID found in Munch's API
            "task2",
            "Tolke",
            "The missing painting",
            context.getString(R.string.speech_tolke1),
            context.getString(R.string.speech_tolke2)
        )
        scanTaskList.add(task1)
        val task2 = ScanTaskModel(
            4746, // ID found in Munch's API
            "task5",
            "Gåten",
            "The missing painting",
            context.getString(R.string.speech_gaaten1),
            ""
        )
        scanTaskList.add(task2)
        return scanTaskList
    }

    // Create a list of tasks of type WriteTaskModel
    fun writeTaskList(context: Context): ArrayList<WriteTaskModel>{
        val writeTaskList = ArrayList<WriteTaskModel>()

        val task1 = WriteTaskModel(
            "task3",
            "Koden",
            "The missing painting",
            context.getString(R.string.speech_koden1),
            context.getString(R.string.speech_koden2),
            "skrik"
        )
        writeTaskList.add(task1)

        val task2 = WriteTaskModel(
            "task4",
            "Observere",
            "The missing painting",
            context.getString(R.string.speech_observere1),
            context.getString(R.string.speech_observere2),
            "oslofjorden"
        )
        writeTaskList.add(task2)

        val task3 = WriteTaskModel(
            "task6",
            "Avsløringen",
            "The missing painting",
            context.getString(R.string.speech_avsloeringen1),
            context.getString(R.string.speech_avsloeringen2),
            "rod villvin"
        )
        writeTaskList.add(task3)

        return writeTaskList
    }




    // Check if network is available
    @RequiresApi(Build.VERSION_CODES.M)
    fun isNetworkAvailable(context: Context) : Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when{
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    // Update task score in the database
    fun updateTask(taskAndNumber: String, solved: Boolean, userId: Int) {
        val request = UpdateScoreboardInfinitev1(taskAndNumber, solved, userId)
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5000/User/") // TODO: move to Constants
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(ApiService::class.java)
        val call = apiService.updateScoreInInfinitev1Table(request)

        call.enqueue(object : retrofit2.Callback<Void> {
            override fun onResponse(call: Call<Void>, response: retrofit2.Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("API Response", "Scoreboard updated.")
                } else {
                    Log.d("API Response", "Unable to update scoreboard")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("API Response", "Request failed with error: ${t.message}")
            }
        })
    }
}