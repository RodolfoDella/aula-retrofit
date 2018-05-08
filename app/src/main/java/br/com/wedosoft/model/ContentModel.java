package br.com.wedosoft.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by rodolfodellagiustina on 05/05/2018.
 */
public class ContentModel {
    @SerializedName("content")
    @Expose
    private List<PessoaModel> pessoas;

    public ContentModel(List<PessoaModel> pessoas) {
        this.pessoas = pessoas;
    }

    public List<PessoaModel> getPessoas() {
        return pessoas;
    }

    public void setPessoas(List<PessoaModel> pessoas) {
        this.pessoas = pessoas;
    }
}
