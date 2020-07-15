package com.fjp.entity;

public class Question {
    private String description;
    private String ans;

    public Question(String description, String ans) {
        this.description = description;
        this.ans = ans;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    @Override
    public String toString() {
        return "Question{" +
                "description='" + description + '\'' +
                ", ans='" + ans + '\'' +
                '}';
    }
}
