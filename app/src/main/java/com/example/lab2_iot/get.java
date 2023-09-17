package com.example.lab2_iot;

import retrofit2.Call;
import retrofit2.http.GET;

public interface get {
    @GET("/perfil/")
    Call<perfil> getPerfil();
}
