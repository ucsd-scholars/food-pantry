package ucsd_scholars.tritonfoodpantry;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import static ucsd_scholars.tritonfoodpantry.GoogleSignInActivity.mGoogleApiClient;
import static ucsd_scholars.tritonfoodpantry.MainActivity.mAuth;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MoreOptionsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MoreOptionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoreOptionsFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button button_to_Admin;
    private Button button_to_Settings;
    private Button button_sign_out;

    // for now, this boolean checks if user has admin options
    boolean isAdmin = true;

    private OnFragmentInteractionListener mListener;

    public MoreOptionsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MoreOptionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MoreOptionsFragment newInstance(String param1, String param2) {
        MoreOptionsFragment fragment = new MoreOptionsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_more_options,
                container, false);

        button_to_Admin = (Button) view.findViewById(R.id.button_toAdminMenu);
        button_sign_out = (Button) view.findViewById(R.id.button_SignOut);
        button_to_Settings = (Button) view.findViewById(R.id.button_toSettings);

        button_to_Settings.setOnClickListener(this);
        button_sign_out.setOnClickListener(this);

        // gives admins permissions like notification ability, hides its otherwise
        if(isAdmin){
            button_to_Admin.setOnClickListener(this);

        }
        else{
            button_to_Admin.setVisibility(View.GONE);
        }

        //return inflater.inflate(R.layout.fragment_more_options, container, false);
        return view;
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
        Intent intent;
        switch (view.getId()) {
            case R.id.button_toAdminMenu:
                intent = new Intent(getActivity(), AdminMenuActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.button_toNotificationActivity:
                intent = new Intent(getActivity(), NotificationActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.button_toContact:
                break;
            case R.id.button_toSettings:
                intent = new Intent(getActivity(), SettingsActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.button_toVolunteer:
                break;
            case R.id.button_SignOut:
                signOutDialog();
                //mAuth.signOut();
                break;

        }
    }

    // prompts user and asks if he/she wants to sign out; if so, we sign out and return to main activity
    public void signOutDialog(){
        Log.d("MoreOptionsFragment", "sign out button pressed");
        new AlertDialog.Builder(getActivity())
                .setTitle("Sign Out")
                .setMessage("Are you sure you want to sign out?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Firebase sign out
                        mAuth.signOut();

                        // Google sign out
                        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                                new ResultCallback<Status>() {
                                    @Override
                                    public void onResult(@NonNull Status status) {
                                        returnToMain();
                                    }
                                });

                       /* getActivity().finish();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);*/
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    // when user signs out, we return to main screen
    public void returnToMain(){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }
}
