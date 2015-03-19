package com.ridhofikri.resistancemobile;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ridho on 03-Mar-15.
 */
public class Player implements Parcelable {
    private String playerName;
    private String playerRole;

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Player> CREATOR = new Parcelable.Creator<Player>() {
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    public Player(Parcel in) {
        playerName = in.readString();
        playerRole = in.readString();
    }

    public Player() {
    }

    public void setPlayer(String playerName, String playerRole) {
        this.playerName = playerName;
        this.playerRole = playerRole;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getPlayerRole() {
        return playerRole;
    }

    public boolean isEmpty(){
        if(playerName.isEmpty() || playerRole.isEmpty()) return true;
        else return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(playerName);
        dest.writeString(playerRole);
    }
}
