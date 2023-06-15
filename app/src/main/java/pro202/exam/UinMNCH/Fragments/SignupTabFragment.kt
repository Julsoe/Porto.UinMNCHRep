package pro202.exam.UinMNCH.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import pro202.exam.UinMNCH.Services.ApiService
import com.example.apitest.CreateUserRequest
import okhttp3.ResponseBody
import pro202.exam.UinMNCH.Activities.ToursActivity
import pro202.exam.UinMNCH.R
import retrofit2.Retrofit
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback

class SignupTabFragment : Fragment() { // Sign in as a new user

    private lateinit var apiService: ApiService // for later connection to database

    // initiation of sign up variables
    private var username: EditText? = null
    private var dateOfBirth: EditText? = null
    private var email: EditText? = null
    private var password: EditText? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.signup_tab_fragment, container, false)
        val btnRegister = view.findViewById<Button>(R.id.register)

        // Assign xml elements to variables
        username = view.findViewById(R.id.username)
        dateOfBirth = view.findViewById(R.id.age)
        email = view.findViewById(R.id.email_signup)
        password = view.findViewById(R.id.pass_signup)

        // Convert input to String
        val username = username.toString()
        val dateOfBirth = dateOfBirth.toString()
        val email = email.toString()
        val password = password.toString()

        // Create the Retrofit instance
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5000/User/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Create the service interface
        apiService = retrofit.create(ApiService::class.java)

        btnRegister.setOnClickListener {
            // Create new user in database
            val createUserRequest = CreateUserRequest(
                email = email,
                firstName = username,
                lastName = username,
                password = password,
                dateOfBirth = dateOfBirth
            )

            val successfulCreation = addNewUser(createUserRequest)

            //Go further to the list if tours in case of successful sign up
            if(successfulCreation) {
                val intent = Intent(activity, ToursActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(requireActivity(), "An error occurred", Toast.LENGTH_LONG).show()
            }
        }
        return view
    }

    // Make a HTTP request to add a new user
    private fun addNewUser(user: CreateUserRequest) : Boolean {

        // Call an user creation function from API Interface
        val callAddNewUser: Call<ResponseBody> = apiService.createUser(user)

        // Local variable to determine the sign up result
        var success = false

        callAddNewUser.enqueue(object : Callback<ResponseBody> {

            //Handle successful response
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                success = if (response.isSuccessful) {
                    Log.i("New-user-response", "Successful")
                    true
                // Handle API call failure
                } else {
                    val errorCode = response.code()
                    Log.i("New-user-response", "$errorCode")
                    Toast.makeText(requireActivity(), "Sign up failed", Toast.LENGTH_LONG).show()
                    false
                }
            }

            // Handle network request failure
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.i("New-user-response", "Response failure ${t.message}")
                Toast.makeText(requireActivity(), "An error occurred", Toast.LENGTH_LONG).show()
                success = false
            }
        })
        return success
    }
}
