package com.user.ui.fragment


import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.RadioButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.core.db.AppDataBase
import com.core.util.Constants.Companion.QUERY_PAGE_SIZE
import com.core.util.Resource
import com.core.util.TestAdapterClass
import com.example.anull.R
import com.example.anull.databinding.TestlayoutBinding
import com.user.data.UserRepository
import com.user.data.modelfromservice.Article
import com.user.data.modelfromservice.Author
import com.user.ui.ArticleView
import com.user.ui.ClickListener
import com.user.ui.viewmodel.ProfileViewModel
import com.user.ui.viewmodel.providerfactory.ProfileViewModelProviderFactory
import kotlinx.android.synthetic.main.testlayout.*

class ProfileFragment : Fragment(), ClickListener, BottomSheetFragment.CallBack {

    lateinit var testAdapter: TestAdapterClass
    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE articles ADD COLUMN test TEXT")
        }
    }

    var userName: String = ""
    private lateinit var bindingProf: TestlayoutBinding

    private val titleInProfList = mutableListOf<String>()
    private val bottomSheetFragment = BottomSheetFragment()
    private lateinit var viewModel: ProfileViewModel

    private var isFromEdit: Boolean = false
    private var argsFromEdit: Bundle? = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        argsFromEdit = this.arguments
        isFromEdit = !argsFromEdit?.isEmpty!!


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

        var authorArgs = arguments?.let { ProfileFragmentArgs.fromBundle(it) }
        userName = authorArgs?.author?.username.toString()


        titleInProfList.add("نوشته ها")
        titleInProfList.add("علاقه مندی")


        val userRepository = UserRepository(AppDataBase.invoke(requireContext(), MIGRATION_1_2))
        val profileViewModelProviderFactory =
            ProfileViewModelProviderFactory(userRepository, userName)

        viewModel = ViewModelProvider(
            this,
            profileViewModelProviderFactory
        ).get(ProfileViewModel::class.java)


//        bindingProf.lifecycleOwner=this
//        recycler_posts_in_prof.apply {
//            adapter = viewModel.getPosts()?.let { list ->
//                PostsInProfAdapter(
//                    list,
//                    this@ProfileFragment
//                )
//            }
//        }

        viewModel.postList.observe(viewLifecycleOwner, Observer {
            recycler_posts_in_prof.adapter?.notifyDataSetChanged()

        })

        back.setOnClickListener {
            findNavController().navigateUp()
        }

        if (isFromEdit) {
            val num = argsFromEdit?.getInt("numberOfEditPost")
            val newPost = argsFromEdit?.getParcelable<ArticleView>("editPost")
            viewModel.postList.value!![num!!].title = newPost?.title.toString().trim()
            viewModel.postList.value!![num].desc = newPost?.desc.toString().trim()

            //recycler_posts_in_prof.adapter?.notifyItemChanged(num)


        }


        titleRadioBtn.setOnCheckedChangeListener { _, i ->
            val radio = requireActivity().findViewById<RadioButton>(i)

            // Toast.makeText(requireContext(),"${radio.text}",Toast.LENGTH_SHORT).show()
            if (radio.tag == "posts") {
                viewModel.getAllArticleOfPerson(userName)
                radio.setTextColor(Color.parseColor("#813ac1"))
                requireActivity().findViewById<RadioButton>(R.id.radio2)
                    .setTextColor(Color.parseColor("#363636"))
            } else {

                viewModel.getFavoritedArticleByUserName(userName)
                recycler_posts_in_prof.adapter?.notifyDataSetChanged()
                radio.setTextColor(Color.parseColor("#813ac1"))
                requireActivity().findViewById<RadioButton>(R.id.radio1)
                    .setTextColor(Color.parseColor("#363636"))

            }
        }
        //tow below line is just for test
//        viewModel.allArticleOfPerson.observe(viewLifecycleOwner, Observer {
//            Log.d("lashii", "gooz ")
//        })

        setUpRecyclerView()

        viewModel.allArticleOfPerson.observe(viewLifecycleOwner, Observer { response ->
            Log.d("asghar", "bbbbbb: ")
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { allArticleOfPerson ->
                        testAdapter.differ.submitList(allArticleOfPerson.articles.toList())
                        val totalPages = allArticleOfPerson.articlesCount / QUERY_PAGE_SIZE + 2
                        isLastPage = totalPages == viewModel.allArticleOfPersonPage

                        if (isLastPage){
                            recycler_posts_in_prof.setPadding(0,0,0,0)
                        }

                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e("ProfileFragment ", "an error happened: $message ")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }


            }

        })

        viewModel.isDeleteSuccess.observe(viewLifecycleOwner, Observer { isDeletedSuccess ->
            if (isDeletedSuccess) {
                val itemNumberOfDeletedArticle = viewModel.itemNumberOfDeletedArticle
                if (itemNumberOfDeletedArticle > 0) {
                    viewModel.deleteArticleFromList(itemNumberOfDeletedArticle)
                }
                viewModel.itemNumberOfDeletedArticle = -1
                viewModel.isDeleteSuccess.value = false
            }

        })

    }


    //for bottom sheet
    override fun onCall(action: String, numberOfItem: Int?) {
        changeListByCallBack(action, numberOfItem)

    }

    private fun changeListByCallBack(action: String, numberOfItem: Int?) {

        if (action == ("delete")) {
            if (numberOfItem != null) {
                viewModel.deleteArticleTest(
                    viewModel.allArticleOfPerson.value?.data?.articles!![numberOfItem].slug,
                    numberOfItem
                )
                viewModel.deleteArticleFromList(numberOfItem)
                //  recycler_posts_in_prof.adapter?.notifyItemRemoved(numberOfItem)
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

        }
    }


    override fun onClick(article: Article, layoutPosition: Int) {
        val args = Bundle()

        args.putInt("layoutPosition", layoutPosition)

        bottomSheetFragment.arguments = args
        bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)


//        bottomSheetFragment.edit_article_btn.setOnClickListener {
//            Toast.makeText(requireContext(),"edit",Toast.LENGTH_SHORT).show()
//        }
    }

    override fun onCardClick(article: Article, layoutPosition: Int) {
        Toast.makeText(requireContext(), "card clicked", Toast.LENGTH_SHORT).show()
    }

    override fun onImageClick(author: Author) {
        if (bindingProf.titleRadioBtn.checkedRadioButtonId == bindingProf.radio2.id) {

            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentSelf(author))

        }

    }

    override fun onBookMarkClick(slug: String) {
        viewModel.favoriteArticle(slug)
    }

    override fun onLikeClick() {
        Toast.makeText(requireContext(), "liked", Toast.LENGTH_SHORT).show()
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
        isLoading = true
    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recycler_posts_in_prof.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            //momkene badan be moshkel bokhore chon bad az yekam scroll recycler tamame sfhe ro miigre
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isLastItem && isNotAtBeginning
                    && isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                viewModel.getAllArticleOfPerson(userName)
                isScrolling = false
            }

        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }

    private fun setUpRecyclerView() {
        testAdapter = TestAdapterClass(this@ProfileFragment)
        recycler_posts_in_prof.apply {
            adapter = testAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@ProfileFragment.scrollListener)
        }
    }
}

