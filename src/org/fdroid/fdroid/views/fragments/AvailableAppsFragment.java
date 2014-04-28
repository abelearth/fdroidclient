package org.fdroid.fdroid.views.fragments;

import android.net.Uri;
import android.util.Log;

import org.fdroid.fdroid.R;
import org.fdroid.fdroid.data.AppProvider;
import org.fdroid.fdroid.views.AppListAdapter;
import org.fdroid.fdroid.views.AvailableAppListAdapter;

public class AvailableAppsFragment extends AppListFragment {

	public static String currentCategory = null;
	
	@Override
	protected AppListAdapter getAppListAdapter() {
		return new AvailableAppListAdapter(getActivity(), null);
	  }

    @Override
    protected String getFromTitle() {
        return getString(R.string.tab_noninstalled);
    }

    @Override
    protected Uri getDataUri() {
    	Log.d("FDroid", "getDataUri"+"---current category is "+ currentCategory +"!!!");
    	
      if (currentCategory == null || currentCategory.equals(AppProvider.Helper.getCategoryAll(getActivity())))
	      return AppProvider.getContentUri();
	  else if (currentCategory.equals(AppProvider.Helper.getCategoryRecentlyUpdated(getActivity())))
	      return AppProvider.getRecentlyUpdatedUri();
	  else if (currentCategory.equals(AppProvider.Helper.getCategoryWhatsNew(getActivity())))
	      return AppProvider.getNewlyAddedUri();
	  else
	      return AppProvider.getCategoryUri(currentCategory);
    }   

}