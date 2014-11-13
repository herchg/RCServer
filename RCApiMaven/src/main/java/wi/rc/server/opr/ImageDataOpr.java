/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wi.rc.server.opr;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author samuelatwistron
 */
public class ImageDataOpr {
    
    private static final String SERVER_UPLOAD_LOCATION_FOLDER = "/usr/local/rc/employee/";
    
    private static String getImageName(int company_id,int employee_id) {
        
        return String.valueOf(company_id) + "_" + String.valueOf(employee_id) + ".png"; //"." + processFileName(imageItem.getName());
    }
    
    public static Response getImageFile(int company_id,int employee_id) {
        
        Response resp;
        
        String imageName = getImageName(company_id,employee_id);
        
        File imageFile = new File(SERVER_UPLOAD_LOCATION_FOLDER, imageName);
        
        if(imageFile.exists()){
            resp = Response.ok(imageFile, "image/png").build();
        }else{
            //不存在 使用預設圖片
            imageFile = new File(SERVER_UPLOAD_LOCATION_FOLDER, "default.png");
            
            resp = Response.ok(imageFile, "image/png").build();
        }
        
        return resp;
    }
    
    
    public static Response uploadImage(HttpServletRequest request) {
        
        Response resp;
        
        int employee_id = 0;
        int company_id = 0;
        FileItem imageItem = null;
        
        resp = Response.status(Response.Status.BAD_REQUEST).entity("Upload Error").build();
        try {
            if (ServletFileUpload.isMultipartContent(request)) {
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List<FileItem> items = null;
                
                items = upload.parseRequest(request);
                if (items != null) {
                    Iterator<FileItem> iter = items.iterator();
                    while (iter.hasNext()) {
                        
                        FileItem item = iter.next();
                        // 接收表单里的参数 
                        if(item.isFormField()){
                            if(item.getFieldName().equals("employee_id")){
                                employee_id = Integer.parseInt(item.getString("UTF-8"));
                            }
                            if(item.getFieldName().equals("company_id")){
                                company_id = Integer.parseInt(item.getString("UTF-8"));
                            }
                        }
                        //圖黨
                        if (!item.isFormField() && item.getSize() > 0) {
                            imageItem = item;
                        }
                    }
                }      
            }
        } catch (Exception e) {
            resp = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
        
        if(employee_id == 0 && company_id == 0 && imageItem != null){

            String fileName = getImageName(company_id,employee_id);
            try {
                imageItem.write(new File(SERVER_UPLOAD_LOCATION_FOLDER + fileName));

                resp = Response.status(Response.Status.OK).build();
            } catch (Exception e) {
                resp = Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
            }
        }

        return resp;
    }

    private static String processFileName(String fileNameInput) {
        String fileNameOutput = null;
        //fileNameOutput = fileNameInput.substring(fileNameInput.lastIndexOf("\\") + 1, fileNameInput.length());
        fileNameOutput = fileNameInput.substring(fileNameInput.lastIndexOf(".") + 1, fileNameInput.length());
        return fileNameOutput;
    }
}
