package br.com.wedosoft.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by rodolfodellagiustina on 05/05/2018.
 */
public class ImagemModel implements Serializable {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("descricao")
    @Expose
    private String descricao;

    @SerializedName("imagem")
    @Expose
    private String imagem;

    @SerializedName("tipo")
    @Expose
    private String tipo;

    @SerializedName("dsUsuarioUltimaAlteracao")
    @Expose
    private String dsUsuarioUltimaAlteracao;

    @SerializedName("dtUltimaAlteracao")
    @Expose
    private Long dtUltimaAlteracao;

    public ImagemModel(Long id, String descricao, String imagem, String tipo) {
        this.id = id;
        this.descricao = descricao;
        this.imagem = imagem;
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDsUsuarioUltimaAlteracao() {
        return dsUsuarioUltimaAlteracao;
    }

    public void setDsUsuarioUltimaAlteracao(String dsUsuarioUltimaAlteracao) {
        this.dsUsuarioUltimaAlteracao = dsUsuarioUltimaAlteracao;
    }

    public Long getDtUltimaAlteracao() {
        return dtUltimaAlteracao;
    }

    public void setDtUltimaAlteracao(Long dtUltimaAlteracao) {
        this.dtUltimaAlteracao = dtUltimaAlteracao;
    }
}
