package saurabh.com.speechtotext.Utils;

import java.util.Comparator;

import saurabh.com.speechtotext.Data.DictionaryItem;

public class SortByFrequency implements Comparator<DictionaryItem> {
    @Override
    public int compare(DictionaryItem o1, DictionaryItem o2) {
        return o2.getFrequency()-o1.getFrequency();
    }
}
