package com.example.guia7;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.guia7.db.DB;
import com.example.guia7.model.Image;
import com.example.guia7.model.Player;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;

public class ConfigActivity extends AppCompatActivity {
    ///USER
    private EditText edtUser;
    private EditText edtIdUser;
    ///IMG
    private EditText edtIdImg;
    private Button btnSave, btnImg;

    /*public static String FILE_CONF = "configuration";
    private SharedPreferences sharedPreferences;*/

    ///Persistencia con BD
    private DB db;
    private Player player;
    private Image image;


    //IMAGEN
    final int READ_EXTERNAL_STORAGE_PERMISSION_CODE = 23;
    ImageView imgView;
    Bitmap img;

    //IMAGEN
    ActivityResultLauncher<Intent> activityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent data = result.getData();
                    if( result.getResultCode() == Activity.RESULT_OK) {
                        Uri selectedImage = data.getData();
                        Bitmap bmp = null;
                        try{
                            bmp = getBitMapFromURI(selectedImage);
                            if ( bmp != null ) {
                                img = bmp;
                                imgView.setImageBitmap(bmp);
                            }

                        } catch (IOException ioException) {
                            Toast.makeText(ConfigActivity.this, "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
                            ioException.printStackTrace();
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ///USER
        edtIdUser = findViewById(R.id.edtUserId);
        edtUser = findViewById(R.id.edtUser);
        ///IMG
        edtIdImg = findViewById(R.id.edtIdImg);

        btnSave = findViewById(R.id.btnSave);
        ///addToEditText();

        // Inicializando db
        db = new DB(ConfigActivity.this);

        btnSave.setOnClickListener( aux -> {
            ///addUser();
            guardarImagen();
        });

        //IMAGEN
        imgView = findViewById(R.id.imageView);
        img = ((BitmapDrawable) imgView.getDrawable()).getBitmap();

        //IMAGEN
        btnImg = findViewById(R.id.btnImagen);
        btnImg.setOnClickListener( aux -> {
            openFile();
        });

    }

    /*private void addToEditText(){
        sharedPreferences = getSharedPreferences(FILE_CONF, MODE_PRIVATE);
        if(sharedPreferences != null){
            edtUser.setText(sharedPreferences.getString("USER", ""));
        }
    }*/

    /*private void addUser() {
        if(!edtUser.getText().toString().isEmpty()) {
            int numero = (int) (Math.random() * 10) + 1;
            sharedPreferences = getSharedPreferences(FILE_CONF, MODE_PRIVATE);
            SharedPreferences.Editor editorConfig = sharedPreferences.edit();
            editorConfig.putString("USER", edtUser.getText().toString());
            editorConfig.putString("NUMBER", String.valueOf(numero));
            editorConfig.putString("SCORE", "0");
            editorConfig.commit();
            Toast.makeText(ConfigActivity.this, "El usuario se guardó con éxito", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Toast.makeText(ConfigActivity.this, "Por favor, llene todos los campos", Toast.LENGTH_SHORT).show();
        }
    }*/

    //IMAGEN
    private Bitmap getBitMapFromURI(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(uri,"r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    //IMAGEN
    private void openFile() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_PERMISSION_CODE);
            }
        }
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activityResult.launch(intent);
    }
    ///Guardar Imagen
    private void guardarImagen(){
        Image img = new Image(edtIdImg.getText().toString(), "m");
        if(db.guardar_O_ActualizarImagen(img)){
            limpiarJugador();
            Toast.makeText(ConfigActivity.this, "Guardado", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(ConfigActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
        }
    }
    ///Guardar Jugador
    private void guardarJugador(){
        Player player = new Player(edtUser.getText().toString(), edtIdUser.getText().toString(), null, "0");
        if(db.guardar_O_ActualizarJUgador(player)){
            ///Toast.makeText(ConfigActivity, "", Toast.LENGTH_SHORT).show();
            limpiarJugador();
        }else{
            Toast.makeText(ConfigActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
        }
    }
    public void limpiarJugador(){
        edtUser.setText("");
        edtUser.setText("");
    }
    public void LimpiarImagen(){
        edtIdImg.setText("");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}