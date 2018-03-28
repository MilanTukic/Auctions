package com.example.tukicmilan.auctions.milan.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.tukicmilan.auctions.milan.R;

import static android.content.ContentValues.TAG;
import static com.example.tukicmilan.auctions.milan.R.xml.preferences;


/**
 * Created by TukicMilan on 1/23/2018.
 */

public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

    private boolean editable = true;
   /* private Preference userFirstName;
    private Preference userLastName;
    private Preference userEmail;
    private Preference userAddress;
    private Preference userPhone;
    private Preference userPassword;*/

    public SettingsFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i("aaab", "onCreate");

        super.onCreate(savedInstanceState);
        addPreferencesFromResource(preferences);

        //imageButton =  findPreference(R.id.cbEdit);


        CheckBoxPreference checkBox = (CheckBoxPreference) findPreference(getString(R.string.pref_splash_screen_edit));
        //checkBox.setSummaryOn(R.string.preference_on);


       /* checkBox.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Log.i("!!!!!", "Checkbox clicked: " + editable);
                if (!editable) {
                    preferencesEnabling(true, userFirstName, userLastName, userEmail, userPassword, userAddress, userPhone);
                } else {
                    preferencesEnabling(false, userFirstName, userLastName, userEmail, userPassword, userAddress, userPhone);
                }
                editable = !editable;
                return editable;
            }
        });*/

       // Preference edit = findPreference(getString(R.string.pref_splash_screen_edit));
       // bindPreferenceSummaryToValue(edit);

        Preference splashScreenVisible = findPreference(getString(R.string.pref_splash_screen_key));
        bindPreferenceSummaryToValue(splashScreenVisible);

       /* userFirstName = findPreference(getString(R.string.pref_user_first_name_key));
        bindPreferenceSummaryToValue(userFirstName);

        userLastName = findPreference(getString(R.string.pref_user_last_name_key));
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
        //forFun();
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

    /*public void forFun(){
        for(int i = 0; i < 10; i++){
            i--;
            Log.i("!!!!!!", "i: "+i);
        }
    }*/

    /***
     *
     *
     *
     * @param checked
     * @param preferences
     */
    public void preferencesEnabling(boolean checked, Preference... preferences) {
        for (Preference p : preferences) {
            if (checked) {
                p.setEnabled(true);
            } else {
                p.setEnabled(false);
            }
        }
    }

    private void bindPreferenceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(this);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());

        Object preferenceValue = null;
      /*  if (preference instanceof EditTextPreference) {
            preferenceValue = sharedPreferences.getString(preference.getKey(), "");
        }*/

        if (preference instanceof CheckBoxPreference) {
            preferenceValue = sharedPreferences.getBoolean(preference.getKey(), true);
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


    @Override
    public void onStart() {
        Log.i("aaab", "onStart");

        super.onStart();
    }

    @Override
    public void onResume() {
        Log.i("aaab", "onResume");

        super.onResume();

    }

    @Override
    public void onPause() {
        Log.i("aaab", "onPause");

        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i("aaab", "onStop");

        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.i("aaab", "onDestroyView");

        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.i("aaab", "onDestroy");

        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.i("aaab", "onDetech");
        super.onDetach();
    }

}