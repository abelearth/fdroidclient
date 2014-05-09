package org.fdroid.fdroid.views;

import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import org.fdroid.fdroid.FDroid;
import org.fdroid.fdroid.R;
import org.fdroid.fdroid.data.AppProvider;
import org.fdroid.fdroid.views.fragments.AvailableAppsFragment;
import org.fdroid.fdroid.views.fragments.CanUpdateAppsFragment;
import org.fdroid.fdroid.views.fragments.CategoryListFragment;
import org.fdroid.fdroid.views.fragments.InstalledAppsFragment;

/**
 * Used by the FDroid activity in conjunction with its ViewPager to support
 * swiping of tabs for both old devices (< 3.0) and new devices.
 */
public class AppListFragmentPageAdapter extends FragmentPagerAdapter {

    private FDroid parent = null;

    public AppListFragmentPageAdapter(FDroid parent) {
        super(parent.getSupportFragmentManager());
        this.parent = parent;
    }


    private String getTabTitle(Uri uri) {
        String[] projection = new String[] { AppProvider.DataColumns._COUNT };
        Cursor cursor = parent.getContentResolver().query(uri, projection, null, null, null);
        String suffix = "";
        if (cursor != null && cursor.getCount() == 1) {
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            suffix = " (" + count + ")";
        }
        
        return suffix;
    }
    
    @Override
    public Fragment getItem(int i) {
    	
        if ( i == 0 ) {
            return new CategoryListFragment();
        } 	
        if ( i == 1 ) {
            return new AvailableAppsFragment();
        }
        if ( i == 2 ) {
            return new InstalledAppsFragment();
        }
        return new CanUpdateAppsFragment();
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public String getPageTitle(int i) {
    	Uri uri = null;
    	String currentCategory_uri = AvailableAppsFragment.currentCategory;
    	String Title = "";
    	
        switch(i) {
        	case 0:
        		return parent.getString(R.string.tab_category);
            case 1:
                if(currentCategory_uri.equals(parent.getString(R.string.category_all))){
                	uri = AppProvider.getContentUri();
                }
                else if(currentCategory_uri.equals(parent.getString(R.string.category_recentlyupdated))){
                	uri = AppProvider.getRecentlyUpdatedUri();
                }
                else if(currentCategory_uri.equals(parent.getString(R.string.category_whatsnew))){
                	uri = AppProvider.getNewlyAddedUri();
                }
                else{
                	uri = AppProvider.getCategoryUri(currentCategory_uri);
                }
                Title = parent.getString(R.string.tab_noninstalled);
                break;
            case 2:
                uri = AppProvider.getInstalledUri();
                Title = parent.getString(R.string.inst);
            	break;
            case 3:
            	uri = AppProvider.getCanUpdateUri();
            	Title = parent.getString(R.string.tab_updates);
            	break;
            default:
                return "";    
        }
        
        return Title + getTabTitle(uri);  
    }

}
