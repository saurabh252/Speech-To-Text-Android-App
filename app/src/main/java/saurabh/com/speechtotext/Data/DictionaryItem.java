package saurabh.com.speechtotext.Data;

import android.support.annotation.NonNull;

import java.util.Comparator;

/**
 * Author: Saurabh
 * Created by: ModelGenerator on 30-12-2018
 */
public class DictionaryItem {
    private String word;
    private int frequency;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

}