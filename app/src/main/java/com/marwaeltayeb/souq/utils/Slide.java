package com.marwaeltayeb.souq.utils;

import com.marwaeltayeb.souq.R;

import java.util.ArrayList;
import java.util.List;

public class Slide {

    private Slide(){}

    private static final List<Integer> slides = new ArrayList<>();

    static {
        slides.add(R.drawable.s1);
        slides.add(R.drawable.s2);
        slides.add(R.drawable.s3);
        slides.add(R.drawable.s4);
        slides.add(R.drawable.s5);
        slides.add(R.drawable.s6);
        slides.add(R.drawable.s7);
    }

    public static List<Integer> getSlides() {
        return slides;
    }
}
