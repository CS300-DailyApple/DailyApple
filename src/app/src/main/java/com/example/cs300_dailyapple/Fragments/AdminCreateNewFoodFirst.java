package com.example.cs300_dailyapple.Fragments;

import android.content.ContentResolver;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.cs300_dailyapple.R;

import java.io.IOException;
import java.io.InputStream;

public class AdminCreateNewFoodFirst extends Fragment {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;
    private ImageView imageView;
    private Button btnCamera;
    private Button btnGallery;
    private EditText foodName;
    private EditText numberOfUnit;
    private Spinner unitSpinner;
    private Button btnNext;

    private ActivityResultLauncher<Intent> cameraLauncher;
    private ActivityResultLauncher<String> galleryLauncher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_create_new_food_first, container, false);
        foodName = view.findViewById(R.id.editTextDishName);
        numberOfUnit = view.findViewById(R.id.portionEditText);
        unitSpinner = view.findViewById(R.id.unitSpinner);
        btnNext = view.findViewById(R.id.continueButton);
        // Tạo một mảng chứa các đơn vị
        String[] units = {"g", "ml"};

        // Tạo Adapter cho Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set Adapter cho Spinner
        unitSpinner.setAdapter(adapter);

        // Đặt giá trị mặc định là "Gram"
        unitSpinner.setSelection(0);
        unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedUnit = (String) parentView.getItemAtPosition(position);

                int unitPosition = getUnitPosition(selectedUnit);

                // Set lại vị trí chọn của Spinner
                unitSpinner.setSelection(unitPosition);
            }

            private int getUnitPosition(String unit) {
                String[] units = {"g", "ml"};

                // Duyệt qua mảng để tìm vị trí của đơn vị
                for (int i = 0; i < units.length; i++) {
                    if (units[i].equals(unit)) {
                        return i;
                    }
                }

                // Trả về Gram nếu không tìm thấy
                return 0;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageView = view.findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.food_photo_placeholder);
        btnCamera = view.findViewById(R.id.btnCamera);
        btnGallery = view.findViewById(R.id.btnGallery);

        // Initialize ActivityResultLauncher for camera
        cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == getActivity().RESULT_OK) {
                        Bitmap imageBitmap = (Bitmap) result.getData().getExtras().get("data");
                        if (imageBitmap != null) {
                            // Resize bitmap to 100x100
                            Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, 100, 100, true);
                            imageView.setImageBitmap(resizedBitmap);
                        }
                    }
                });

        // Initialize ActivityResultLauncher for gallery
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        try {
                            InputStream inputStream = requireActivity().getContentResolver().openInputStream(uri);
                            Bitmap selectedImage = BitmapFactory.decodeStream(inputStream);
                            if (selectedImage != null) {
                                // Resize bitmap to 100x100
                                Bitmap resizedBitmap = Bitmap.createScaledBitmap(selectedImage, 100, 100, true);
                                imageView.setImageBitmap(resizedBitmap);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

        btnCamera.setOnClickListener(v -> dispatchTakePictureIntent());
        btnGallery.setOnClickListener(v -> pickImageFromGallery());
        btnNext.setOnClickListener(v -> {
            // put data to bundle
            Bundle bundle = new Bundle();
            bundle.putString("foodName", foodName.getText().toString());
            Log.d("unit", numberOfUnit.getText().toString());
            bundle.putInt("amountUnit", Integer.parseInt(numberOfUnit.getText().toString()));
            Log.d("unit", unitSpinner.getSelectedItem().toString());
            bundle.putString("unit", unitSpinner.getSelectedItem().toString());
            Navigation.findNavController(view).navigate(R.id.action_adminCreateNewFoodFirst_to_adminCreateNewFoodSecond, bundle);}
        );

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            cameraLauncher.launch(takePictureIntent);
        }
    }

    private void pickImageFromGallery() {
        galleryLauncher.launch("image/*");
    }
}
