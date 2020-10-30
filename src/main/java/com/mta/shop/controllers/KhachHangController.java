package com.mta.shop.controllers;

import com.mta.shop.controllers.message.*;
import com.mta.shop.entities.KhachHangEntity;
import com.mta.shop.repository.KhachHangRepositoryCustom;
import com.mta.shop.service.KhachHangService;
import com.mta.shop.service.mapper.KhachHangDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin("*")
@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class KhachHangController {

    @Autowired
    private KhachHangRepositoryCustom khachHangRepositoryCustom;

    private final KhachHangService khachHangService;

    // lấy thông tin khách hàng
    @PostMapping(value = "/get")
    public AppResponse getCustomer(@RequestBody GetKhachHangRequest request) throws IOException {
        System.out.println("nhận body: " + request.toString());
        AppResponse appResponse;
        if (request.getTypeAccount().equals("customer")){
            KhachHangEntity khachHangEntity = khachHangRepositoryCustom.getKhachHang(request.getUserName());

            String imgBase64 = khachHangService.getFile(khachHangEntity.getImg());
            System.out.println("img baser 64: " + imgBase64);
            KhachHangDTO khachHangDTO = new KhachHangDTO(khachHangEntity, imgBase64, "" );


            appResponse = new AppResponseSuccess();
            appResponse.setData(khachHangDTO);
            return appResponse;
        }

        return  new AppResponseSuccess();

    }

//    // cập nhật thông tin cá nhân
//    @PostMapping(value = "/update")
//    public AppResponse updateCustomer(@RequestBody UpdateInformationCustomerRequest request) {
//        System.out.println("nhận body: " + request.toString());
//        AppResponse appResponse;
//        KhachHangEntity khachHangEntity = khachHangService.updateCustomer(request);
//        if (null != khachHangEntity){
//            appResponse = new AppResponseSuccess();
//            appResponse.setData(khachHangEntity);
//            return appResponse;
//        }else {
//            appResponse = new AppResponseFailure();
//        }
//        return  new AppResponseSuccess();
//
//    }

    // cập nhật thông tin cá nhân
    @PostMapping(value = "/update")
    public AppResponse updateCustomer(@RequestBody UpdateInformationCustomerRequest request) throws IOException {
        // System.out.println("nhận body: " + request.toString());
        AppResponse appResponse;

        // file upleen có trùng với file gốc
        boolean theSamePath = false;

        // Save url to database
        String filePath = khachHangService.saveFile(request.getImgBase64(), request.getImg(), request.getFileTail());
        if (null != filePath){
            request.setImg(filePath);
        }
        if (request.getFilePathOld().replaceAll(" ", "").equals(filePath)){
            theSamePath = true;
        }
        KhachHangEntity khachHangEntity = khachHangService.updateCustomer(request, theSamePath);
        if (null != khachHangEntity){
            appResponse = new AppResponseSuccess();
//            appResponse.setData(khachHangEntity);
            String imgBase64 = khachHangService.getFile(khachHangEntity.getImg());
//            System.out.println("img baser 64: " + imgBase64);
            KhachHangDTO khachHangDTO = new KhachHangDTO(khachHangEntity, imgBase64, "" );
            appResponse.setData(khachHangDTO);
            return appResponse;
        }else {
            appResponse = new AppResponseFailure();
        }
        return  appResponse;
    }



}