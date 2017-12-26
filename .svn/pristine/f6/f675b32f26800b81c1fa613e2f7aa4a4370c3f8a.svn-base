package com.bocom.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.CharsetUtils;
import org.apache.http.util.EntityUtils;

public class Test
{
    /** 
     * 文件上传 
     * @param is 
     * @param fileName 
     * @param url 
     * @return 
     */  
    public static String uploadFile(InputStream is ,String fileName ,String url){  
        String result = null;  
        HttpClient httpclient = new DefaultHttpClient();  
        HttpPost httppost = new HttpPost(url);  
        //防止文件名乱码  
        InputStreamBody fileis = new InputStreamBody(is,ContentType.create("text/plain", Consts.UTF_8),fileName);  
        HttpEntity reqEntity = null;  
        HttpResponse responses = null;  
        try {  
            //BROWSER_COMPATIBLE 设置浏览器为兼容模式  随便设置编码防止乱码  
            StringBody comment = new StringBody("A binary file of some kind", ContentType.TEXT_PLAIN);
            reqEntity = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE)  
                    .addPart("filedata", fileis).addPart("uploadInfo", comment).setCharset(CharsetUtils.get("utf-8")).build();  
            httppost.setEntity(reqEntity);  
            // 如果需要登陆加上sessionId  
            httppost.addHeader(new BasicHeader("Cookie", "sessionId=" + "humulan"));  
            responses = httpclient.execute(httppost);  
            HttpEntity entity = responses.getEntity();  
            if(entity != null){  
                result = EntityUtils.toString(entity, Charset.forName("utf-8"));  
            }  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        } catch (ClientProtocolException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
          
        return result;  
    }  
    
    /** 
     * 文件下载 
     * @param url 
     * @param fileName 
     * @return 
     */  
    public static InputStream fileDownload(String url,String fileName){  
//        CloseableHttpClient  httpclient = new HttpUtils().buildHttpClient();
        HttpClient httpclient = new DefaultHttpClient(); 
        HttpPost httppost = new HttpPost(url);  
        StringEntity sEntity = new StringEntity(fileName, Charset.forName("utf-8"));  
//      BasicNameValuePair basicNameValuePair = new BasicNameValuePair("fileName",fileName);  
//      List<NameValuePair> list = new ArrayList<NameValuePair>();   
//      list.add(basicNameValuePair);  
//      UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(list, Charset.forName("utf-8"));  
//      httppost.setEntity(formEntity);  
        httppost.setEntity(sEntity);  
  
        httppost.addHeader(new BasicHeader("Cookie", "sessionId=" + ""));  
        httppost.addHeader("Content-Type", "application/xml;charset=utf-8");  
        try {  
//            CloseableHttpResponse responses = httpclient.execute(httppost); 
            HttpResponse responses = httpclient.execute(httppost);
            HttpEntity entity = responses.getEntity();  
            InputStream content = entity.getContent();  
            return content;  
        } catch (ClientProtocolException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
          
        return null;  
    } 
    
    private static void byte2File(byte[] buf, String filePath, String fileName)
    {
        File dir = new File(filePath);
        if (!dir.exists())
        {
            dir.mkdirs();
        }
        File file = new File(filePath + File.separator + fileName);
        try (FileOutputStream fos = new FileOutputStream(file);)
        {
            fos.write(buf);
        }
        catch (Exception e)
        {
            
        }
    }
    
    public static void main(String[] args)
    {
        try
        {
//            File file = new File("C:\\regset.ini");  
//            FileInputStream fin = new FileInputStream(file); 
//            uploadFile(fin,"regset.ini","http://localhost:8080/wrm-web/api/wrm/uploadWidgetByUser1");
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
