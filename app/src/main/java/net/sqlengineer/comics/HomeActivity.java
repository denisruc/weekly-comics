package net.sqlengineer.comics;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import net.sqlengineer.comics.data.Result;

public class HomeActivity extends AppCompatActivity implements NewReleasesFragment.OnListFragmentInteractionListener {

    NewReleasesFragment newReleasesFrag = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (null == savedInstanceState) {
            String attributionStr = NewReleasesList.getInstance().getAttributionString();
            newReleasesFrag = NewReleasesFragment.newInstance(attributionStr);
            getFragmentManager().beginTransaction().add(R.id.content_home, newReleasesFrag, null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListFragmentInteraction(Result item) {
        Log.i("TAG", "Click on " + (item != null ?item.getTitle() : "null item") );
        if (item != null) {
            this.startActivity(DetailsActivity.createStartIntent(this, item));
        }
    }

}
