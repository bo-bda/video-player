package com.android.bo.video.activities;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.android.bo.video.R;
import com.android.bo.video.dreamfactory.DFApiError;
import com.android.bo.video.dreamfactory.DFChannel;
import com.android.bo.video.dreamfactory.DFResponse;
import com.android.bo.video.dreamfactory.DFUser;
import com.android.bo.video.dreamfactory.RESTClient;
import com.android.bo.video.dreamfactory.RESTError;
import com.android.bo.video.models.Channels;
import com.android.bo.video.utils.Storage;
import com.android.bo.video.utils.Types;
import com.android.bo.video.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
 * Created by Bo on 06.03.2016.
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        Utils.updateAll();

        RESTClient.getInstance().login(new Callback<DFUser>() {
            @Override
            public void onResponse(Call<DFUser> call, Response<DFUser> response) {
                if (response.isSuccess()) {
                    getChannels(response);
                } else {
                    DFApiError error = RESTError.getInstance(response);
                    Toast.makeText(SplashActivity.this, error.getError().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DFUser> call, Throwable t) {
                Toast.makeText(SplashActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getChannels(Response<DFUser> response) {
        DFUser dfUser = response.body();
        Storage.getInstance().saveUserProfile(dfUser);
        RESTClient.getInstance().getChannels(dfUser.getSessionToken(), new Callback<DFResponse<DFChannel>>() {
            @Override
            public void onResponse(Call<DFResponse<DFChannel>> call, Response<DFResponse<DFChannel>> response) {
                if (response.isSuccess()) {
                    Channels<DFChannel> dfChannels = new Channels<>();
                    dfChannels.addAll(response.body().getResource());
                    startActivity(MainActivity.getLaunchMainActivity(SplashActivity.this, dfChannels));
                } else {
                    DFApiError error = RESTError.getInstance(response);
                    Toast.makeText(SplashActivity.this, error.getError().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DFResponse<DFChannel>> call, Throwable t) {
                Toast.makeText(SplashActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
