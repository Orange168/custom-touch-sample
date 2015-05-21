package com.example.root.fragment_activity;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Demonstration of using ListFragment to show a list of items
 * from a canned array.
 */
public class FragmentListArray extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("HJJ", "Activity &&&& onCreate...");
        // Create the list fragment and add it as our sole content.
        if (getFragmentManager().findFragmentById(android.R.id.content) == null) {
            ArrayListFragment list = new ArrayListFragment();
//            ListAdapter listAdapter = new ListAdapter() ;
            getFragmentManager().beginTransaction().add(android.R.id.content, list).commit();
        }
    }

    @Override
    protected void onStart() {
    	// TODO Auto-generated method stub
    	super.onStart();
    	Log.e("HJJ", "Activity &&&& onStart...");
    }
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	Log.e("HJJ", "Activity &&&& onResume...");
    }
    
    @Override
    protected void onStop() {
    	// TODO Auto-generated method stub
    	super.onStop();
    	Log.e("HJJ", "Activity &&&& onStop...");
    }
    
    @Override
    protected void onPause() {
    	// TODO Auto-generated method stub
    	super.onPause();
    	Log.e("HJJ", "Activity &&&& onPause...");
    }
    
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    	Log.e("HJJ", "Activity &&&& onDestroy...");
    }





       public static class ArrayListFragment extends ListFragment {

        public ArrayListFragment() {

           }

    	@Override
    	public void onAttach(Activity activity) {
    		// TODO Auto-generated method stub
    		Log.e("HJJ", "ArrayListFragment **** onAttach...");
    		super.onAttach(activity);
    	}
    	
    	@Override
    	public void onCreate(Bundle savedInstanceState) {
    		// TODO Auto-generated method stub
    		Log.e("HJJ", "ArrayListFragment **** onCreate...");
    		super.onCreate(savedInstanceState);
    	}
    	
    	@Override
    	public View onCreateView(LayoutInflater inflater, ViewGroup container,
    			Bundle savedInstanceState) {
    		// TODO Auto-generated method stub
    		Log.e("HJJ", "ArrayListFragment **** onCreateView...");
    		return super.onCreateView(inflater, container, savedInstanceState);
    	}
    	
        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            Log.e("HJJ", "ArrayListFragment **** onActivityCreated...");
            String[] array = new String[]{"C++", "JAVA", "PYTHON"};
            setListAdapter(new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, array));
        }

        @Override
        public void onStart() {
        	// TODO Auto-generated method stub
        	Log.e("HJJ", "ArrayListFragment **** onStart...");
        	super.onStart();
        }
        
        @Override
        public void onResume() {
        	Log.e("HJJ", "ArrayListFragment **** onResume...");
        	// TODO Auto-generated method stub
        	super.onResume();
        }
        
        @Override
        public void onPause() {
        	Log.e("HJJ", "ArrayListFragment **** onPause...");
        	// TODO Auto-generated method stub
        	super.onPause();
        }
        
        @Override
        public void onStop() {
        	Log.e("HJJ", "ArrayListFragment **** onStop...");
        	// TODO Auto-generated method stub
        	super.onStop();
        }
        
        @Override
        public void onDestroyView() {
        	Log.e("HJJ", "ArrayListFragment **** onDestroyView...");
        	// TODO Auto-generated method stub
        	super.onDestroyView();
        }
        
        @Override
        public void onDestroy() {
        	// TODO Auto-generated method stub
        	Log.e("HJJ", "ArrayListFragment **** onDestroy...");
        	super.onDestroy();
        }
        
        @Override
        public void onDetach() {
        	Log.e("HJJ", "ArrayListFragment **** onDetach...");
        	// TODO Auto-generated method stub
        	super.onDetach();
        }
        
        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            Log.i("FragmentList", "Item clicked: " + id);
        }
    }
}
