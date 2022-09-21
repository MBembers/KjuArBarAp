package com.example.kjuarbarap.ui.barcode;

        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.fragment.app.Fragment;
        import androidx.lifecycle.ViewModelProvider;

        import com.example.kjuarbarap.databinding.FragmentBarcodeBinding;
        import com.example.kjuarbarap.databinding.FragmentHomeBinding;

public class BarcodeFragment extends Fragment {

    private FragmentBarcodeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentBarcodeBinding.inflate(inflater, container, false);

        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}