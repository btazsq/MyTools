package com.example.testtools.network.requestion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BTAJson {
    private HashMap<String, String> hashMap = new HashMap<String, String>();

    private String json;

    private BTAJson(){}

    public String getString(String key){
        return this.hashMap.get(key);
    }

    private BTAJson setJson(String json){
        this.json = json;
        return this;
    }

    public static BTAJson getBTAJson(String str){
        byte[] json = str.getBytes();
        int i = 0,flag = 0;
        while (json[i] == ' ')i++;
        if (json[i] != '{')return null;
        BTAJson back = new BTAJson();
        back.setJson(str);
        int start,j;
        do {
            i++;
            if (json[i] == '}'&&flag <= 0)break;
            if (json[i] == '{'||json[i] == '[')flag++;
            if (json[i] == '}'||json[i] == ']')flag--;
            if (json[i] == '"'&&flag <= 0){
                start = i++;
                while (json[i] != '"'){
                    if (json[i] == '\\' )i++;
                    i++;
                }
                byte[] string1 = new byte[i-start-1];
                start++;
                for (j = 0;j<string1.length;j++){
                    string1[j] = json[start+j];
                }
                i++;
                while (json[i] != '"'){
                    if (json[i] == '\\' )i++;
                    i++;
                }
                start = i++;
                int deep = 0;
                do {
                    if (json[i] == '{'||json[i] == '[')deep++;
                    if (json[i] == '}'||json[i] == ']')deep--;
                    if (json[i] == '\\' )i++;
                    i++;
                }while (deep > 0||json[i] != '"');
                byte[] string2 = new byte[i-start-1];
                start++;
                for (j = 0;j<string2.length;j++){
                    string2[j] = json[start+j];
                }
                back.hashMap.put(new String(string1),new String(string2));
            }
        }while (i < json.length);
        return back;
    }

    public static List<BTAJson> getBTAJsonArray(String str){
        byte[] json = str.getBytes();
        int i = 0;
        while (json[i] == ' ')i++;
        if (json[i] != '[')return null;
        int start,j;
        List<BTAJson> list = new ArrayList<BTAJson>();
        do {
            i++;
            if (json[i] == ']')break;
            if (json[i] == '{'){
                start = i++;
                int flag = 0;
                do {
                    if (json[i] == '}'&&flag <= 0){
                        byte[] string = new byte[i-start+1];
                        for (j=0;j<string.length;j++)string[j] = json[start+j];
                        if(string[0] == '[')string[0] = ' ';
                        list.add(getBTAJson(new String(string)));
                        break;
                    }
                    if (json[i] == '\\')i++;
                    if (json[i] == '{'||json[i] == '[')flag++;
                    if (json[i] == '}'||json[i] == ']')flag--;
                    i++;
                }while (true);
            }
        }while (i < json.length);
        return list;
    }

    public String toString(){
        return this.json;
    }

}
