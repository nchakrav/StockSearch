package com.nikhilchakravartula.stocksearch.models;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SentimentModel {

    @SerializedName("atTime")
    String atTime;
    @SerializedName("mention")
    Integer mention;
    @SerializedName("positiveScore")
    Double positiveScore;
    @SerializedName("negativeScore")
    Double negativeScore;
    @SerializedName("positiveMention")
    Integer positiveMention;
    @SerializedName("negativeMention")
    Integer negativeMention;
    @SerializedName("score")
    Double score;

    public Integer getMention() {
        return mention;
    }

    public void setMention(Integer mention) {
        this.mention = mention;
    }

    public Double getPositiveScore() {
        return positiveScore;
    }

    public void setPositiveScore(Double positiveScore) {
        this.positiveScore = positiveScore;
    }

    public Double getNegativeScore() {
        return negativeScore;
    }

    public void setNegativeScore(Double negativeScore) {
        this.negativeScore = negativeScore;
    }

    public Integer getPositiveMention() {
        return positiveMention;
    }

    public void setPositiveMention(Integer positiveMention) {
        this.positiveMention = positiveMention;
    }

    public Integer getNegativeMention() {
        return negativeMention;
    }

    public void setNegativeMention(Integer negativeMention) {
        this.negativeMention = negativeMention;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getAtTime() {
        return new SimpleDateFormat("MM-dd-yyyy").format(atTime);
    }
}
