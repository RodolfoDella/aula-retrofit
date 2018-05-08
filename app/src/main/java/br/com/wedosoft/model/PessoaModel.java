package br.com.wedosoft.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by rodolfodellagiustina on 22/04/2018.
 */

public class PessoaModel implements Serializable {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("nome")
    @Expose
    private String nome;

    @SerializedName("nomeFantasia")
    @Expose
    private String nomeFantasia;

    @SerializedName("rg")
    @Expose
    private String rg;

    @SerializedName("cpfCnpj")
    @Expose
    private String cpfCnpj;

    @SerializedName("dtNascimento")
    @Expose
    private Long dtNascimento;

    @SerializedName("sexo")
    @Expose
    private String sexo;

    @SerializedName("telefone1")
    @Expose
    private String telefone1;

    @SerializedName("telefone2")
    @Expose
    private String telefone2;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("endereco")
    @Expose
    private Long endereco;

    @SerializedName("imagem")
    @Expose
    private ImagemModel imagem;

    @SerializedName("responsavel")
    @Expose
    private String responsavel;

    @SerializedName("cpfCnpjResponsavel")
    @Expose
    private String cpfCnpjResponsavel;

    @SerializedName("dsUsuarioUltimaAlteracao")
    @Expose
    private String dsUsuarioUltimaAlteracao;

    @SerializedName("dtUltimaAlteracao")
    @Expose
    private Long dtUltimaAlteracao;

    public PessoaModel(long id, String nome, String sexo, Long dtNascimento, String cpfCnpj, String telefone1, String email) {
        this.id = id;
        this.nome = nome;
        this.sexo = sexo;
        this.dtNascimento = dtNascimento;
        this.cpfCnpj = cpfCnpj;
        this.telefone1 = telefone1;
        this.email = email;
    }

    public PessoaModel(String nome, String sexo, Long dataNascimento, String cpf, String telefone, String email) {
        this(0, nome, sexo, dataNascimento, cpf, telefone, email);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public Long getDtNascimento() {
        return dtNascimento;
    }

    public void setDtNascimento(Long dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTelefone1() {
        return telefone1;
    }

    public void setTelefone1(String telefone1) {
        this.telefone1 = telefone1;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public void setTelefone2(String telefone2) {
        this.telefone2 = telefone2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getEndereco() {
        return endereco;
    }

    public void setEndereco(Long endereco) {
        this.endereco = endereco;
    }

    public ImagemModel getImagem() {
        return imagem;
    }

    public void setImagem(ImagemModel imagem) {
        this.imagem = imagem;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getCpfCnpjResponsavel() {
        return cpfCnpjResponsavel;
    }

    public void setCpfCnpjResponsavel(String cpfCnpjResponsavel) {
        this.cpfCnpjResponsavel = cpfCnpjResponsavel;
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

    @Override
    public String toString() {
        return nome;
    }
}
