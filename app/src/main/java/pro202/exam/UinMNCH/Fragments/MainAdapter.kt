package pro202.exam.UinMNCH.Fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

// Adapter to switch between sign in tabs (Log in, Sign up or Guest)
class MainAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm!!) {

    override fun getCount(): Int {
        return 3
    }

    // Method to get result while swiping with getItem-method and make new object of login, signup and guest.
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                LoginTabFragment()
            }

            1 -> {
                SignupTabFragment()
            }

            2 -> {
                GuestTabFragment()
            }

            else -> null!!
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        var title: String? = null
        if (position == 0) {
            title = "Login"
        } else if (position == 1) {
            title = "Signup"
        } else if (position == 2) {
            title = "Guest"
        }
        return title
    }
}
