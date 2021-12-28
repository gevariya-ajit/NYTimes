package nytimes.example.app.models

import com.google.gson.annotations.SerializedName

class MediaMetadataItem {
    @SerializedName("format")
    var format: String? = null

    @SerializedName("width")
    var width = 0

    @SerializedName("url")
    var url: String? = null

    @SerializedName("height")
    var height = 0
}