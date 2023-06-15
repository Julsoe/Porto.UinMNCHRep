package pro202.exam.UinMNCH.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import pro202.exam.UinMNCH.Services.ApiService
import com.example.apitest.User
import pro202.exam.UinMNCH.Activities.ToursActivity
import pro202.exam.UinMNCH.R
import retrofit2.Response
import retrofit2.Call
import retrofit2.Callback

class LoginTabFragment : Fragment() { // Log in as an existing user

    private var apiService: ApiService? = null// for later connection to database

    // initiation of log in variables
    private var emailInput : EditText? = null
    private var passwordInput : EditText? = null
    private var btnLogin : Button? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Assign xml elements to variables
        val view = layoutInflater.inflate(R.layout.login_tab_fragment, container, false)
        btnLogin = view.findViewById(R.id.btnLogin)
        emailInput = view.findViewById(R.id.email)
        passwordInput = view.findViewById(R.id.pass)

        // Convert input to String
        val email = emailInput?.text.toString()
        val password = passwordInput?.text.toString()

        /** In case of failed database connection during evaluation, the button is programmed to take the user further no matter of login result */
        btnLogin?.setOnClickListener {
            loginUser(email, password)
            val intent = Intent(activity, ToursActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    // Log in with the user data
    private fun loginUser(email: String, password: String) {
        apiService?.getUsers()?.enqueue(object : Callback<ArrayList<User>> {
            override fun onResponse(
                call: Call<ArrayList<User>>,
                response: Response<ArrayList<User>>
            ) {
                // Handle successful response
                if (response.isSuccessful) {
                    val userList = response.body()
                    userList?.let {
                        val user = it.find { it.email == email && it.password == password }
                        // Handle successful log in
                        if (user != null) {
                            val intent = Intent(activity, ToursActivity::class.java)
                            startActivity(intent)
                        // Handle log in failure
                        } else {
                            Toast.makeText(requireActivity(), "Wrong e-mail or password", Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    // Handle API call failure
                    Toast.makeText(requireActivity(), "Log in failed", Toast.LENGTH_LONG).show()
                }
            }

            // Handle network request failure
            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                Log.i("Log-in-response", "Response failure ${t.message}")
                Toast.makeText(requireActivity(), "An error occurred", Toast.LENGTH_LONG).show()
            }
        })
    }
}
