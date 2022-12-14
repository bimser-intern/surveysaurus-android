package com.android.surveysaurus.fragment


import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.VirtualLayout
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.navigation.Navigation
import com.android.surveysaurus.R
import com.android.surveysaurus.activity.MapActivity
import com.android.surveysaurus.databinding.FragmentFillSurveyBinding
import com.android.surveysaurus.model.FillModel
import com.android.surveysaurus.model.IsFilledModel
import com.android.surveysaurus.model.ListedSurvey
import com.android.surveysaurus.model.ResponseIsFilled
import com.android.surveysaurus.service.ApiService
import com.android.surveysaurus.singleton.LoginSingleton


class FillSurveyFragment(val survey: ListedSurvey,val isFilled : ResponseIsFilled?) : Fragment() {
    private var _binding: FragmentFillSurveyBinding? = null
    private val binding get() = _binding!!
    private var index: Int? = null
    private var percentence: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFillSurveyBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var optionList: ArrayList<TextView> = ArrayList()
        var optionRateList: ArrayList<TextView> = ArrayList()
        var linearList: ArrayList<LinearLayout> = ArrayList()
        println("Control")

           /* val survey = ViewPagerFragment2Args.fromBundle(parentFragmentManager.findFragmentById(R.id.viewPagerFragment2)!!.requireArguments()).surveyModel
            val isFilled = ViewPagerFragment2Args.fromBundle(parentFragmentManager.findFragmentById(R.id.viewPagerFragment2)!!.requireArguments()).isFilled
            */

            binding.addQuestionFill.text = survey.question
            binding.addTitleFill.text = survey.title

