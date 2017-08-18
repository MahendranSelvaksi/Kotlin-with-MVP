package mindmade.com.myapplication.youtube;

import java.util.List;

/**
 * Created by Mindmade technologies.
 */

public interface YoutubeView {

    public interface TubeView{
        void setAdapterData(List<YouTubeDataModel> data);
        void setError(String error);
    }
    interface TubePresenter{
        void loadData(String pageKoken);
    }
}
