package net.sqlengineer.comics;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import net.sqlengineer.comics.client.MarvelRestClient;
import net.sqlengineer.comics.data.ComicDataWrapper;
import net.sqlengineer.comics.data.Data;
import net.sqlengineer.comics.data.Result;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.function.UnaryOperator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by druckebusch on 6/8/17.
 */

public class NewReleasesList implements List<Result>, Callback<ComicDataWrapper> {

    private static NewReleasesList mInstance;

    public static NewReleasesList getInstance() {
        if (null == mInstance) {
            mInstance = new NewReleasesList();
        }
        return mInstance;
    }

    public static void wipe() {
        mInstance = null;
    }

    private NewReleasesList() {

    }

    @Override
    public int size() {
        return mList.size();
    }

    @Override
    public boolean isEmpty() {
        return mList.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return mList.contains(o);
    }

    @NonNull
    @Override
    public Iterator<Result> iterator() {
        return mList.iterator();
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return mList.toArray();
    }

    @NonNull
    @Override
    public <T> T[] toArray(@NonNull T[] ts) {
        return mList.toArray(ts);
    }

    @Override
    public boolean add(Result result) {
        return mList.add(result);
    }

    @Override
    public boolean remove(Object o) {
        return mList.remove(o);
    }

    @Override
    public boolean containsAll(@NonNull Collection<?> collection) {
        return mList.containsAll(collection);
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends Result> collection) {
        return mList.addAll(collection);
    }

    @Override
    public boolean addAll(int i, @NonNull Collection<? extends Result> collection) {
        return mList.addAll(i, collection);
    }

    @Override
    public boolean removeAll(@NonNull Collection<?> collection) {
        return mList.removeAll(collection);
    }

    @Override
    public boolean retainAll(@NonNull Collection<?> collection) {
        return mList.retainAll(collection);
    }

    @Override
    public void clear() {
        mList.clear();
    }

    @Override
    public boolean equals(Object o) {
        return mList.equals(o);
    }

    @Override
    public int hashCode() {
        return mList.hashCode();
    }

    @Override
    public Result get(int i) {
        return mList.get(i);
    }

    @Override
    public Result set(int i, Result result) {
        return mList.set(i, result);
    }

    @Override
    public void add(int i, Result result) {
        mList.add(i, result);
    }

    @Override
    public Result remove(int i) {
        return mList.remove(i);
    }

    @Override
    public int indexOf(Object o) {
        return mList.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return mList.lastIndexOf(o);
    }

    @Override
    public ListIterator<Result> listIterator() {
        return mList.listIterator();
    }

    @NonNull
    @Override
    public ListIterator<Result> listIterator(int i) {
        return mList.listIterator(i);
    }

    @NonNull
    @Override
    public List<Result> subList(int i, int i1) {
        return mList.subList(i, i1);
    }

    @Override
    public Spliterator<Result> spliterator() {
        return mList.spliterator();
    }

    @Override
    public void replaceAll(UnaryOperator<Result> operator) {
        mList.replaceAll(operator);
    }

    @Override
    public void sort(Comparator<? super Result> c) {
        mList.sort(c);
    }

    private List<Result> mList = new ArrayList<Result>();
    private NewReleasesFragment mFrag;

    // will store the total value returned by the REST API
    private int mTotal = -1;

    public String getAttributionString() {
        return mAttributionStr;
    }

    // Save the last version of the attribution String
    private String mAttributionStr;


    private void setAttributionText(String attributionText) {
        mAttributionStr = attributionText;
        if (mFrag != null) {
            mFrag.setAttributionText(attributionText);
        }
    }

    public void setNewReleasesFragment(NewReleasesFragment frag) {
        this.mFrag = frag;
        NewReleasesRecyclerViewAdapter adapter = new NewReleasesRecyclerViewAdapter(this, frag.getListener());
        frag.getRecyclerView().setAdapter(adapter);
    }


    @Override
    public void onResponse(Call<ComicDataWrapper> call, Response<ComicDataWrapper> response) {

        final Data data = response.body().getData();
        mTotal = data.getTotal();

        int position = size();
        setAttributionText(response.body().getAttributionText());

        for (Result result : data.getResults()) {
            mList.add(result);
            mFrag.getRecyclerView().getAdapter().notifyItemInserted(position++);
        }


        /*
        NewReleaseRecyclerViewAdapter adapter = new NewReleaseRecyclerViewAdapter2(mList, mListener);
        mRecyclerView.setAdapter(adapter);
        */

    }

    @Override
    public void onFailure(Call<ComicDataWrapper> call, Throwable t) {

    }

    public void notifyItemChanged(Result result) {
        final int index = mList.indexOf(result);
        if (index >= 0) {

            mFrag.getRecyclerView().post(new Runnable() {
                @Override
                public void run() {
                    mFrag.getRecyclerView().getAdapter().notifyItemChanged(index);
                }
            });


        }
    }

    public boolean isLandscape() {
        if (null == mFrag || null == mFrag.getRecyclerView()) {
            return false;
        }
        return mFrag.getRecyclerView().getContext().getResources().getBoolean(R.bool.is_landscape);
    }

    public boolean isDoneFetching() {
        return (mTotal >= 0 && mList.size() >= mTotal);
    }


    public boolean fetchNewReleases() {

        if (isDoneFetching()) {
            return false;
        }

        Call<ComicDataWrapper> req = MarvelRestClient.getClient().getComics("thisWeek", size());
        req.enqueue(this);
        return true;
    }

}
