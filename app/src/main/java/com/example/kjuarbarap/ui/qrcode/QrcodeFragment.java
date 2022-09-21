package com.example.kjuarbarap.ui.qrcode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.kjuarbarap.R;
import com.example.kjuarbarap.databinding.FragmentQrcodeBinding;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.w3c.dom.Text;


public class QrcodeFragment extends Fragment {

    Bitmap bitmap;

    private FragmentQrcodeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentQrcodeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button btnGenerate = binding.idBtnGenerateQR;
        TextView etText = binding.idEdt;
        ImageView imageCode = binding.idIVQrcode;

        btnGenerate.setOnClickListener(v -> {
            //getting text from input text field.
            String myText = etText.getText().toString().trim();
            //initializing MultiFormatWriter for QR code
            MultiFormatWriter mWriter = new MultiFormatWriter();
            try {
                //BitMatrix class to encode entered text and set Width & Height
                BitMatrix mMatrix = mWriter.encode(myText, BarcodeFormat.QR_CODE, 400,400);
                BarcodeEncoder mEncoder = new BarcodeEncoder();
                Bitmap mBitmap = mEncoder.createBitmap(mMatrix);//creating bitmap of code
                imageCode.setImageBitmap(mBitmap);//Setting generated QR code to imageView
                // to hide the keyboard
                InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(etText.getApplicationWindowToken(), 0);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        });
        return root;

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}