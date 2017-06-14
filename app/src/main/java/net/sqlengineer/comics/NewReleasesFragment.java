package net.sqlengineer.comics;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.sqlengineer.comics.data.Result;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class NewReleasesFragment extends Fragment {

    //TODO DEBUG
    private static int count = 0;

    private static String ARG_ATTRIBUTION_STRING;

    private OnListFragmentInteractionListener mListener;
    private RecyclerView mRecyclerView;
    private TextView mAttributionTextView;

    private String mAttributionStr;

    public OnListFragmentInteractionListener getListener() {
        return mListener;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public void setAttributionText(String text) {
        if (mAttributionTextView != null) {
            mAttributionTextView.setText(text);
        }
        Bundle args = this.getArguments();
        if (null != args) {
            args.putString(ARG_ATTRIBUTION_STRING, text);
        }
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NewReleasesFragment() {
    }


    public static NewReleasesFragment newInstance() {
        return newInstance(null);
    }

    public static NewReleasesFragment newInstance(String attributionText) {
        NewReleasesFragment fragment = new NewReleasesFragment();
        if (attributionText != null) {
            Bundle args = new Bundle();
            args.putString(ARG_ATTRIBUTION_STRING, attributionText);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null && savedInstanceState.containsKey(ARG_ATTRIBUTION_STRING)) {
            setAttributionText(savedInstanceState.getString(ARG_ATTRIBUTION_STRING));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Log.i("TAG", "NewReleasesFragment.onCreateView " + (++count));

        View view = inflater.inflate(R.layout.fragment_newrelease_list, container, false);
        View listView = view.findViewById(R.id.list);
        mAttributionTextView = (TextView) view.findViewById(R.id.attribution);

        if (savedInstanceState != null && savedInstanceState.containsKey(ARG_ATTRIBUTION_STRING)) {
            setAttributionText(savedInstanceState.getString(ARG_ATTRIBUTION_STRING));
        } else {
            String attribution = NewReleasesList.getInstance().getAttributionString();
            if (attribution != null) {
                setAttributionText(attribution);
            }
        }

        // Set the adapter
        if (listView instanceof RecyclerView) {
            Context context = listView.getContext();
            mRecyclerView = (RecyclerView) listView;
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));

            if (android.os.Build.VERSION.SDK_INT >= 23) {
                mRecyclerView.setOnScrollChangeListener(new NewReleasesOnScrollChangeListener());
            } else {
                mRecyclerView.setOnScrollListener(new NewReleasesOnScrollListener());
            }

            NewReleasesList.getInstance().setNewReleasesFragment(this);
            NewReleasesList.getInstance().fetchNewReleases();
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Result item);
    }
}
