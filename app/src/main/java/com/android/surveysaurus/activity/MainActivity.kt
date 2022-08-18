package com.android.surveysaurus.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ActionMenuView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.android.surveysaurus.R
import com.android.surveysaurus.databinding.ActivityMainBinding
import com.android.surveysaurus.fragment.CreateSurveyFragment
import com.android.surveysaurus.singleton.LoginSingleton
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.crashes.Crashes

class MainActivity : AppCompatActivity() {
    private lateinit var profileActionMenuView: ActionMenuView
    private lateinit var barActionMenuView: ActionMenuView
    private lateinit var profileLoginActionMenuView: ActionMenuView
    private lateinit var barLoginActionMenuView: ActionMenuView
    private lateinit var mMyFragment: Fragment
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        AppCenter.start(
            application, "f81383ab-c4c0-4a23-aac6-8aa9115e5119",
            com.microsoft.appcenter.analytics.Analytics::class.java, Crashes::class.java
        )
        profileActionMenuView = findViewById(R.id.expanded_profile)

        barActionMenuView = findViewById(R.id.expanded_bar)
        profileLoginActionMenuView = findViewById(R.id.expanded_login_profile)

        barLoginActionMenuView = findViewById(R.id.expanded_login_bar)

        menuInflater.inflate(R.menu.logined_profile_menu, profileLoginActionMenuView.menu)
        menuInflater.inflate(R.menu.logined_bar_menu, barLoginActionMenuView.menu)
        menuInflater.inflate(R.menu.profile_menu, profileActionMenuView.menu)
        menuInflater.inflate(R.menu.bar_menu, barActionMenuView.menu)
        MenuController()


    }


    fun HomeClicked(view: View) {
        binding.fragmentContainerView.findNavController()
            .navigate(R.id.viewPagerFragment)
    }


    fun MenuController() {


        if (LoginSingleton.isLogin) {
            profileLoginActionMenuView.isVisible = true
            barLoginActionMenuView.isVisible = true
            profileActionMenuView.isVisible = false
            barActionMenuView.isVisible = false

            profileLoginActionMenuView.setOnMenuItemClickListener {
                val item = it.itemId
                when (item) {
                    R.id.user_info -> {
                        binding.fragmentContainerView.findNavController()
                            .navigate(R.id.userInfoFragment); true
                    }
                    R.id.my_surveys -> {
                        binding.fragmentContainerView.findNavController()
                            .navigate(R.id.mySurveyFragment); true
                    }
                    R.id.log_out -> {
                        LoginSingleton.isLogin = false
                        LoginSingleton.name = ""
                        LoginSingleton.country = ""
                        LoginSingleton.gender = ""
                        LoginSingleton.city = ""
                        LoginSingleton.email = ""

                        MenuController()
                        binding.fragmentContainerView.findNavController()
                            .navigate(R.id.viewPagerFragment)
                        ; true
                    }

                    else -> {
                        println("nothin "); true
                    }
                }

            }

            barLoginActionMenuView.setOnMenuItemClickListener {

                val item = it.itemId
                when (item) {

                    R.id.user_bar -> {;true
                    }

                    else -> {
                        println("nothin "); true
                    }
                }


            }
        } else {
            profileLoginActionMenuView.isVisible = false
            barLoginActionMenuView.isVisible = false
            profileActionMenuView.isVisible = true
            barActionMenuView.isVisible = true
            profileActionMenuView.setOnMenuItemClickListener {
                val item = it.itemId
                when (item) {
                    R.id.dropdown_menu -> {
                        binding.fragmentContainerView.findNavController()
                            .navigate(R.id.loginFragment);true
                    }
                    else -> {
                        println("nothin");true
                    }
                }

            }

            barActionMenuView.setOnMenuItemClickListener {
                val id = it.itemId
                when (id) {
                    R.id.home_bar -> {
                        binding.fragmentContainerView.findNavController()
                            .navigate(R.id.viewPagerFragment);true
                    }
                    R.id.log_in -> {
                        binding.fragmentContainerView.findNavController()
                            .navigate(R.id.loginFragment);true
                    }
                    R.id.sign_up -> {
                        binding.fragmentContainerView.findNavController()
                            .navigate(R.id.signUpFragment);true
                    }

                    else -> {
                        println("nothin ");true
                    }
                }
            }

        }
    }


}




