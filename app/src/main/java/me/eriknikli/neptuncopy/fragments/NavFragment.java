package me.eriknikli.neptuncopy.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import me.eriknikli.neptuncopy.R;
import me.eriknikli.neptuncopy.activities.HomeActivity;
import me.eriknikli.neptuncopy.activities.MainActivity;
import me.eriknikli.neptuncopy.activities.MarksActivity;
import me.eriknikli.neptuncopy.models.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NavFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NavFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NavFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NavFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NavFragment newInstance(String param1, String param2) {
        NavFragment fragment = new NavFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static void init(AppCompatActivity container, FirebaseAuth auth, FirebaseFirestore db) {
        Button showMarksBtn = container.findViewById(R.id.nav_showMarksBtn);
        Button writeMarksBtn = container.findViewById(R.id.nav_writeMarksBtn);
        Button logoutBtn = container.findViewById(R.id.nav_logoutBtn);
        Button homeBtn = container.findViewById(R.id.nav_showCourseBtn);
        homeBtn.setOnClickListener(l -> {
            container.startActivity(new Intent(container, HomeActivity.class));
        });
        showMarksBtn.setOnClickListener(l -> {
            container.startActivity(new Intent(container, MarksActivity.class));
        });
        writeMarksBtn.setOnClickListener(l -> {
            container.startActivity(new Intent(container, MarksActivity.class));
        });
        writeMarksBtn.setVisibility(View.GONE);
        showMarksBtn.setVisibility(View.GONE);
        User.getLatest(auth, db, u -> {
            writeMarksBtn.setVisibility(u.getIsTeacher() ? View.VISIBLE : View.GONE);
            showMarksBtn.setVisibility(u.getIsTeacher() ? View.GONE : View.VISIBLE);
        });
        logoutBtn.setOnClickListener(l -> {
            auth.signOut();
            User.clearLatest();
            container.startActivity(new Intent(container, MainActivity.class));
        });
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


        return inflater.inflate(R.layout.fragment_nav, container, false);
    }
}