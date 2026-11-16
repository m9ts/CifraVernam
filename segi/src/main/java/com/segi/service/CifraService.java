package com.segi.service;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class CifraService {

    public String cifrar(String textoClaro, String chave) {

        // converte a String em bytes usando UTF-8
        byte[] textoBytes = textoClaro.getBytes(StandardCharsets.UTF_8);
        byte[] chaveBytes = chave.getBytes(StandardCharsets.UTF_8);

        // compara em bytes
        // caracteres com acento ocupam mais de 1 byte em UTF-8
        if (textoBytes.length != chaveBytes.length) {
            throw new IllegalArgumentException("O texto claro e a chave devem ter o mesmo tamanho em bytes! (texto: "
                    + textoBytes.length + ", chave: " + chaveBytes.length + ")");
        }

        // acumula os resultados de cada iteração do for, até formar o texto cifrado completo
        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < textoBytes.length; i++) {
            byte cifrado = (byte) (textoBytes[i] ^ chaveBytes[i]); // XOR

            // binario armazena o resultado individual de cada XOR
            // e converte o resultado do XOR para binário de 8 bits
            String binario = String.format("%8s", Integer.toBinaryString(cifrado & 0xFF))
                    .replace(' ', '0');

            resultado.append(binario);
        }

        return resultado.toString();
    }
    public String decifrar(String textoCifrado, String chave) {

        byte[] chaveBytes = chave.getBytes(StandardCharsets.UTF_8);

        // verifica se o texto cifrado é do mesmo tamanho que a chave
        if (textoCifrado.length() != chaveBytes.length * 8) {
            throw new IllegalArgumentException("O texto cifrado deve ter 8 bits para cada byte da chave!");
        }
        if (!textoCifrado.matches("[01]+")) {
            throw new IllegalArgumentException("A string cifrada só pode conter 0 e 1!");
        }

        // array que guarda o resultado do XOR de cada byte do loop
        byte[] resultadoBytes = new byte[chaveBytes.length];

        for (int i = 0; i < chaveBytes.length; i++) {
            String grupo = textoCifrado.substring(i * 8, (i*8) + 8);
            int binParaDecimal = Integer.parseInt(grupo, 2);
            resultadoBytes[i] = (byte) (binParaDecimal ^ chaveBytes[i]);
        }
        return new String(resultadoBytes, StandardCharsets.UTF_8);
    }

}
