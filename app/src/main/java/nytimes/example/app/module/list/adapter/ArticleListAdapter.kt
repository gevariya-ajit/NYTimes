package nytimes.example.app.module.list.adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import nytimes.example.app.R
import nytimes.example.app.base.inflate
import nytimes.example.app.models.ArticleModel
import nytimes.example.app.utils.loadCircleAvatar

typealias ArticleClickListener = (articleModel: ArticleModel) -> Unit

class ArticleListAdapter(private val clickListener: ArticleClickListener) :
    RecyclerView.Adapter<ArticleListViewHolder>() {

    private val articleList = mutableListOf<ArticleModel>()

    fun addArticles(list: List<ArticleModel>) {
        articleList.clear()
        articleList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleListViewHolder {
        val view = parent.inflate(R.layout.item_article_layout)
        return ArticleListViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ArticleListViewHolder, position: Int
    ) {
        holder.bind(articleList[position], clickListener)
    }

    override fun getItemCount() = articleList.size
}

class ArticleListViewHolder(itemView: View) : RecyclerView.ViewHolder(
    itemView
) {

    private val titleText: TextView = itemView.findViewById(R.id.titleText)
    private val byText: TextView = itemView.findViewById(R.id.byText)
    private val dateText: TextView = itemView.findViewById(R.id.dateText)
    private val avatarPic: ImageView = itemView.findViewById(R.id.avatarPic)

    @SuppressLint("SetTextI18n")
    fun bind(model: ArticleModel, clickListener: ArticleClickListener) {

        itemView.setOnClickListener {
            clickListener.invoke(model)
        }

        titleText.text = model.title
        byText.text = model.byline

        dateText.text = model.publishedDate

        if (model.media?.isNotEmpty() == true) {
            if (model.media?.get(0)?.mediaMetadata?.isNotEmpty() == true) {
                avatarPic.loadCircleAvatar(
                    model.media?.get(0)?.mediaMetadata?.get(0)?.url ?: ""
                )
            }
        }

    }

}
