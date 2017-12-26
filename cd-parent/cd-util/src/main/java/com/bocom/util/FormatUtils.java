package com.bocom.util;


import java.math.BigDecimal;

/*****
 * <pre>
 * 类名称：FormatUtils
 * 类描述：格式化工具类
 * 创建人：donghongguang
 * 创建时间：2017年3月28日 下午5:03:50
 * </pre>
 * 
 * @version 1.0.0
 */
public abstract class FormatUtils
{
  
    /*****
     * 功能：byte(字节)根据长度转成kb(千字节)和mb(兆字节)和Gb 
     * 创建人：donghongguang
     * 创建时间：2017年4月5日 下午4:03:29
     * @param bytes 
     * @return 
     * @version 1.0.0
     */
    public static String bytes2kb(long bytes) {  
        BigDecimal spaceSize = new BigDecimal(bytes); 
        BigDecimal gigabyte = new BigDecimal(1024 * 1024 * 1024);
        float returnValue = spaceSize.divide(gigabyte, 2, BigDecimal.ROUND_UP)  
                .floatValue();  
        if (returnValue > 1)  
            return (returnValue + "GB"); 
        BigDecimal megabyte = new BigDecimal(1024 * 1024);  
        returnValue = spaceSize.divide(megabyte, 2, BigDecimal.ROUND_UP)  
                .floatValue();  
        if (returnValue > 1)  
            return (returnValue + "MB");  
        BigDecimal kilobyte = new BigDecimal(1024);  
        returnValue = spaceSize.divide(kilobyte, 2, BigDecimal.ROUND_UP)  
                .floatValue();  
        return (returnValue + "KB");  
    }
    
    public static Long kb2bytes(Long size,String unit){
        
        BigDecimal spaceSize = new BigDecimal(size); 
        BigDecimal gigabyte = new BigDecimal(1024 * 1024 * 1024);
        BigDecimal returnValue = spaceSize.multiply(gigabyte);
        if (unit.equals("GB"))  
            return (returnValue.longValue()); 
        BigDecimal megabyte = new BigDecimal(1024 * 1024);
        returnValue = spaceSize.multiply(megabyte);
        if (unit.equals("MB"))  
            return (returnValue.longValue()); 
         
        BigDecimal kilobyte = new BigDecimal(1024);  
        returnValue = spaceSize.multiply(kilobyte);   
        return (returnValue.longValue()); 
    }
    public static Long add(Long a,Long b){
        
        BigDecimal a1 = new BigDecimal(a); 
        BigDecimal b1 = new BigDecimal(b);
        
        return (a1.add(b1).longValue()); 
    }
    public static Long subtract(Long a,Long b){
        
        BigDecimal a1 = new BigDecimal(a); 
        BigDecimal b1 = new BigDecimal(b);
        
        return (a1.subtract(b1).longValue()); 
    }
    
    
    public static void main(String[] args)
    {
//        System.out.println(FormatUtils.bytes2kb(1111));
//        System.out.println(FormatUtils.kb2bytes(55L,"MB"));
        System.out.println(FormatUtils.add(19340L, 760L));
    }
}
