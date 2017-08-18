package mindmade.com.myapplication.youtube;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mindmade.com.myapplication.utils.AppConstants;
import mindmade.com.myapplication.R;

public class YoutubeActivity extends AppCompatActivity implements YoutubeView.TubeView {

    RecyclerView recyclerView;
    ProgressBar progressBar;
    YoutubeAdapter adapter;
    List<YouTubeDataModel> dataModels;
    private int index = 0;
    YoutubePresenter presenter;
    String nextPageToken = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataModels = new ArrayList<>();
        presenter = new YoutubePresenter(this);

        recyclerView = (RecyclerView) findViewById(R.id.main_recyclerview);
        progressBar = (ProgressBar) findViewById(R.id.mainProgressbar);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        presenter.loadData(nextPageToken);
        adapter = new YoutubeAdapter(this, dataModels);
        recyclerView.setAdapter(adapter);

        adapter.setLoadMoreListener(new YoutubeAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        index = dataModels.size();
                        Log.w("Success", "Size:::: " + index);
                        dataModels.add(new YouTubeDataModel(AppConstants.LOADING_DATA));
                        adapter.notifyItemChanged(dataModels.size() - 1);
                        //   adapter.notifyDataChanged();
                        if (dataModels.size() < dataModels.get(0).getTotalResults()) {
                            presenter.loadData(nextPageToken);
                        } else {
                            adapter.setMoreDataAvailable(false);
                        }
                    }
                });
            }
        });
    }

    @Override
    public void setAdapterData(List<YouTubeDataModel> data) {
        if (data.size() > 0) {
            nextPageToken = data.get(0).getNetPageToken();
            Log.w("Success", "Next Page Token::: " + nextPageToken);
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            if (index > 0) {
                dataModels.remove(index);
            }
            dataModels.addAll(data);
            adapter.setMoreDataAvailable(true);
            adapter.notifyDataChanged();
        } else {
            nextPageToken = data.get(0).getNetPageToken();
            if (index > 0) {
                dataModels.remove(index);
            }
            dataModels.addAll(data);
            adapter.setMoreDataAvailable(false);
            adapter.notifyDataChanged();
        }
    }

    @Override
    public void setError(String error) {
        Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show();
    }
}
