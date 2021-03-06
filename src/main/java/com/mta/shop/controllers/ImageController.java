package com.mta.shop.controllers;

import com.mta.shop.controllers.message.AppResponse;
import com.mta.shop.controllers.message.AppResponseFailure;
import com.mta.shop.controllers.message.AppResponseSuccess;
import com.mta.shop.entities.ImagesEntity;
import com.mta.shop.repository.ImgRepositoryCustomImp;
import com.mta.shop.service.AnhService;
import com.mta.shop.service.mapper.AnhDTO;
import com.mta.shop.service.utils.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {
    // private final ImgRepositoryCustomImp imgRepositoryCustomImp;

    private final FileService fileService;

    private final AnhService anhService;

    @GetMapping("/")
    public AppResponse getAllImagesByIdProduct(@RequestParam("idProduct") int idProduct, Model model){
        AppResponse appResponse;
        // model.addAttribute("name", "Bùi văn chí");
        List<AnhDTO> list = anhService.getAllByIdSanPham(idProduct);
        if (null != list){
            appResponse = new AppResponseSuccess();
            appResponse.setData(list);
        }else {
            appResponse = new AppResponseFailure();
            appResponse.setData(null);
        }
        return appResponse;
    }
}
