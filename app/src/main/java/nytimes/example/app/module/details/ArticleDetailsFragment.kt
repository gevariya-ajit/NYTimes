package nytimes.example.app.module.details

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import nytimes.example.app.R
import nytimes.example.app.base.BaseFragment
import nytimes.example.app.models.ArticleModel
import nytimes.example.app.utils.loadCoverImage

class ArticleDetailsFragment : BaseFragment() {

    private var articleModel: ArticleModel? = null

    companion object {

        private const val EXTRA_ARTICLE = "extra_article"

        fun newInstance(article: ArticleModel): ArticleDetailsFragment {
            val fragment = ArticleDetailsFragment()
            val bundle = Bundle()
            bundle.putParcelable(EXTRA_ARTICLE, article)
            fragment.arguments = bundle
            return fragment
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // region Override methods

    override fun layoutId() = R.layout.fragment_article_details

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar: Toolbar = view.findViewById(R.id.my_toolbar)
        getBaseActivity().setSupportActionBar(toolbar)
        getBaseActivity().supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getBaseActivity().supportActionBar?.setDisplayShowHomeEnabled(true)

        toolbar.setNavigationOnClickListener {
            getBaseActivity().onBackPressed()
        }

        articleModel = arguments?.getParcelable(EXTRA_ARTICLE)

        setCoverImage(view)
        setArticleData(view)

    }

    private fun setArticleData(view: View){
        articleModel?.let {
            view.findViewById<TextView>(R.id.titleText).text = it.title
            view.findViewById<TextView>(R.id.dateText).text = it.publishedDate
            view.findViewById<TextView>(R.id.byText).text = it.byline
            view.findViewById<TextView>(R.id.keywords).text = it.adxKeywords
        }


    }

    private fun setCoverImage(view: View) {
        val coverImage = view.findViewById<ImageView>(R.id.coverImage)

        articleModel?.let {
            if (it.media?.isNotEmpty() == true) {
                if (it.media?.get(0)?.mediaMetadata?.isNotEmpty() == true) {
                    coverImage.loadCoverImage(
                        it.media?.get(0)?.mediaMetadata?.last()?.url ?: ""
                    )
                }
            }
        }
    }

    // endregion
}