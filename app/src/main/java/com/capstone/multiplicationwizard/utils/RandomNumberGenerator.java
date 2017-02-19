package com.capstone.multiplicationwizard.utils;

import android.util.Pair;

import java.util.ArrayList;
import java.util.Random;


public class RandomNumberGenerator  {

    private int leftMultiple;
    private int rightMultiple;
    private ArrayList<Pair<Integer,Integer>> multArrayProblemList;
    private static final int PAIRCOUNT = 20;



    public RandomNumberGenerator(int leftMultiple , int rightMultiple) {
        this.multArrayProblemList = new ArrayList<>(PAIRCOUNT);
        this.leftMultiple = leftMultiple;
        this.rightMultiple = rightMultiple;

    }
    public int getRandomNumberTillValue(int value) {
        Random rand = new Random();
        return rand.nextInt(value);
    }
    public ArrayList<Pair<Integer,Integer>> getMultiplicationPairs() {
        Random rand = new Random();

        for (int i = 0; i < PAIRCOUNT; i++) {
            Integer x;
            Integer y;
            x = rand.nextInt(leftMultiple)+2;
            y = rand.nextInt(rightMultiple)+2;
            Pair<Integer,Integer> pair = new Pair<Integer,Integer>(x,y);
            multArrayProblemList.add(pair);
        }
        return multArrayProblemList;
    }
}
