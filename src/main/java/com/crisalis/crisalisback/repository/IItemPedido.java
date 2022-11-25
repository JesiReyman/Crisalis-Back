package com.crisalis.crisalisback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crisalis.crisalisback.model.ItemPedido;

@Repository
public interface IItemPedido extends JpaRepository<ItemPedido, Long>{
    
}
