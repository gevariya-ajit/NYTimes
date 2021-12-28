package nytimes.example.app.module.list

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import nytimes.example.app.R
import nytimes.example.app.base.BaseFragment
import nytimes.example.app.module.details.ArticleDetailsFragment
import nytimes.example.app.module.list.adapter.ArticleClickListener
import nytimes.example.app.module.list.adapter.ArticleListAdapter
import nytimes.example.app.utils.Utils
import javax.inject.Inject

class ArticleListFragment : BaseFragment() {


    @Inject
    lateinit var viewModel: ArticleListViewModel


    private val clickListener: ArticleClickListener = { model ->
        getBaseActivity().addFragment(
            getBaseActivity().supportFragmentManager,
            ArticleDetailsFragment.newInstance(model)
        )
    }

    private val articleListAdapter = ArticleListAdapter(clickListener)

    private lateinit var recyclerLayout: LinearLayout
    private lateinit var emptyView: RelativeLayout
    private lateinit var progressView: RelativeLayout
    private lateinit var refreshLayout: SwipeRefreshLayout


    companion object {
        fun newInstance(): ArticleListFragment {
            return ArticleListFragment()
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // region Override methods

    override fun layoutId() = R.layout.fragment_article_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val myToolbar: Toolbar = view.findViewById(R.id.my_toolbar)
        getBaseActivity().setSupportActionBar(myToolbar)
        initUi(view)
        setupRecyclerview(view)
        listenEvents()
        viewModel.getArticlesList()
    }
    // endregion

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // region private methods

    private fun initUi(view: View) {
        emptyView = view.findViewById(R.id.emptyView)
        progressView = view.findViewById(R.id.progressView)
        recyclerLayout = view.findViewById(R.id.recyclerLayout)
        refreshLayout = view.findViewById(R.id.refreshLayout)

        refreshLayout.setOnRefreshListener {
            viewModel.getArticlesList()
        }

    }

    private fun setupRecyclerview(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.articleRecycler)
        val linearLayoutManager = LinearLayoutManager(context)
        with(recyclerView) {
            layoutManager = linearLayoutManager
            adapter = articleListAdapter
        }
    }

    private fun listenEvents() {
        viewModel
            .getState()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { state ->
                when (state) {
                    is ArticleListState.ArticlesRequested -> {

                        progressView.visibility = View.VISIBLE

                    }
                    is ArticleListState.Error -> {

                        refreshLayout.isRefreshing = false

                        if (articleListAdapter.itemCount == 0) {
                            emptyView.visibility = View.VISIBLE
                            progressView.visibility = View.GONE
                            recyclerLayout.visibility = View.GONE
                        } else {
                            emptyView.visibility = View.GONE
                            progressView.visibility = View.GONE
                            recyclerLayout.visibility = View.VISIBLE
                            context?.let { Utils.showNoConnectionToast(it) }
                        }
                    }
                    is ArticleListState.ArticlesLoaded -> {

                        refreshLayout.isRefreshing = false

                        emptyView.visibility = View.GONE
                        progressView.visibility = View.GONE
                        recyclerLayout.visibility = View.VISIBLE


                        articleListAdapter.addArticles(state.list)
                    }
                }
            }
    }

    // endregion
}