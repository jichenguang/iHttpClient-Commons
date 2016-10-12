package test.httpClient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.cookie.CookieSpec;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.GetMethod;

import java.util.Iterator;
import java.util.Map;

import javax.print.attribute.standard.DateTimeAtCompleted;

import java.net.SocketTimeoutException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * 最基本的登陆请求
 * @author 700sfriend
 * @version v1.0
 * @since {@link DateTimeAtCompleted}
 */
public class iLoginBaidu {
	

    // 登陆 Url  
    static String loginUrl = "http://www.700store.com/login";  
    // 需登陆后访问的 Url  
    static String dataUrl = "http://www.700store.com/cart"; 
	
	public static void main(String[] args) throws HttpException, IOException {

		
//		创建一个请求
		HttpClient client = new HttpClient();
		
//		请求中需要创建一个连接
		HttpMethod methodLogin = new PostMethod(loginUrl);
		
//      设置登陆时要求的信息，用户名和密码  
		NameValuePair username = new NameValuePair("username","18611360619");
		NameValuePair password = new NameValuePair("password","mljicj00");
        NameValuePair[] data =   new NameValuePair[]{username,password};  
        ((PostMethod) methodLogin).setRequestBody(data); 
		
        
        // 设置 HttpClient 接收 Cookie,用与浏览器一样的策略  
        client.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY); 
        
		//执行登陆
		try {
			client.executeMethod(methodLogin);//会返回请求的状态码，但是需要打印。
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		打印服务器返回的状态		
		System.out.println(methodLogin.getStatusLine());
		
//		获取登陆后的Cookie
		Cookie[] cookies = client.getState().getCookies();
		/*查看cookie字符串*/
        StringBuffer tmpcookies = new StringBuffer();  
        for (Cookie c : cookies) {
        	System.out.println(c.getName() + "##" + c.getValue());
            tmpcookies.append(c.toString() + ";");  
        }  
		System.out.println("###############################");
		
//		释放连接
		methodLogin.releaseConnection();
	
		
//		登陆后的操作。
		/*tmpcookies.toString() 把获取的Cookie转成字符串*/
		GetMethod getMethod = new GetMethod(dataUrl);
		/*给连接增加Cookie*/
		getMethod.setRequestHeader("cookie", tmpcookies.toString());
		client.executeMethod(getMethod);
		
		// 打印出返回数据，检验一下是否成功  
//        String text = getMethod.getResponseBodyAsString();  
//        System.out.println(text);  
		 System.out.println(getMethod.getStatusLine());

//		释放连接
		getMethod.releaseConnection();
		
		String chosefalseUrl ="http://www.700store.com/ajax/post?OP=CartUpdateAllSelected&Selected=false&rnt=1476179981935";
	    GetMethod chosefalse = new GetMethod(chosefalseUrl);
		getMethod.setRequestHeader("cookie", tmpcookies.toString());
		client.executeMethod(chosefalse); 
		// 打印出返回数据，检验一下是否成功  
        System.out.println(chosefalse.getResponseBodyAsStream()); 
        System.out.println(chosefalse.getResponseBodyAsString()); 
        System.out.println(chosefalse.getResponseBody());
		System.out.println(chosefalse.getStatusLine());
	
		
		
		
		
		
//		释放连接
		chosefalse.releaseConnection();	
	
	}

}
