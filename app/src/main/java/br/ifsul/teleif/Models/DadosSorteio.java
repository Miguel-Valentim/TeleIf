package br.ifsul.teleif.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.Date;

public class DadosSorteio {

    @SerializedName("tema")
    @Expose
    private String tema;

    @SerializedName("numero")
    @Expose
    private Integer numero;

    @SerializedName("data")
    @Expose
    private Date data;

    @SerializedName("numerosSorteados")
    @Expose
    private int[] numerosSorteados;

    public DadosSorteio(String tema, Integer numero, Date data, int[] numerosSorteados) {
        this.tema = tema;
        this.numero = numero;
        this.data = data;
        this.numerosSorteados = numerosSorteados;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int[] getNumerosSorteados() {
        return numerosSorteados;
    }

    public void setNumerosSorteados(int[] numerosSorteados) {
        this.numerosSorteados = numerosSorteados;
    }

    @Override
    public String toString() {
        return "DadosSorteio{" +
                "tema='" + tema + '\'' +
                ", numero=" + numero +
                ", data=" + data +
                ", numerosSorteados=" + Arrays.toString(numerosSorteados) +
                '}';
    }
}
