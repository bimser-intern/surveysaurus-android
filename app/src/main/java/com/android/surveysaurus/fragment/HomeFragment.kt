package com.android.surveysaurus.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.android.surveysaurus.activity.MainActivity
import com.android.surveysaurus.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
private  var _binding: FragmentHomeBinding?=null
    private val binding get() = _binding!!
    private  val mainActivity: MainActivity = MainActivity()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Navigates the user to the create survey page when the button is pressed.
        binding.createASurvey.setOnClickListener{
            val action = ViewPagerFragmentDirections.actionViewPagerFragmentToCreateSurveyFragment()
            Navigation.findNavController(it).navigate(action)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}