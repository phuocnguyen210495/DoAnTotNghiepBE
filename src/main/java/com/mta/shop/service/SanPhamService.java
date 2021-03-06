package com.mta.shop.service;

import com.mta.shop.controllers.message.ProductAdminPagingRequest;
import com.mta.shop.controllers.message.product.ProductAdminAddRequest;
import com.mta.shop.entities.LoaiSanPham;
import com.mta.shop.entities.SanPhamEntity;
import com.mta.shop.entities.ThuongHieuEntity;
import com.mta.shop.repository.SanPhamRepository;
import com.mta.shop.service.mapper.SanPhamDTO;
import com.mta.shop.service.utils.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SanPhamService {
    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private ThuongHieuService thuongHieuService;

    @Autowired
    private LoaiSanPhamService loaiSanPhamService;

    public List<SanPhamEntity> getAll() {
        return sanPhamRepository.findAll();
    }

    public List<SanPhamDTO> getAllDTO() {
        return sanPhamRepository.findAll().stream().map(e -> new SanPhamDTO(e)).collect(Collectors.toList());
    }

    public Page<SanPhamEntity> searchAdminPaging(ProductAdminPagingRequest request) {
        List<Integer> loaiSanPhamList = new ArrayList<>();
        List<Integer> thuongHieuList = null;

        if (null == request.getLoaiSanPham() || request.getLoaiSanPham().size() < 1) {
            List<LoaiSanPham> lspList = loaiSanPhamService.getAll();
            List<Integer> finalLoaiSanPhamList = loaiSanPhamList;
            lspList.forEach(e -> finalLoaiSanPhamList.add(e.getId()));

            loaiSanPhamList.addAll(finalLoaiSanPhamList);
        } else {
            loaiSanPhamList = request.getLoaiSanPham();
        }

        if (null == request.getThuongHieu() || request.getThuongHieu().size() < 1) {
//            List<ThuongHieuEntity> thuongHieuEntityList = thuongHieuService.getAll();
//
//            List<Integer> finalThuongHieuList = thuongHieuList;
//            thuongHieuEntityList.forEach(e -> finalThuongHieuList.add(e.getId()));
//
//            thuongHieuList.addAll(finalThuongHieuList);
            thuongHieuList = thuongHieuService.getAllId();
        } else {
            thuongHieuList = request.getThuongHieu();
        }

        return sanPhamRepository.searchAdminPaging(PageRequest.of(request.getPageNumber(), request.getPageSize()),
                request.getCodeProduct(),
                request.getNameProduct(),
                loaiSanPhamList,
                thuongHieuList,
                request.getPriceLower(),
                request.getPriceUpper()
        );
    }

    @Transactional
    public SanPhamEntity addProductAdmin(ProductAdminAddRequest request) throws IOException {
        SanPhamEntity sanPhamEntity = request.getSanPhamEntity();

        // nếu mã sản phẩm đã tồn tại thì trả về null để báo lỗi
        Optional<SanPhamEntity> sanPhamEntityOptional = sanPhamRepository.findByMaSanPham(sanPhamEntity.getMaSanPham());
        if (sanPhamEntityOptional.isPresent()) {
            return null;
        }

        // Save url to database
        String filePath = fileService.saveFile(request.getImgBase64(), request.getFileName(), request.getFileTail());
        if (null != filePath) {
            sanPhamEntity.setAnhDaiDien(filePath);
        }

        Integer idThuongHieu = sanPhamEntity.getThuongHieuEntity().getId();
        ThuongHieuEntity thuongHieuEntity = thuongHieuService.getThuongHieuById(idThuongHieu).get();
        System.out.println("th" + thuongHieuEntity);

        Integer idLoaiSanPham = sanPhamEntity.getLoaiSanPham().getId();
        LoaiSanPham loaiSanPham = loaiSanPhamService.findById(idLoaiSanPham).get();
        System.out.println("th" + loaiSanPham);


        sanPhamEntity.setThuongHieuEntity(thuongHieuEntity);
        sanPhamEntity.setLoaiSanPham(loaiSanPham);

        return sanPhamRepository.save(sanPhamEntity);
    }

    @Transactional
    public SanPhamEntity updateProductAdmin(ProductAdminAddRequest request) throws IOException {
        SanPhamEntity sanPhamEntityRequest = request.getSanPhamEntity();

        // nếu id phẩm không tồn tại thì trả về null để báo lỗi
        Optional<SanPhamEntity> sanPhamEntityOptional = sanPhamRepository.findById(sanPhamEntityRequest.getId());
        if (sanPhamEntityOptional.isEmpty()) {
            return null;
        }

        SanPhamEntity sanPhamEntity = sanPhamEntityOptional.get();

        // kiểm tra nếu đường dẫn gửi lên và đường dẫn trong db giống nhau thì ko cập nhật ảnh
        if (null==sanPhamEntity.getAnhDaiDien()||!sanPhamEntity.getAnhDaiDien().equals(sanPhamEntityRequest.getAnhDaiDien())) {
            // Save url to database
            String filePath = fileService.saveFile(request.getImgBase64(), request.getFileName(), request.getFileTail());
            if (null != filePath) {
                sanPhamEntity.setAnhDaiDien(filePath);
            }
        } else {

        }

        // cập nhật thương hiệu và loại sản phẩm
        Integer idThuongHieu = sanPhamEntityRequest.getThuongHieuEntity().getId();
        Integer idLoaiSanPham = sanPhamEntityRequest.getLoaiSanPham().getId();


        sanPhamEntity.setMaSanPham(sanPhamEntityRequest.getMaSanPham());
        sanPhamEntity.setTenSanPham(sanPhamEntityRequest.getTenSanPham());
        sanPhamEntity.setGia(sanPhamEntityRequest.getGia());

        sanPhamEntity.setGiamGia(sanPhamEntityRequest.getGiamGia());
//        sanPhamEntity.setAnhDaiDien(sanPhamEntityRequest.getAnhDaiDien());

        sanPhamEntity.setLoaiSanPham(loaiSanPhamService.findById(idLoaiSanPham).isEmpty() ? null : loaiSanPhamService.findById(idLoaiSanPham).get());
        sanPhamEntity.setThuongHieuEntity(thuongHieuService.getThuongHieuById(idThuongHieu).isEmpty() ? null : thuongHieuService.getThuongHieuById(idThuongHieu).get());
//        sanPhamEntity.setNgayCapNhap(new java.sql.Date(System.currentTimeMillis()));

        return sanPhamRepository.save(sanPhamEntity);
    }

    public SanPhamDTO getAProduct(Integer idSanPham) throws IOException {
        Optional<SanPhamEntity> sanPhamEntity = sanPhamRepository.findById(idSanPham);

        if (sanPhamEntity.isEmpty()) {
            return null;
        }

        SanPhamEntity sanPhamEntity1 = sanPhamEntity.get();

        return new SanPhamDTO(sanPhamEntity1, getImgBase64(sanPhamEntity1.getAnhDaiDien()), "");
    }

    public Boolean deleteAProduct(Integer idSanPham) throws IOException {
        Optional<SanPhamEntity> sanPhamEntity = sanPhamRepository.findById(idSanPham);

        if (sanPhamEntity.isEmpty()) {
            return null;
        }

        SanPhamEntity sanPhamEntity1 = sanPhamEntity.get();

        sanPhamRepository.delete(sanPhamEntity1);

        if (sanPhamRepository.findById(idSanPham).isEmpty())
            return true;
        return false;
    }

    public String getImgBase64(String path) throws IOException {
        return fileService.getFile(path);
    }

}
