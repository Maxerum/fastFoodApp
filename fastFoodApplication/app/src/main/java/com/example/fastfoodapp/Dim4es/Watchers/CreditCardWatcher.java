package com.example.fastfoodapp.Dim4es.Watchers;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

public class CreditCardWatcher implements TextWatcher {

    private EditText editText;


    public CreditCardWatcher(EditText editText){
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        int count = 0;

        String str = s.toString().replace(" ","");
        StringBuilder stringBuilder = new StringBuilder(str);
        Log.d("Log", str);
        if(s.length() != 0) {
            if (s.toString().charAt(s.toString().length() - 1) == ' ') {
                count = s.length() / 5 - 1;
            } else {
                count = s.length() / 5;
            }
        }
        Log.d("Log", String.valueOf(count));
        for(int i = 0; i < count; ++i){
            stringBuilder.insert((i*5) + 4," ");
        }
        Log.d("Log", stringBuilder.toString());

        //to avoid recursion remove editText listener
        this.editText.removeTextChangedListener(this);
        this.editText.setText(stringBuilder.toString());
        this.editText.addTextChangedListener(this);
        this.editText.setSelection(stringBuilder.toString().length());
    }
}
