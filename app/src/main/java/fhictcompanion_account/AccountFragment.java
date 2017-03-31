package fhictcompanion_account;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.naydenov.papukchiev.fhictcomapnion.R;

import java.io.InputStream;
import java.net.URL;

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

        ImageView profilePic = (ImageView) view.findViewById(R.id.profilePic);
        profilePic.setImageDrawable(LoadImageFromWebOperations(account.photo));

        return view;
    }


    public Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
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
