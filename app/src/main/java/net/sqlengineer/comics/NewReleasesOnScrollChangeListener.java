package net.sqlengineer.comics;


import android.annotation.TargetApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by druckebusch on 6/8/17.
 */
@TargetApi(23)
public class NewReleasesOnScrollChangeListener implements View.OnScrollChangeListener {

    public NewReleasesOnScrollChangeListener() {
    }

    @Override
    public void onScrollChange(View view, int i, int i1, int i2, int i3) {

        RecyclerView recyclerView = (RecyclerView) view;
        GridLayoutManager mgr;
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

        int lastPosition = layoutManager.findLastCompletelyVisibleItemPosition();
        if (lastPosition >= (recyclerView.getAdapter().getItemCount() - 1)) {
            NewReleasesList.getInstance().fetchNewReleases();
        }

    }
}
