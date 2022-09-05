package com.android.surveysaurus.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.surveysaurus.adapter.SurveyAdapter
import com.android.surveysaurus.databinding.FragmentSurveysBinding
import com.android.surveysaurus.model.IsFilledModel
import com.android.surveysaurus.model.ListedSurvey
import com.android.surveysaurus.service.ApiService
import com.android.surveysaurus.singleton.LoginSingleton


//
class SurveysFragment : Fragment(), SurveyAdapter.Listener {
    private var _binding: FragmentSurveysBinding? = null
    private val binding get() = _binding!!
    private lateinit var surveyAdapter: SurveyAdapter
    private var SurveyModels: ArrayList<ListedSurvey> = ArrayList()
    private  var listedSurveys: ArrayList<ListedSurvey> = ArrayList()
    private var controlSample:Boolean=false
    private var controlSurveys:Boolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSurveysBinding.inflate(inflater, container, false)
        val view = binding.root
        val layoutManager =  GridLayoutManager(view.context, 2)
        binding.surveysRecycler.setHasFixedSize(false);
        binding.surveysRecycler.layoutManager = layoutManager
        surveyAdapter = SurveyAdapter(SurveyModels, this@SurveysFragment)
        binding.surveysRecycler.adapter = surveyAdapter

        try {
            val apiService = ApiService()
            apiService.getSurveys {

                if (it != null) {
                    Toast.makeText(
                        view.context,
                        "Succesful", Toast.LENGTH_SHORT
                    ).show();
                    if(controlSample==false){
                        SurveyModels.addAll(it)
                        surveyAdapter.notifyDataSetChanged()

                        binding.surveysRecycler.refreshDrawableState()
                        binding.surveysRecycler.requestLayout()
                        controlSample=true
                    }

                    try {
                        val apiService = ApiService()
                        apiService.getAllSurveys { item->
                            if(item!=null){
                                if(controlSurveys==false){
                                    SurveyModels.addAll(item)
                                    surveyAdapter.notifyDataSetChanged()

                                    binding.surveysRecycler.refreshDrawableState()
                                    binding.surveysRecycler.requestLayout()
                                    println("oldu bu iş")
                                    controlSurveys=true
                                }

                            }
                            else {
                                Toast.makeText(
                                    view.context,
                                    "Fail", Toast.LENGTH_SHORT
                                ).show();

                            }
                        }
                    }
                    catch (e: Exception) {
                        e.printStackTrace()
                    }

                    println("Control")

                } else {
                    Toast.makeText(
                        view.context,
                        "Fail", Toast.LENGTH_SHORT
                    ).show();

                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onItemClick(mySurveyModel: ListedSurvey) {
        if(LoginSingleton.isLogin){
        try {
            val apiService = ApiService()
            val isfilled: IsFilledModel = IsFilledModel(mySurveyModel.title)

            apiService.isFilled(isfilled){
                if (it.toString()!=null) {
                    val action =
                        ViewPagerFragmentDirections.actionViewPagerFragmentToFillSurveyFragment(mySurveyModel,it)
                    Navigation.findNavController(binding.root).navigate(action)
                    println("succes")
                }
                else{
                    val action =
                        ViewPagerFragmentDirections.actionViewPagerFragmentToFillSurveyFragment(mySurveyModel)
                    Navigation.findNavController(binding.root).navigate(action)

                }

            }} catch (e: Exception) {
            e.printStackTrace()
        }}
        else{
            val action =
                ViewPagerFragmentDirections.actionViewPagerFragmentToFillSurveyFragment(mySurveyModel)
            Navigation.findNavController(binding.root).navigate(action)

        }

    }

}