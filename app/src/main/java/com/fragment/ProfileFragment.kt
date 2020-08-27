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
import com.icallback.ClickListener
import com.model.PostInProf
import kotlinx.android.synthetic.main.testlayout.*

class ProfileFragment : Fragment(), ClickListener, BottomSheetFragment.CallBack {
    private val postLists = mutableListOf<PostInProf>()
    private val titleInProfList = mutableListOf<String>()
    private val bottomSheetFragment = BottomSheetFragment()

    private var isFromEdit: Boolean = false
    private var argsFromEdit: Bundle? = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        argsFromEdit = this.arguments
        isFromEdit = argsFromEdit != null


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.testlayout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //  shit.initInterface(this)
        titleInProfList.add("نوشته ها")
        titleInProfList.add("علاقه مندی")
        for (i in 0..5) {
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
        recycler_posts_in_prof.apply {
            adapter = PostsInProfAdapter(postLists, this@ProfileFragment)


        }
        if (isFromEdit) {
            val num = argsFromEdit?.getInt("numberOfEditPost")
            val newPost = argsFromEdit?.getParcelable<PostInProf>("editPost")
            postLists[num!!].title = newPost?.title.toString().trim()
            postLists[num].desc = newPost?.desc.toString().trim()

            recycler_posts_in_prof.adapter?.notifyItemChanged(num)

        }

        titleRadioBtn.setOnCheckedChangeListener { _, i ->
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





    }

    override fun onClick(postInProf: PostInProf, layoutPosition: Int) {


        val args = Bundle()

        args.putInt("layoutPosition", layoutPosition)

        bottomSheetFragment.arguments = args
        bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)


//        bottomSheetFragment.edit_article_btn.setOnClickListener {
//            Toast.makeText(requireContext(),"edit",Toast.LENGTH_SHORT).show()
//        }

    }


    //for bottom sheet
    override fun onCall(action: String, numberOfItem: Int?) {
        changeListByCallBack(action, numberOfItem)

    }

    private fun changeListByCallBack(action: String, numberOfItem: Int?) {

        if (action == ("delete")) {
            if (numberOfItem != null) {
                postLists.removeAt(numberOfItem)
                recycler_posts_in_prof.adapter?.notifyItemRemoved(numberOfItem)
                bottomSheetFragment.dismiss()

            }
        } else if (action == ("edit")) {
            val bundle = Bundle()
            bundle.putParcelable("post", postLists[numberOfItem!!])
            bundle.putInt("number", numberOfItem)

            findNavController().navigate(
                R.id.action_profileFragment_to_writeArticleFragment,
                bundle
            )

            bottomSheetFragment.dismiss()

            // findNavController().navigate(
//                ProfileFragmentDirections.actionProfileFragmentToEditFragment(
//                    postLists[numberOfItem!!],
//                    numberOfItem
//                )
            //          )
        }
    }


}

