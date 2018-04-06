package droid.demos.com.signaturepad.fragments;

import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import droid.demos.com.signaturepad.R;

public class SignatureFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = SignatureFragment.class.getSimpleName();
    private Button btnClearSignature, btnSaveSignature, btnSendSignature;

    private GestureOverlayView gestureView;
    private Bitmap bitmap;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signature_fragment, container, false);

        btnClearSignature = (Button) view.findViewById(R.id.btnClearSignature);
        btnSaveSignature = (Button) view.findViewById(R.id.btnSaveSignature);
        btnSendSignature = (Button) view.findViewById(R.id.btnSendSignature);
        gestureView = (GestureOverlayView) view.findViewById(R.id.signaturePad);

        btnSaveSignature.setOnClickListener(this);
        btnClearSignature.setOnClickListener(this);
        btnSendSignature.setOnClickListener(this);

        gestureView.setDrawingCacheEnabled(true);
        gestureView.setAlwaysDrawnWithCacheEnabled(true);
        gestureView.setHapticFeedbackEnabled(false);
        gestureView.cancelLongPress();
        gestureView.cancelClearAnimation();

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnClearSignature:
                clearSignatures();
                break;
            case R.id.btnSaveSignature:
                saveSignature();
                break;
            case R.id.btnSendSignature:
                sendSignatureToResultFragment();
                break;
        }
    }

    private void clearSignatures() {
        gestureView.invalidate();
        gestureView.clear(true);
        gestureView.clearAnimation();
        gestureView.cancelClearAnimation();
    }

    private void saveSignature() {

    }

    private void sendSignatureToResultFragment() {
        if (gestureView.getGesture() == null) {
            Toast.makeText(getContext(), "Ingresa tu firma", Toast.LENGTH_LONG).show();
        } else {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            ResultFragment resultFragment = new ResultFragment();

            gestureView.setBackgroundColor(Color.parseColor("#ffffff"));

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap = Bitmap.createBitmap(gestureView.getDrawingCache());
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            Bundle bundle = new Bundle();
            bundle.putByteArray("IMAGE", byteArray);
            resultFragment.setArguments(bundle);

            transaction.addToBackStack(ResultFragment.TAG);
            transaction.replace(R.id.conteinerFragments, resultFragment, ResultFragment.TAG).commit();
        }
    }

}
