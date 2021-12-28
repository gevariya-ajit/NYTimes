package nytimes.example.app.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class ArticleModel() : Parcelable {
    @SerializedName("per_facet")
    var perFacet: List<String>? = null

    @SerializedName("eta_id")
    var etaId = 0

    @SerializedName("subsection")
    var subsection: String? = null

    @SerializedName("org_facet")
    var orgFacet: List<String>? = null

    @SerializedName("nytdsection")
    var nytdsection: String? = null

    @SerializedName("column")
    var column: Any? = null

    @SerializedName("section")
    var section: String? = null

    @SerializedName("asset_id")
    var assetId: Long = 0

    @SerializedName("source")
    var source: String? = null

    @SerializedName("abstract")
    var jsonMemberAbstract: String? = null

    @SerializedName("media")
    var media: List<MediaItem>? = null

    @SerializedName("type")
    var type: String? = null

    @SerializedName("title")
    var title: String? = null

    @SerializedName("des_facet")
    var desFacet: List<String>? = null

    @SerializedName("uri")
    var uri: String? = null

    @SerializedName("url")
    var url: String? = null

    @SerializedName("adx_keywords")
    var adxKeywords: String? = null

    @SerializedName("geo_facet")
    var geoFacet: List<String>? = null

    @SerializedName("id")
    var id: Long = 0

    @SerializedName("published_date")
    var publishedDate: String? = null

    @SerializedName("updated")
    var updated: String? = null

    @SerializedName("byline")
    var byline: String? = null

    constructor(parcel: Parcel) : this() {
        perFacet = parcel.createStringArrayList()
        etaId = parcel.readInt()
        subsection = parcel.readString()
        orgFacet = parcel.createStringArrayList()
        nytdsection = parcel.readString()
        section = parcel.readString()
        assetId = parcel.readLong()
        source = parcel.readString()
        jsonMemberAbstract = parcel.readString()
        type = parcel.readString()
        title = parcel.readString()
        desFacet = parcel.createStringArrayList()
        uri = parcel.readString()
        url = parcel.readString()
        adxKeywords = parcel.readString()
        geoFacet = parcel.createStringArrayList()
        id = parcel.readLong()
        publishedDate = parcel.readString()
        updated = parcel.readString()
        byline = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeStringList(perFacet)
        parcel.writeInt(etaId)
        parcel.writeString(subsection)
        parcel.writeStringList(orgFacet)
        parcel.writeString(nytdsection)
        parcel.writeString(section)
        parcel.writeLong(assetId)
        parcel.writeString(source)
        parcel.writeString(jsonMemberAbstract)
        parcel.writeString(type)
        parcel.writeString(title)
        parcel.writeStringList(desFacet)
        parcel.writeString(uri)
        parcel.writeString(url)
        parcel.writeString(adxKeywords)
        parcel.writeStringList(geoFacet)
        parcel.writeLong(id)
        parcel.writeString(publishedDate)
        parcel.writeString(updated)
        parcel.writeString(byline)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ArticleModel> {
        override fun createFromParcel(parcel: Parcel): ArticleModel {
            return ArticleModel(parcel)
        }

        override fun newArray(size: Int): Array<ArticleModel?> {
            return arrayOfNulls(size)
        }
    }
}