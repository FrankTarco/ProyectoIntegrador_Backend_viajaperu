package com.viajaperu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viajaperu.models.Opcion;
import com.viajaperu.models.Rol;
import com.viajaperu.models.Usuario;
import com.viajaperu.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService{

  @Autowired
  private UsuarioRepository repository;
  
  @Override
  public List<Opcion> traerEnlacesDeUsuario(int idUsuario) {
    return repository.traerEnlacesDeUsuario(idUsuario);
  }

  @Override
  public List<Rol> traerRolesDeUsuario(int idUsuario) {
    return repository.traerRolesDeUsuario(idUsuario);
  }

  @Override
  public Usuario buscaPorLogin(String login) {
    return repository.findByLogin(login);
  }

}
