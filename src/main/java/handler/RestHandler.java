package handler;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

import model.DataModel;

/**
 * Servlet implementation class RestHandler
 */
public class RestHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public RestHandler() {
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject myJSONObject=null;
		String RestServiceURL=null;
		String userName =null;
		String password = null; 
		DataModel dataModel=null;
		PrintWriter out= response.getWriter();
		String parameterValue= request.getParameter("paramData");
		System.out.println(parameterValue);
		try {
		InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties");
		Properties prop = new Properties();
		prop.load(input);
		RestServiceURL= prop.getProperty("RestService.URL");
		userName=prop.getProperty("RestService.username");
		password = prop.getProperty("RestService.password");	
		RestServiceURL=RestServiceURL+parameterValue;
		System.out.println(RestServiceURL);
		}catch (IOException ex) {
            ex.printStackTrace();
        }
		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(userName, password);
		provider.setCredentials(AuthScope.ANY, credentials);
		HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
		HttpResponse Invokedresponse = client.execute(new HttpGet(RestServiceURL));
		int statusCode = Invokedresponse.getStatusLine().getStatusCode();
		System.out.println("Status Code:"+statusCode);
		if(statusCode==200) {
		HttpEntity entity = Invokedresponse.getEntity();
		if (entity != null) {
			InputStream instream = entity.getContent();
			String result = convertStreamToString(instream);
			System.out.println(result);
			myJSONObject = new JSONObject(result);
			/*
			 * out.println("Track & Trace Number :"+myJSONObject.getString("attribute1"));
			 * out.println("Inco Term :"+ myJSONObject.getString("incoTermGid"));
			 * out.println("Inco Term Location :"+
			 * myJSONObject.getString("termLocationText")); out.println("Customer Name :"+
			 * myJSONObject.getString("attribute3")); out.println("Vendor Name:"+
			 * myJSONObject.getString("attribute14"));
			 */
			dataModel=new DataModel();
			dataModel.setTrackNumber(myJSONObject.getString("attribute1"));
			dataModel.setIncoTerm(myJSONObject.getString("incoTermGid"));
			dataModel.setIncoTermLocation(myJSONObject.getString("termLocationText"));
			dataModel.setCustomerName(myJSONObject.getString("attribute3"));
			dataModel.setVendorName(myJSONObject.getString("attribute14"));
			HttpSession session = request.getSession();
			session.setAttribute("model", dataModel);
			RequestDispatcher rs = request.getRequestDispatcher("index.jsp");
			rs.forward(request, response);
		}
	}else if(statusCode==404) {
		HttpSession session = request.getSession();
		session.setAttribute("message", "No data for this parameter value.");
		RequestDispatcher rs = request.getRequestDispatcher("index.jsp");
		rs.forward(request, response);
	}
		else {
		HttpSession session = request.getSession();
		session.setAttribute("message", "Error occured while connecting to the service");
		RequestDispatcher rs = request.getRequestDispatcher("index.jsp");
		rs.forward(request, response);
	}
	}
	private String convertStreamToString(InputStream instream) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
	    StringBuilder sb = new StringBuilder();

	    String line = null;
	    try {
	        while ((line = reader.readLine()) != null) {
	            sb.append(line + "\n");
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	        	instream.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    return sb.toString();
	}

}
