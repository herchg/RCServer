/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wi.server.rc.api;

import java.io.File;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.Consumes;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import wi.rc.server.opr.ImageDataOpr;

@Path("image")
@Consumes(MediaType.MULTIPART_FORM_DATA)
public class ImageResource {

//    @Context
    private UriInfo context;

    private static final String SERVER_UPLOAD_LOCATION_FOLDER = "/";
    
    public ImageResource() {
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String uploadStatePolicy(@Context HttpServletRequest request) {
        
        String result; 

        try {
            String fileName = saveFile(request);
            if (!fileName.equals("")) {
                result = fileName;
            } else {
                result = "save file Error";
            }
        } catch (Exception ex) {
            result = "Exception";
        }

        return result;
    }

    private String saveFile(HttpServletRequest request) {
        String fileName = "";
        try {
            if (ServletFileUpload.isMultipartContent(request)) {
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List<FileItem> items = null;
                
                try {
                    items = upload.parseRequest((RequestContext) request);
                } catch (FileUploadException e) {
                    e.printStackTrace();
                }

                if (items != null) {
                    Iterator<FileItem> iter = items.iterator();
                    while (iter.hasNext()) {
                        FileItem item = iter.next();
                        if (!item.isFormField() && item.getSize() > 0) {
                            fileName = processFileName(item.getName());
                            try {
                                item.write(new File(
                                        SERVER_UPLOAD_LOCATION_FOLDER
                                                + fileName));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

            }
        } catch (Exception e) {
            
        }
        return fileName;
    }

    private String processFileName(String fileNameInput) {
        String fileNameOutput = null;
        fileNameOutput = fileNameInput.substring(
                fileNameInput.lastIndexOf("\\") + 1, fileNameInput.length());
        return fileNameOutput;
    }
    
}
