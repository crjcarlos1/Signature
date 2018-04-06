package droid.demos.com.signaturepad.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import droid.demos.com.signaturepad.R;

public class ResultFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = ResultFragment.class.getSimpleName();
    private ImageView imgvResult;
    private Button btnBack;
    private Bitmap resultBitmap = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            byte[] byteArray = bundle.getByteArray("IMAGE");// getArgument().getByteArrayExtra("IMAGE");
            resultBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        } else {
            Toast.makeText(getContext(), "Error al recibir firma", Toast.LENGTH_LONG).show();
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.result_fragment, container, false);

        imgvResult = (ImageView) view.findViewById(R.id.imgvResult);
        btnBack = (Button) view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        putSignatureIntoImageResult();

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
    }

    private void putSignatureIntoImageResult() {
        if (resultBitmap != null) {
            imgvResult.setImageBitmap(resultBitmap);
        } else {
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }

}
