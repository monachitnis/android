package com.myandroid.apps.BasicTwitter;

import android.util.Log;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

public abstract class EndlessScrollListener implements OnScrollListener {
	// The minimum amount of items to have below your current scroll position
    // before loading more.
    private int visibleThreshold = 5;
    // The current offset index of data you have loaded
    //private int currentPage = 1;
    //private long currentIndex = 1;
    // The total number of items in the dataset after the last load
    private int previousTotalItemCount = 1;
    // True if we are still waiting for the last set of data to load.
    private boolean loading = true;
    private int count;

    public EndlessScrollListener() {
    }

    public EndlessScrollListener(int count) {
        this.count = count;
    }
    
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		//do-nothing

	}

	// This happens many times a second during a scroll, so be wary of the code you place here.
    // We are given a few useful parameters to help us work out if we need to load some more data,
    // but first we check if we are waiting for the previous load to finish.
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		Log.d("Twitter", "totalcount="+totalItemCount);
		// If the total item count is zero and the previous isn't, assume the
        // list is invalidated and should be reset back to initial state
        if (totalItemCount < previousTotalItemCount) {
        	Log.d("Twitter", "LESS, totalcount="+totalItemCount + ",previous="+previousTotalItemCount);
            //currentIndex = startIndex;
            previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) { loading = true; } 
        }

        // If it’s still loading, we check to see if the dataset count has
        // changed, if so we conclude it has finished loading and update the current page
        // number and total item count.
        if (loading && (totalItemCount > previousTotalItemCount)) {
        	Log.d("Twitter", "GREATER, totalcount="+totalItemCount + ",previous="+previousTotalItemCount);
        	//currentIndex = totalItemCount;
        	previousTotalItemCount = totalItemCount;
        	if (totalItemCount < count) {
        		//continue to load till the count we've set
        		Log.d("MONA", "less than count, load more");
        		loading = true;
        	}
        	else
        		loading = false;
        }

        // If it isn’t currently loading, we check to see if we have breached
        // the visibleThreshold and need to reload more data.
        // If we do need to reload some more data, we execute onLoadMore to fetch the data.
        if (!loading && (totalItemCount - visibleItemCount)<=(firstVisibleItem + visibleThreshold)) {
        	Log.d("Twitter", "calling onLoadMore, totalcount="+totalItemCount + ",previous="+previousTotalItemCount);
            onLoadMore();
            loading = true;
        }

	}
	
	// Defines the process for actually loading more data based on page
	// to be defined in GridViewActivity
    public abstract void onLoadMore();

}
