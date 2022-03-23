package com.nikhilchakravartula.stocksearch.services;

public class SentimentSummary {


    int positiveMentions;
    int negativeMentions;
    int mentions;
    public SentimentSummary()
    {
        positiveMentions = negativeMentions = mentions = 0;
    }

    public int getPositiveMentions() {
        return positiveMentions;
    }

    public void setPositiveMentions(int positiveMentions) {
        this.positiveMentions = positiveMentions;
    }

    public int getNegativeMentions() {
        return negativeMentions;
    }

    public void setNegativeMentions(int negativeMentions) {
        this.negativeMentions = negativeMentions;
    }

    public int getMentions() {
        return mentions;
    }

    public void setMentions(int mentions) {
        this.mentions = mentions;
    }


}
