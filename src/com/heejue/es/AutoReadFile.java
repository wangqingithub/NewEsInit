package com.heejue.es;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
public class AutoReadFile {
	private static ArrayList<String> listname = new ArrayList<String>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String endwith = ".json";
		readAllFile(System.getProperty("user.dir")+"/conf");
//		readAllFile("E:/wangqin/properties");
		if(args[0].equals("1")){
			for(int i=0;i<listname.size();i++){
				if(listname.get(i).endsWith(endwith)){
					if(listname.get(i).endsWith("template.json")){
						String iname = listname.get(i).substring(0,listname.get(i).length()-14);
						String filename = listname.get(i).substring(0,listname.get(i).length()-5);
						String inameS = iname + "_*";
						System.out.println(inameS);
						new ESQuerynewV1_1().delete(inameS);
						String build = LoadJson(filename);
						new ESQuerynewV1_1().tempBuild(build, "template_"+iname);
					}else{
						String iname = listname.get(i).substring(0,listname.get(i).length()-5);
						System.out.println(iname);
						new ESQuerynewV1_1().delete(iname);
						String build = LoadJson(iname);
						new ESQuerynewV1_1().aggregations_w(build, iname);
					}
				}
			}
		}else{
			if(args[1].endsWith("_*")){
				new ESQuerynewV1_1().delete(args[1]);
				String iname = args[1].substring(0, args[1].length()-2);
				String filename = iname + "_template";
				String build = LoadJson(filename);
				new ESQuerynewV1_1().tempBuild(build, "template_"+iname);
			}else{
				new ESQuerynewV1_1().delete(args[1]);
				String build = LoadJson(args[1]);
				new ESQuerynewV1_1().aggregations_w(build, args[1]);
			}
			
		}
		
	}
	public static void readAllFile(String filepath) {
		
		File file= new File(filepath);
		if(!file.isDirectory()){
			listname.add(file.getName());
			
		}else if(file.isDirectory()){
			System.out.println("文件");
			String[] filelist=file.list();
			for(int i = 0;i<filelist.length;i++){
				File readfile = new File(filepath);
				if (!readfile.isDirectory()) {
                    listname.add(readfile.getName());
				} else if (readfile.isDirectory()) {
					readAllFile(filepath + "\\" + filelist[i]);//递归
				}
			}
			
		}
		
		
	}
	public static String LoadJson(String filename) {
		String param = "";
		try {
			String encoding = "UTF-8";
//			File file = new File("E:/wangqin/properties/"+filename+".json");
			File file = new File(System.getProperty("user.dir")+"/conf/"+filename+".json");
			
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;

				while ((lineTxt = bufferedReader.readLine()) != null) {
					param = param + lineTxt;
				}
				read.close();
				return param;
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return param;

	}
}
