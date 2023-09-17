package com.inovance.dam.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Set;

public class HttpUtils {

    private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * 支持的请求类型
     */
    private enum HttpMethod {
        POST("POST"), DELETE("DELETE"), GET("GET"), PUT("PUT"), HEAD("HEAD");

        private final String note;

        HttpMethod(String note) {
            this.note = note;
        }
    }

    private static String invokeUrl(String url, Map<String, Object> params, Map<String, String> headers, int connectTimeout, int readTimeout, String encoding, HttpMethod method) {
        // 构造请求参数字符串
        StringBuilder paramsStr;
        if (params != null && method != HttpMethod.POST) {
            paramsStr = new StringBuilder();
            for (String key : params.keySet()) {
                if (paramsStr.length() != 0) paramsStr.append("&");
                Object value = params.get(key);
                paramsStr.append(key).append("=").append(value != null ? String.valueOf(value) : "");
            }
            // 只有POST方法才能通过OutputStream(即form的形式)提交参数，其他使用URL传参
            url += "?" + paramsStr;
        }

        URL uUrl;
        HttpURLConnection conn = null;
        BufferedWriter out = null;
        BufferedReader in = null;
        try {
            // 创建和初始化连接
            uUrl = new URL(url);
            conn = (HttpURLConnection) uUrl.openConnection();
            conn.setRequestProperty("content-type", "application/json");
            conn.setRequestMethod(method.toString());
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 设置连接超时时间
            conn.setConnectTimeout(connectTimeout);
            // 设置读取超时时间
            conn.setReadTimeout(readTimeout);
            // 指定请求header参数
            if (headers != null && !headers.isEmpty()) {
                Set<String> headerSet = headers.keySet();
                for (String key : headerSet) {
                    conn.setRequestProperty(key, headers.get(key));
                }
            }
            //如果是POST请求并且有参数
            if (params != null && method == HttpMethod.POST) {
                // 发送请求参数
                out = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), encoding));
                out.write(JsonUtil.objectToJson(params));
                out.flush();
            }

            // 接收返回结果
            StringBuilder result = new StringBuilder();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), encoding));
            String line = "";
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        } catch (Exception e) {
            log.error("调用接口[" + url + "]失败！请求URL：" + url + "，参数：" + params, e);
            // 处理错误流，提高http连接被重用的几率
            try {
                InputStream es = null;
                if (conn != null) {
                    es = conn.getErrorStream();
                }
                if (es != null) {
//                var buf = new byte[100];
//                while (es.read(buf) > 0) {}
                    es.close();
                }
            } catch (Exception e1) {
                log.error("发生http请求异常！", e1);
            }
            throw new RuntimeException(e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
                log.error("发生http请求异常！", e);
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                log.error("发生http请求异常！", e);
            }
            // 关闭连接
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    /**
     *
     * @param url               资源路径（如果url中已经包含参数，则params应该为null）
     * @param params            参数
     * @param headers           请求头参数
     * @param connectTimeout    链接超时时间
     * @param readTimeout       返回超时时间
     * @return
     */
    public static String post(String url, Map<String, Object> params, Map<String, String> headers , int connectTimeout, int readTimeout) {
        return invokeUrl(url, params, headers, connectTimeout, readTimeout, "utf-8", HttpMethod.POST);
    }

    /**
     *
     * @param url               资源路径（如果url中已经包含参数，则params应该为null）
     * @param params            参数
     * @param headers           请求头参数
     * @param readTimeout       返回超时时间
     * @return
     */
    public static String post(String url, Map<String, Object> params, Map<String, String> headers , int readTimeout) {
        return post(url, params, headers, 3000, readTimeout);
    }

    /**
     * POST方法提交Http请求，语义为“增加” <br/>
     * 注意：Http方法中只有POST方法才能使用body来提交内容
     *
     * @param url     资源路径（如果url中已经包含参数，则params应该为null）
     * @param params  参数
     * @param headers 请求头参数
     * @return
     */
    public static String post(String url, Map<String, Object> params, Map<String, String> headers) {
        return post(url, params, headers, 3000, 3000);
    }

    /**
     * POST方法提交Http请求，语义为“增加” <br/>
     * 注意：Http方法中只有POST方法才能使用body来提交内容
     *
     * @param url     资源路径（如果url中已经包含参数，则params应该为null）
     * @param params  参数
     * @return
     */
    public static String post(String url, Map<String, Object> params) {
        return post(url, params, null);
    }

    /**
     * POST方法提交Http请求，语义为“增加” <br/>
     * 注意：Http方法中只有POST方法才能使用body来提交内容
     *
     * @param url     资源路径（如果url中已经包含参数，则params应该为null）
     * @return
     */
    public static String post(String url) {
        return post(url, null, null);
    }

    /**
     * GET方法提交Http请求，语义为“查询”
     * 注意：Http方法中只有POST方法才能使用body来提交内容
     *
     * @param url     资源路径（如果url中已经包含参数，则params应该为null）
     * @param params  参数
     * @param headers 请求头参数
     * @return
     */
    public static String get(String url, Map<String, Object> params, Map<String, String> headers) {
        return get(url, params, headers, 3000);
    }

    /**
     * GET方法提交Http请求，语义为“查询”
     * 注意：Http方法中只有POST方法才能使用body来提交内容
     *
     * @param url     资源路径（如果url中已经包含参数，则params应该为null）
     * @param params  参数
     * @param headers 请求头参数
     * @return
     */
    public static String get(String url, Map<String, Object> params, Map<String, String> headers, int readTimeout) {
        return invokeUrl(url, params, headers, 3000, readTimeout, "utf-8", HttpMethod.GET);
    }

    /**
     * PUT方法提交Http请求，语义为“更改” <br/>
     * 注意：PUT方法也是使用url提交参数内容而非body，所以参数最大长度收到服务器端实现的限制，Resin大概是8K
     *
     * @param url            资源路径（如果url中已经包含参数，则params应该为null）
     * @param params         参数
     * @param connectTimeout 连接超时时间（单位为ms）
     * @param readTimeout    读取超时时间（单位为ms）
     * @param charset        字符集（一般该为“utf-8”）
     * @return
     */
    public static String put(String url, Map<String, Object> params, int connectTimeout, int readTimeout, String charset) {
        return invokeUrl(url, params, null, connectTimeout, readTimeout, charset, HttpMethod.PUT);
    }

    /**
     * PUT方法提交Http请求，语义为“更改” <br/>
     * 注意：PUT方法也是使用url提交参数内容而非body，所以参数最大长度收到服务器端实现的限制，Resin大概是8K
     *
     * @param url            资源路径（如果url中已经包含参数，则params应该为null）
     * @param params         参数
     * @param headers        请求头参数
     * @param connectTimeout 连接超时时间（单位为ms）
     * @param readTimeout    读取超时时间（单位为ms）
     * @param charset        字符集（一般该为“utf-8”）
     * @return
     */
    public static String put(String url, Map<String, Object> params, Map<String, String> headers, int connectTimeout, int readTimeout,
                             String charset) {
        return invokeUrl(url, params, headers, connectTimeout, readTimeout, charset, HttpMethod.PUT);
    }

    /**
     * DELETE方法提交Http请求，语义为“删除”
     *
     * @param url            资源路径（如果url中已经包含参数，则params应该为null）
     * @param params         参数
     * @param connectTimeout 连接超时时间（单位为ms）
     * @param readTimeout    读取超时时间（单位为ms）
     * @param charset        字符集（一般该为“utf-8”）
     * @return
     */
    public static String delete(String url, Map<String, Object> params, int connectTimeout, int readTimeout, String charset) {
        return invokeUrl(url, params, null, connectTimeout, readTimeout, charset, HttpMethod.DELETE);
    }

    /**
     * DELETE方法提交Http请求，语义为“删除”
     *
     * @param url            资源路径（如果url中已经包含参数，则params应该为null）
     * @param params         参数
     * @param headers        请求头参数
     * @param connectTimeout 连接超时时间（单位为ms）
     * @param readTimeout    读取超时时间（单位为ms）
     * @param charset        字符集（一般该为“utf-8”）
     * @return
     */
    public static String delete(String url, Map<String, Object> params, Map<String, String> headers, int connectTimeout,
                                int readTimeout, String charset) {
        return invokeUrl(url, params, headers, connectTimeout, readTimeout, charset, HttpMethod.DELETE);
    }

    /**
     * HEAD方法提交Http请求，语义同GET方法 <br/>
     * 跟GET方法不同的是，用该方法请求，服务端不返回message body只返回头信息，能节省带宽
     *
     * @param url            资源路径（如果url中已经包含参数，则params应该为null）
     * @param params         参数
     * @param connectTimeout 连接超时时间（单位为ms）
     * @param readTimeout    读取超时时间（单位为ms）
     * @param charset        字符集（一般该为“utf-8”）
     * @return
     */
    public static String head(String url, Map<String, Object> params, int connectTimeout, int readTimeout, String charset) {
        return invokeUrl(url, params, null, connectTimeout, readTimeout, charset, HttpMethod.HEAD);
    }

    /**
     * HEAD方法提交Http请求，语义同GET方法 <br/>
     * 跟GET方法不同的是，用该方法请求，服务端不返回message body只返回头信息，能节省带宽
     *
     * @param url            资源路径（如果url中已经包含参数，则params应该为null）
     * @param params         参数
     * @param headers        请求头参数
     * @param connectTimeout 连接超时时间（单位为ms）
     * @param readTimeout    读取超时时间（单位为ms）
     * @param charset        字符集（一般该为“utf-8”）
     * @return
     */
    public static String head(String url, Map<String, Object> params, Map<String, String> headers, int connectTimeout, int readTimeout, String charset) {
        return invokeUrl(url, params, headers, connectTimeout, readTimeout, charset, HttpMethod.HEAD);
    }

}
