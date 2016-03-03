package com.zeleznick.represent;

/**
 * Created by zeleznick on 3/2/16.
 */
public class Representative {
    public int icon;
    public String firstName;
    public String lastName;
    public String party;
    public String email;
    public String twitterHandle;
    public String website;
    // MAIN ID should be unique, perhaps use twitterHandle

    public Representative(){
        super();
    }
    public Representative(int icon, String firstName, String lastName, String party,
                          String email, String twitterHandle, String website) {
        super();
        this.icon = icon;
        this.firstName = firstName;
        this.lastName = lastName;
        this.party = party;
        this.email = email;
        this.twitterHandle = twitterHandle;
        this.website = website;
    }
}