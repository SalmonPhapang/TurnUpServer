package com.turnup.jersey;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.BodyPartEntity;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.FormDataParam;
import com.turnup.attended.AttendedItem;
import com.turnup.career.UserProfile;

@Path("/Profile")
public class Profile {

	
	@GET
	@Path("updateProfilePic")
	public String updateProfilePic(@QueryParam("file") String file,@QueryParam("username") String uname)
	{
		System.out.println("Creating image");
		try {
			System.out.println(new Date()+" Updating Profile Pic for :"+uname);
			String base64Image = file.split(",")[0];
			byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
			String uploadedFileLocation = "d://uploads/"+uname+".png";
			InputStream stream = new ByteArrayInputStream(imageBytes);
			//BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageBytes));
			writeToFile(stream, uploadedFileLocation);
			stream.close();
		
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		
		}
		
		return "true";
	}
	
	
	private void writeToFile(InputStream uploadedInputStream,String uploadedFileLocation) {

			try {
				File file =new File(uploadedFileLocation);
				FileOutputStream  out = new FileOutputStream(file,false);
				int read = 0;
				byte[] bytes = new byte[1024];

				out = new FileOutputStream(new File(uploadedFileLocation));
				while ((read = uploadedInputStream.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}
				out.flush();
				out.close();
			} catch (IOException e) {

				e.printStackTrace();
			}

		}

}
