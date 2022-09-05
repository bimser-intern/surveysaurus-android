package com.android.surveysaurus.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.surveysaurus.activity.MainActivity
import com.android.surveysaurus.adapter.SlidePageAdapter
import com.android.surveysaurus.databinding.FragmentViewPagerBinding


class ViewPagerFragment : Fragment() {
    private var _binding: FragmentViewPagerBinding? = null
    private val binding get() = _binding!!
    private val mainActivity: MainActivity = MainActivity()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    /* This fragment is used to slide the page to the left,down or up */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        val view = binding.root
        val fragmentList = arrayListOf<Fragment>(
            HomeFragment(),
            SurveysFragment()
        )
        val adapter = SlidePageAdapter(fragmentList, requireActivity().supportFragmentManager)
        val viewPager = binding.viewPager
        viewPager.adapter = adapter
        arguments?.let {
            val index=ViewPagerFragmentArgs.fromBundle(it).index
            viewPager.setCurrentItem(index)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentList = arrayListOf<Fragment>(
            HomeFragment(),
            SurveysFragment()
        )
        val adapter = SlidePageAdapter(fragmentList, requireActivity().supportFragmentManager)
        val viewPager = binding.viewPager
        viewPager.adapter = adapter
        arguments?.let {
            val index=ViewPagerFragmentArgs.fromBundle(it).index
            viewPager.setCurrentItem(index)
        }
    }

    override fun onResume() {
        super.onResume()
        val fragmentList = arrayListOf<Fragment>(
            HomeFragment(),
            SurveysFragment()
        )
        val adapter = SlidePageAdapter(fragmentList, requireActivity().supportFragmentManager)
        binding.viewPager.adapter = adapter
        arguments?.let {
            val index=ViewPagerFragmentArgs.fromBundle(it).index
            binding.viewPager.setCurrentItem(index)
        }

    }

    override fun onPause() {
        super.onPause()
        binding.viewPager.adapter = null //I don't remember why I did this, I gues
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}