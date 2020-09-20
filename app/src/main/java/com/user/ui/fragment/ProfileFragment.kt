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
import com.bumptech.glide.Glide
import com.core.db.AppDataBase
import com.core.util.Constants.Companion.QUERY_PAGE_SIZE
import com.core.util.Resource
import com.core.util.TestAdapterClass
import com.example.anull.R
import com.example.anull.databinding.TestlayoutBinding
import com.user.data.UserRepository
import com.user.data.localdatasource.UserLocalDataSource
import com.user.data.modelfromservice.Article
import com.user.data.modelfromservice.Author
import com.user.data.modelfromservice.EmailBody
import com.user.data.modelfromservice.FollowRequest
import com.user.data.reomtedatasource.UserRemote
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
    var isFollowing = false
    private lateinit var bindingProf: TestlayoutBinding

    private val titleInProfList = mutableListOf<String>()
    private val bottomSheetFragment = BottomSheetFragment()
    private lateinit var viewModel: ProfileViewModel


    private var isFromEdit: Boolean = false
    private var argsFromEdit: Bundle? = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        argsFromEdit = this.arguments
        argsFromEdit?.let { args ->
            isFromEdit = (args.getString("userName") != null)
        }


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
        val thisViewLifeCycleOwner=viewLifecycleOwner

        if (isFromEdit) {
            userName = argsFromEdit?.getString("userName").toString()

        } else {
            val authorArgs = arguments?.let {
                ProfileFragmentArgs.fromBundle(it)
            }
            userName = authorArgs?.author?.username.toString()
            authorArgs?.author?.following?.let {
                Log.d("bibi", "log ${it}: ")
                isFollowing = it
            }
        }


        val userLocalDataSource=UserLocalDataSource(AppDataBase.invoke(requireContext(),MIGRATION_1_2))
        val userRemoteDataSource=UserRemote()
        val userRepository = UserRepository(userLocalDataSource,userRemoteDataSource)
        val profileViewModelProviderFactory =
            ProfileViewModelProviderFactory(userRepository, userName)

        viewModel = ViewModelProvider(
            this,
            profileViewModelProviderFactory
        ).get(ProfileViewModel::class.java)

        viewModel.isFollowing = isFollowing


        titleInProfList.add("نوشته ها")
        titleInProfList.add("علاقه مندی")

        //set profile
        viewModel.profile.observe(thisViewLifeCycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { profile ->

                        bindingProf.author = profile.profile

                    }

                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                    }
                }
                is Resource.Loading -> {

                    showProgressBar()
                }


            }
        })

//        bindingProf.lifecycleOwner=this
//        recycler_posts_in_prof.apply {
//            adapter = viewModel.getPosts()?.let { list ->
//                PostsInProfAdapter(
//                    list,
//                    this@ProfileFragment
//                )
//            }
//        }

        viewModel.postList.observe(thisViewLifeCycleOwner, Observer {
            recycler_posts_in_prof.adapter?.notifyDataSetChanged()

        })

        back.setOnClickListener {
            findNavController().navigateUp()
        }

        titleRadioBtn.check(R.id.radio1)



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


        setUpRecyclerView()

        viewModel.allArticleOfPerson.observe(thisViewLifeCycleOwner, Observer { response ->
            Log.d("asghar", "bbbbbb: ")
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { allArticleOfPerson ->
                        if (titleRadioBtn.checkedRadioButtonId==radio1.id){
                            bindingProf.countOfArticles = allArticleOfPerson.articlesCount.toString()
                        }

                        testAdapter.differ.submitList(allArticleOfPerson.articles.toList())
                        val totalPages = allArticleOfPerson.articlesCount / QUERY_PAGE_SIZE + 2
                        isLastPage = totalPages == viewModel.allArticleOfPersonPage

                        if (isLastPage) {
                            recycler_posts_in_prof.setPadding(0, 0, 0, 0)
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

        viewModel.isDeleteSuccess.observe(thisViewLifeCycleOwner, Observer { isDeletedSuccess ->
            if (isDeletedSuccess) {

                Log.d("zendegi", "detele observe: ")
                val itemNumberOfDeletedArticle = viewModel.itemNumberOfDeletedArticle

                Log.d("zendegi", "delte item :${itemNumberOfDeletedArticle} ")
                if (itemNumberOfDeletedArticle >= 0) {
                    viewModel.deleteArticleFromList(itemNumberOfDeletedArticle)
                }
                viewModel.itemNumberOfDeletedArticle = -1
                viewModel.isDeleteSuccess.value = false
            }

        })

        viewModel.followResponse.observe(thisViewLifeCycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    Log.d("bibi", "follow res ${viewModel.isFollowing}: ")

                    viewModel.isFollowing=true
                    bindingProf.follow=true
                }
                is Resource.Error -> {

                }
                is Resource.Loading -> {

                }
            }

        })

        viewModel.unFollowResponse.observe(thisViewLifeCycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    Log.d("bibi", "Unfollow res ${viewModel.isFollowing}: ")
                   viewModel.isFollowing=false
                    bindingProf.follow=false
                }
                is Resource.Error -> {

                }
                is Resource.Loading -> {

                }
            }

        })

        viewModel.favoriteArticleResponse.observe(thisViewLifeCycleOwner, Observer {response->
            when(response){
                is Resource.Success->{

                    recycler_posts_in_prof.adapter?.notifyDataSetChanged()

                    Log.d("hadige", "fa res: ")

                }
                is Resource.Error->{

                }
                is Resource.Loading->{

                }
            }

        })

        viewModel.unFavoriteArticleResponse.observe(thisViewLifeCycleOwner, Observer {response->

            when(response){
                is Resource.Success->{
                    recycler_posts_in_prof.adapter?.notifyDataSetChanged()
                    Log.d("hadige", "unfa res: ")
                }
                is Resource.Error->{

                }
                is Resource.Loading->{

                }
            }
        })

        follow_button.setOnClickListener {
            Log.d("bibi", "onViewCreated:${viewModel.isFollowing} ")
            if (viewModel.isFollowing) {
                viewModel.unFollow(userName)

            } else {
                viewModel.follow(userName, FollowRequest(EmailBody("seyed@gmail.com")))
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
                Log.d("zendegi", "step 1 number= ${numberOfItem} ")
                viewModel.deleteArticleTest(
                    viewModel.allArticleOfPerson.value?.data?.articles!![numberOfItem].slug,
                    numberOfItem
                )
//                viewModel.deleteArticleFromList(numberOfItem)
                //  recycler_posts_in_prof.adapter?.notifyItemRemoved(numberOfItem)
                bottomSheetFragment.dismiss()

            }
        } else if (action == ("edit")) {
            val bundle = Bundle()

            numberOfItem?.let { number ->
                viewModel.allArticleOfPerson.value?.data?.articles?.get(number)?.let { article ->
                    bundle.putParcelable("post", article)
                    bundle.putString("userName", userName)
                }
            }



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

    override fun onBookMarkClick(slug: String, isFavorited: Boolean, itemNumber: Int) {
        if (isFavorited) {
            viewModel.unFavoritedArticle(slug, itemNumber,titleRadioBtn.checkedRadioButtonId==radio1.id)

            Log.d("hadige", "${titleRadioBtn.checkedRadioButtonId==radio1.id}: ")
        } else {
            viewModel.favoriteArticle(slug)

        }

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

    private fun showProfProgressBar() {
        profProgressBar.visibility = View.VISIBLE
    }

    private fun hideProfProgressBar() {
        profProgressBar.visibility = View.INVISIBLE
    }
}

