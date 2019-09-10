package com.test;

import java.io.*;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Iterator;

public class TestApp {
    // https://www.databasetestdata.com/
    public static void main(String[] args) {
        StringBuffer response = new StringBuffer("[");
        JSONParser parser = new JSONParser();

            try (Reader reader = new FileReader("{filepath}\\testdata.json")) {
                StringBuilder keyStringBuilder = new StringBuilder("(\"");
                StringBuilder entries = new StringBuilder("{\"entities\":[");

              StringBuilder finalString = new StringBuilder();



                JSONObject jsonObject = (JSONObject) parser.parse(reader);
                Iterator<Map.Entry> itr1 = jsonObject.entrySet().iterator();
                JSONArray mapPersonalDetail = (JSONArray) jsonObject.get("pageInfo");
                Iterator itr2 = mapPersonalDetail.iterator();

                StringBuilder data = new StringBuilder();
                while (itr2.hasNext()) {
                    itr1 = ((Map) itr2.next()).entrySet().iterator();
                    //StringBuilder singleData = new StringBuilder("(");
                    StringBuilder dataEntities = new StringBuilder("{\"entities\":[");
                    int length = 0;
                    StringBuilder dataValue = new StringBuilder();
                    while (itr1.hasNext()) {
                        Map.Entry pair = itr1.next();
                        String key = pair.getKey().toString();
                        System.out.print(pair.getValue() + " ");

                        dataValue.append(pair.getValue().toString());
                        dataValue.append(" ");
                        dataEntities.append(createEntity(key, pair.getValue().toString(), length));
                        length = dataValue.length();
                    }

                    keyStringBuilder.append("\",");
                    entries.append("]},)");
                    data.append("(\"");
                    data.append(dataValue);
                    data.append("\",");
                    data.append(dataEntities);
                    data.append("]} ");
                    data.append("),\n");
                }

                response.append(data);
                response.append(")");
                BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\jsonpar\\test1.txt", true));
                writer.append(response.toString());
                writer.append("\n");
                writer.close();
                response.append("]");

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
    }

    static String createEntity(String key, String value, int length) {
        return "(" + length + "," + ((value.length())+length) + ",\"" + key + "\"),";
    }
}
