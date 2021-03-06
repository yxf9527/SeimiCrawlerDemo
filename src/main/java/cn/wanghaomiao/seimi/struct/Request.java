/*
   Copyright 2015 Wang Haomiao<et.tw@163.com>

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package cn.wanghaomiao.seimi.struct;

import java.util.HashMap;
import java.util.Map;

import cn.wanghaomiao.seimi.annotation.validate.NotNull;
import cn.wanghaomiao.seimi.http.HttpMethod;
import cn.wanghaomiao.seimi.http.SeimiAgentContentType;

/**
 * 封装一个抓取请求的基本信息体
 * 
 * @author 汪浩淼 [et.tw@163.com] Date: 14-7-7.
 */
public class Request extends CommonObject implements Comparable<Request> {
	private static final long serialVersionUID = 777691230758072729L;

	public Request(String url, String callBack, HttpMethod httpMethod, Map<String, String> params,
			Map<String, String> meta, int maxReqCount) {
		this.url = url;
		this.httpMethod = httpMethod;
		this.params = params;
		this.meta = meta;
		this.callBack = callBack;
		this.maxReqCount = maxReqCount;
	}

	public Request(String url, String callBack, HttpMethod httpMethod, Map<String, String> params,
			Map<String, String> meta) {
		this.url = url;
		this.httpMethod = httpMethod;
		this.params = params;
		this.meta = meta;
		this.callBack = callBack;
	}

	public Request(String url, String callBack) {
		this.url = url;
		this.callBack = callBack;
	}

	public Request(String url, String callBack, int maxReqCount) {
		this.url = url;
		this.callBack = callBack;
		this.maxReqCount = maxReqCount;
	}

	public Request(String url, String cookies, String callBack) {
		this.url = url;
		this.callBack = callBack;
		this.cookies = cookies;
	}

	public static Request build(String url, String callBack, HttpMethod httpMethod, Map<String, String> params,
			Map<String, String> meta) {
		return new Request(url, callBack, httpMethod, params, meta);
	}

	public static Request build(String url, String callBack, HttpMethod httpMethod, Map<String, String> params,
			Map<String, String> meta, int maxReqcount) {
		return new Request(url, callBack, httpMethod, params, meta, maxReqcount);
	}

	public static Request build(String url, String callBack) {
		return new Request(url, callBack);
	}

	public static Request build(String url, String callBack, int maxReqCount) {
		return new Request(url, callBack, maxReqCount);
	}

	/**
	 * 如果有返回值，说明该请求需要设置COOKIE
	 */
	public String cookies() {
		return this.getCookies();
	}

	public Request() {
		super();
	}

	/**
	 * 消息头
	 */
	private Map<String, String> header;
	@NotNull
	private String crawlerName;
	/**
	 * 需要请求的url
	 */
	@NotNull
	private String url;
	/**
	 * 要请求的方法类型 get,post,put...
	 */
	private HttpMethod httpMethod;
	/**
	 * 如果请求需要参数，那么将参数放在这里
	 */
	private Map<String, String> params;
	/**
	 * 这个主要用于存储向下级回调函数传递的一些自定义数据
	 */
	private Map<String, String> meta;
	/**
	 * 回调函数方法名
	 */
	@NotNull
	private String callBack;
	/**
	 * 是否停止的信号，收到该信号的处理线程会退出
	 */
	private boolean stop = false;
	/**
	 * 最大可被重新请求次数
	 */
	private int maxReqCount = 3;

	/**
	 * 用来记录当前请求被执行过的次数
	 */
	private int currentReqCount = 0;

	/**
	 * 用来指定一个请求是否要经过去重机制
	 */
	private boolean skipDuplicateFilter = false;

	/**
	 * 针对该请求是否启用SeimiAgent
	 */
	private boolean useSeimiAgent = false;

	/**
	 * 定义SeimiAgent的渲染时间，单位毫秒
	 */
	private long seimiAgentRenderTime = 0;

	/**
	 * 用于支持在SeimiAgent上执行指定的js脚本
	 */
	private String seimiAgentScript;

