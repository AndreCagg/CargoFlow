package it.ac.cargoflow.security;

import io.jmix.security.role.annotation.JpqlRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;
import it.ac.cargoflow.entity.Azienda;
import it.ac.cargoflow.entity.Cliente;
import it.ac.cargoflow.entity.Incarico;
import it.ac.cargoflow.entity.Sede;

@RowLevelRole(name = "ScopeAzienda", code = ScopeAzienda.CODE)
public interface ScopeAzienda {
    String CODE = "scope-azienda";

    @JpqlRowLevelPolicy(entityClass = Sede.class, where = "{E}.azienda = :current_user_azienda")
    void sede();

    @JpqlRowLevelPolicy(entityClass = Azienda.class, where = "{E} = :current_user_azienda")
    void azienda();

    @JpqlRowLevelPolicy(entityClass = Cliente.class, where = "{E}.azienda = :current_user_azienda")
    void cliente();

    @JpqlRowLevelPolicy(entityClass = Incarico.class, where = "{E}.azienda = :current_user_azienda")
    void incarico();
}