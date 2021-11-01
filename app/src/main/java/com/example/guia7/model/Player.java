package com.example.guia7.model;

public class Player {
    private String idPlayer;
    private String nickNamePlayer;
    private String idImg;
    private String scorePlayer;

    public Player(String idPlayer, String nickNamePlayer, String idImg, String scorePlayer) {
        this.idPlayer = idPlayer;
        this.nickNamePlayer = nickNamePlayer;
        this.idImg = idImg;
        this.scorePlayer = scorePlayer;
    }

    public String getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(String idPlayer) {
        this.idPlayer = idPlayer;
    }

    public String getNickNamePlayer() {
        return nickNamePlayer;
    }

    public void setNickNamePlayer(String nickNamePlayer) {
        this.nickNamePlayer = nickNamePlayer;
    }

    public String getIdImg() {
        return idImg;
    }

    public void setIdImg(String idImg) {
        this.idImg = idImg;
    }

    public String getScorePlayer() {
        return scorePlayer;
    }

    public void setScorePlayer(String scorePlayer) {
        this.scorePlayer = scorePlayer;
    }
}
