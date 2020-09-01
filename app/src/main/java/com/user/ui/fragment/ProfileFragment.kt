package com.user.ui.fragment


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.anull.R
import com.example.anull.databinding.TestlayoutBinding
import com.user.ui.ClickListener
import com.user.ui.PostInProfView
import com.user.ui.adapter.PostsInProfAdapter
import com.user.ui.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.testlayout.*

class ProfileFragment : Fragment(), ClickListener, BottomSheetFragment.CallBack {

    private lateinit var bindingProf: TestlayoutBinding

    private val titleInProfList = mutableListOf<String>()
    private val bottomSheetFragment = BottomSheetFragment()
    private lateinit var viewModel: ProfileViewModel

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

        bindingProf = DataBindingUtil.inflate(inflater, R.layout.testlayout, container, false)

        return bindingProf.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleInProfList.add("نوشته ها")
        titleInProfList.add("علاقه مندی")


        //  shit.initInterface(this)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
//        bindingProf.lifecycleOwner=this
        recycler_posts_in_prof.apply {
            adapter = PostsInProfAdapter(
                viewModel.getPosts().value!!,
                this@ProfileFragment
            )
        }

        viewModel.postList.observe(viewLifecycleOwner, Observer {
            recycler_posts_in_prof.adapter?.notifyDataSetChanged()

        })

        back.setOnClickListener {
            findNavController().navigateUp()
        }

        if (isFromEdit) {
            val num = argsFromEdit?.getInt("numberOfEditPost")
            val newPost = argsFromEdit?.getParcelable<PostInProfView>("editPost")
            viewModel.postList.value!![num!!].title = newPost?.title.toString().trim()
            viewModel.postList.value!![num].desc = newPost?.desc.toString().trim()

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


    //for bottom sheet
    override fun onCall(action: String, numberOfItem: Int?) {
        changeListByCallBack(action, numberOfItem)

    }

    private fun changeListByCallBack(action: String, numberOfItem: Int?) {

        if (action == ("delete")) {
            if (numberOfItem != null) {
                viewModel.postList.value!!.removeAt(numberOfItem)
                recycler_posts_in_prof.adapter?.notifyItemRemoved(numberOfItem)
                bottomSheetFragment.dismiss()

            }
        } else if (action == ("edit")) {
            val bundle = Bundle()
            bundle.putParcelable("post", viewModel.postList.value!![numberOfItem!!])
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


    override fun onClick(postInProf: PostInProfView, layoutPosition: Int) {
        val args = Bundle()

        args.putInt("layoutPosition", layoutPosition)

        bottomSheetFragment.arguments = args
        bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)


//        bottomSheetFragment.edit_article_btn.setOnClickListener {
//            Toast.makeText(requireContext(),"edit",Toast.LENGTH_SHORT).show()
//        }
    }


}

