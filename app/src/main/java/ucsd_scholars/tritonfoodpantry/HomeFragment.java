package ucsd_scholars.tritonfoodpantry;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
   static final String STORY_TITLE_AND_DETAILS = "param1";

    private static final int STORY_TEXT_SIZE = 40;

    private ScrollView scroll;
    private LinearLayout ll;
    LinearLayout.LayoutParams layoutParams;

    // TODO: Rename and change types of parameters
    private String storyText;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param text the event title and details
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newHomeFragment(String text) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(STORY_TITLE_AND_DETAILS, text);
        fragment.setArguments(args);
        return fragment;
    }

    // if our HomeFragment was given a bundle, we add new story to home page with "storyText"
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null && savedInstanceState.getString(STORY_TITLE_AND_DETAILS) != null){
            storyText = savedInstanceState.getString(STORY_TITLE_AND_DETAILS);
            Log.d("HomeFragment", storyText);
            addNewStory(storyText);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home,
                container, false);

        // the linear layout ll is the layout in which we want to add scrolling events/stories
        scroll = (ScrollView) view.findViewById(R.id.home_scroll);
        ll = (LinearLayout) scroll.findViewById(R.id.home_news_feed);
        layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        // this function adds events/stories to our scrolling home page
        // addNewStory("This is a news story");
        if(getArguments() != null && getArguments().getString(STORY_TITLE_AND_DETAILS) != null){
            storyText = getArguments().getString(STORY_TITLE_AND_DETAILS);
            Log.d("HomeFragment", "got the story text from bundle!!!" + storyText);
            addNewStory(storyText);
        }

        return view;
    }

    // this function adds events/stories to our scrolling home page
    private void addNewStory(String string) {
        // creates a new textView that will be placed in our scrolling linear layout
        TextView tv = new TextView(getActivity());
        tv.setText(string);
        tv.setLayoutParams(layoutParams);
        tv.setTextSize(STORY_TEXT_SIZE);
        tv.setPadding(0,0,0,5);
        ll.addView(tv);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()) {

        }
    }
}
