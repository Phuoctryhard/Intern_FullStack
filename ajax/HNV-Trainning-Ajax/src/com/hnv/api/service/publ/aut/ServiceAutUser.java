package com.hnv.api.service.publ.aut;


import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.hnv.api.def.DefAPI;
import com.hnv.api.def.DefJS;
import com.hnv.api.interf.IService;
// ko biết API này ở đâu ta 
import com.hnv.api.main.API;
import com.hnv.common.tool.ToolData;
import com.hnv.common.tool.ToolJSON;
import com.hnv.common.tool.ToolLogServer;
import com.hnv.data.json.JSONObject;
import com.hnv.db.aut.TaAutUser;


// Các import từ org.hibernate.criterion.* là để tạo các tiêu chí truy vấn cơ sở dữ liệu sử dụng Hibernate.
// Các import từ com.hnv.api.*, com.hnv.common.tool.*, và com.hnv.db.aut.* là để sử dụng các lớp và phương thức đặc thù của ứng dụng này.


/**
* ----- ServiceAutUser by H&V
* ----- Copyright 2023------------
*/

// public class ServiceAutUser implements IService: Định nghĩa lớp ServiceAutUser và chỉ ra rằng lớp này triển khai giao diện IService.

public class ServiceAutUser implements IService {
	private static	String 			filePath	= null; 
	private	static	String 			urlPath		= null; 
	

	//--------------------------------Service Definition----------------------------------
	public static final String SV_MODULE 				= "HNV".toLowerCase();

	public static final String SV_CLASS 				= "ServiceAutUser".toLowerCase();	
	
	public static final String SV_PING 					= "SVPing"		.toLowerCase();	
	
	public static final String SV_GET 					= "SVGet"		.toLowerCase();	
	public static final String SV_LST 					= "SVLst"		.toLowerCase();
	public static final String SV_LST_DYN				= "SVLstDyn"	.toLowerCase(); 

	public static final String SV_NEW 					= "SVNew"		.toLowerCase();	
	public static final String SV_MOD 					= "SVMod"		.toLowerCase();	
	public static final String SV_DEL 					= "SVDel"		.toLowerCase();
	
	//-----------------------------------------------------------------------------------------------
	//-------------------------Default Constructor - Required -------------------------------------
	public ServiceAutUser(){
		ToolLogServer.doLogInf("----" + SV_CLASS + " is loaded -----");
	}
	// Hàm khởi tạo (constructor) của lớp, ghi log thông tin khi lớp được tải.
	//-----------------------------------------------------------------------------------------------

	@Override
// 	Phương Thức doService
// 	Phương thức doService là một phương thức trung tâm xử lý các yêu cầu dịch vụ khác nhau được gửi tới lớp ServiceAutUser.
//  Nó dựa vào tên phương thức dịch vụ (sv) được yêu cầu trong đối tượng JSON để xác định và gọi phương thức xử lý tương ứng.
	public void doService(JSONObject json, HttpServletResponse response) throws Exception {
		String 		sv 		= API.reqSVFunctName(json);// Lấy tên phương thức dịch vụ (sv) từ JSON
		TaAutUser 	user	= (TaAutUser) json.get("userInfo"); // Lấy thông tin người dùng từ đối tượng JSON và ép kiểu về TaAutUser.
		try {
			// Sử dụng một chuỗi if-else để kiểm tra giá trị của sv và 
			//gọi phương thức tương ứng.Sử dụng một chuỗi if-else để kiểm tra giá trị của sv và gọi phương thức tương ứng.
			//---------------------------------------------------------------------------------
			if(sv.equals(SV_PING)					){ // Gọi phương thức doPing
				doPing(user,  json, response);
			} else if(sv.equals(SV_NEW)					){ // Gọi phương thức doNew
				doNew(user,  json, response);
			} else if(sv.equals(SV_MOD)					){// Gọi phương thức doMod
				doMod(user,  json, response);
			} else  if(sv.equals(SV_DEL)				){// Gọi phương thức doDel
				doDel(user,  json, response);	
			} else if(sv.equals(SV_GET) 				){// Gọi phương thức doGet
				doGet(user,  json, response);

			} else if(sv.equals(SV_LST)					){// Gọi phương thức doLst
				doLst(user,  json, response);
			} else {
				API.doResponse(response, DefAPI.API_MSG_ERR_RIGHT);// Phương thức không hợp lệ, trả về lỗi
			}	
		}catch(Exception e){
			API.doResponse(response, DefAPI.API_MSG_ERR_API);// Xử lý ngoại lệ, trả về lỗi API
			e.printStackTrace();
		}		
	}
// 	doService là phương thức chính xử lý các yêu cầu dịch vụ.
// Nó lấy tên phương thức dịch vụ từ json và gọi phương thức tương ứng (doPing, doNew, doMod, doDel, doGet, doLst).
// Nếu xảy ra lỗi, nó sẽ trả về thông báo lỗi và in ra stack trace
	//---------------------------------------------------------------------------------------------------------
	private static void doPing(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
	// 1. Phương thức doPing
	// Phương thức này kiểm tra xem dịch vụ có đang hoạt động không bằng cách gửi phản hồi "Hello you touched the ServiceAutUser
	// Đầu vào: Thông tin người dùng (user), đối tượng JSON (json), phản hồi HTTP (response).
	// Đầu ra: Phản hồi JSON với thông điệp xác nhận dịch vụ đang hoạt động.
		API.doResponse(response, ToolJSON.reqJSonString(		
				DefJS.SESS_STAT		, 1, 
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA		, "Hello you touched the ServiceAutUser"
		));
	}
	
