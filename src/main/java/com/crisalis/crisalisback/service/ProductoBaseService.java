package com.crisalis.crisalisback.service;

import com.crisalis.crisalisback.model.ProductoBase;
import com.crisalis.crisalisback.repository.IProductoBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ProductoBaseService {
    IProductoBaseRepository iProductoBaseRepository;

    @Autowired

    public ProductoBaseService(IProductoBaseRepository iProductoBaseRepository) {
        this.iProductoBaseRepository = iProductoBaseRepository;
    }

    public ProductoBase encontrarProductoBase(String nombre){
        return iProductoBaseRepository.findByNombre(nombre).orElseThrow();
    }


}
