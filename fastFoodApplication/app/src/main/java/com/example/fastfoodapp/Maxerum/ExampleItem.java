package com.example.fastfoodapp.Maxerum;

public class ExampleItem {
    private int nImageResource;
    private String mText1;
    private String mText2;

    public ExampleItem(int imageResource, String text1, String text2){
        nImageResource = imageResource;
        mText1 = text1;
        mText2 = text2;
    }

    public int getImageResource(){
        return nImageResource;
    }

    public String getText1(){
        return mText1;
    }

    public String getText2(){
        return mText2;
    }

}
