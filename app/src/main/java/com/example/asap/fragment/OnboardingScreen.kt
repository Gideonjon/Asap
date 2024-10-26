package com.example.asap.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.viewpager2.widget.ViewPager2
import com.example.asap.R
import com.example.asap.adapter.ViewpagerAdapter
import com.example.asap.databinding.FragmentOnboardingScreenBinding
import com.example.asap.onboarding.FirstScreen
import com.example.asap.onboarding.SecondScreen
import com.example.asap.onboarding.ThirdScreen
import com.google.android.material.tabs.TabLayoutMediator


class OnboardingScreen : Fragment() {
   private var _binding : FragmentOnboardingScreenBinding? = null
    private val binding get() = _binding!!

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentOnboardingScreenBinding.inflate(inflater,container,false)
        val view = binding.root


        var fragmentList = arrayListOf<Fragment>(
            FirstScreen(),
            SecondScreen(),
            ThirdScreen()
        )

        val adapter = ViewpagerAdapter(
            fragmentList, requireActivity().supportFragmentManager,lifecycle
        )
        binding.viewPager.adapter = adapter
        binding.dotsIndicator.attachTo(binding.viewPager)

        TabLayoutMediator(binding.tabLayout,binding.viewPager){ tab, position ->

        }.attach()

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                when(position){
                    0 ->{
                        binding.textView.text = getString(R.string.title_onboarding_1)
                        binding.textView.text = getString(R.string.description_onboarding_1)
                    } 1 ->{
                        binding.textView.text = getString(R.string.title_onboarding_2)
                        binding.textView.text = getString(R.string.description_onboarding_2)
                    }
                    else -> {
                        binding.textView.text = getString(R.string.title_onboarding_3)
                        binding.textView.text = getString(R.string.description_onboarding_3)

                    }
                }
            }

        })



        binding.signUp.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_onboardingScreen_to_createAccount)
        }
        binding.loginBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_onboardingScreen_to_login)
        }


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object{
        const val MAX_STEP = 3
    }


}