            for (item in 0 until survey.choices.size) {

                val option1: TextView = TextView(view.context)
                option1.id = View.generateViewId()
                option1.textAlignment = View.TEXT_ALIGNMENT_VIEW_START
                option1.text = survey.choices[item]
                option1.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.vector3, 0, 0, 0)
                option1.setPaddingRelative(20, 0, 0, 0)
                option1.compoundDrawablePadding=20
                option1.setBackgroundResource(R.drawable.question)
                option1.setTextColor(Color.parseColor("#000000"))
                option1.gravity = Gravity.CENTER
                option1.textSize = 15f
                binding.fillLayout.addView(option1)
                optionList.add(option1)
                val params = option1.layoutParams as ConstraintLayout.LayoutParams
                if (item == 0)
                    params.topToBottom = binding.optionsFill.id
                else
                    params.topToBottom = optionList.get(item - 1).id
                params.startToStart = binding.fillLayout.id
                params.endToEnd = binding.fillLayout.id
                params.topMargin = 20
                option1.requestLayout()
            }

            val params = binding.doneLayout.layoutParams as ConstraintLayout.LayoutParams
            params.topToBottom = optionList.get(optionList.lastIndex).id
            params.topMargin = 40
            binding.doneLayout.requestLayout()

            val paramsWorld = binding.worldLayout.layoutParams as ConstraintLayout.LayoutParams
            paramsWorld.topToBottom = optionList.get(optionList.lastIndex).id
            paramsWorld.topMargin = 80
            binding.worldLayout.requestLayout()



            for (item in 0 until optionList.size) {
                if (item == isFilled?.data?.choice) {
                    optionList.get(item).setCompoundDrawablesRelativeWithIntrinsicBounds(
                        R.drawable.selected_option,
                        0,
                        0,
                        0
                    )
                }
                optionList.get(item).setOnClickListener {

                    optionList.get(item).setCompoundDrawablesRelativeWithIntrinsicBounds(
                        R.drawable.selected_option,
                        0,
                        0,
                        0
                    )
                    for (other in 0 until optionList.size) {
                        if (other == item) {
                            index = other
                        } else {
                            optionList.get(other).setCompoundDrawablesRelativeWithIntrinsicBounds(
                                R.drawable.vector3,
                                0,
                                0,
                                0
                            )
                        }
                    }
                }
            }

            binding.doneLayout.setOnClickListener {
                if (index != null) {
                    if (LoginSingleton.isLogin) {

                        try {
                            val apiService = ApiService()
                            var fillModel: FillModel = FillModel(
                                binding.addTitleFill.text.toString(), index!!
                            )

                            apiService.postFillSurvey(fillModel) { response ->

                                if (response.toString() != null) {
                                    Toast.makeText(
                                        view.context,
                                        "Succesful", Toast.LENGTH_SHORT
                                    ).show();
                                    try {
                                        val apiService = ApiService()
                                        val isfilled: IsFilledModel =
                                            IsFilledModel(survey.title)

                                        apiService.getSurvey(isfilled) {
                                            if (it.toString() != null) {

                                                for (item in 0 until optionList.size) {
                                                    binding.fillLayout.removeView(
                                                        view.findViewById<View>(
                                                            optionList.get(item).id
                                                        )
                                                    )

                                                    binding.fillLayout.removeView(binding.doneLayout)

                                                }


                                                for (item in 0 until survey.choices.size) {

                                                    if (item == it?.data?.percent?.indexOf(it.data.percent.max())) {
                                                        val option1: TextView =
                                                            TextView(view.context)
                                                        option1.id = View.generateViewId()
                                                        option1.textAlignment =
                                                            View.TEXT_ALIGNMENT_TEXT_START

                                                        option1.gravity = Gravity.START
                                                        option1.text =
                                                            survey.choices[item]+"  %" + it.data.percent.get(
                                                                item
                                                            )
                                                        val typeface: Typeface? =
                                                            ResourcesCompat.getFont(
                                                                view.context!!,
                                                                com.android.surveysaurus.R.font.roboto
                                                            )
                                                        option1.setTypeface(typeface)
                                                        option1.setPadding(20, 0, 0, 0)
                                                        option1.setBackgroundResource(R.drawable.selected)
                                                        option1.setTextColor(Color.parseColor("#000000"))
                                                        option1.textSize = 15f
                                                        binding.rateLayout.addView(option1)
                                                        optionRateList.add(option1)
                                                        val params =
                                                            option1.layoutParams as ConstraintLayout.LayoutParams
                                                        if (item == 0)
                                                            params.topToBottom =
                                                                binding.optionsFill.id
                                                        else
                                                            params.topToBottom =
                                                                optionRateList.get(item - 1).id
                                                        params.startToStart = binding.rateLayout.id

                                                        params.marginStart = dpToPx(0, view.context)

                                                        params.topMargin = dpToPx(20, view.context)
                                                        params.height = dpToPx(40, view.context)
                                                        params.width = 0
                                                        if(it?.data?.percent!!.get(item)<=10f||it?.data?.percent?.get(item)==null){
                                                           params.width=ConstraintLayout.LayoutParams.WRAP_CONTENT
                                                        }
                                                        else{
                                                            println( (it!!.data!!.percent!!.get(item).div(100f)))
                                                            params.matchConstraintPercentWidth = (it!!.data!!.percent!!.get(item).div(100f))-0.000001f
                                                        }
                                                        option1.requestLayout()


                                                    } else {

                                                        val option1: TextView =
                                                            TextView(view.context)
                                                        option1.id = View.generateViewId()
                                                        option1.textAlignment =
                                                            View.TEXT_ALIGNMENT_VIEW_START
                                                        option1.text =
                                                            survey.choices[item] +"  %"+ it?.data?.percent?.get(
                                                                item
                                                            )
                                                        option1.gravity = Gravity.START
                                                        option1.setBackgroundResource(R.drawable.unselected)
                                                        option1.setTextColor(Color.parseColor("#000000"))
                                                        option1.textSize = 15f
                                                        val typeface: Typeface? =
                                                            ResourcesCompat.getFont(
                                                                view.context!!,
                                                                com.android.surveysaurus.R.font.roboto
                                                            )
                                                        option1.setTypeface(typeface)
                                                        option1.setPadding(20, 0, 0, 0)
                                                        binding.rateLayout.addView(option1)
                                                        optionRateList.add(option1)
                                                        val params =
                                                            option1.layoutParams as ConstraintLayout.LayoutParams
                                                        if (item == 0)
                                                            params.topToBottom =
                                                                binding.optionsFill.id
                                                        else
                                                            params.topToBottom =
                                                                optionRateList.get(item - 1).id
                                                        params.startToStart = binding.rateLayout.id

                                                        params.topMargin = dpToPx(20, view.context)
                                                         params.marginStart = dpToPx(0, view.context)

                                                        params.height = dpToPx(40, view.context)
                                                        params.width = 0
                                                        if(it?.data?.percent!!.get(item)<=10f||it?.data?.percent?.get(item)==null){
                                                            params.width=ConstraintLayout.LayoutParams.WRAP_CONTENT
                                                        }
                                                        else{
                                                            params.matchConstraintPercentWidth = (it!!.data!!.percent!!.get(item).div(100f))
                                                        }

                                                        option1.requestLayout()


                                                    }
                                                }

                                               binding.optionsFill.text="Rates"
                                                val paramsWorld = binding.worldLayout.layoutParams as ConstraintLayout.LayoutParams
                                                paramsWorld.topToBottom = optionList.get(optionRateList.lastIndex).id
                                                paramsWorld.topMargin = 40
                                                binding.worldLayout.requestLayout()

                                                /*  for(item in 0 until optionRateList.size){
                                                      val yuzde:TextView= TextView(view.context)
                                                      yuzde.id= View.generateViewId()
                                                      yuzde.text="%0.3"
                                                      yuzde.setTextColor(Color.parseColor("#000000"))
                                                      yuzde.textSize = 15f
                                                      val paramsYuzde =
                                                          yuzde.layoutParams as ConstraintLayout.LayoutParams
                                                      paramsYuzde.startToEnd=optionRateList.get(item).id
                                                      paramsYuzde.topToTop=optionRateList.get(item).id
                                                      paramsYuzde.bottomToBottom=optionRateList.get(item).id
                                                      paramsYuzde.width=dpToPx(20, view.context)
                                                      paramsYuzde.height=dpToPx(20, view.context)
                                                      yuzde.requestLayout()
                                                      binding.root.addView(yuzde)

                                                  }*/



                                                println("succes")
                                            } else {

                                                println("fail")
                                            }

                                        }
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }


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
                    } else {
                        var fillModel: FillModel = FillModel(
                            binding.addTitleFill.text.toString(), index!!
                        )
                        val action =
                            ViewPagerFragment2Directions.actionViewPagerFragment2ToLoginFragment(null,fillModel)
                        Navigation.findNavController(it).navigate(action)
                    }

                } else {
                    Toast.makeText(view.context, "Please select one of options", Toast.LENGTH_SHORT)
                        .show()
                }


            }

            binding.worldLayout.setOnClickListener {
                val intent = Intent(it.context, MapActivity::class.java)
                intent.putExtra("title",survey.title)
                startActivity(intent)

            }



    }

    fun dpToPx(dp: Int, context: Context): Int {
        val density: Float = context.getResources().getDisplayMetrics().density
        return Math.round(dp.toFloat() * density)
    }

}


