package com.user.ui.fragment


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.article.data.ArticleUser
import com.core.ResultCallBack
import com.core.db.AppDataBase
import com.core.util.TestAdapterClass
import com.example.anull.R
import com.example.anull.databinding.TestlayoutBinding
import com.google.android.material.snackbar.Snackbar
import com.user.data.UserEntity
import com.user.data.UserRepository
import com.user.data.localdatasource.UserLocalDataSource
import com.user.data.modelfromservice.EmailBody
import com.user.data.modelfromservice.FollowRequest
import com.user.data.reomtedatasource.UserRemote
import com.user.ui.ClickListener
import com.user.ui.viewmodel.ProfileViewModel
import com.user.ui.viewmodel.providerfactory.ProfileViewModelProviderFactory
import kotlinx.android.synthetic.main.testlayout.*

class ProfileFragment : Fragment(), ClickListener, BottomSheetFragment.CallBack,
    SwipeRefreshLayout.OnRefreshListener {


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
        val thisViewLifeCycleOwner = viewLifecycleOwner

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


        val userLocalDataSource =
            UserLocalDataSource(AppDataBase.invoke(requireContext(), MIGRATION_1_2))
        val userRemoteDataSource = UserRemote()
        val userRepository = UserRepository(userLocalDataSource, userRemoteDataSource)
        val profileViewModelProviderFactory =
            ProfileViewModelProviderFactory(userRepository, userName)

        viewModel = ViewModelProvider(
            this,
            profileViewModelProviderFactory
        ).get(ProfileViewModel::class.java)

        viewModel.isFollowing = isFollowing

        titleInProfList.add("نوشته ها")
        titleInProfList.add("علاقه مندی")
        bindingProf.isMyPage = viewModel.userName == viewModel.userRepository.getUserNameFromShare()
        //set profile
        viewModel.profile.observe(thisViewLifeCycleOwner, Observer { response ->
            when (response) {
                is ResultCallBack.Success -> {
                    hideProgressBar()
                    response.data.let { profile ->

//                        Log.d("TAGT p", "${profile.following}: ")
                        bindingProf.author = profile
                    }

                }
                is ResultCallBack.Error -> {
                    hideProgressBar()


                }
                is ResultCallBack.Loading -> {

                    showProgressBar()
                }


            }
        })


        back.setOnClickListener {
            findNavController().navigateUp()
        }

        titleRadioBtn.setOnCheckedChangeListener { _, i ->
            val radio = requireActivity().findViewById<RadioButton>(i)

            if (radio.id == radio1.id) {

                viewModel.getAllArticleOfPersonNew(viewModel.userName)
                radio.setTextColor(Color.parseColor("#813ac1"))
                requireActivity().findViewById<RadioButton>(R.id.radio2)
                    .setTextColor(Color.parseColor("#363636"))
            } else if (radio.id == radio2.id) {

                viewModel.getFavoritedArticleByUserName(viewModel.userName)

                radio.setTextColor(Color.parseColor("#813ac1"))
                requireActivity().findViewById<RadioButton>(R.id.radio1)
                    .setTextColor(Color.parseColor("#363636"))

            }
        }

        setUpRecyclerView()

//        viewModel.allArticleOfPerson.observe(thisViewLifeCycleOwner, Observer { response ->
//            Log.d("asghar", "bbbbbb: ")
//            when (response) {
//                is Resource.Success -> {
//                    hideProgressBar()
//                    response.data?.let { allArticleOfPerson ->
//                        if (titleRadioBtn.checkedRadioButtonId==radio1.id){
//                            bindingProf.countOfArticles = allArticleOfPerson.articlesCount.toString()
//                        }
//
//                        testAdapter.differ.submitList(allArticleOfPerson.articles.toList())
//                        val totalPages = allArticleOfPerson.articlesCount / QUERY_PAGE_SIZE + 2
//                        isLastPage = totalPages == viewModel.allArticleOfPersonPage
//
//                        if (isLastPage) {
//                            recycler_posts_in_prof.setPadding(0, 0, 0, 0)
//                        }
//
//                    }
//                }
//                is Resource.Error -> {
//                    hideProgressBar()
//                    response.message?.let { message ->
//                        Log.e("ProfileFragment ", "an error happened: $message ")
//                    }
//                }
//                is Resource.Loading -> {
//                    showProgressBar()
//                }
//
//
//            }
//
//        })


        viewModel.allArticleOfPersonNew.observe(thisViewLifeCycleOwner, Observer { response ->

            response?.let { allArticleOfPerson ->
//
//                if (titleRadioBtn.checkedRadioButtonId == radio1.id) {
                bindingProf.countOfArticles = allArticleOfPerson.size.toString()
//                }

                Log.d("ProfPage", "radio is ${titleRadioBtn.checkedRadioButtonId}: ")
                testAdapter.differ.submitList(allArticleOfPerson.toList())

            }

        })

        viewModel.allFavoriteArticleOfPersonNew.observe(
            thisViewLifeCycleOwner,
            Observer { response ->

                response?.let { allFavoriteArticleOfPerson ->
//
//                if (titleRadioBtn.checkedRadioButtonId == radio1.id) {
//                    bindingProf.countOfArticles = allFavoriteArticleOfPerson.size.toString()
//                }
                    Log.d("ProfPage", "radio is ${titleRadioBtn.checkedRadioButtonId}: ")
                    testAdapter.differ.submitList(allFavoriteArticleOfPerson.toList())

                }

            })


        viewModel.followResponse.observe(thisViewLifeCycleOwner, Observer { response ->
            when (response) {
                is ResultCallBack.Success -> {
                    Log.d("bibi", "follow res ${viewModel.isFollowing}: ")

                    viewModel.isFollowing = true
                    bindingProf.follow = true
                }
                is ResultCallBack.Error -> {
                    Snackbar.make(
                        requireView(),
                        response.exception.message.toString(),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                is ResultCallBack.Loading -> {

                }
            }

        })

        viewModel.unFollowResponse.observe(thisViewLifeCycleOwner, Observer { response ->
            when (response) {
                is ResultCallBack.Success -> {
                    Log.d("bibi", "Unfollow ressss ${viewModel.isFollowing}: ")
                    viewModel.isFollowing = false
                    bindingProf.follow = false
                }
                is ResultCallBack.Error -> {
                    Snackbar.make(
                        requireView(),
                        response.exception.message.toString(),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                is ResultCallBack.Loading -> {

                }
            }

        })

        viewModel.favoriteArticleResponse.observe(thisViewLifeCycleOwner, Observer { response ->
            when (response) {
                is ResultCallBack.Success -> {

                }
                is ResultCallBack.Error -> {
                    Snackbar.make(
                        requireView(),
                        response.exception.message.toString(),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                is ResultCallBack.Loading -> {

                }
            }

        })

        viewModel.unFavoriteArticleResponse.observe(thisViewLifeCycleOwner, Observer { response ->

            when (response) {
                is ResultCallBack.Success -> {

                    Log.d("hadige", "unfa ressss: ")
                }
                is ResultCallBack.Error -> {
                    Snackbar.make(
                        requireView(),
                        response.exception.message.toString(),
                        Snackbar.LENGTH_SHORT
                    ).show()

                }
                is ResultCallBack.Loading -> {

                }
            }
        })

        follow_button.setOnClickListener {

            if (viewModel.isFollowing) {
                viewModel.unFollow(userName)

            } else {
                viewModel.userRepository.getEmailFromShare().let { email ->
                    EmailBody(email)
                }.let { emailBody ->
                    FollowRequest(emailBody)
                }.let { followRequset ->
                    viewModel.follow(userName, followRequset)
                }
            }

        }

        bindingProf.swipeRefresh.setOnRefreshListener(this)
    }


    //for bottom sheet
    override fun onCall(action: String, numberOfItem: Int?) {
        changeListByCallBack(action, numberOfItem)

    }

    private fun changeListByCallBack(action: String, numberOfItem: Int?) {

        if (action == ("delete")) {
            if (numberOfItem != null) {
                viewModel.allArticleOfPersonNew.value?.let { list ->
                    list[numberOfItem].articleDataEntity.slug
                }?.let { slug ->
                    viewModel.deleteArticleTest(
                        slug, numberOfItem
                    )
                }
                bottomSheetFragment.dismiss()
            }
        } else if (action == ("edit")) {
            val bundle = Bundle()

//            numberOfItem?.let { number ->
//                viewModel.allArticleOfPerson.value?.data?.articles?.get(number)?.let { article ->
//                    bundle.putParcelable("post", article)
//                    bundle.putString("userName", userName)
//                }
//            }
            numberOfItem?.let { number ->
                viewModel.allArticleOfPersonNew.value?.get(number)?.articleDataEntity?.let { article ->
                    bundle.putParcelable("post", article)
                    bundle.putString("userName", userName)
                }
            }

            findNavController().navigate(
                R.id.action_profileFragment_to_writeArticleFragment,
                bundle
            )
            bottomSheetFragment.dismiss()
        } else if (action == ("share")) {
            var title = "title \n :"
            var body = "body \n:"

            if (titleRadioBtn.checkedRadioButtonId == radio1.id) {
                title += numberOfItem?.let { viewModel.allArticleOfPersonNew.value?.get(it)?.articleDataEntity?.title }
                    .toString().trim()
                body += numberOfItem?.let { viewModel.allArticleOfPersonNew.value?.get(it)?.articleDataEntity?.body }
                    .toString().trim()
            } else {
                title += numberOfItem?.let { viewModel.allFavoriteArticleOfPersonNew.value?.get(it)?.articleDataEntity?.title }
                    .toString().trim()
                body += numberOfItem?.let { viewModel.allFavoriteArticleOfPersonNew.value?.get(it)?.articleDataEntity?.body }
                    .toString().trim()
            }

            val sendIntent: Intent = Intent().apply {
                setAction(Intent.ACTION_SEND)
                putExtra(Intent.EXTRA_TEXT, title)
                putExtra(Intent.EXTRA_TEXT, body)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)

        }
    }


    override fun onClick(article: ArticleUser, layoutPosition: Int) {
        val args = Bundle()
        val isMyOwnPage = (viewModel.userRepository.getUserNameFromShare() == viewModel.userName) &&
                (titleRadioBtn.checkedRadioButtonId == radio1.id)

        args.putInt("layoutPosition", layoutPosition)
        args.putBoolean("isMyOwnPage", isMyOwnPage)
        bottomSheetFragment.arguments = args
        bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)


//        bottomSheetFragment.edit_article_btn.setOnClickListener {
//            Toast.makeText(requireContext(),"edit",Toast.LENGTH_SHORT).show()
//        }
    }

    override fun onCardClick(article: ArticleUser, layoutPosition: Int) {
        Toast.makeText(requireContext(), "card clicked", Toast.LENGTH_SHORT).show()
    }

    override fun onImageClick(author: UserEntity) {
        if (bindingProf.titleRadioBtn.checkedRadioButtonId == bindingProf.radio2.id) {


            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentSelf(author))
            //for when navigateUp fix radio button
            titleRadioBtn.check(radio1.id)


        }

    }

    override fun onBookMarkClick(slug: String, isFavorited: Boolean, itemNumber: Int) {
        if (isFavorited) {
            viewModel.userRepository.getUserNameFromShare().let { userName ->
                viewModel.unFavoritedArticle(
                    slug, itemNumber,
                    titleRadioBtn.checkedRadioButtonId == radio1.id,
                    userName
                )
            }

            Log.d("hadige", "on book${titleRadioBtn.checkedRadioButtonId == radio1.id}: ")
        } else {
            viewModel.userRepository.getUserNameFromShare().let {
                viewModel.favoriteArticle(
                    slug, itemNumber,
                    it
                )
            }

        }

    }

    override fun onLikeClick() {
        Toast.makeText(requireContext(), "liked", Toast.LENGTH_SHORT).show()
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.INVISIBLE

    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE

    }

    private fun setUpRecyclerView() {
        testAdapter = TestAdapterClass(this@ProfileFragment)
        recycler_posts_in_prof.apply {
            adapter = testAdapter
            layoutManager = LinearLayoutManager(activity)

        }
    }

    private fun showProfProgressBar() {
        profProgressBar.visibility = View.VISIBLE
    }

    private fun hideProfProgressBar() {
        profProgressBar.visibility = View.INVISIBLE
    }

    override fun onRefresh() {
        if (titleRadioBtn.checkedRadioButtonId == radio1.id) {
            viewModel.getAllArticleOfPersonNew(viewModel.userName)
        } else {
            viewModel.getFavoritedArticleByUserName(viewModel.userName)
        }
    }
}

