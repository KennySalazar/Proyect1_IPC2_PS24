package com.example.proyecto1_ipc2.utils;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

public class JsonUtils<T> {
    private final Gson gson;

    public JsonUtils(){
        gson = new Gson();
    }
    public void enviarComoJSON(HttpServletResponse response, Object objeto) throws IOException {
        response.setContentType("application/json");
        String res = gson.toJson(objeto); //pasamos el objeto a formato gson

        var out = response.getWriter();
        out.println(res);//se lo mostramos al usuario
    }

    public T leerDeJSON(HttpServletRequest request, Class<T> classT) throws IOException{//de JSON a una clase (classT)
        StringBuilder bufer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while((line=reader.readLine())!= null) bufer.append(line);

        String payload = bufer.toString();
        return  gson.fromJson(payload, classT);
    }
}
