package br.com.claro.batch.catalogo.dados.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BatchController {


    @RequestMapping("/batch")
    public String handle() throws Exception {
        return "Batch job no AR";
    }
}
