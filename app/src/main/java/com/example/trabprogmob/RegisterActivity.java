package com.example.trabprogmob;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;

public class RegisterActivity extends AppCompatActivity {

    private UserController userController;
    private EditText emailField;
    private EditText passwordField;
    private Button registerButton;
    private Button selectPhotoButton;
    private ProgressDialog progressDialog;
    private Uri selectedImageUri;

    private ActivityResultLauncher<Intent> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userController = new UserController();
        emailField = findViewById(R.id.email_field);
        passwordField = findViewById(R.id.password_field);
        registerButton = findViewById(R.id.register_button);
        selectPhotoButton = findViewById(R.id.select_photo_button);
        progressDialog = new ProgressDialog(this);

        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        selectedImageUri = result.getData().getData();
                        Toast.makeText(this, "Imagem selecionada com sucesso", Toast.LENGTH_SHORT).show();
                    }
                });

        selectPhotoButton.setOnClickListener(v -> openGallery());
        registerButton.setOnClickListener(v -> registerUser());
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagePickerLauncher.launch(intent);
    }

    private void registerUser() {
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isEmailValid(email)) {
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering, please wait...");
        progressDialog.show();

        String userId = UUID.randomUUID().toString();

        if (selectedImageUri != null) {
            // Upload da imagem se houver
            StorageReference storageRef = FirebaseStorage.getInstance().getReference("profile_images/" + userId + ".jpg");
            storageRef.putFile(selectedImageUri).addOnSuccessListener(taskSnapshot ->
                    storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        String imageUrl = uri.toString();
                        continueRegistration(userId, email, password, imageUrl);
                    }).addOnFailureListener(e -> {
                        progressDialog.dismiss();
                        Toast.makeText(this, "Erro ao obter URL da imagem", Toast.LENGTH_SHORT).show();
                    })
            ).addOnFailureListener(e -> {
                progressDialog.dismiss();
                Toast.makeText(this, "Falha no upload da imagem", Toast.LENGTH_SHORT).show();
            });
        } else {
            // Sem imagem
            continueRegistration(userId, email, password, null);
        }
    }

    private void continueRegistration(String userId, String email, String password, @Nullable String imageUrl) {
        userController.register(userId, email, password, imageUrl, task -> {
            progressDialog.dismiss();
            if (task.isSuccessful()) {
                Toast.makeText(this, "Registro bem-sucedido", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            } else {
                String error = (task.getException() != null) ? task.getException().getMessage() : "Erro desconhecido";
                Toast.makeText(this, "Falha no registro: " + error, Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean isEmailValid(String email) {
        String emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailRegex);
    }
}
