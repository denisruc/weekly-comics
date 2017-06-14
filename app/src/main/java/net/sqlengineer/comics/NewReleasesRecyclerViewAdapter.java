package net.sqlengineer.comics;

import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.sqlengineer.comics.data.Result;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Result} and makes a call to the
 * specified {@link NewReleasesFragment.OnListFragmentInteractionListener}.
 */
public class NewReleasesRecyclerViewAdapter extends RecyclerView.Adapter<NewReleasesRecyclerViewAdapter.ViewHolder> {

    private final NewReleasesList mValues;
    private final NewReleasesFragment.OnListFragmentInteractionListener mListener;

    public NewReleasesRecyclerViewAdapter(NewReleasesList items, NewReleasesFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_newrelease, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        holder.mTitleView.setText(mValues.get(position).getTitle());
        holder.mDescriptionView.setText(mValues.get(position).getDescription());
        // Clear whatever image was there
        holder.mCoverView.setImageDrawable(null);
        holder.mCoverView.setVisibility(View.VISIBLE);

        // Give the image view a new result to display
        holder.mCoverView.setResult(mValues.get(position));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });



    }

/*
    private void formatViews(final ViewHolder holder, int position) {

        int titleWidth = holder.mTitleView.getWidth();
        int descWidth = holder.mDescriptionView.getWidth();

        if ( (2 * titleWidth) > descWidth) {
            holder.mDescriptionView.setVisibility(View.INVISIBLE);
            holder.mTitleView.setWidth(titleWidth + descWidth);
        }

        if (null == holder.mItem) {

            TextView targetView = (holder.mDescriptionView.getVisibility() == View.VISIBLE) ?
                    holder.mDescriptionView : holder.mTitleView;


            // Deal with the null Result we have for attribution
            targetView.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                targetView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
            }
            holder.mDescriptionView.setText(NewReleasesFragment2.getAttributionText());
            holder.mTitleView.setText("");
            holder.mCoverView.setVisibility(View.INVISIBLE);

        } else {
            holder.mTitleView.setText(mValues.get(position).getTitle());
            holder.mDescriptionView.setGravity(Gravity.TOP);
            holder.mTitleView.setGravity(Gravity.TOP);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                holder.mDescriptionView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                holder.mTitleView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            }
            holder.mDescriptionView.setText(mValues.get(position).getDescription());
            // Clear whatever image was there
            holder.mCoverView.setImageDrawable(null);
            holder.mCoverView.setVisibility(View.VISIBLE);
            // Give the image view a new result to display
            holder.mCoverView.setResult(mValues.get(position));
        }

    }

*/

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitleView;
        public final TextView mDescriptionView;
        public final CoverImage mCoverView;
        public Result mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitleView = (TextView) view.findViewById(R.id.title);
            mDescriptionView = (TextView) view.findViewById(R.id.description);
            mCoverView = (CoverImage) view.findViewById(R.id.cover);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mDescriptionView.getText() + "'";
        }
    }
}
