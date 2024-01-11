package com.example.cs300_dailyapple.Fragments;

import android.content.ContentResolver;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavAction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.cs300_dailyapple.Models.Food;
import com.example.cs300_dailyapple.Models.GlobalApplication;
import com.example.cs300_dailyapple.R;

import java.io.IOException;
import java.io.InputStream;

public class AddDishFragment extends Fragment {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;

    private ImageView imageView;
    private Button btnCamera;
    private Button btnGallery;
    private EditText unitNameView;
    private AppCompatButton continueButton;
    private ActivityResultLauncher<Intent> cameraLauncher;
    private ActivityResultLauncher<String> galleryLauncher;
    private EditText foodNameView;
    private Food customDish;
    GlobalApplication globalApplication;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_dish, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageView = view.findViewById(R.id.imageView);
        btnCamera = view.findViewById(R.id.btnCamera);
        btnGallery = view.findViewById(R.id.btnGallery);
        continueButton = view.findViewById(R.id.continueButton);
        unitNameView = view.findViewById(R.id.unitName);
        foodNameView = view.findViewById(R.id.editTextDishName);
        navController = Navigation.findNavController(view);
        globalApplication = (GlobalApplication) GlobalApplication.getInstance();
        customDish = globalApplication.getCustomDish();
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDish.setName(foodNameView.getText().toString());
                customDish.setUnit(unitNameView.getText().toString());
                globalApplication.setCustomDish(customDish);
                navController.navigate(R.id.action_addDishFragment_to_addDishDetailsFragment);

            }
        });
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
