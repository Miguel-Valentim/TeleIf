package br.ifsul.teleif.API;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RestService {

    @GET("sorteio/{dados}")
    Call<ResponseBody> ProcurarDados(@Path("dados") String dados);
}
