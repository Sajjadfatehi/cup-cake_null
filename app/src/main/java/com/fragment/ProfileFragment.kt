package com.fragment


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.adapter.PostsInProfAdapter
import com.example.anull.R
import com.icallback.ClickListener
import com.model.PostInProf
import kotlinx.android.synthetic.main.fragment_profile.recycler_posts_in_prof
import kotlinx.android.synthetic.main.fragment_profile.titleRadioBtn
import kotlinx.android.synthetic.main.testlayout.*

class ProfileFragment : Fragment(), ClickListener {
    private val postLists = mutableListOf<PostInProf>()
    private val titleInProfList = mutableListOf<String>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.testlayout, container, false)

        return inflater.inflate(R.layout.testlayout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titleInProfList.add("نوشته ها")
        titleInProfList.add("علاقه مندی")
        for (i in 0..20) {
            postLists.add(
                PostInProf(
                    R.drawable.prof_image,
                    "سجاد فاتحی",
                    "3روز پیش",
                    "کاهش درآمدهای گوگل در سهماهه دوم سال ۲۰۲۰ طی ۱۶ سال اخیر بیسابقه بوده است ",
                    "اپل در طول اعلام نتایج درآمد سه ماهه سوم خود در روز پنج شنبه گفت که هیات مدیره شرکت یک تجزیه سهام چهار به یک را تصویب کرده است",
                    "4.5k",
                    "20کامنت"
                )
            )
        }
        back.setOnClickListener {
            findNavController().navigateUp()
        }
//
//         {
//
//            //
//            val bottomSheet = BottomSheetFragment()
//            bottomSheet.show(requireActivity().supportFragmentManager, bottomSheet.tag)
//
//        }
//

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
            adapter = PostsInProfAdapter(postLists)
        }

    }

    override fun onClick(postInProf: PostInProf, layoutPosition: Int) {
        Toast.makeText(requireContext(), "fytgyhhhhhhhw", Toast.LENGTH_SHORT).show()
        findNavController().navigate(
            ProfileFragmentDirections.actionProfileFragmentToBottomSheetFragment(
                postInProf,
                layoutPosition
            )
        )
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

