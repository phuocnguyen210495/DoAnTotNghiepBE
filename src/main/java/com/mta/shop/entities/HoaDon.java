package com.mta.shop.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity // Đánh dấu đây là table trong db
@Data // lombok giúp generate các hàm constructor, get, set v.v.
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "HOADON")
public class HoaDon {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "NGAYTAO")
    private Timestamp ngayTao;

    @Column(name = "TONGTIEN")
    private Float tongTien;

    @Column(name = "TONGTIENPHAITRA")
    private Float tongTienPhaiTra;

    @Column(name = "PHANTRAMGIAMGIA")
    private Integer phanTramGiamGia;

    @Column(name = "TIENGIAMGIA")
    private Float tienGiamGia;

    @Column(name = "MAHOADON")
    private String maHoaDon;

    @Column(name = "TRANGTHAI")
    private Integer trangThai;

    @Column(name = "HINHTHUCTHANHTOAN")
    private Integer hinhThucThanhToan;

    @Column(name = "SOTIENDATRA")
    private Float soTienDaTra;

    @Column(name = "HINHTHUCGIAOHANG")
    private Integer hinhThucGiaoHang;

    @Column(name = "TRANGTHAIGIAOHANG")
    private Integer idKhachHang;



//    @Column(name = "IDGIAMGIA")
//    private Integer idGiamGia;
//
//    @Column(name = "IDKHACHHANG")
//    private Integer idKhachHang;
//
//    @Column(name = "IDNHANVIEN")
//    private Integer idNhanVien;

    @ManyToOne
    @JoinColumn(name = "IDGIAMGIA", insertable = false, updatable = true) // phải set thằng này ko update hay thêm mới nó ko sét giá trị cho đâu
    private GiamGia giamGia;

    @ManyToOne
    @JoinColumn(name = "IDKHACHHANG", insertable = false, updatable = true) // phải set thằng này ko update hay thêm mới nó ko sét giá trị cho đâu
    private KhachHangEntity khachHangEntity;

    @ManyToOne
    @JoinColumn(name = "IDNHANVIEN", insertable = false, updatable = true) // phải set thằng này ko update hay thêm mới nó ko sét giá trị cho đâu
    private NhanVienEntity nhanVienEntity;

    @OneToMany(mappedBy = "hoaDon", cascade = CascadeType.ALL) // Quan hệ 1-n với đối tượng ở dưới (Person) (1 địa điểm có nhiều người ở)
    @JsonBackReference
    private Collection<ChiTietHoaDonEntity> chiTietHoaDonEntities;
}
