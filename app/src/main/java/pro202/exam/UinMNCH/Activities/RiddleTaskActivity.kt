package pro202.exam.UinMNCH.Activities

import android.Manifest
import android.R
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import pro202.exam.UinMNCH.Constants.Constants
import pro202.exam.UinMNCH.Constants.ImageComparison
import pro202.exam.UinMNCH.Models.ScanTaskModel
import pro202.exam.UinMNCH.databinding.ActivityScanTaskBinding
import pro202.exam.UinMNCH.databinding.DialogResultBinding

class RiddleTaskActivity : AppCompatActivity() {

    // initiation of the layout variables
    private var binding : ActivityScanTaskBinding? = null
    private var btnRewards : ImageView? = null
    private var ivCamera: ImageView? = null
    private var ivPic: ImageView? = null
    private var tvTaskContent1: TextView? = null
    private var tvTaskContent2: TextView? = null
    private var capturedImg : Bitmap? = null
    private var ivStoredImg: ImageView? = null
    private var isSimilar : Boolean? = null
    private var tvTaskTitle : TextView? = null
    private var tvTaskSubTitle : TextView? = null
    private var tvSpeech: TextView? = null
    private var circleImageView: CircleImageView? = null
    private var cipherLayout: LinearLayout? = null

    // initiation of the task variables
    private var taskList : ArrayList<ScanTaskModel>? = null
    private var currentTaskPosition = 1

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // assigning layout to the activity
        binding = ActivityScanTaskBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // assigning xml elements to layout variables
        ivCamera = binding?.ivCamera
        ivPic = binding?.ivPic
        ivStoredImg = binding?.ivStoredImg
        tvTaskTitle = binding?.tvTaskTitle
        tvTaskSubTitle = binding?.tvTaskSubtitle

        // assigning Constants' write task list to the variable
        taskList = Constants.scanTaskList(this)

        // setting up the task view
        setupTaskView()

        // checking permission for camera use
        if (ContextCompat.checkSelfPermission(this@RiddleTaskActivity,
                Manifest.permission.ACCESS_FINE_LOCATION) !==
            PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this@RiddleTaskActivity,
                    Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(this@RiddleTaskActivity,
                    arrayOf(Manifest.permission.CAMERA), 1)
            } else {
                ActivityCompat.requestPermissions(this@RiddleTaskActivity,
                    arrayOf(Manifest.permission.CAMERA), 1)
            }
        }

        // onClick to launch rewards activity
        btnRewards?.setOnClickListener{
            val intent = Intent(this, RewardActivity::class.java)
            startActivity(intent)
        }
    }

    // setting up the task view
    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupTaskView() {

        // assigning xml elements to corresponding variables
        tvTaskTitle = binding?.tvTaskTitle
        tvTaskSubTitle = binding?.tvTaskSubtitle
        tvTaskContent1 = binding?.tvTaskContent1
        tvTaskContent2 = binding?.tvTaskContent2
        ivStoredImg = binding?.ivStoredImg
        tvSpeech = binding?.textViewSpeech
        circleImageView = binding?.circleImageView
        cipherLayout = binding?.cipherLayout

        tvTaskContent2?.visibility = View.INVISIBLE
        ivStoredImg?.visibility = View.INVISIBLE
        tvSpeech?.visibility = View.INVISIBLE
        circleImageView?.visibility = View.INVISIBLE
        cipherLayout?.visibility = View.VISIBLE

        // assigning the task's arguments to the layout variables
        tvTaskTitle?.text = taskList!![currentTaskPosition].getTitle().uppercase()
        tvTaskSubTitle?.text = taskList!![currentTaskPosition].getSubTitle().uppercase()
        tvTaskContent1?.text = taskList!![currentTaskPosition].getContent1()

        // fetching the right picture from Munch API for comparison purposes
        val id = taskList!![currentTaskPosition].getId()
        val url = Constants.URL_1 + id.toString() + Constants.URL_2
        if (Constants.isNetworkAvailable(this)) {
            ivStoredImg?.let {
                // Glide library to load a picture from URL into a xml layout element
                Glide.with(this@RiddleTaskActivity)
                    .load(url)
                    .into(it)
            }
        }
    }

    // opening the camera if permissions granted
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if ((ContextCompat.checkSelfPermission(this@RiddleTaskActivity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) &&
                        (ContextCompat.checkSelfPermission(this@RiddleTaskActivity, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED)
                    ) {
                        // Both camera and internet permissions are granted
                        Log.i("Permission status", "Permission granted")
                        ivCamera?.setOnClickListener {
                            capturePhoto()
                        }
                    } else {
                        // Internet permission is not granted
                        Log.i("Permission status", "Internet permission denied")
                    }
                } else {
                    // Camera permission is not granted
                    Log.i("Permission status", "Camera permission denied")
                }
                return
            }
        }
    }

    // launching the camera
    private fun capturePhoto() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, Constants.REQUEST_CODE)
    }

    // storing the photo taken by the user and comparing it with the correct answer picture
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == Constants.REQUEST_CODE && data != null) {
            capturedImg = data.extras?.get("data") as Bitmap

            if (capturedImg != null) {
                // converting the image from API to bitmap
                val storedImageBitmap = ivStoredImg?.let {
                    ImageComparison.convertImageViewToBitmap(
                        it
                    )
                }
                // comparing the two images
                if (storedImageBitmap != null) {
                    isSimilar = ImageComparison.compareImages(capturedImg!!, storedImageBitmap)
                    displayResultDialog()
                }
            }
        }
    }

    // showing dialog when the photo is taken and compared to the correct answer picture
    @RequiresApi(Build.VERSION_CODES.M)
    private fun displayResultDialog() {
        Log.i("result dialog", "hey")
        val resultDialog = Dialog(this, R.style.Theme_Black_NoTitleBar_Fullscreen)
        resultDialog.setCancelable(false)
        resultDialog.setCanceledOnTouchOutside(false)

        // assign dialog layout to the activity
        val binding = DialogResultBinding.inflate(layoutInflater)
        resultDialog.setContentView(binding.root)

        // assign xml elements to the corresponding variables
        val tvResult = binding.tvResult
        val btnRetake = binding.btnRetake
        val btnNextTask = binding.btnNextTask

        // handling the comparison result
        if (isSimilar == true) {
            // updating task score in database in case of correct answer
            Constants.updateTask(taskList!![currentTaskPosition].getTaskNr(), true, 1)
            // display the result to the user
            tvResult.text = "Correct!"
        } else {
            // display the result to the user
            tvResult.text = "Wrong picture, try again!"
        }

        // onClick to launch camera once again
        btnRetake.setOnClickListener {
            resultDialog.dismiss()
            capturePhoto()
        }

        // onClick to launch next task's activity
        btnNextTask.setOnClickListener {
            resultDialog.dismiss()
            val intent = Intent(this, DisclosureTaskActivity::class.java)
            startActivity(intent)
        }

        resultDialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}