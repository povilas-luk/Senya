package com.example.senya.ui.fragments

import androidx.fragment.app.Fragment
import com.example.senya.arch.AttractionsViewModel
import com.example.senya.ui.MainActivity

abstract class BaseFragment : Fragment() {

    protected val navController by lazy {
        (activity as MainActivity).navController
    }

    protected  val activityViewModel: AttractionsViewModel
        get() =  (activity as MainActivity).viewModel


}