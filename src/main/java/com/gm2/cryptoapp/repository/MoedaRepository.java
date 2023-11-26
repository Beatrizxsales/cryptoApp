package com.gm2.cryptoapp.repository;

import com.gm2.cryptoapp.dto.MoedaTransationDTO;
import com.gm2.cryptoapp.entity.Moeda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@EnableAutoConfiguration
public class MoedaRepository {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public Moeda insert (Moeda moeda){
        entityManager.persist(moeda);
        return moeda;
    }

    @Transactional
    public Moeda update(Moeda moeda){
        entityManager.merge(moeda);
        return moeda;
    }
    public List<MoedaTransationDTO> getAll(){
        String jpql = "select new com.gm2.cryptoapp.dto.MoedaTransationDTO(m.nome,sum(m.quantidade)) from Moeda m group by m.nome";
        TypedQuery<MoedaTransationDTO> query = entityManager.createQuery(jpql, MoedaTransationDTO.class);
        return query.getResultList();
    }
        public List<Moeda> getByName( String nome){
       String jpql = "select m from Moeda m where m.nome like :nome";
       TypedQuery<Moeda> query =entityManager.createQuery(jpql, Moeda.class);
       query.setParameter("nome","%"+ nome +"%");
       return query.getResultList();
    }

    @Transactional
    public boolean remove(int id){
    Moeda moeda = entityManager.find(Moeda.class, id);
    if(moeda == null)
        throw new RuntimeException();
    entityManager.remove(moeda);
    return true;

    }
}
