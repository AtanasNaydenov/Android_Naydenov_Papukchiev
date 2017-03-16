package com.naydenov.papukchiev.fhictcomapnion;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AccountFragment extends android.app.Fragment {
    private static Account account;

    public AccountFragment() {
    }

    public static AccountFragment newInstance(Account account) {

        AccountFragment.account = account;
        return new AccountFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_account, container, false);

        TextView displayName = (TextView) view.findViewById(R.id.displayName);
        displayName.setText(account.displayName);

        TextView details = (TextView) view.findViewById(R.id.details);
        details.setText(account.department + ", " + account.personalTitle);

        TextView mail = (TextView) view.findViewById(R.id.mail);
        mail.setText(account.mail);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
