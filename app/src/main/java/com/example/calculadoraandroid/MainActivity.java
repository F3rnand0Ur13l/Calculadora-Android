package com.example.calculadoraandroid;

import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {
    EditText pantalla;
    int tamLetra = 30;

    Button[] botones = new Button[17];

    Double num1 = null;
    String operador = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout principal = new LinearLayout(this);
        principal.setOrientation(LinearLayout.VERTICAL);
        principal.setPadding(20, 60, 20, 30);
        principal.setBackgroundColor(Color.BLACK);

        pantalla = new EditText(this);
        pantalla.setBackgroundColor(Color.WHITE);
        pantalla.setTextSize(tamLetra);
        pantalla.setSingleLine(true);
        pantalla.setShowSoftInputOnFocus(false);
        principal.addView(pantalla);

        LinearLayout l1 = crearFila(Color.BLUE);
        LinearLayout l2 = crearFila(Color.BLUE);
        LinearLayout l3 = crearFila(Color.BLUE);
        LinearLayout l4 = crearFila(Color.BLUE);
        LinearLayout l5 = crearFila(Color.DKGRAY);

        // digitos del 0 al 9
        for (int i = 0; i <= 9; i++) {
            botones[i] = new Button(this);
            botones[i].setText(String.valueOf(i));
            botones[i].setTextSize(tamLetra);
            botones[i].setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    String textoBoton = ((Button) v).getText().toString();
                    if (pantalla.getText().toString().equals("Error")) {
                        pantalla.setText("");
                    }
                    pantalla.setText(pantalla.getText() + textoBoton);
                }
            });
        }

        // decimal
        botones[10] = new Button(this);
        botones[10].setText(".");
        botones[10].setTextSize(tamLetra);
        botones[10].setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String texto = pantalla.getText().toString();
                if (texto.equals("Error")) texto = "";
                if (texto.contains(".")) return;
                if (texto.isEmpty()) {
                    pantalla.setText("0.");
                } else {
                    pantalla.setText(texto + ".");
                }
            }
        });

        // operadores
        botones[11] = crearOperador("+");
        botones[13] = crearOperador("-");
        botones[14] = crearOperador("×");
        botones[15] = crearOperador("÷");

        // igual
        botones[12] = new Button(this);
        botones[12].setText("=");
        botones[12].setTextSize(tamLetra);
        botones[12].setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String texto = pantalla.getText().toString();
                if (num1 == null || operador.isEmpty() || texto.isEmpty() || texto.equals(".")) {
                    return;
                }
                double num2 = Double.parseDouble(texto);
                double res = 0;

                switch (operador) {
                    case "+":
                        res = num1 + num2;
                        break;
                    case "-":
                        res = num1 - num2;
                        break;
                    case "×":
                        res = num1 * num2;
                        break;
                    case "÷":
                        if (num2 == 0) {
                            pantalla.setText("Error");
                            num1 = null;
                            operador = "";
                            return;
                        }
                        res = num1 / num2;
                        break;
                }

                pantalla.setText(formatear(res));
                num1 = null;
                operador = "";
            }
        });

        // bton limpiar
        botones[16] = new Button(this);
        botones[16].setText("C");
        botones[16].setTextSize(tamLetra);
        botones[16].setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                pantalla.setText("");
                num1 = null;
                operador = "";
            }
        });

        // fila 1
        agregar(l1, botones[7]);
        agregar(l1, botones[8]);
        agregar(l1, botones[9]);
        agregar(l1, botones[11]);

        // fila 2
        agregar(l2, botones[4]);
        agregar(l2, botones[5]);
        agregar(l2, botones[6]);
        agregar(l2, botones[13]);

        // fila 3
        agregar(l3, botones[1]);
        agregar(l3, botones[2]);
        agregar(l3, botones[3]);
        agregar(l3, botones[14]);

        // fila 4
        agregar(l4, botones[10]);
        agregar(l4, botones[0]);
        agregar(l4, botones[12]);
        agregar(l4, botones[15]);

        // fila btn limpiar
        agregar(l5, botones[16]);

        principal.addView(l1);
        principal.addView(l2);
        principal.addView(l3);
        principal.addView(l4);
        principal.addView(l5);
        setContentView(principal);
    }

    private LinearLayout crearFila(int color) {
        LinearLayout fila = new LinearLayout(this);
        fila.setOrientation(LinearLayout.HORIZONTAL);
        fila.setBackgroundColor(Color.BLUE);
        return fila;
    }

    private void agregar(LinearLayout fila, Button b) {
        b.setMinWidth(0);
        b.setMinimumWidth(0);
        fila.addView(b, new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
    }

    private Button crearOperador(final String simbolo) {
        Button b = new Button(this);
        b.setText(simbolo);
        b.setTextSize(tamLetra);
        b.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String texto = pantalla.getText().toString();
                if (texto.isEmpty() || texto.equals(".") || texto.equals("Error")) {
                    return;
                }
                num1 = Double.parseDouble(texto);
                operador = simbolo;
                pantalla.setText("");
            }
        });
        return b;
    }

    // muestra el numero en formato entero
    private String formatear(double valor) {
        if (valor == Math.floor(valor) && Math.abs(valor) < 1e15) {
            return String.valueOf((long) valor);
        }
        return String.valueOf(valor);
    }
}