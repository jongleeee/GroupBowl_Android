package com.heapstack.groupbowl;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

/**
 * Created by Jong on 1/7/15.
 */
public class SettingFragment extends ListFragment {

    protected Button mChangePassword;
    protected Button mChangePhone;
    protected Button mChangeName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        System.out.println("HERE IS THE SETTING FRAGMENT");


       View rootView = inflater.inflate(R.layout.fragment_setting, container, false);

        mChangePassword = (Button) rootView.findViewById(R.id.setting_change_password);
        mChangePhone = (Button) rootView.findViewById(R.id.setting_change_phone);
        mChangeName = (Button) rootView.findViewById(R.id.setting_change_name);


        mChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.requestPasswordResetInBackground(CurrentMember.getUserEmail(), new RequestPasswordResetCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage("Please check your email.")
                                    .setTitle("Yes!")
                                    .setPositiveButton(android.R.string.ok, null);
                            AlertDialog dialog = builder.create();
                            dialog.show();

                        } else {

                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage("Please try again.")
                                    .setTitle("Oops!")
                                    .setPositiveButton(android.R.string.ok, null);
                            AlertDialog dialog = builder.create();
                            dialog.show();

                        }
                    }
                });

            }
        });

        mChangePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),SettingChangePhoneActivity.class);
                startActivity(intent);
            }
        });

        mChangeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingChangeNameActivity.class);
                startActivity(intent);
            }
        });


        return rootView;
    }

}
