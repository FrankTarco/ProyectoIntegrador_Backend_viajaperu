package com.viajaperu.service;

import java.util.List;

import com.viajaperu.models.Opcion;
import com.viajaperu.models.Rol;
import com.viajaperu.models.Usuario;

public interface UsuarioService {

	  public abstract List<Opcion> traerEnlacesDeUsuario(int idUsuario);

	  public abstract List<Rol> traerRolesDeUsuario(int idUsuario);

	  public abstract Usuario buscaPorLogin(String login);
	}
