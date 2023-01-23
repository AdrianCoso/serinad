package com.example.serinad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity {

    private int colorDefecto;
    private View vistaPreviaColor;
    private Button btnSeleccionColor;
    private Button btnSeleccionAudio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Crear el spinner para seleccionar uno de los cuatro botones
        final Spinner spinner = findViewById(R.id.spinnerBotones);
        Integer[] botones = {1, 2, 3, 4};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, botones);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //TODO asignar botón seleccionado
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Crear la seleccion del color de fondo
        btnSeleccionColor = findViewById(R.id.btnSeleccionColor);
        vistaPreviaColor = findViewById(R.id.vistaPreviaColor);

        colorDefecto = 0;

        btnSeleccionColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Añadimos la funcionalidad de seleccionar el color en otro método para mayor claridad
                abrirSelectorColor();
            }
        });

        //Crear la selección del audio de alerta
        btnSeleccionAudio = findViewById(R.id.btnSeleccionAudio);
        btnSeleccionAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirSelectorArchivoAudio();
            }
        });

    }

    private void abrirSelectorArchivoAudio() {
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