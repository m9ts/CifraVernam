package com.segi.controller;

import com.segi.dto.CifrarResponse;
import com.segi.dto.DecifrarRequest;
import com.segi.dto.DecifrarResponse;
import com.segi.service.CifraService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import com.segi.dto.CifrarRequest;
@RestController
public class CifraController {

    private final CifraService cifraService;

    public CifraController(CifraService cifraService) {
        this.cifraService = cifraService;
    }

    @PostMapping("/cifrar")
    public CifrarResponse cifrar(@Valid @RequestBody CifrarRequest request) {
        String cifrado = cifraService.cifrar(request.getTextoClaro(),request.getChave()); //variavel vai receber o cifrar passando o texto claro e a chave do request
        CifrarResponse response = new CifrarResponse(); // vai criar um objeto de resposta
        response.setTextoCifrado(cifrado); // vai guardar o texto cifrado nesse objeto
        return response; // retorna o objeto de resposta com o texto cifrado
    }
    @PostMapping("/decifrar")
    public DecifrarResponse decifrar(@Valid @RequestBody DecifrarRequest request) {
        String decifrado = cifraService.decifrar(request.getTextoCifrado(), request.getChave()); // variavel vai receber o decifrar passando o texto cifrado e a chave do request
        DecifrarResponse response = new DecifrarResponse(); // vai criar um objeto de resposta
        response.setTextoClaro(decifrado); // vai guaradar o texto claro (decifrado)
        return response; // vai retornar o objeto de resposta com o texto decifrado
    }

}
