package com.Ashish.foodnetwork.UserDashboard;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.Ashish.foodnetwork.R;
import com.Ashish.foodnetwork.userAccount.UserAccountActivity;
import com.Ashish.foodnetwork.utils.Constants;
import com.Ashish.foodnetwork.utils.SharePrefrenceUtils;


public class ProfileFragment extends Fragment {

    Button logout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logout= view.findViewById(R.id.logout);
        onClickmethod();
    }

    private void onClickmethod() {
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharePrefrenceUtils.setBooleanPreference(getContext(), Constants.IS_LOGIN_KEY,false);
                SharePrefrenceUtils.setStringPreference(getContext(),Constants.API_KEY,"");
                Intent intent= new Intent(getActivity(), UserAccountActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }
}