package com.sistema.cinema.controller;

import com.sistema.cinema.model.Filme;
import com.sistema.cinema.model.Analise;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller  // Indica que esta classe é um controlador do Spring
@RequestMapping("/filmes")  // Define o mapeamento base para todos os métodos deste controlador
public class FilmeController {

    // Listas para armazenar filmes e análises em memória
    private final List<Filme> filmes = new ArrayList<>();
    private final List<Analise> analises = new ArrayList<>();

    // Método para listar todos os filmes
    @GetMapping
    public String listarFilmes(Model model) {
        model.addAttribute("filmes", filmes);  // Adiciona a lista de filmes ao modelo
        return "lista";  // Retorna a view lista.html
    }

    // Método para exibir o formulário de cadastro de um novo filme
    @GetMapping("/cadastro")
    public String exibirFormularioCadastro(Model model) {
        model.addAttribute("filme", new Filme());  // Cria um novo objeto Filme para o formulário
        return "cadastro";  // Retorna a view cadastro.html
    }

    // Método para processar o cadastro de um novo filme
    @PostMapping("/cadastro")
    public String cadastrarFilme(@ModelAttribute Filme filme) {
        filme.setId((long) (filmes.size() + 1));  // Atribui um ID único ao filme
        filmes.add(filme);  // Adiciona o filme à lista
        return "redirect:/filmes";  // Redireciona para a lista de filmes após o cadastro
    }

    // Método para exibir os detalhes de um filme específico
    @GetMapping("/{id}")
    public String exibirDetalhesFilme(@PathVariable("id") Long id, Model model) {
        Filme filme = filmes.stream()  // Busca o filme na lista usando o ID
                .filter(f -> f.getId().equals(id))
                .findFirst()
                .orElse(null);  // Retorna null se o filme não for encontrado
        
        if (filme == null) {
            return "redirect:/filmes";  // Redireciona caso o filme não seja encontrado
        }
        
        model.addAttribute("filme", filme);  // Adiciona o filme ao modelo
        model.addAttribute("analises", filtraAnalisesPorFilme(id));  // Filtra as análises do filme
        model.addAttribute("novaAnalise", new Analise());  // Cria um novo objeto Analise para o formulário
        
        return "detalhes";  // Retorna a view detalhes.html
    }

    // Método privado para filtrar análises associadas a um filme específico
    private List<Analise> filtraAnalisesPorFilme(Long id) {
        return analises.stream()  // Filtra as análises na lista
                .filter(a -> a.getFilme().getId().equals(id))
                .collect(Collectors.toList());  // Retorna a lista filtrada
    }

    // Método para adicionar uma nova análise a um filme
    @PostMapping("/{id}/analise")
    public String adicionarAnalise(@PathVariable("id") Long id, @ModelAttribute Analise analise) {
        Filme filme = filmes.stream()  // Busca o filme usando o ID
                .filter(f -> f.getId().equals(id))
                .findFirst()
                .orElse(null);  // Retorna null se o filme não for encontrado
        
        if (filme != null) {
            analise.setId((long) (analises.size() + 1));  // Atribui um ID único à análise
            analise.setFilme(filme);  // Associa a análise ao filme
            analises.add(analise);  // Adiciona a análise à lista
        }
        
        return "redirect:/filmes/" + id;  // Redireciona para os detalhes do filme
    }
}
