package com.ew.school_epidemic.utils;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;

import java.util.HashMap;
import java.util.Map;

public class RiskAreaUtil {

    public  String url = "https://bmfw.www.gov.cn/bjww/interface/interfaceJson";
    public  String STATE_COUNCIL_TOEKN = "23y0ufFl5YxIyGrI8hWRUZmKkvtSjLQA";
    public  String STATE_COUNCIL_NONCE = "123456789abcdefg";
    public  String signature = "fTN2pfuisxTavbTuYVSsNJHetwq5bJvCQkjjtiLM2dCratiA";

    public  String x_wif_nonce = "QkjjtiLM2dCratiA";
    public  String x_wif_paasid = "smt-application";

    public  String appId = "NcApplication";
    public  String paasHeader = "zdww";
    public  String nonceHeader = "123456789abcdefg";
    public  String key = "3C502C97ABDA40D0A60FBEE50FAAD1DA";

    public  String get_risk(){
        long timestamp = get_timestamp();
        Map<String,String> headers = new HashMap<>();
        headers.put("x-wif-nonce", x_wif_nonce);
        headers.put("x-wif-signature", get_x_wif_signature(timestamp));
        headers.put("x-wif-timestamp", timestamp+"");
        headers.put("x-wif-paasid", x_wif_paasid);
        headers.put("Accept", "*/*");

        Map<String,Object> data = new HashMap<String,Object>();
        data.put("appId",appId);
        data.put("paasHeader",paasHeader);
        data.put("timestampHeader",timestamp);
        data.put("nonceHeader",nonceHeader);
        data.put("signatureHeader",crypo_sha256(timestamp));
        data.put("key",key);
//        System.out.println(headers);
//        System.out.println(data);
        String result = HttpUtil.createPost(url)
                .headerMap(headers,false)
                .body(JSONUtil.toJsonStr(data)).execute().body();
        return result;
    }

    public  long get_timestamp(){
        long timestamp = System.currentTimeMillis()/1000;
        return timestamp;
    }
    public  String crypo_sha256(long timestamp){
        String str = timestamp + STATE_COUNCIL_TOEKN + STATE_COUNCIL_NONCE + timestamp;
        String r = SHA256Util.getSHA256String(str).toUpperCase();
        return r;
    }
    public  String get_x_wif_signature(long timestamp) {
        String str = (timestamp + signature +timestamp);
        String r = SHA256Util.getSHA256String(str).toUpperCase();
        return r;
    }


}

