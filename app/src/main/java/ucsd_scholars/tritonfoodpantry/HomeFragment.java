package ucsd_scholars.tritonfoodpantry;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    static final String STORY_TITLE_AND_DETAILS = "param1";

    private static final int STORY_TITLE_SIZE = 30;
    private static final int STORY_BODY_SIZE = 15;
    private static final int BOTTOM_PADDING = 10;

    private static final int HEADER_SIZE = 50;
    private static final int HEADER_PADDING = 5;
    private static final int MAX_LINES = 1000;

    private ScrollView scroll;
    private LinearLayout ll;
    LinearLayout.LayoutParams layoutParams, imageParams;
    private ImageView image;

    // TODO: Rename and change types of parameters
    // private String storyText;

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
        layoutParams.setMargins(convertToDP(HEADER_PADDING),convertToDP(HEADER_PADDING),
                                convertToDP(HEADER_PADDING), convertToDP(HEADER_PADDING));
        imageParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        imageParams.gravity = Gravity.CENTER;

        updateNewsFeed();

        return view;
    }

    // this function adds notifications to our scrolling inbox page
    private void addNewStory(Story s) {
        // creates a new textView that will be placed in our scrolling linear layout

        TextView tv = new TextView(getActivity());
        String text = "<b>" + s.title + "</b> <br />" + s.story;
        tv.setText(Html.fromHtml(text));
        tv.setLayoutParams(layoutParams);
        tv.setTextSize(STORY_TITLE_SIZE);
        tv.setTextColor(getResources().getColor(R.color.white));
        tv.setBackgroundColor(getResources().getColor(R.color.DeepSkyBlue));
        tv.setMaxLines(MAX_LINES);
        //tv.setPadding(0, convertToDP(HEADER_PADDING), 0, 0);
        ll.addView(tv);

        /*TextView tv2 = new TextView(getActivity());
        tv2.setText(s.story);
        tv2.setLayoutParams(layoutParams);
        tv2.setTextSize(STORY_BODY_SIZE);
        // tv2.setPadding(0,0,0,5);
        tv2.setBottom(BOTTOM_PADDING);
        tv2.setTextColor(getResources().getColor(R.color.white));
        tv2.setBackgroundColor(getResources().getColor(R.color.blue));
        //tv2.setPadding(0, 0, 0, convertToDP(HEADER_PADDING));
        ll.addView(tv2);*/
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
        clearNewsFeed();
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

    // loops through all notifications from database
    public void updateNewsFeed(){
        clearNewsFeed();
        for(int i = 0; i < MainActivity.db.stories.size(); i++){
            addNewStory(MainActivity.db.stories.get(i));
        }
    }

    // clears the news feed before updating, adds the header back
    public void clearNewsFeed(){
        if(ll.getChildCount() > 0){
            ll.removeAllViews();
        }

        // re-adds image and header after clearing screen
        image = new ImageView(getActivity());
        image.setBackgroundResource(R.drawable.profile_outline);
        image.setLayoutParams(imageParams);
        ll.addView(image);

        TextView tv = new TextView(getActivity());
        tv.setText("News Feed");
        tv.setTextColor(getResources().getColor(R.color.colorPrimary));
        tv.setLayoutParams(layoutParams);
        tv.setTextSize(HEADER_SIZE);
        tv.setPadding(0,0,0, HEADER_PADDING);
        tv.setGravity(Gravity.CENTER);
        ll.addView(tv);
    }

    // converts pixels to dp
    private int convertToDP(int paddingPixel){
        float density = this.getResources().getDisplayMetrics().density;
        int paddingDp = (int)(paddingPixel * density);
        return paddingDp;
    }
}
