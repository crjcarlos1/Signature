package droid.demos.com.signaturepad.views;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import droid.demos.com.signaturepad.R;
import droid.demos.com.signaturepad.fragments.SignatureFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentManager manager;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showSignatureFragment();
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count > 1) {
            super.onBackPressed();
        } else {
            finish();
        }
    }

    private void showSignatureFragment() {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();

        SignatureFragment signatureFragment = new SignatureFragment();

        transaction.addToBackStack(SignatureFragment.TAG);
        transaction.add(R.id.conteinerFragments, signatureFragment, SignatureFragment.TAG).commit();
    }

}
