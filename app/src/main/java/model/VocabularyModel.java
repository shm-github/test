package model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Hossein on 11/8/2016.
 */

public class VocabularyModel implements Parcelable {
    private static String moviePath ;
    private static long startSubtitlePosition ;
    private static long endSubtitlePosition ;
    private String vocabulary ;
    private String vocMeaning ;
    private boolean[] isAddedToLeitner ;

    public VocabularyModel(){

        isAddedToLeitner = new boolean[1];
        isAddedToLeitner[0] = false ;

    };

    protected VocabularyModel(Parcel in) {
        vocabulary = in.readString();
        vocMeaning = in.readString();
        isAddedToLeitner = in.createBooleanArray();

    }


    public boolean isAddedToLeitner() {
        return isAddedToLeitner[0];
    }

    public void setAddedToLeitner(boolean addedToLeitner) {
        isAddedToLeitner[0] = addedToLeitner;
    }

    public String getVocMeaning() {
        return vocMeaning;
    }

    public void setVocMeaning(String vocMeaning) {
        this.vocMeaning = vocMeaning;
    }

    public String getVocabulary() {
        return vocabulary;
    }

    public void setVocabulary(String vocabulary) {
        this.vocabulary = vocabulary;
    }

    public static long getEndSubtitlePosition() {
        return endSubtitlePosition;
    }

    public static void setEndSubtitlePosition(long endSubtitlePosition) {
        VocabularyModel.endSubtitlePosition = endSubtitlePosition;
    }

    public static long getStartSubtitlePosition() {
        return startSubtitlePosition;
    }

    public static void setStartSubtitlePosition(long startSubtitlePosition) {
        VocabularyModel.startSubtitlePosition = startSubtitlePosition;
    }

    public static String getMoviePath() {
        return moviePath;
    }

    public static void setMoviePath(String moviePath) {
        VocabularyModel.moviePath = moviePath;
    }


    public static final Creator<VocabularyModel> CREATOR = new Creator<VocabularyModel>() {
        @Override
        public VocabularyModel createFromParcel(Parcel in) {
            return new VocabularyModel(in);
        }

        @Override
        public VocabularyModel[] newArray(int size) {
            return new VocabularyModel[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(vocabulary);
        dest.writeString(vocMeaning);
        dest.writeBooleanArray(isAddedToLeitner);
    }

    @Override
    public int describeContents() {
        return 0;
    }


}
