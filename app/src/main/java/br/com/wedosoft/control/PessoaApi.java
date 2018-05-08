package br.com.wedosoft.control;

import br.com.wedosoft.model.ContentModel;
import br.com.wedosoft.model.ImagemModel;
import br.com.wedosoft.model.PessoaModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by rodolfodellagiustina on 05/05/2018.
 */
public interface PessoaApi {


    @GET("pessoas")
    Call<ContentModel> select();


    @Headers({
            "Content-Type: application/json",
            "usuario: rodolfo"
    })
    @POST("pessoas")
    Call<PessoaModel> insert(@Body PessoaModel pessoa);


    @Headers({
            "Content-Type: application/json",
            "usuario: rodolfo"
    })
    @PUT("pessoas/{id}")
    Call<PessoaModel> update(@Path ("id") long id, @Body PessoaModel pessoa);


    @Headers({
            "Content-Type: application/json",
            "usuario: rodolfo"
    })
    @HTTP(method = "DELETE", path = "pessoas/{id}", hasBody = true)
    Call<PessoaModel> delete(@Path("id") long id, @Body PessoaModel pessoa);

}
