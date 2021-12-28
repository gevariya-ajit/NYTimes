package nytimes.example.app.models

import com.google.gson.annotations.SerializedName
import nytimes.example.app.models.MediaMetadataItem

class MediaItem {
    @SerializedName("copyright")
    var copyright: String? = null

    @SerializedName("media-metadata")
    var mediaMetadata: List<MediaMetadataItem>? = null

    @SerializedName("subtype")
    var subtype: String? = null

    @SerializedName("caption")
    var caption: String? = null

    @SerializedName("type")
    var type: String? = null

    @SerializedName("approved_for_syndication")
    var approvedForSyndication = 0
}