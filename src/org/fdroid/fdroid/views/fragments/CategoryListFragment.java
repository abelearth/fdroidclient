package org.fdroid.fdroid.views.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.fdroid.fdroid.R;
import org.fdroid.fdroid.data.AppProvider;
import org.fdroid.fdroid.views.AppListFragmentPageAdapter;
import org.fdroid.fdroid.views.fragments.AvailableAppsFragment;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;


public  class CategoryListFragment extends Fragment {
	
		private View rootView;  
		
		private List<String> categories = new ArrayList<String>(); 
		private ListView categoryListView = null;

		
	    @Override  
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
	            Bundle savedInstanceState) {  
	    	if (rootView == null) { 
	    		rootView = inflater.inflate(R.layout.categorylist, container, false); 
	    	}
	    	
	        //fixed bug of view page  
	        ViewGroup parent = (ViewGroup) rootView.getParent();  
	        if (parent != null) {  
	            parent.removeView(rootView);  
	        }
	        
	        return  rootView;
	    }  
	      
	      
	    @SuppressWarnings("rawtypes")
		@Override  
	    public void onActivityCreated(Bundle savedInstanceState) {  
	        super.onActivityCreated(savedInstanceState);  
	        
	        //add data
	        categories = AppProvider.Helper.categories(getActivity());
	        
	        //view ListView  
	        categoryListView = (ListView) getActivity().findViewById(R.id.categorylistview);  
	        categoryListView.setAdapter(new ArrayAdapter(getActivity(), 
	        		android.R.layout.simple_list_item_1, categories)); 
	        
	        //key action     
	        categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					Log.d("FDroid","OnItemClick(position = "+position+")" );	
					ShowAvailableAppsFragment(categories.get(position));
				}
	        });
	        


	    }//onActivityCreated
	    	    
	    /**
	     * Send Directional Pad key
	     * @param KeyCode
	     */
	    public static void simulateKey(final int KeyCode) {
	        new Thread() {
	            public void run() {
	                try {
	                    Instrumentation inst = new Instrumentation();
	                    inst.sendKeyDownUpSync(KeyCode);
	                } catch (Exception e) {
	                    Log.e("Exception when sendKeyDownUpSync", e.toString());
	                }
	            }
	   
	        }.start();
	    }
	
	    /**
	     * Show AvailableAppsFragment view page
	     * @param String category
	     */
	    public void ShowAvailableAppsFragment(String category) {
	    	//Restore Category
	    	AvailableAppsFragment.currentCategory = category;
	    	
	    	/* store the Category Spinner position */
	        for (int i = 0; i < AvailableAppsFragment.categorySpinner.getCount(); i++) {
	            if (AvailableAppsFragment.currentCategory.equals(AvailableAppsFragment.categorySpinner.getItemAtPosition(i).toString())) {
	            	AvailableAppsFragment.categorySpinner.setSelection(i);
	            	Log.d("FDroid", "Category (" + i + ")setSelection");
	                break;
	            }
	        }
	        
	    	//Send key
	    	simulateKey(KeyEvent.KEYCODE_DPAD_RIGHT);
	    	
	    	Log.d("FDroid","Showing AvailableAppsFragment!!!");
	    }
	    
}




