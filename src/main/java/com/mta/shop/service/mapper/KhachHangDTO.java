package com.mta.shop.service.mapper;

import com.mta.shop.entities.KhachHangEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KhachHangDTO {
    private Integer id;
    private String maKhachHang;
    private String tenKhachHang;
    private Date ngaySinh;
    private Boolean gioiTinh;
    private String diaChi;
    private String soDienThoai;
    private Integer idTaiKhoan;
    private String img;

    private String imgBase64;
    private String fileTail;

    public KhachHangDTO(KhachHangEntity khachHangEntity) {
        this.id = khachHangEntity.getId();
        this.maKhachHang = khachHangEntity.getMaKhachHang();
        this.tenKhachHang = khachHangEntity.getTenKhachHang();
        this.ngaySinh = khachHangEntity.getNgaySinh();
        this.gioiTinh = khachHangEntity.getGioiTinh();
        this.diaChi = khachHangEntity.getDiaChi();
        this.soDienThoai = khachHangEntity.getSoDienThoai();
        // this.idTaiKhoan = khachHangEntity.getIdTaiKhoan();
        this.img = khachHangEntity.getImg();
    }

    public KhachHangDTO(KhachHangEntity khachHangEntity, String imgBase64, String fileTail){
        this.id = khachHangEntity.getId();
        this.maKhachHang = khachHangEntity.getMaKhachHang();
        this.tenKhachHang = khachHangEntity.getTenKhachHang();
        this.ngaySinh = khachHangEntity.getNgaySinh();
        this.gioiTinh = khachHangEntity.getGioiTinh();
        this.diaChi = khachHangEntity.getDiaChi();
        this.soDienThoai = khachHangEntity.getSoDienThoai();
        // this.idTaiKhoan = khachHangEntity.getIdTaiKhoan();
        this.img = khachHangEntity.getImg();

        this.imgBase64 = imgBase64;
        this.fileTail = fileTail;
    }
}
