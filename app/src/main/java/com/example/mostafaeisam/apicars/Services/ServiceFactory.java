package com.example.mostafaeisam.apicars.Services;

import android.content.Context;

import com.example.mostafaeisam.apicars.responses.GetCarsListResponse;
import com.google.gson.Gson;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import java.io.IOException;
import java.util.logging.Handler;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class ServiceFactory {


    public static void getData(final Context context, String url, final RequestListener listener) {
/*
        Ion.with(context)
                .load(url)
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, com.koushikdutta.ion.Response<String> result) {
                        if (result==null || result.getHeaders().code()!=200){
                            if (result==null)
                                listener.onFailure(1);
                            else
                                listener.onFailure(result.getHeaders().code());
                        }else{
                            listener.onSuccess(result.getResult());
                        }
                    }
                });
*/
        final OkHttpClient okHttpClient = new OkHttpClient();
        String a = url;
        final Request request = new Request.Builder()
                .url(a)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onFailure(1000);
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                if (response == null || response.code() != 200) {
                    if (response == null)
                        listener.onFailure(1);
                    else
                        listener.onFailure(response.code());
                } else {

                    String body = response.body().string();
                    call.cancel();
                    listener.onSuccess(body);

                }
                // response.code();
            }
        });
    }


}
