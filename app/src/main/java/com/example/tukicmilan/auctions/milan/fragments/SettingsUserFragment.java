package com.example.tukicmilan.auctions.milan.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.tukicmilan.auctions.milan.R;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.tukicmilan.auctions.milan.R.xml.preferences;
import static com.example.tukicmilan.auctions.milan.R.xml.preferences_user;

/**
 * Created by TukicMilan on 3/15/2018.
 */

public class SettingsUserFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

    private Preference userFirstName;
    private Preference userLastName;
    private Preference userEmail;
    private Preference userAddress;
    private Preference userPhone;
    private Preference userPassword;

    public SettingsUserFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i("aaab", "onCreate");

        super.onCreate(savedInstanceState);
        addPreferencesFromResource(preferences_user);

       // EditText et = getView().findViewById(R.id.username);
      //  bindPreferenceSummaryToValue(userFirstName);


       /* userLastName = findPreference(getString(R.string.pref_user_last_name_key));
        bindPreferenceSummaryToValue(userLastName);

        userEmail = findPreference(getString(R.string.pref_user_email_key));
        bindPreferenceSummaryToValue(userEmail);

        userPassword = findPreference(getString(R.string.pref_user_password_key));
        bindPreferenceSummaryToValue(userPassword);

        userAddress = findPreference(getString(R.string.pref_user_address_key));
        bindPreferenceSummaryToValue(userAddress);

        userPhone = findPreference(getString(R.string.pref_user_phone_key));
        bindPreferenceSummaryToValue(userPhone);
*/
    }

    /***
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    /*@Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText et = view.findViewById(R.id.username);
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("aaaaa ", s.toString());
                bindPreferenceSummaryToValue(userFirstName);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }*/

    private void bindPreferenceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(this);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());

        Object preferenceValue = null;
        if (preference instanceof EditTextPreference) {
            preferenceValue = sharedPreferences.getString(preference.getKey(), "");
        }

        if (preferenceValue != null) {
            onPreferenceChange(preference, preferenceValue);
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String stringValue = newValue.toString();

        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            int preferenceIndex = listPreference.findIndexOfValue(stringValue);
            if (preferenceIndex >= 0) {
                CharSequence[] labels = listPreference.getEntries();
                preference.setSummary(labels[preferenceIndex]);
            }
        } else if (preference instanceof EditTextPreference) {
            preference.setSummary(newValue.toString());
        }
        return true;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i("aaab", "onActivityCreated");

        super.onActivityCreated(savedInstanceState);
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        }
    }


}
