package com.rxkj.util;

import com.rxkj.enums.KeywordEnum;
import com.rxkj.message.MessageA;
import io.swagger.models.auth.In;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class AlexUtil {

    public static String toStringHex1(String s) {
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(
                        i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "utf-8");// UTF-16le:Not
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }

    /**
     * 字符串转换为16进制字符串
     *
     * @param s
     * @return
     */
    public static String stringToHexString(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }

    /**
     * 16进制字符串转换为字符串
     *
     * @param s
     * @return
     */
    public static String hexStringToString(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        s = s.replace(" ", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(
                        s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "utf-8");
            new String();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }

    /**
     * 16进制表示的字符串转换为字节数组
     *
     * @param s 16进制表示的字符串
     * @return byte[] 字节数组
     */
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] b = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个字节
            b[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character
                    .digit(s.charAt(i + 1), 16));
        }
        return b;
    }

    public static byte[] hexStringToByteArray2(String hexString) {
        int len = hexString.length();
        byte[] byteArray = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            int firstDigit = Character.digit(hexString.charAt(i), 16);
            int secondDigit = Character.digit(hexString.charAt(i + 1), 16);
            int byteValue = (firstDigit << 4) + secondDigit;
            byteArray[i / 2] = (byte) byteValue;
        }
        return byteArray;
    }

    public static byte[] hexStringToByteArray3(String hexString) {
        hexString = hexString.replaceAll("\\s+", ""); // Remove any whitespace

        // Check if the length of the input string is odd
        if (hexString.length() % 2 != 0) {
            hexString = "0" + hexString; // Prepend a '0' if necessary
        }

        byte[] byteArray = new byte[hexString.length() / 2];

        for (int i = 0; i < hexString.length(); i += 2) {
            String hex = hexString.substring(i, i + 2);
            byteArray[i / 2] = (byte) Integer.parseInt(hex, 16);
        }

        return byteArray;
    }


    /**
     * byte数组转16进制字符串
     *
     * @param bArray
     * @return
     */
    public static final String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    public static String alexhandle(String msg) {
        int i = msg.indexOf("data=");
        String s = msg.substring(i).replace("data=", "");
        String s1 = toStringHex1(s);
        return s1;
    }

    public static byte[] checksum(MessageA msg, byte[] key) {


        ByteBuffer buffer = ByteBuffer.allocate(msg.getLength());
        //重新将msg转换为byte类型
        buffer.put(AlexUtil.hexStringToByteArray(KeywordEnum.CHANNEL_HEAD.value));
        buffer.put(AlexUtil.hexStringToByteArray3(Integer.toHexString(msg.getLength())));
        //计算密钥时密钥2字节为0x00,计算完成后用结果赋值
        byte[] checkSum = {(byte) 0x00, (byte) 0x00};
        buffer.put(checkSum);

        buffer.put(AlexUtil.hexStringToByteArray(msg.getCommand()));
        buffer.put(AlexUtil.hexStringToByteArray(msg.getData()));

        byte[] pktData = buffer.array();
        pktData[3] = pktData[4] = 0; //计算密钥时密钥2字节为0x00,计算完成后用结果赋值
        byte[] sum = new byte[]{0, 0, 0, 0};

        for (int j = 0; j < 4; j++) {
            for (int i = j; i < pktData.length; i += 4) {
                sum[j] += pktData[i];
            }
        }
        byte[] checksum = new byte[2];
        checksum[0] = pktData[3] = (byte) (sum[0] + sum[1] + key[1] + key[0]);
        checksum[1] = pktData[4] = (byte) (sum[2] + sum[3] + key[3] + key[2] + pktData[3]);
        return checksum;
    }

    /**
     * 截取字符数组的字串
     *
     * @param bytes
     * @param start
     * @param end
     * @return
     */
    public static byte[] substring(byte[] bytes, int start, int end) {
        int length = end - start;

        byte[] msg = new byte[length];
        for (int i = 0; i < length; i++) {
            msg[i] = bytes[start - 1];
        }
        return msg;
    }
    /***
     * 根据传入的sampleKey计算plc和sampleId的映射关系
     */
    public  static Map<Integer,Integer> sampleMap(int sampleKey){
        Map<Integer,Integer> sampleMap = new HashMap<Integer,Integer>();
        for (int i = 1; i <= 8; i++) {
            sampleMap.put(i,i+(sampleKey-1)*8);
        }
        return sampleMap;
    }
    /**
     * 使用sample Id计算sampleKey
     * */
    public  static Integer getSampleKey(int sampleId){
        return (sampleId-1)/8+1;
    }
    /**
     * 使用sample id计算plc id
     * */
    public  static Integer getPlcId(int sampleId){
        return sampleId-(AlexUtil.getSampleKey(sampleId)-1)*8;
    }
    public static void main(String[] args) {
//        String s = stringToHexString("01050000FF008C3A");
//        System.out.println(s);
        byte[] bytes = {(byte) 0xAA, (byte) 0xAA, (byte) 0x08, (byte) 0x00, (byte) 0xFF};
        System.out.println(AlexUtil.bytesToHexString(substring(bytes, 3, 4)));
    }
//    /**
//     * 通过map value获取key
//     * */
//    public static ArrayList<String> getKey(Map map, Integer value){
//        Set set = map.entrySet(); //通过entrySet()方法把map中的每个键值对变成对应成Set集合中的一个对象
//        Iterator<Map.Entry<Integer, String>> iterator = set.iterator();
//        ArrayList<String> arrayList = new ArrayList();
//        while(iterator.hasNext()){
//            //Map.Entry是一种类型，指向map中的一个键值对组成的对象
//            Map.Entry<Integer, String> entry = iterator.next();
//            if(entry.getValue().equals(value)){
//                arrayList.add(entry.getKey());
//            }
//        }
//        return arrayList;
//    }

}
