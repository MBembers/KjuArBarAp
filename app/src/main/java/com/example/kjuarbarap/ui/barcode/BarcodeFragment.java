package com.example.kjuarbarap.ui.barcode;

        import android.content.SharedPreferences;
        import android.graphics.Bitmap;
        import android.graphics.Color;
        import android.os.Bundle;
        import android.os.Environment;
        import android.preference.Preference;
        import android.preference.PreferenceManager;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.annotation.NonNull;
        import androidx.fragment.app.Fragment;
        import androidx.lifecycle.ViewModelProvider;

        import com.example.kjuarbarap.databinding.FragmentBarcodeBinding;
        import com.example.kjuarbarap.databinding.FragmentHomeBinding;
        import com.google.zxing.BarcodeFormat;
        import com.google.zxing.EncodeHintType;
        import com.google.zxing.Writer;
        import com.google.zxing.common.BitMatrix;
        import com.google.zxing.oned.Code128Writer;
        import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

        import java.io.File;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.util.Calendar;
        import java.util.Date;
        import java.util.Hashtable;
        import java.util.Objects;

public class BarcodeFragment extends Fragment {

    private FragmentBarcodeBinding binding;
    private EditText editTextProductId;
    private Button buttonGenerate, buttonSave;
    private ImageView imageViewResult;
    private Bitmap bitmap;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentBarcodeBinding.inflate(inflater, container, false);

        View root = binding.getRoot();
        initView();
        return root;
    }

    private void initView() {
        editTextProductId = binding.idEdt;
        imageViewResult = binding.idIVBarCode;
        buttonGenerate = binding.idBtnGenerateBar;
        buttonGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonGenerate_onClick(view);
            }
        });
        buttonSave = binding.idSaveBarcode;
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveBitmap();
            }
        });

    }

    private void saveBitmap() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String path = preferences.getString("savePath", null);
        if(path == null){
            path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
        }
        File file = new File(path, Calendar.getInstance().getTime().toString().replaceAll(":", ".")+ ".png");
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

    private void buttonGenerate_onClick(View view) {
        try {
            String productId = editTextProductId.getText().toString();
            Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            Writer codeWriter;
            codeWriter = new Code128Writer();
            BitMatrix byteMatrix = codeWriter.encode(productId, BarcodeFormat.CODE_128,400, 200, hintMap);
            int width = byteMatrix.getWidth();
            int height = byteMatrix.getHeight();
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    bitmap.setPixel(i, j, byteMatrix.get(i, j) ? Color.BLACK : Color.WHITE);
                }
            }
            imageViewResult.setImageBitmap(bitmap);
        } catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
//        if (intentResult != null) {
//            String productId = intentResult.getContents();
//            Toast.makeText(getApplicationContext(), productId, Toast.LENGTH_LONG).show();
//        }
//    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}