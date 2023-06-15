package pro202.exam.UinMNCH.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import pro202.exam.UinMNCH.R

/** No connection to any database and thus no possibility to get score in the game */
class GuestTabFragment : Fragment() { // 'Play as guest' alternative
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.guest_tab_fragment, container, false) as ViewGroup
    }
}