package com.niedzielski.wikiimagesearch.android.activity;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import com.niedzielski.wikiimagesearch.android.R;

/** Sane defaults and boilerplate for most {@link FragmentActivity} implementations. */
public abstract class DefaultActivity extends ActionBarActivity {
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        animateOldActivityOut();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                animateOldActivityOut();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /** Slides this activity out left and the new activity in from the right. Often used when
     * increasing the activity stack. */
    protected void animateNewActivityIn() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
    }

    /** Slides this activity out right and the new activity in from the left. Often used when
     * reducing the activity stack. */
    protected void animateOldActivityOut() {
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
    }
}