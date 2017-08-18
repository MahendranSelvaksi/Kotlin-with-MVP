package mindmade.com.myapplication.kotlin.youtube

import mindmade.com.myapplication.youtube.YouTubeDataModel

/**
 * Created by Mindmade technologies.
 */
public interface YoutubeKotlinView {

    interface TubeKotlinView {
        fun setAdapterData(data: List<YouTubeDataModel> = ArrayList());
        fun setError(error: String);
    }

    interface TubeKotlinPresenter {
        fun loadData(nextPageToken: String);
    }
}