	//---------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------

	// 	Đầu vào: Thông tin người dùng (user), đối tượng JSON (json), phản hồi HTTP (response).
	// Đầu ra: Phản hồi JSON với thông điệp xác nhận dịch vụ đang hoạt động.



	// 2. Phương thức doNew
	// Phương thức này tạo một đối tượng TaAutUser mới.
	// Đầu vào: Thông tin người dùng (user), đối tượng JSON (json), phản hồi HTTP (response).
	// Đầu ra: Phản hồi JSON với đối tượng TaAutUser mới được tạo.
	private static void doNew(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		TaAutUser 			ent		= reqNew		(user, json);
		if (ent==null){
			API.doResponse(response, DefAPI.API_MSG_KO);
			return;
		}
		API.doResponse(response, ToolJSON.reqJSonString(		//filter,
				DefJS.SESS_STAT		, 1, 
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA		, ent
		));
	}


	private static TaAutUser reqNew(TaAutUser user,  JSONObject json) throws Exception {
		JSONObject			obj		= ToolData.reqJson		 (json, "obj", null);
		Map<String, Object> attr 	= API.reqMapParamsByClass(obj, TaAutUser.class);	
		TaAutUser  			ent	 	= new TaAutUser		 (attr);
		
		ent.reqSet(TaAutUser.ATT_I_ID, null);//đảm bảo id null để CSDL quyết định khi tạo mới
		
		TaAutUser.DAO.doPersist(ent);

		return ent;
	}


	//---------------------------------------------------------------------------------------------------------
	//Phương thức này sửa đổi một đối tượng TaAutUser hiện có.
	// Đầu vào: Thông tin người dùng (user), đối tượng JSON (json), phản hồi HTTP (response).
	// Đầu ra: Phản hồi JSON với đối tượng TaAutUser đã được sửa đổi.


	// Đầu vào:user: Thông tin người dùng đang thực hiện thao tác.
	// json: Đối tượng JSON chứa thông tin cần thiết để chỉnh sửa đối tượng TaAutUser.
	// response: Đối tượng HttpServletResponse để trả về kết quả.
	// Đầu ra: Phản hồi JSON chứa thông tin của đối tượng TaAutUser sau khi chỉnh sửa hoặc thông báo lỗi nếu không thành công.
	private static void doMod(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		// Gọi phương thức reqMod để lấy đối tượng TaAutUser đã được chỉnh sửa
		TaAutUser  		ent	 	=  reqMod(user, json); 	
		//// Nếu không tìm thấy đối tượng, trả về thông báo lỗi							
		if (ent==null){
			API.doResponse(response, DefAPI.API_MSG_KO);
		} else {
			API.doResponse(response, ToolJSON.reqJSonString(
					DefJS.SESS_STAT		, 1, 
					DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
					DefJS.RES_DATA		, ent // // Nếu thành công, trả về đối tượng TaAutUser đã được chỉnh sửa dưới dạng JSON
			));	
		}		
	}
	//Phương thức reqMod thực hiện các bước cụ thể để lấy ra đối tượng TaAutUser từ cơ sở dữ liệu và cập nhật các thuộc tính mới từ đối tượng JSON.
	private static TaAutUser reqMod(TaAutUser user,  JSONObject json) throws Exception {
		
		JSONObject			obj		= ToolData.reqJson 	(json	, "obj"	, null); // Lấy đối tượng JSON chứa thông tin cần chỉnh sửa
		int 				objId 	= ToolData.reqInt	(obj	, "id"	, null); // Lấy ID của đối tượng cần chỉnh sửa từ đối tượng JSON
		
		TaAutUser 			ent 	= TaAutUser.DAO.reqEntityByRef(objId);// Tìm đối tượng TaAutUser trong cơ sở dữ liệu bằng ID
		if (ent==null){
			return null;
		}
		
		Map<String, Object> attr 	= API.reqMapParamsByClass(obj, TaAutUser.class);// Chuyển đổi đối tượng JSON thành Map các thuộc tính
		
		TaAutUser.DAO.doMerge(ent, attr);// Sử dụng đối tượng DAO để cập nhật đối tượng TaAutUser với các thuộc tính mới
		return ent; // Trả về đối tượng TaAutUser đã được cập nhật
	}

