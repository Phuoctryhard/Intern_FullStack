package com.hnv.api.service.main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hnv.api.def.DefAPI;
import com.hnv.api.interf.IService;
import com.hnv.api.interf.IServiceCallback;
import com.hnv.api.main.API;
import com.hnv.common.tool.ToolLogServer;
import com.hnv.data.json.JSONObject;
import com.hnv.def.DefAPIExt;

/**
 * NVu.Hoang - Rin
 */

@RestController
@CrossOrigin()
@RequestMapping(value = DefAPIExt.URL_API_PUBLIC )
public class ServiceAPIPublic extends HttpServlet implements IServiceCallback{
	private static final long serialVersionUID = 1L;

	static {
		
	}
	
	@RequestMapping(method = RequestMethod.POST)
	protected void doGet(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		try {
			API.doService (API.SV_TYPE_PUBLIC, request, response, this);
		} catch (Exception e) {
			ToolLogServer.doLogErr(e);
		}
	}

	
	@Override
	public void doCallBack(HttpServletRequest arg0, HttpServletResponse arg1, Object... arg2) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
// Tóm lại
// Đoạn code này cung cấp một cơ chế xử lý yêu cầu API công khai (/api/publ) trong ứng dụng của bạn. Khi có yêu cầu POST hoặc GET tới URL này, 
// Servlet sẽ chuyển hướng việc xử lý sang API.doService, một phương thức khác trong ứng dụng của bạn để thực hiện các logic xử lý, ví dụ như xác thực,
//  truy vấn cơ sở dữ liệu, hoặc thao tác khác tùy thuộc vào thiết kế của bạn.

// Nếu bạn có câu hỏi cụ thể hơn hoặc cần giải thích thêm về từng phần trong đoạn code này, hãy cho mình biết để mình có thể giúp bạn rõ hơn nhé!