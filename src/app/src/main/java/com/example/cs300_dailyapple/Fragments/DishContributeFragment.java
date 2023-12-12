package com.example.cs300_dailyapple.Fragments;

import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.example.cs300_dailyapple.R;

public class DishContributeFragment extends Fragment {

    public DishContributeFragment() {
        // Required empty public constructor
    }

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;

    private ImageView imageView;
    private Button btnCamera;
    private Button btnGallery;
    private Spinner unitSpinner;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dish_contribute, container, false);

        unitSpinner = view.findViewById(R.id.unitSpinner);


        // Tạo một mảng chứa các đơn vị
        String[] units = {"Gram", "Mililit"};

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
                String[] units = {"Gram", "Mililit"};

                // Duyệt qua mảng để tìm vị trí của đơn vị
                for (int i = 0; i < units.length; i++) {
                    if (units[i].equals(unit)) {
                        return i;
                    }
                }

                // Trả về -1 nếu không tìm thấy
                return -1;
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
        btnCamera = view.findViewById(R.id.btnCamera);
        btnGallery = view.findViewById(R.id.btnGallery);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageFromGallery();
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void pickImageFromGallery() {
        Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhotoIntent, REQUEST_IMAGE_PICK);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                if (imageBitmap != null) {
                    // Resize bitmap to 100x100
                    Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, 100, 100, true);
                    imageView.setImageBitmap(resizedBitmap);
                }
            } else if (requestCode == REQUEST_IMAGE_PICK) {
                try {
                    InputStream inputStream = requireActivity().getContentResolver().openInputStream(data.getData());
                    Bitmap selectedImage = BitmapFactory.decodeStream(inputStream);
                    if (selectedImage != null) {
                        // Resize bitmap to 100x100
                        Bitmap resizedBitmap = Bitmap.createScaledBitmap(selectedImage, 100, 100, true);
                        imageView.setImageBitmap(resizedBitmap);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}