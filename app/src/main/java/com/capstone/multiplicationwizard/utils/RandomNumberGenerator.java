package com.capstone.multiplicationwizard.utils;

import android.util.Pair;

import java.util.ArrayList;
import java.util.Random;


public class RandomNumberGenerator  {

    private static final int PAIRCOUNT = 20;

    public int getRandomNumberTillValue(int value) {
        int retRandom = 0;
        Random rand = new Random();
        do {
            retRandom = rand.nextInt(value);
        }while(retRandom == 0);
        return retRandom;
    }
    public ArrayList<Pair<Integer,Integer>> getMultiplicationPairs(int leftMultiple, int rightMultiple) {
        ArrayList<Pair<Integer,Integer>> multArrayProblemList = new ArrayList<>(PAIRCOUNT);

        Random rand = new Random();

        for (int i = 0; i < PAIRCOUNT; i++) {
            Integer x;
            Integer y;
            x = rand.nextInt(leftMultiple)+leftMultiple;
            y = rand.nextInt(rightMultiple)+rightMultiple;
            Pair<Integer,Integer> pair = new Pair<Integer,Integer>(x,y);
            multArrayProblemList.add(pair);
        }
        return multArrayProblemList;
    }
}
