package com.scm.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.scm.helpers.AppConstants;
import com.scm.services.ImageService;
import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageServiceImpl implements ImageService {

    private Cloudinary cloudinary;
    //constructor injection
    public ImageServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }


    @Override
    public String uploadImage(MultipartFile contactImage,String filename) {
        // Implementation for uploading image , using cloudinary
        //vo code likhna jo image upload krke uska url de de

        if (contactImage == null || contactImage.isEmpty()) {
            throw new IllegalArgumentException("Image file must not be null or empty");
        }

        try {
            byte[] data = new byte[contactImage.getInputStream().available()];

            contactImage.getInputStream().read(data);
            cloudinary.uploader().upload(data,ObjectUtils.asMap(
                "public_id", filename
            ));

            return this.getUrlFromPublicId(filename);
            
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        
    }



    @Override
    public String getUrlFromPublicId(String publicId) {
        //will get url from the public id
        //public id means the image name stored in cloudinary e.g. cloudinary me jo image 
        // h uska name as a public id use krke uska url le aayega
       return cloudinary
       .url()
       .transformation(
        new Transformation<>()
        .width(AppConstants.CONTACT_IMAGE_WIDTH)
        .height(AppConstants.CONTACT_IMAGE_HEIGHT)
        .crop(AppConstants.CONTACT_IMAGE_CROP)
       )
       .generate(publicId);
    }
}
