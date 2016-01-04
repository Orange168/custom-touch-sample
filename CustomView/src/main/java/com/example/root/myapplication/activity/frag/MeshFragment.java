package com.example.root.myapplication.activity.frag;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.root.myapplication.R;
import com.example.root.myapplication.view.BitmapMeshView;
import com.example.root.myapplication.view.MeshViewAdvance;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MeshFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MeshFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeshFragment extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	private OnFragmentInteractionListener mListener;
	private BitmapMeshView meshView;
	private MeshViewAdvance advance;

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment MeshFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static MeshFragment newInstance(String param1, String param2) {
		MeshFragment fragment = new MeshFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}	public MeshFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.add(0, 0, 0, "translation");
		menu.add(0, 1, 1, "magnify");
		menu.add(0, 2, 2, "advance");
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case 0:
				advance.setVisibility(View.GONE);
				meshView.setVisibility(View.VISIBLE);
				meshView.translation();
				break;
			case 1:
				advance.setVisibility(View.GONE);
				meshView.setVisibility(View.VISIBLE);
				meshView.magnify();
				break;
			case 2:
				meshView.setVisibility(View.GONE);
				advance.setVisibility(View.VISIBLE);
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_mesh, container, false);
		meshView = (BitmapMeshView) view.findViewById(R.id.bitmap);
		advance = (MeshViewAdvance) view.findViewById(R.id.advance);
		return view;
	}

	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			mListener.onFragmentInteraction(uri);
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
	public interface OnFragmentInteractionListener {
		// TODO: Update argument type and name
		void onFragmentInteraction(Uri uri);
	}
}
