package com.fragment


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.adapter.PostsInProfAdapter
import com.example.anull.R
import com.model.PostInProf
import kotlinx.android.synthetic.main.fragment_profile.recycler_posts_in_prof
import kotlinx.android.synthetic.main.fragment_profile.titleRadioBtn
import kotlinx.android.synthetic.main.testlayout.*

class ProfileFragment : Fragment() {
    private val postLists = mutableListOf<PostInProf>()
    private val titleInProfList = mutableListOf<String>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.testlayout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titleInProfList.add("نوشته ها")
        titleInProfList.add("علاقه مندی")
        repeat(60) {
            postLists.add(
                PostInProf(
                    "سجاد فاتحی",
                    "3روز پیش",
                    "کاهش درآمدهای گوگل در سهماهه دوم سال ۲۰۲۰ طی ۱۶ سال اخیر بیسابقه بوده است ",
                    "اپل در طول اعلام نتایج درآمد سه ماهه سوم خود در روز پنج شنبه گفت که هیات مدیره شرکت یک تجزیه سهام چهار به یک را تصویب کرده است",
                    "4.5k",
                    "20کامنت"
                )
            )
        }
//
//         {
//
//            //
//            val bottomSheet = BottomSheetFragment()
//            bottomSheet.show(requireActivity().supportFragmentManager, bottomSheet.tag)
//
//        }
//        arrow_back.setOnClickListener {
//            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToHomeFragment())
//        }

        titleRadioBtn.setOnCheckedChangeListener { radioGroup, i ->
            val radio = requireActivity().findViewById<RadioButton>(i)
            // Toast.makeText(requireContext(),"${radio.text}",Toast.LENGTH_SHORT).show()
            if (radio.tag == "posts") {
                radio.setTextColor(Color.parseColor("#813ac1"))
                requireActivity().findViewById<RadioButton>(R.id.radio2)
                    .setTextColor(Color.parseColor("#363636"))
            } else {
                radio.setTextColor(Color.parseColor("#813ac1"))
                requireActivity().findViewById<RadioButton>(R.id.radio1)
                    .setTextColor(Color.parseColor("#363636"))

            }
        }


        recycler_posts_in_prof.apply {
            setHasFixedSize(true)
            adapter = PostsInProfAdapter(postLists)
        }

    }

//    override fun onSupportNavigateUp(): Boolean {
//        val navController = this.findNavController(R.id.fragment)
//        return when(navController.currentDestination?.id) {
//            R.id.redFragment -> {
//                // custom behavior here
//                true
//            }
//            else -> navController.navigateUp()
//        }
//    }


}

