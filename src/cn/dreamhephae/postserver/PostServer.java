package cn.dreamhephae.postserver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import net.sourceforge.tess4j.TesseractException;

/**
 * Servlet implementation class PostServer
 */
@WebServlet("/PostServer")
public class PostServer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final long FILE_MAX_SIZE = 1024*1024*10;   //最大文件大小10M Bytes
	private static final String FILE_SAVE_PATH = "E:\\eclipse-workspace\\PostServer\\cache\\";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PostServer() {
        super();
        // TODO Auto-generated constructor stub
        //System.out.println(System.getProperty("java.library.path"));
        System.loadLibrary("JavacTesseract");
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Create by Dreamhephae");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		RequestContext req = new ServletRequestContext(request);
		if(FileUpload.isMultipartContent(req)){
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload fileUpload = new ServletFileUpload(factory);
			fileUpload.setFileSizeMax(FILE_MAX_SIZE);

			List items = new ArrayList();
			try {
				items = fileUpload.parseRequest(req);
			}catch(Exception e) {
				System.out.println(e.toString());
			}

			Iterator it = items.iterator();
			while(it.hasNext()){
				FileItem fileItem = (FileItem)it.next();
				if(fileItem.isFormField()){
					System.out.println(fileItem.getFieldName()+" "+fileItem.getName()
					+" "+new String(fileItem.getString().getBytes("ISO-8859-1"),"GBK"));
				} 
				else {
					System.out.println(fileItem.getFieldName()+" "+fileItem.getName()+" "+
					fileItem.isInMemory()+" "+fileItem.getContentType()+" "+fileItem.getSize());
					if(fileItem.getName()!=null && fileItem.getSize()!=0){
						SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
				        //根据当前时间生成图片的名称
				        String timestamp = formatter.format(new Date())+".jpg";
						//File fullFile = new File(fileItem.getName());
						File newFile = new File(FILE_SAVE_PATH + timestamp);
						try {
							fileItem.write(newFile);
							String result = OCRProcess.process(FILE_SAVE_PATH + timestamp);
							System.out.println(result);
							response.setCharacterEncoding("utf-8");
							response.getWriter().append(result);
						} catch (Exception e) {
							System.out.println(e.toString());
							System.out.println("in write file");
						}
						
					} 
					else {
						System.out.println("no file choosen or empty file");
					}	
				}
			}
		}
	}

}