package br.com.wedosoft.control;

/**
 * Created by rodolfodellagiustina on 05/05/2018.
 */
public class PessoaUtil {

    private PessoaUtil() {}

    public static PessoaApi getAPIService() {
        return RetrofitBuilder.getBuilder().create(PessoaApi.class);
    }
}
