package com.fragment


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.databinding.DataBindingUtil
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.adapter.PostsInProfAdapter
import com.example.anull.R
import com.example.anull.databinding.FragmentLoginBinding
import com.example.anull.databinding.FragmentProfileBinding
import com.icallback.ClickListener
import com.model.PostInProf
import com.model.profile.ProfileModel
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.testlayout.*

class ProfileFragment : Fragment(), ClickListener, BottomSheetFragment.CallBack {
    private val postLists = mutableListOf<PostInProf>()
    private val titleInProfList = mutableListOf<String>()
    val shit = BottomSheetFragment()

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.user = ProfileModel("محمد عزیزی","برنامه نویس اندروید","214","5010")
        repeat(6) {
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
//        recycler_my_articles.apply {
//            adapter = PostsInProfAdapter(postLists)
//            setHasFixedSize(true)}

        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }

//         {
//
//            //
//            val bottomSheet = BottomSheetFragment()
//            bottomSheet.show(requireActivity().supportFragmentManager, bottomSheet.tag)
//
//        }
//

//        titleRadioBtn.setOnCheckedChangeListener { radioGroup, i ->
//            val radio = requireActivity().findViewById<RadioButton>(i)
//            // Toast.makeText(requireContext(),"${radio.text}",Toast.LENGTH_SHORT).show()
//            if (radio.tag == "posts") {
//                radio.setTextColor(Color.parseColor("#813ac1"))
//                requireActivity().findViewById<RadioButton>(R.id.radio2)
//                    .setTextColor(Color.parseColor("#363636"))
//            } else {
//                radio.setTextColor(Color.parseColor("#813ac1"))
//                requireActivity().findViewById<RadioButton>(R.id.radio1)
//                    .setTextColor(Color.parseColor("#363636"))
//
//            }
//        }


        binding.recyclerMyArticles.apply {
            adapter = PostsInProfAdapter(postLists, this@ProfileFragment)

        }

    }

    override fun onClick(postInProf: PostInProf, layoutPosition: Int) {

        val bottomSheetFragment = BottomSheetFragment()
        val args = Bundle()
        args.putParcelable("postItem", postInProf)
        args.putInt("layoutPosition", layoutPosition)

        bottomSheetFragment.arguments = args
        bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)


//        bottomSheetFragment.edit_article_btn.setOnClickListener {
//            Toast.makeText(requireContext(),"edit",Toast.LENGTH_SHORT).show()
//        }

    }


    override fun onCall(action: String, numberOfItem: Int?) {
        if (action == ("delete"))
            Toast.makeText(requireContext(), "delete", Toast.LENGTH_SHORT).show()

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
