package filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.sun.research.ws.wadl.Request;

public class encodeFilter implements Filter {
	FilterConfig config = null;     //读取初始化信息的对象，在init方法里赋值
	String encode = null;           //确定编码的方式，在init方法里赋值
	Boolean hasEncode = false;     //为了让编码的转换只执行一次，因为编码转换之后，
	                               //getParameterMap会保存转换之后的字符串数组，如果在进行转换会出现乱码。
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		response.setContentType("text/html; charset=utf-8");
		
		chain.doFilter(new MyRequest((HttpServletRequest) request), response);

	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		// TODO Auto-generated method stub
		this.config=config;          
		encode = config.getInitParameter("encode");
	}
	
	//装饰类，因为继承了HttpServletRequestWrapper 而HttpServletRequestWrapper实现了所需要的接口
	//所以这个装饰类没有实现所需要的借口。
	//装饰类增强三个方法的功能，其他的和原来的一样。
	class MyRequest extends HttpServletRequestWrapper{
		private HttpServletRequest request = null;
		public MyRequest(HttpServletRequest request) {
			super(request);
			this.request = request;
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public Map<String, String[]> getParameterMap() {
			try{
				 //如果是post提交的操作
				if(request.getMethod().equals("POST")){ 
					request.setCharacterEncoding(encode);
					return request.getParameterMap();
				}
				//如果是get提交的操作：获取map数组，然后将map数组里面的string数组拿出来遍历，然后更改编码样式。
				else if(request.getMethod().equals("GET")){  
					Map<String, String[]> map = request.getParameterMap();
					if(!hasEncode){
						for(Map.Entry<String, String[]>entry:map.entrySet()){
							String[] s = entry.getValue();
							for(int i =0;i<s.length;i++){
								s[i]=new String(s[i].getBytes("ISO8859-1"),encode);
							}
						}
						hasEncode = true;
					}
					return map;
				}
				//如果是其他方式提交的话，不解决乱码，直接返回原来的返回值
				else{
					return request.getParameterMap();
				}
			}catch(Exception e){
				e.printStackTrace();
				throw new RuntimeException();
			}
		}
		
		@Override
		public String[] getParameterValues(String name) {
			// TODO Auto-generated method stub
			return getParameterMap().get(name);
		}
		
		@Override
		public String getParameter(String name) {
			// TODO Auto-generated method stub
			return getParameterValues(name)==null?null: getParameterValues(name)[0];
		}
	}
}
