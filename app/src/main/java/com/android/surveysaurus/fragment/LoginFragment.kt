package com.android.surveysaurus.fragment

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.android.surveysaurus.activity.MainActivity
import com.android.surveysaurus.databinding.FragmentLoginBinding
import com.android.surveysaurus.model.LoginModel
import com.android.surveysaurus.service.ApiService
import com.android.surveysaurus.singleton.LoginSingleton


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private var isVisible: Boolean? =false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.visiblePassword.setOnClickListener {

            if(isVisible==false){
                binding.editTextTextPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD// PRESSED
                isVisible=true
            }
            else{
                binding.editTextTextPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD// RELEASED
                isVisible=false
            }
        }


        binding.donTHave.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
            Navigation.findNavController(it).navigate(action)
        }


        binding.button.setOnClickListener {
            val email = binding.editTextTextEmailAddress.text
            val password = binding.editTextTextPassword.text
            if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {

                if (!email.endsWith(".com") || !email.contains("@")) {
                    Toast.makeText(
                        view.context,
                        "Incorrect email or password", Toast.LENGTH_SHORT
                    ).show()
                } else if (password.length < 8) {
                    Toast.makeText(
                        view.context,
                        "Your password needs to contain at least 8 letters", Toast.LENGTH_SHORT
                    ).show()
                } else {
                    try {

                        val apiService = ApiService()
                        var loginModel: LoginModel = LoginModel(
                            email.toString(), password.toString()
                        )
                        apiService.postLogin(loginModel) {
                            if (it != null) {
                                var text: String = it.string()
                                var t??rnak = '"'

                                LoginSingleton.token = text.substringAfter("accessToken$t??rnak")
                                    .substringBefore(t??rnak + "data").substringAfter(t??rnak)
                                    .substringBefore(t??rnak)
                                println(LoginSingleton.token)
                                LoginSingleton.name = text.substringAfter("name$t??rnak")
                                    .substringBefore(t??rnak + "gender").substringAfter(t??rnak)
                                    .substringBefore(t??rnak)
                                LoginSingleton.gender = text.substringAfter("gender$t??rnak")
                                    .substringBefore(t??rnak + "email").substringAfter(t??rnak)
                                    .substringBefore(t??rnak)
                                LoginSingleton.country = text.substringAfter("country$t??rnak")
                                    .substringBefore(t??rnak + "message").substringAfter(t??rnak)
                                    .substringBefore(t??rnak)
                                LoginSingleton.city = text.substringAfter("city$t??rnak")
                                    .substringBefore(t??rnak + "country").substringAfter(t??rnak)
                                    .substringBefore(t??rnak)
                                LoginSingleton.email = text.substringAfter("email$t??rnak")
                                    .substringBefore(t??rnak + "city").substringAfter(t??rnak)
                                    .substringBefore(t??rnak)
                                Toast.makeText(
                                    view.context,
                                    "Succesfully logined " + LoginSingleton.name, Toast.LENGTH_LONG
                                ).show();
                                LoginSingleton.isLogin = true
                                (activity as MainActivity?)!!.MenuController()
                                arguments?.let {
                                    val survey = LoginFragmentArgs.fromBundle(it).survey
                                    val filled = LoginFragmentArgs.fromBundle(it).filled
                                    if(survey!=null){
                                        apiService.postCreateSurvey(survey){
                                            if (it.toString()!=null){
                                                Toast.makeText(
                                                    view.context,
                                                    "Succesfully created survey " , Toast.LENGTH_LONG
                                                ).show();
                                                val action =
                                                    LoginFragmentDirections.actionLoginFragmentToCreateSurveyFragment()
                                                Navigation.findNavController(view).navigate(action)
                                            }

                                        }
                                    }
                                    if(filled!=null){
                                        apiService.postFillSurvey(filled) {
                                            if (it.toString()!=null){
                                                Toast.makeText(
                                                    view.context,
                                                    "Succesfully filled survey " , Toast.LENGTH_LONG
                                                ).show();
                                                val action =
                                                    LoginFragmentDirections.actionLoginFragmentToViewPagerFragment(1)
                                                Navigation.findNavController(view).navigate(action)
                                            }
                                        }
                                    }
                                    if(survey==null&&filled==null){
                                        val action =
                                            LoginFragmentDirections.actionLoginFragmentToViewPagerFragment()
                                        Navigation.findNavController(view).navigate(action)
                                    }

                                }




                            } else {
                                Toast.makeText(
                                    view.context,
                                    "Incorrect email or password", Toast.LENGTH_SHORT
                                ).show();
                            }

                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }


                }


            }
            else if(email.isNullOrEmpty()) {
                Toast.makeText(
                    view.context,
                    "Please fill the E-Mail field", Toast.LENGTH_SHORT
                ).show()
            }
            else if(password.isNullOrEmpty()) {
                Toast.makeText(
                    view.context,
                    "Please fill your password to continue", Toast.LENGTH_SHORT
                ).show()
            }

            else {
                Toast.makeText(
                    view.context,
                    "Please fill in the starred fields", Toast.LENGTH_SHORT
                ).show()

            }

        }
    }


}