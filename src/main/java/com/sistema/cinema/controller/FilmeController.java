/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistema.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FilmeController {
    
    /*localhost:8080/index = endereço pra acessar via navegador com o server rodando*/
   
    @GetMapping("/index")
    public String mostraHome(){
        return "home";
    }
    
}
