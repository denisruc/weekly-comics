package net.sqlengineer.comics;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by druckebusch on 6/8/17.
 */

public class NewReleasesOnScrollListener extends RecyclerView.OnScrollListener {

    public NewReleasesOnScrollListener() {
        super();
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int lastPosition = layoutManager.findLastCompletelyVisibleItemPosition();
        if (lastPosition >= (recyclerView.getAdapter().getItemCount() - 1)) {
            NewReleasesList.getInstance().fetchNewReleases();
        }
    }

}
