package pro202.exam.UinMNCH.Constants

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.widget.ImageView
import org.opencv.android.Utils
import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc

object ImageComparison {

    // Final comparison if two images
    fun compareImages(userImage: Bitmap, storedImage: Bitmap): Boolean {

        // Convert images to Mat
        val userImage = bitmapToMat(userImage)
        val storedImage = bitmapToMat(storedImage)


    //OpenCV library functions

        // Convert images to grayscale
        val userGray = Mat()
        val storedGray = Mat()
        Imgproc.cvtColor(userImage, userGray, Imgproc.COLOR_BGR2GRAY)
        Imgproc.cvtColor(storedImage, storedGray, Imgproc.COLOR_BGR2GRAY)

        // Resize images to a fixed size
        val resizedUser = Mat()
        val resizedStored = Mat()
        Imgproc.resize(userGray, resizedUser, Size(200.0, 200.0))
        Imgproc.resize(storedGray, resizedStored, Size(200.0, 200.0))

        // Compute image hashes
        val userHash = computeHash(resizedUser)
        val storedHash = computeHash(resizedStored)

        // Compare hash values and compute similarity score
        val similarityScore = computeSimilarity(userHash, storedHash)

        // Set a similarity threshold and determine if images are similar
        val similarityThreshold = 80.0
        Log.d("Similarity", similarityScore.toString())
        return similarityScore >= similarityThreshold
    }

    // helping function to compute image hash for later comparison
    fun computeHash(image: Mat): String {

        // Resize the image to a fixed size
        val resizedImage = Mat()
        Imgproc.resize(image, resizedImage, Size(8.0, 8.0))

        // Calculate the average pixel value
        val meanValue = Core.mean(resizedImage).`val`[0]

        // Compute the hash by comparing each pixel with the average value
        val hash = StringBuilder()
        for (i in 0 until resizedImage.rows()) {
            for (j in 0 until resizedImage.cols()) {
                val pixelValue = resizedImage.get(i, j)[0]
                if (pixelValue >= meanValue) {
                    hash.append("1")
                } else {
                    hash.append("0")
                }
            }
        }

        return hash.toString()
    }

    // Compute similarity of two hashes
    fun computeSimilarity(hash1: String, hash2: String): Double {
        require(hash1.length == hash2.length) { "Hash lengths must be equal" }

        var distance = 0
        for (i in hash1.indices) {
            if (hash1[i] != hash2[i]) {
                distance++
            }
        }

        // Calculate the similarity as a percentage
        val similarity = (hash1.length - distance) / hash1.length.toDouble() * 100.0

        return similarity
    }

    // Helping function to convert Bitmap to Mat required for OpenCV methods
    fun bitmapToMat(bitmap: Bitmap): Mat {
        val mat = Mat(bitmap.height, bitmap.width, CvType.CV_8UC3)
        Utils.bitmapToMat(bitmap, mat)
        return mat
    }

    // Helping function to convert Image to Bitmap required for later Mat conversion
    fun convertImageViewToBitmap(imageView: ImageView): Bitmap? {
        val drawable = imageView.drawable ?: return null

        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }

        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        return bitmap
    }
}