package com.manoelcampos.rest;

import com.manoelcampos.model.Categoria;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

//JAX-RS (RS = REST)
@Path("/categoria")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoriaResource {
    @GET
    public List<Categoria> list() {
        return Categoria.listAll();
    }

    @Transactional
    @POST
    public void insert(Categoria categoria) {
        categoria.persistAndFlush();
    }

    @Transactional
    @PUT
    public void update(Categoria categoria) {
       Categoria localizada = Categoria.findById(categoria.getId());
        if (localizada == null) {
            throw new WebApplicationException("Categoria não Localizada", Response.Status.NOT_FOUND);
        }
        localizada.setDescricao(categoria.getDescricao());
        localizada.persistAndFlush();

    }
    @Transactional
    @Path("{id}")
    @DELETE
    public void delete(@PathParam("id") long id) {
        try {
            Categoria localizada = Categoria.findById(id);
            if (localizada == null) {
                throw new WebApplicationException("Categoria não Localizada", Response.Status.NOT_FOUND);
            }

            localizada.delete();
        } catch (WebApplicationException e) {
            Logger.getLogger("categoria").log(Level.ALL, e.getMessage());
            throw new WebApplicationException(
                    "Erro inesperado ao tentar acessar o Banco de Dados",
                    Response.Status.INTERNAL_SERVER_ERROR);
            
        }

    }

}