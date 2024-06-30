package com.hnv.api.service.main;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hnv.api.def.DefTime;
import com.hnv.common.tool.ToolLogServer;
import com.hnv.common.util.CacheData;
import com.hnv.db.aut.TaAutUser;

@Service
public class ServiceAPILoginCheck implements UserDetailsService {
	public static BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		String password = reqAutUserPassword(username);
		if (password!=null) {
			User sprUser = new User(username, password,	new ArrayList<>());
			return sprUser;
		}else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}

	// Phương thức này thực hiện tìm kiếm thông tin người dùng theo tên đăng nhập.
	// Gọi reqAutUserPassword(username) để lấy mật khẩu đã mã hóa.
	// Nếu tìm thấy mật khẩu, tạo một đối tượng User với tên đăng nhập và mật khẩu đã mã hóa và trả về.
	// Nếu không tìm thấy, ném ra ngoại lệ UsernameNotFoundException


	private static CacheData<TaAutUser> 	cache_dbUser	= new CacheData<TaAutUser> 	(500, DefTime.TIME_SLEEP_00_30_00_000);
	private static Hashtable<String,String> cache_Pass		= new Hashtable<String,String>();
	
	public static String reqAutUserPassword(String uName) {
		try {
			//---password = reqHash256(reqHash256(ui Pass) + salt)
			//--for test ------------------------------------------------------------------
			//password = ToolSecurity.reqHash256(password);
			//password = ToolSecurity.reqHash256(password + salt);
			//-----------------------------------------------------------------------------
			if (uName==null) return null;
			
			String		dbPass 	= cache_Pass.get(uName);
			TaAutUser 	user 	= null; 
			// kiemerm tra có mật khẩu mã hóa ko 
			if (dbPass!=null) {
				// Nếu tồn tại, kiểm tra người dùng trong cache_dbUser.
				user = cache_dbUser.reqCheckIfOld(uName);
				// Nếu người dùng không có trong bộ nhớ đệm, trả về mật khẩu đã mã hóa.
				if (user!=null) 
					cache_Pass.remove(uName);
				else 
					return dbPass;
			}

			// Nếu không tìm thấy trong bộ nhớ đệm, thực hiện truy vấn cơ sở dữ liệu để lấy thông tin người dùng.
			// Sử dụng Criterion để xây dựng điều kiện truy vấn.
			// Nếu tìm thấy người dùng, lấy mật khẩu và mã hóa nó bằng BCryptPasswordEncoder.
			// Lưu thông tin người dùng và mật khẩu vào bộ nhớ đệm.
			// Nếu có bất kỳ lỗi nào xảy ra, ghi log lỗi và trả về null.
			
			Criterion cri 	= Restrictions.and(	
					Restrictions.eq(TaAutUser.ATT_T_LOGIN, uName),
					Restrictions.eq(TaAutUser.ATT_I_STATUS, TaAutUser.STAT_ACTIVE)
					);

			List<TaAutUser>users = TaAutUser.DAO.reqList(cri);
			if(users != null && users.size()>0) {
				user 	= users.get(0);
				dbPass 	= (String)user.req(TaAutUser.ATT_T_PASS).toString();
				dbPass 	= bcryptEncoder.encode(dbPass);
				
				user.reqSet(TaAutUser.ATT_T_PASS, null);
				
				cache_dbUser.reqPut(uName, user);
				cache_Pass	.put(uName, dbPass);
				
				return 		dbPass;		
			}
		} catch (Exception e) {
			ToolLogServer.doLogErr(e);
		}
		return null;
	}

	// Phương thức này lấy mật khẩu của người dùng từ cơ sở dữ liệu.
	// Nếu tên đăng nhập là null, trả về null.
	// Kiểm tra trong bộ nhớ đệm cache_Pass xem mật khẩu đã mã hóa có tồn tại hay không.
	// Nếu tồn tại, kiểm tra người dùng trong cache_dbUser.
	// Nếu người dùng không có trong bộ nhớ đệm, trả về mật khẩu đã mã hóa.
	// Nếu không tìm thấy trong bộ nhớ đệm, thực hiện truy vấn cơ sở dữ liệu để lấy thông tin người dùng.
	// Sử dụng Criterion để xây dựng điều kiện truy vấn.
	// Nếu tìm thấy người dùng, lấy mật khẩu và mã hóa nó bằng BCryptPasswordEncoder.
	// Lưu thông tin người dùng và mật khẩu vào bộ nhớ đệm.
	// Nếu có bất kỳ lỗi nào xảy ra, ghi log lỗi và trả về null.

	public static TaAutUser reqAutUser(String uName) {
		if (uName==null) return null;
		TaAutUser u = cache_dbUser.reqData(uName);
		return u;
	}
}

// Đoạn mã này cung cấp một dịch vụ kiểm tra đăng nhập cho người dùng bằng cách lấy thông tin người dùng từ cơ sở dữ liệu và sử dụng bộ nhớ đệm để tăng tốc độ truy xuất. 
//Nó sử dụng Spring Security để xác thực người dùng và BCryptPasswordEncoder
// để mã hóa mật khẩu. Các phương thức chính bao gồm loadUserByUsername, reqAutUserPassword, và reqAutUser để quản lý thông tin người dùng và mật khẩu.