package com.inclutec.louis.lib;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.inclutec.louis.R;

/**
 * Created by martin on 9/7/15.
 */
public class BrailleCellImageHandler {

    Context context;
    int drawableId = 0;
    public BrailleCellImageHandler(Context c){
        this.context = c;
    }

    public Drawable getCellPicture(String character){
        Drawable brailleCellPicture = null;
        switch(character){
            case "a":
                drawableId = R.drawable.braille_char_a;
                break;
            case "b":
                drawableId = R.drawable.braille_char_b;
                break;
            case "c":
                drawableId = R.drawable.braille_char_c;
                break;
            case "d":
                drawableId = R.drawable.braille_char_d;
                break;
            case "e":
                drawableId = R.drawable.braille_char_e;
                break;
            case "f":
                drawableId = R.drawable.braille_char_f;
                break;
            case "g":
                drawableId = R.drawable.braille_char_g;
                break;
            case "h":
                drawableId = R.drawable.braille_char_h;
                break;
            case "i":
                drawableId = R.drawable.braille_char_i;
                break;
            case "j":
                drawableId = R.drawable.braille_char_j;
                break;
            case "k":
                drawableId = R.drawable.braille_char_k;
                break;
            case "l":
                drawableId = R.drawable.braille_char_l;
                break;
            case "m":
                drawableId = R.drawable.braille_char_m;
                break;
            case "n":
                drawableId = R.drawable.braille_char_n;
                break;
            case "ñ":
                drawableId = R.drawable.braille_char_ntilde;
                break;
            case "o":
                drawableId = R.drawable.braille_char_o;
                break;
            case "p":
                drawableId = R.drawable.braille_char_p;
                break;
            case "q":
                drawableId = R.drawable.braille_char_q;
                break;
            case "r":
                drawableId = R.drawable.braille_char_r;
                break;
            case "s":
                drawableId = R.drawable.braille_char_s;
                break;
            case "t":
                drawableId = R.drawable.braille_char_t;
                break;
            case "u":
                drawableId = R.drawable.braille_char_u;
                break;
            case "v":
                drawableId = R.drawable.braille_char_v;
                break;
            case "w":
                drawableId = R.drawable.braille_char_w;
                break;
            case "x":
                drawableId = R.drawable.braille_char_x;
                break;
            case "y":
                drawableId = R.drawable.braille_char_y;
                break;
            case "z":
                drawableId = R.drawable.braille_char_z;
                break;
            case "á":
                drawableId = R.drawable.braille_char_aacute;
                break;
            case "é":
                drawableId = R.drawable.braille_char_eacute;
                break;
            case "í":
                drawableId = R.drawable.braille_char_iacute;
                break;
            case "ó":
                drawableId = R.drawable.braille_char_oacute;
                break;
            case "ú":
                drawableId = R.drawable.braille_char_uacute;
                break;
            case "ü":
                drawableId = R.drawable.braille_char_uuml;
                break;
            case "&":
                drawableId = R.drawable.braille_char_amp;
                break;
            case ".":
                drawableId = R.drawable.braille_char_dot;
                break;
            case "#":
                drawableId = R.drawable.braille_char_sharp;
                break;
            case ",":
                drawableId = R.drawable.braille_char_comma;
                break;
            case "?":
                drawableId = R.drawable.braille_char_question;
                break;
            case ";":
                drawableId = R.drawable.braille_char_semicolon;
                break;
            case "!":
                drawableId = R.drawable.braille_char_exclamation;
                break;
            case "(":
                drawableId = R.drawable.braille_char_open_parenthesis;
                break;
            case ")":
                drawableId = R.drawable.braille_char_close_parenthesis;
                break;
            case "-":
                drawableId = R.drawable.braille_char_hyphen;
                break;
            case "*":
                drawableId = R.drawable.braille_char_asterisk;
                break;
            case "1": //Para el aprestamiento, el punto 1
                drawableId = R.drawable.braille_char_1;
                break;
            case "2": //Para el aprestamiento, el punto 2
                drawableId = R.drawable.braille_char_2;
                break;
            case "3": //Para el aprestamiento, el punto 3
                drawableId = R.drawable.braille_char_3;
                break;
            case "4": //Para el aprestamiento, el punto 4
                drawableId = R.drawable.braille_char_4;
                break;
            case "5": //Para el aprestamiento, el punto 5
                drawableId = R.drawable.braille_char_5;
                break;
            case "6": //Para el aprestamiento, el punto 6
                drawableId = R.drawable.braille_char_6;
                break;
            case "12": //Para el aprestamiento, los puntos 1 y 2
                drawableId = R.drawable.braille_char_12;
                break;
            case "34": //Para el aprestamiento, los puntos 3 y 4
                drawableId = R.drawable.braille_char_34;
                break;
            case "56": //Para el aprestamiento, los puntos 5 y 6
                drawableId = R.drawable.braille_char_56;
                break;
            case "123": //Para el aprestamiento, los puntos 1 2 y 3
                drawableId = R.drawable.braille_char_123;
                break;
            case "456": //Para el aprestamiento, los puntos 4 5 y 6
                drawableId = R.drawable.braille_char_456;
                break;
            case "123456": //Para el aprestamiento, todos los puntos arriba
                drawableId = R.drawable.braille_char_123456;
                break;
            case "0"://Para el aprestamiento, all abajo
            case "":
                drawableId = R.drawable.braille_char_0;
                break;

        }

        brailleCellPicture = ContextCompat.getDrawable(context, drawableId);

        return brailleCellPicture;

    }
}
