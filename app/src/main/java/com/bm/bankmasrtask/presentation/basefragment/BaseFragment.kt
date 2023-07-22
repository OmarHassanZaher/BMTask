package com.bm.bankmasrtask.presentation.basefragment

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bm.bankmasrtask.R
import dagger.hilt.android.AndroidEntryPoint
import org.aviran.cookiebar2.CookieBar

@AndroidEntryPoint
open class BaseFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_base, container, false)
    }

    fun handleError(message: String) {
        CookieBar.build(activity)
            .setMessage(message)
            .setCookiePosition(Gravity.BOTTOM)
            .setMessageColor(R.color.black)
            .setBackgroundColor(R.color.white)
            .show()
    }
}