package com.heejue.es;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


public class ESQuerynewV1_1 {

	
	private String urlIp = "192.168.10.158";
	private String port = "9200";
	private String search = "/_search";
	private List<String> must = new ArrayList<String>();
	private List<String> must_not = new ArrayList<String>();
	private List<String> should = new ArrayList<String>();
	private int from = 0;
	private int size = 10;
	private List<String> sort = new ArrayList<String>();
	private String aggs = "";
	private List<String> aggs_aggs = new ArrayList<String>();
	//protected
	
	public ESQuerynewV1_1() {
		
	}

	public void addAggs_aggs(String name,String func,String column) {
		aggs_aggs.add(name+","+func+","+column);
	}
	
	/**
	 * 绛変簬  <br>@鍙傛暟锛歱aram锛涙牸寮忥細"cloumn,value"
	 * @param锛歱aram
	 */
	public void addMust(String param) {
		must.add(param);
	}
	/**
	 * 闂翠簬
	 * @鍙傛暟 column
	 * @鍙傛暟 gte  璧峰
	 * @鍙傛暟 lt  缁撴潫
	 */
	public void addMustRange(String column,String gte,String lt) {
		must.add("range,"+column+","+gte+","+lt);
	}
	/**
	 * 涓嶇瓑浜�
	 * @鍙傛暟锛歱aram锛涙牸寮忥細"cloumn,value"
	 */
	public void addMust_not(String param) {
		must_not.add(param);
	}
	/**
	 * 鎴栬��
	 * @鍙傛暟锛歱aram锛涙牸寮忥細"cloumn,value"
	 */
	public void addShould(String param) {
		should.add(param);
	}
	/**
	 * 鎺掑簭
	 * @鍙傛暟锛歱aram锛涙牸寮忥細"cloumn,desc"銆�"cloumn"
	 */
	public void addSort(String param) {
		sort.add(param);
	}
	
	public String getUrlIp() {
		return urlIp;
	}
	public void setUrlIp(String urlIp) {
		this.urlIp = urlIp;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public int getFrom() {
		return from;
	}
	public void setFrom(int from) {
		this.from = from;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getAggs() {
		return aggs;
	}
	/**
	 * 鑱氬悎
	 * @鍙傛暟锛歛ggs锛涙牸寮忥細"cloumn"
	 */
	public void setAggs(String aggs) {
		this.aggs = aggs;
	}
	public void setAggsDate_histogram(String column,String interval,int min_doc_count,String format) {
		this.aggs = "date_histogram,"+column+","+format+","+min_doc_count+","+interval;
	}
	
	public void aggregations_w (String httpparam,String indexname){
		setFrom(0);
		setSize(0);
		//HTTPSentUtils http = new HTTPSentUtils();
		//String httpparam = getQueryString();
		//String result = http.sendPost("http://" + urlIp + ":" + port + search, httpparam);
		HTTPClient hc = new HTTPClient();
		Map<String, String> EsProperties = new ESQuerynewV1_1().getEsnameAndip();
		hc.sendPost("http://elastic:changeme@" + EsProperties.get("ip") + ":" + EsProperties.get("port") + "/"+indexname, httpparam);
	}
	public void tempBuild (String httpparam,String indexname){
		setFrom(0);
		setSize(0);
		//HTTPSentUtils http = new HTTPSentUtils();
		//String httpparam = getQueryString();
		//String result = http.sendPost("http://" + urlIp + ":" + port + search, httpparam);
		HTTPClient hc = new HTTPClient();
		Map<String, String> EsProperties = new ESQuerynewV1_1().getEsnameAndip();
		hc.sendPost("http://elastic:changeme@" + EsProperties.get("ip") + ":" + EsProperties.get("port") + "/_template/"+indexname, httpparam);
	}
	public void delete(String indexname){
		
		
		//HTTPSentUtils http = new HTTPSentUtils();
		//String httpparam = getQueryString();
		//String result = http.sendPost("http://" + urlIp + ":" + port + search, httpparam);
		HTTPClient hc = new HTTPClient();
		try {
			System.out.println("删除索引"+indexname);
			Map<String, String> EsProperties = new ESQuerynewV1_1().getEsnameAndip();
			hc.doDelete("http://elastic:changeme@" + EsProperties.get("ip") + ":" + EsProperties.get("port") + "/"+indexname);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Map<String, String> getEsnameAndip() {
		// TODO Auto-generated method stub
		Map<String, String> EsProperties = new HashMap<String, String>();
		String PROPERTIES_NAME = System.getProperty("user.dir")+"/conf/es1.properties";
//		String PROPERTIES_NAME = "E:/wangqin/properties/es.properties";
		String ES_IP = null;
		String ES_cluster_name = null;
		String ES_port = null;
		FileInputStream in = null;
		try {
			Properties properties = new Properties();
			in = new FileInputStream(PROPERTIES_NAME);
			properties.load(in);
			ES_IP = properties.getProperty("ESip");
			ES_cluster_name = properties.getProperty("ES_cluster_name");
			ES_port = properties.getProperty("ES_port");
			EsProperties.put("ip", ES_IP);
			EsProperties.put("name", ES_cluster_name);
			EsProperties.put("port", ES_port);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("读取配置文件失败");
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return EsProperties;
	}
}
