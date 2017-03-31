package com.mbanna.swiftbraille;

/**
 * Author: Mohammad M. AlBanna
 * Copyright to © SwiftBraille.com
 */

import java.util.ArrayList;

//Create a fixed size array list and remove the first in. This used to check the stopped time on Braille dots
class FixedArrayList extends ArrayList {
    private int fixedSize = 10;

    //--------------------------------------------------------------------------------------------//
    FixedArrayList(int fixedSize){
        this.fixedSize = fixedSize;
    }
    //--------------------------------------------------------------------------------------------//
    @SuppressWarnings("All")
    public void addItem(Object object){
        try{
            if(size() < fixedSize){
                add(object);
            } else{
                remove(0);
                add(object);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
