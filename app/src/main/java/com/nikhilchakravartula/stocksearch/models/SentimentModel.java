package com.nikhilchakravartula.stocksearch.models;

import com.google.gson.annotations.SerializedName;

public class SentimentModel {

    @SerializedName("atTime")
    String atTime;
    @SerializedName("mention")
    String mention;
    @SerializedName("positiveScore")
    String positiveScore;
    @SerializedName("negativeScore")
    String negativeScore;
    @SerializedName("positiveMention")
    String positiveMention;
    @SerializedName("negativeMention")
    String negativeMention;
    @SerializedName("score")
    String score;


    public String getAtTime() {
        return atTime;
    }

    public void setAtTime(String atTime) {
        this.atTime = atTime;
    }

    public String getMention() {
        return mention;
    }

    public void setMention(String mention) {
        this.mention = mention;
    }

    public String getPositiveScore() {
        return positiveScore;
    }

    public void setPositiveScore(String positiveScore) {
        this.positiveScore = positiveScore;
    }

    public String getNegativeScore() {
        return negativeScore;
    }

    public void setNegativeScore(String negativeScore) {
        this.negativeScore = negativeScore;
    }

    public String getPositiveMention() {
        return positiveMention;
    }

    public void setPositiveMention(String positiveMention) {
        this.positiveMention = positiveMention;
    }

    public String getNegativeMention() {
        return negativeMention;
    }

    public void setNegativeMention(String negativeMention) {
        this.negativeMention = negativeMention;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }


}
