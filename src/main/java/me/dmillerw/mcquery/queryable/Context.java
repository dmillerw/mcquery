package me.dmillerw.mcquery.queryable;

import com.google.common.collect.Maps;
import fi.iki.elonen.NanoHTTPD;

import java.util.Map;

public class Context {

    private final NanoHTTPD.IHTTPSession httpSession;

    private final Map<String, String> pathParams = Maps.newHashMap();

    public Context(NanoHTTPD.IHTTPSession httpSession) {
        this.httpSession = httpSession;
    }

    public void setPathParams(Map<String, String> pathParams) {
        this.pathParams.putAll(pathParams);
    }

    public NanoHTTPD.IHTTPSession getHttpSession() {
        return httpSession;
    }

    public String getPathParam(String key) {
        return pathParams.get(key);
    }

    public void json(Object response) {

    }

    public void fixedLength(String response) {

    }
}
