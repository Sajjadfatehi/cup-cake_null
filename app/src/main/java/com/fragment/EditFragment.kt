package com.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.activity.MainActivity
import com.example.anull.R
import com.example.anull.databinding.FragmentEditBinding

class EditFragment : Fragment() {

    private lateinit var binding: FragmentEditBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backWA.setOnClickListener {
            findNavController().navigateUp()
        }


        val args = EditFragmentArgs.fromBundle(requireArguments())
        binding.post = args.post
        val numberOfItem = args.numberOfItem



        binding.submitEditArticle.setOnClickListener {
            val title = binding.editTextEditTitle.text.toString().trim()
            val desc = binding.editTextEditText.text.toString().trim()
            val bundle = Bundle()
            bundle.putString("title", title)
            bundle.putString("desc", desc)
            bundle.putInt("number", numberOfItem)

            val intent: Intent = Intent(activity?.baseContext, MainActivity::class.java)
            intent.putExtra("bundle", bundle)
            startActivity(intent)


        }


    }


}