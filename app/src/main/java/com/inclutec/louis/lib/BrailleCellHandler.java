package com.inclutec.louis.lib;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.inclutec.louis.R;

/**
 * Created by martin on 9/7/15.
 */
public class BrailleCellHandler {

    Context context;
    int drawableId = 0;
    public BrailleCellHandler(Context c){

    }

    public Drawable getCellPicture(char character){
        Drawable brailleCellPicture = null;
        switch(character){
            case 'a':
                drawableId = R.drawable.braille_char_a;
                break;
            case 'b':
                drawableId = R.drawable.braille_char_b;
                break;
            case 'c':
                drawableId = R.drawable.braille_char_c;
                break;
            case 'd':
                drawableId = R.drawable.braille_char_d;
                break;
            case 'e':
                drawableId = R.drawable.braille_char_e;
                break;
            case 'f':
                drawableId = R.drawable.braille_char_f;
                break;
            case 'g':
                drawableId = R.drawable.braille_char_g;
                break;
            case 'h':
                drawableId = R.drawable.braille_char_h;
                break;
            case 'i':
                drawableId = R.drawable.braille_char_i;
                break;
            case 'j':
                drawableId = R.drawable.braille_char_j;
                break;
            case 'k':
                drawableId = R.drawable.braille_char_k;
                break;
            case 'l':
                drawableId = R.drawable.braille_char_l;
                break;
            case 'm':
                drawableId = R.drawable.braille_char_m;
                break;
            case 'n':
                drawableId = R.drawable.braille_char_n;
                break;
            case 'ñ':
                drawableId = R.drawable.braille_char_ñ;
                break;
            case 'o':
                drawableId = R.drawable.braille_char_o;
                break;
            case 'p':
                drawableId = R.drawable.braille_char_p;
                break;
            case 'q':
                drawableId = R.drawable.braille_char_q;
                break;
            case 'r':
                drawableId = R.drawable.braille_char_r;
                break;
            case 's':
                drawableId = R.drawable.braille_char_s;
                break;
            case 't':
                drawableId = R.drawable.braille_char_t;
                break;
            case 'u':
                drawableId = R.drawable.braille_char_u;
                break;
            case 'v':
                drawableId = R.drawable.braille_char_v;
                break;
            case 'w':
                drawableId = R.drawable.braille_char_w;
                break;
            case 'x':
                drawableId = R.drawable.braille_char_x;
                break;
            case 'y':
                drawableId = R.drawable.braille_char_y;
                break;
            case 'z':
                drawableId = R.drawable.braille_char_z;
                break;
            case 'á':
                drawableId = R.drawable.braille_char_a;
                break;
            case 'é':
                drawableId = R.drawable.braille_char_a;
                break;
            case 'í':
                drawableId = R.drawable.braille_char_a;
                break;
            case 'ó':
                drawableId = R.drawable.braille_char_a;
                break;
            case 'ú':
                drawableId = R.drawable.braille_char_a;
                break;
            case '&':
                drawableId = R.drawable.braille_char_a;
                break;
            case '.':
                drawableId = R.drawable.braille_char_a;
                break;
            case '#':
                drawableId = R.drawable.braille_char_a;
                break;
            case '^':
                drawableId = R.drawable.braille_char_a;
                break;
            case ',':
                drawableId = R.drawable.braille_char_a;
                break;
            case '?':
                drawableId = R.drawable.braille_char_a;
                break;
            case ';':
                drawableId = R.drawable.braille_char_a;
                break;
            case '!':
                drawableId = R.drawable.braille_char_a;
                break;
            case '"':
                drawableId = R.drawable.braille_char_a;
                break;
            case '(':
                drawableId = R.drawable.braille_char_a;
                break;
            case ')':
                drawableId = R.drawable.braille_char_a;
                break;
            case '-':
                drawableId = R.drawable.braille_char_a;
                break;
            case '*':
                drawableId = R.drawable.braille_char_a;
                break;
            
        }

        brailleCellPicture = ContextCompat.getDrawable(context, drawableId);

        return brailleCellPicture;

    }
}
