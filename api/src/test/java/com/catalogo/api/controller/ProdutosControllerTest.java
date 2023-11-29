package com.catalogo.api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class ProdutosControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Devolve 200 ok no metodo cadastrar")
    void cadastrar02() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post("/catalogo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"produto\": \"arroz\", \"secao\": \"Mercearia\", \"descricao\": \"testando cadastro do produto arroz\", \"preco\": 4.10}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Devolver erro 400 quando campos obrigatorios não são preenchidos")
    void cadastrar01() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/catalogo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"secao\": \"Mercearia\", \"descricao\": \"testando cadastro sem o campos obrigatorios\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Atualizando produto com sucesso")
    void atualizaProduto() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .put("/catalogo")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": \"7\", \"produto\": \"Sorvete \", \"descricao\": \"testando atualização do sorvete flocos\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Lista produtos")
    void listaProdutos() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/catalogo")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Deleta produto")
    void deletaProduto() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .delete("/catalogo/{id}",13)).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("Testando pesquisa de produto através do id do produto")
    void pesquisaProduto01() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/catalogo","/buscar?id=7"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    @DisplayName("Testando pesquisa de produto através do nome do produto")
    void pesquisaProduto02() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/catalogo","/buscar?produto=sorvete"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }@Test
    @DisplayName("Testando pesquisa de produto através da seção do produto")
    void pesquisaProduto03() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/catalogo","/buscar?secao=Congelados"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}