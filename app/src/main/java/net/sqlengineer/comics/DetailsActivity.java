package net.sqlengineer.comics;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import net.sqlengineer.comics.data.Result;

public class DetailsActivity extends AppCompatActivity {

    protected static String KEY_ID = "id";

    public static Intent createStartIntent(Context ctx, Result result) {
        Intent ret = new Intent(ctx, DetailsActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        Log.i("XXX", "Adding ID " + result.getId() + " to intent");
        // Put the info necessary to identify the release and populate the views
        ret.putExtra(KEY_ID, result.getId());
        return ret;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            Log.i("XXX", "Extras found");
            Integer titleId = extras.getInt(KEY_ID);
            Log.i("XXX", "Title ID " + titleId);
            Result titleToDisplay = null;
            for (Result result : NewReleasesList.getInstance()) {
                Log.i("XXX", "Examining result " + result.getId());
                if (result.getId().intValue() == titleId.intValue()) {
                    titleToDisplay = result;
                    Log.d("XXX", "FOUND TITLE");
                    break;
                }
            }

            if (titleToDisplay != null) {
                CoverImage img = (CoverImage) findViewById(R.id.cover);
                img.setResult(titleToDisplay);
            }
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
