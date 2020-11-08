package com.mta.shop.repository;

import com.mta.shop.entities.ThuongHieuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThuongHieuRepository extends JpaRepository<ThuongHieuEntity, Integer> {
}
