package com.example.kjuarbarap.ui.qrcode;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;


public class QrcodeFragment extends Fragment {

    Bitmap bitmap;
    TextView etText;
    ImageView imageCode;
    private FragmentQrcodeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentQrcodeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button btnGenerate = binding.idBtnGenerateQR;
        etText = binding.idEdt;
        imageCode = binding.idIVQrcode;
        binding.idSaveQrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveBitmap();
            }
        });

        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createQR();
            }
        });
        return root;

    }

    private void createQR() {
        String myText = etText.getText().toString().trim();
        //initializing MultiFormatWriter for QR code
        MultiFormatWriter mWriter = new MultiFormatWriter();
        try {
            //BitMatrix class to encode entered text and set Width & Height
            BitMatrix mMatrix = mWriter.encode(myText, BarcodeFormat.QR_CODE, 400,400);
            BarcodeEncoder mEncoder = new BarcodeEncoder();
            bitmap = mEncoder.createBitmap(mMatrix);//creating bitmap of code
            imageCode.setImageBitmap(bitmap);//Setting generated QR code to imageView
            // to hide the keyboard
            InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(etText.getApplicationWindowToken(), 0);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private void saveBitmap() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String path = preferences.getString("path", null);
        Log.d("XXX", "SAVE PATH: " + path);
        if(path == null){
            path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
        }
        else {
            path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath()+ "/"+ path;
        }
        File directory = new File(path);
        if(!directory.exists()) directory.mkdirs();
        File file = new File(path, Calendar.getInstance().getTimeInMillis() + ".png");
        try (FileOutputStream out = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (IOException e) {
            e.printStackTrace();
        }

        File sus = new File(path);
        if(sus.isDirectory()){
            for(File f : sus.listFiles()){
                Log.d("XXX", "saveBitmap: " + f.getName());
            }
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}