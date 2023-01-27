package com.example.serinad;

import android.app.Activity;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity {

    private int colorDefecto;
    private View vistaPreviaColor;
    private TextView nombreTono;
    private EditText etTextoAlerta;
    private Button btnSeleccionColor;
    private Button btnSeleccionAudio;
    private Button btnSeleccionPicto;
    private Spinner spinner;
    private ImageView vistaPreviaPictoGrama;
    private ActivityResultLauncher<Intent> lanzarSelectorFotos;
    private ActivityResultLauncher<Intent> lanzarSelectorTonos;
    private Button btnCancelar;
    private Button btnGuardar;

    //Datos para guardar
    int botonSelecciondo;
    String textoAlerta;
    Uri uriTonoSeleccionado;
    int colorSeleccionado;
    Uri uriImagenSeleccionada;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Crear el spinner para seleccionar uno de los cuatro botones
        spinner = findViewById(R.id.spinnerBotones);
        Integer[] botones = {1, 2, 3, 4};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, botones);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //TODO asignar botón seleccionado
                botonSelecciondo = botones[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Crear la seleccion del color de fondo
        btnSeleccionColor = findViewById(R.id.btnSeleccionColor);
        vistaPreviaColor = findViewById(R.id.vistaPreviaColor);

        colorDefecto = 0;//TODO asignar color desde la base de datos si el botón ya está creado

        btnSeleccionColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Añadimos la funcionalidad de seleccionar el color en otro método para mayor claridad
                abrirSelectorColor();
            }
        });

        //Crear la selección del audio de alerta
        nombreTono = findViewById(R.id.tvNombreTono);
        nombreTono.setText("Tono por defecto");
        lanzarSelectorTonos = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            uriTonoSeleccionado = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI, Uri.class);
                            String stringTono = uriTonoSeleccionado.toString().split("=")[1];
                            nombreTono.setText(stringTono);

                        }
                    }
                }
        );
        btnSeleccionAudio = findViewById(R.id.btnSeleccionAudio);
        btnSeleccionAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirSelectorArchivoAudio();
            }
        });

        //Crear la selección del pictograma
        vistaPreviaPictoGrama = findViewById(R.id.vistaPreviaPicto);
        btnSeleccionPicto = findViewById(R.id.btnSeleccionPicto);
        lanzarSelectorFotos = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        //Realizamos la operación de cargar la imagen
                        uriImagenSeleccionada = data.getData();
                        vistaPreviaPictoGrama.setImageURI(uriImagenSeleccionada);

                    }
                }
        );
        btnSeleccionPicto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirSelectorImagen();

            }
        });

        //Crear funcionalidad para botón guardar
        btnGuardar = findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Guardar en la base de datos los datos del formulario
            }
        });

    }

    //Creamos el lanzamiento de la actividad

    private void abrirSelectorImagen() {

        //Creamos un intent de tipo imagen
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        lanzarSelectorFotos.launch(i);
    }

    private void abrirSelectorArchivoAudio() {
        final Uri tonoActual = RingtoneManager.getActualDefaultRingtoneUri(MainActivity.this, RingtoneManager.TYPE_ALARM);
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_RINGTONE);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Seleccione un tono de alerta");
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, tonoActual);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);
        lanzarSelectorTonos.launch(intent);
    }

    private void abrirSelectorColor() {
        final AmbilWarnaDialog colorPickerDialogue = new AmbilWarnaDialog(this, colorDefecto,
                new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {
                        // leave this function body as
                        // blank, as the dialog
                        // automatically closes when
                        // clicked on cancel button
                    }

                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        // change the mDefaultColor to
                        // change the GFG text color as
                        // it is returned when the OK
                        // button is clicked from the
                        // color picker dialog
                        colorDefecto = color;

                        // now change the picked color
                        // preview box to mDefaultColor
                        vistaPreviaColor.setBackgroundColor(colorDefecto);

                        //TODO Asignar el color de fondo para la alerta
                    }
                });
        colorPickerDialogue.show();

    }
}