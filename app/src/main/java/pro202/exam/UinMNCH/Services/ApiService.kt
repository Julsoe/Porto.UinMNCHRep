package pro202.exam.UinMNCH.Services

import com.example.apitest.CreateUserRequest
import com.example.apitest.UpdateScoreboardInfinitev1
import com.example.apitest.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiService {
    @GET("TestConnection")
    fun testConnection(): Call<String>

    @GET("GetUsers")  // Retrieve the list of users
    fun getUsers(): Call<ArrayList<User>>

    @POST("AddNewUser")  // Create new user in db
    fun createUser(@Body request: CreateUserRequest): Call<ResponseBody>

    @PUT("UpdateScoreInInfinite_v1Table") // Update task result in case of correct answer (false -> true)
    fun updateScoreInInfinitev1Table(@Body request: UpdateScoreboardInfinitev1): Call<Void>

    @GET("GetSumOfScoreBoardInfinite_v1") // Retrieve the sum of all right answers in order to unlock rewards
    fun getPointSum(@Query("userId") userId: Int): Call<Int>

}