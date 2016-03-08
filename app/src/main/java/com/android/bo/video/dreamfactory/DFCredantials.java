package com.android.bo.video.dreamfactory;

import com.google.gson.annotations.Expose;

/*
 * Created by Bo on 06.03.2016.
 */
public class DFCredantials {

    private static DFCredantials credantials;
    @Expose
    private String email;
    @Expose
    private String password;
    @Expose
    private long duration;

    public DFCredantials(String email, String password, long duration) {
        this.email = email;
        this.password = password;
        this.duration = duration;
    }

    public static DFCredantials getInstance() {
        if (credantials == null) {
            credantials = new DFCredantials("boyko_dimka@mail.ru", "123123123Aa", 0);
        }
        return credantials;
    }
}