	//---------------------------------------------------------------------------------------------------------
	private static void doDel(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		if (!canDel(user, json)){
			API.doResponse(response, DefAPI.API_MSG_KO);
		} else {
			API.doResponse(response, DefAPI.API_MSG_OK);
		}
	}

	private static boolean canDel(TaAutUser user,  JSONObject json) throws Exception {
		Integer 		entId	= ToolData.reqInt	(json, "id", null	);	
				
		TaAutUser  	ent	 	= TaAutUser.DAO.reqEntityByRef(entId);
		if (ent==null){
			return false;
		}

		TaAutUser.DAO		.doRemove (ent);
		//---we have to check T_Aut_Right in AutRole + AutAuthorization?

		return true;
	}

	//---------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------
	private static void doGet(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {	
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doGet --------------");

		Integer 			objId		= ToolData.reqInt	(json, "id"			, -1);				
		TaAutUser 			ent 		= reqGet(objId);

		if (ent==null){
			API.doResponse(response, DefAPI.API_MSG_KO);
			return;
		}


		API.doResponse(response, ToolJSON.reqJSonString(		//filter,
				DefJS.SESS_STAT		, 1, 
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA		, ent 
				));
	}
	
	public static TaAutUser reqGet(Integer objId) throws Exception{
		TaAutUser 		ent 	= TaAutUser.DAO.reqEntityByRef(objId);
		return ent;
	}
	
	//---------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------
	private static void doLst(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doLst --------------");

		List<TaAutUser> 	list = reqLst(user, json); //and other params if necessary
		if (list==null || list.size()==0){
			API.doResponse(response,DefAPI.API_MSG_KO);
			return;
		}

		API.doResponse(response, ToolJSON.reqJSonString(//filter,		
				DefJS.SESS_STAT		, 1, 
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA		, list 
				));				
	}

	private static List<TaAutUser> reqLst(TaAutUser user, JSONObject json) throws Exception  {
		Integer 			nbLine      = ToolData.reqInt		(json, "nbLine" 	, 10);
		Set<String> 		searchkey	= ToolData.reqSetStr	(json, "searchkey"	, null);
		Set<Integer>		stat		= ToolData.reqSetInt	(json, "stat"		, null);
		Set<Integer>		typ			= ToolData.reqSetInt	(json, "typ"		, null);
		
		
		if (typ==null && stat==null){
			return null;
		}

		Criterion cri				= reqRestriction (user, searchkey, stat, typ);	
		List<TaAutUser>	list		= TaAutUser.DAO.reqList(0, nbLine, cri);	

		return list;
	}
	
	//---------------------------------------------------------------------------------------------------------		
	

	private static Criterion reqRestriction(TaAutUser user, Set<String> searchKey, Set<Integer> stats, Set<Integer> types) throws Exception {		
		if (stats == null){
			stats = new HashSet<Integer>() ; 
			stats.add(TaAutUser.STAT_ACTIVE);
		}
		
		Criterion cri = Restrictions.in(TaAutUser.ATT_I_STATUS, stats);
		
		if(types!=null) {
			cri = Restrictions.and(	cri, Restrictions.in(TaAutUser.ATT_I_TYPE_01 , types));
		}

		if (searchKey!=null) {
			for (String s : searchKey){
				cri = 	 Restrictions.and	(cri, Restrictions.or(
													Restrictions.ilike(TaAutUser.ATT_T_LOGIN, s), 
													Restrictions.ilike(TaAutUser.ATT_T_INFO_01	, s),
													Restrictions.ilike(TaAutUser.ATT_T_INFO_02	, s))
											);
			}
		}
		
		return cri;
	}

	
	
}
