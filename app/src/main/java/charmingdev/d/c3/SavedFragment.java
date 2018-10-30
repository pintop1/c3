package charmingdev.d.c3;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import charmingdev.d.c3.prefs.C3UserSession;


/**
 * A simple {@link Fragment} subclass.
 */
public class SavedFragment extends Fragment {

    View fragView;
    private C3UserSession c3UserSession;

    public SavedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragView = inflater.inflate(R.layout.fragment_saved, container, false);
        c3UserSession = new C3UserSession(getActivity());
        checkIfUserIsLogged();
        return fragView;
    }

    private void checkIfUserIsLogged(){
        if(c3UserSession.isUserLoggedIn()) {

        }else {
           startActivity(new Intent(getActivity(), Login.class));
       }
    }

}