	/**
	 * 指定提交到SeimiAgent的请求是否使用cookie
	 */
	private Boolean seimiAgentUseCookie;

	/**
	 * 告诉SeimiAgent将结果渲染成何种格式返回，默认HTML
	 */
	private SeimiAgentContentType seimiAgentContentType = SeimiAgentContentType.HTML;

	/**
	 * cookies
	 */
	private String cookies;
	/**
	 * 权重
	 */
	private int priority;

	public void incrReqCount() {
		this.currentReqCount += 1;
	}

	public String getUrl() {
		return url;
	}

	public Request setUrl(String url) {
		this.url = url;
		return this;
	}

	public HttpMethod getHttpMethod() {
		return httpMethod;
	}

	public Request setHttpMethod(HttpMethod httpMethod) {
		this.httpMethod = httpMethod;
		return this;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public Request setParams(Map<String, String> params) {
		this.params = params;
		return this;
	}

	public Map<String, String> getMeta() {
		// 保证用起来时可定不为空，方便使用
		if (meta == null) {
			meta = new HashMap<>();
		}
		return meta;
	}

	public Request setMeta(Map<String, String> meta) {
		this.meta = meta;
		return this;
	}

	public String getCallBack() {
		return callBack;
	}

	public Request setCallBack(String callBack) {
		this.callBack = callBack;
		return this;
	}

	public boolean isStop() {
		return stop;
	}

	public Request setStop(boolean stop) {
		this.stop = stop;
		return this;
	}

	public int getMaxReqCount() {
		return maxReqCount;
	}

	public Request setMaxReqCount(int maxReqCount) {
		this.maxReqCount = maxReqCount;
		return this;
	}

	public int getCurrentReqCount() {
		return currentReqCount;
	}

	public Request setCurrentReqCount(int currentReqCount) {
		this.currentReqCount = currentReqCount;
		return this;
	}

	public boolean isSkipDuplicateFilter() {
		return skipDuplicateFilter;
	}

	public Request setSkipDuplicateFilter(boolean skipDuplicateFilter) {
		this.skipDuplicateFilter = skipDuplicateFilter;
		return this;
	}

	public String getCrawlerName() {
		return crawlerName;
	}

	public Request setCrawlerName(String crawlerName) {
		this.crawlerName = crawlerName;
		return this;
	}

	public Request useSeimiAgent() {
		this.useSeimiAgent = true;
		return this;
	}

	public boolean isUseSeimiAgent() {
		return useSeimiAgent;
	}

	public long getSeimiAgentRenderTime() {
		return seimiAgentRenderTime;
	}

	public Request setSeimiAgentRenderTime(long seimiAgentRenderTime) {
		this.seimiAgentRenderTime = seimiAgentRenderTime;
		return this;
	}

	public String getSeimiAgentScript() {
		return seimiAgentScript;
	}

	public Request setSeimiAgentScript(String seimiAgentScript) {
		this.seimiAgentScript = seimiAgentScript;
		return this;
	}

	public Boolean isSeimiAgentUseCookie() {
		return seimiAgentUseCookie;
	}

	public Request setSeimiAgentUseCookie(Boolean seimiAgentUseCookie) {
		this.seimiAgentUseCookie = seimiAgentUseCookie;
		return this;
	}

	public SeimiAgentContentType getSeimiAgentContentType() {
		return seimiAgentContentType;
	}

	public Request setSeimiAgentContentType(SeimiAgentContentType seimiAgentContentType) {
		this.seimiAgentContentType = seimiAgentContentType;
		return this;
	}

	public String getCookies() {
		return cookies;
	}

	public void setCookies(String cookies) {
		this.cookies = cookies;
	}

	public Map<String, String> getHeader() {
		return header;
	}

	public Request setHeader(Map<String, String> header) {
		this.header = header;
		return this;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	/**
	 * 数字大，优先级高
	 * */
	@Override
	public int compareTo(Request o) {
		return this.priority < o.priority ? 1 : (this.priority > o.priority ? -1 : 0);
	}
}
