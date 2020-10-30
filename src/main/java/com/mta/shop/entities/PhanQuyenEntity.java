package com.mta.shop.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "PHANQUYEN")
@IdClass(PhanQuyenEntityPK.class)
public class PhanQuyenEntity {
    private Integer idTaiKhoan;
    private Integer idQuyen;

    @Id
    @Column(name = "IDTAIKHOAN")
    public Integer getIdTaiKhoan() {
        return idTaiKhoan;
    }

    public void setIdTaiKhoan(Integer idTaiKhoan) {
        this.idTaiKhoan = idTaiKhoan;
    }

    @Id
    @Column(name = "IDQUYEN")
    public Integer getIdQuyen() {
        return idQuyen;
    }

    public void setIdQuyen(Integer idQuyen) {
        this.idQuyen = idQuyen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhanQuyenEntity that = (PhanQuyenEntity) o;
        return Objects.equals(idTaiKhoan, that.idTaiKhoan) &&
                Objects.equals(idQuyen, that.idQuyen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTaiKhoan, idQuyen);
    }
}
