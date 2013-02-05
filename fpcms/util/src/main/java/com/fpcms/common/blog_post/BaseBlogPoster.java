package com.fpcms.common.blog_post;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.fpcms.common.blog_post.Blog;
import com.fpcms.common.blog_post.BlogPoster;
import com.fpcms.common.blog_post.BlogPosterHelper;

public abstract class BaseBlogPoster implements BlogPoster,InitializingBean{

	static Logger logger = LoggerFactory.getLogger(BaseBlogPoster.class);

	private BlogPosterHelper helper = new BlogPosterHelper();
	private String loginUrl = null;
	private String postNewBlogUrl = null;
	private String postNewBlogContentType = null;
	private HttpClient client = new HttpClient();
	{
		client.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
	}
	
	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = StringUtils.trim(loginUrl);
	}

	public String getPostNewBlogUrl() {
		return postNewBlogUrl;
	}

	public void setPostNewBlogUrl(String postNewBlogUrl) {
		this.postNewBlogUrl = StringUtils.trim(postNewBlogUrl);
	}

	@Override
	public void postBlog(Blog blog) {
		try {
			login(blog.getUsername(),blog.getPassword());
			postNewBlog(blog.getTitle(),blog.getContent(),blog.getMetaDescription(),blog.getExt());
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	protected void login(String username,String password) throws Exception,IOException {
		Assert.hasText(getLoginUrl(),"loginUrl must be not empty");
		PostMethod post = helper.newPostMethod(getLoginUrl());
		try {
			logger.info("start login with username:"+username+" loginUrl:"+getLoginUrl());
			setLoginRequestBody(username, password, post);
			client.executeMethod(post);
			Cookie[] cookies = client.getState().getCookies();
			InputStream stream = post.getResponseBodyAsStream();
			String responseString = IOUtils.toString(stream,"UTF-8");
			verifyHttpStatusCode(post.getStatusCode(),"login error,username:" + username);
			Assert.isTrue(verifyLoginResult(responseString),"login error,username:"+username + " response:"+responseString);
			logger.info("login_success with username:"+username+" loginUrl:"+getLoginUrl());
		}finally {
			post.releaseConnection();
		}
	}

	public static void verifyHttpStatusCode(int statusCode,String attachErrorMessage) {
		if(statusCode >= 200 && statusCode < 400) {
			return;
		}
		throw new IllegalStateException("error http statusCode:" + statusCode + "; " +attachErrorMessage);
	}

	protected void postNewBlog(String title,String content,String metaDescription,Map<String,String> ext) throws Exception,IOException, HttpException {
		Assert.hasText(getPostNewBlogUrl(),"postNewBlogUrl must be not empty");
		PostMethod post = helper.newPostMethod(getPostNewBlogUrl());
		if(StringUtils.isNotBlank(getPostNewBlogContentType())) {
			post.setRequestHeader("Content-Type",getPostNewBlogContentType());
		}
		
		try {
			logger.info("start postNewBlog:"+title+" url:"+getPostNewBlogUrl());
			setPostNewBlogRequestBody(title, content,metaDescription, ext, post);
			client.executeMethod(post);
			InputStream stream = post.getResponseBodyAsStream();
			String responseString = IOUtils.toString(stream,"UTF-8");
			stream.close();
			verifyHttpStatusCode(post.getStatusCode(),"post new blog error,blog title:"+title);
			Assert.isTrue(verifyPostNewBlogResult(responseString),"post blog error,title:"+title+" response:"+responseString);
			logger.info("postNewBlog_success:"+title+" url:"+getPostNewBlogUrl());
		}finally {
			post.releaseConnection();
		}
	}

	public String getPostNewBlogContentType() {
		return postNewBlogContentType;
	}

	public void setPostNewBlogContentType(String postNewBlogContentType) {
		this.postNewBlogContentType = postNewBlogContentType;
	}

	protected boolean verifyLoginResult(String responseString) {
		return true;
	}

	protected abstract void setLoginRequestBody(String username, String password,PostMethod post) throws Exception;
	
	protected boolean verifyPostNewBlogResult(String responseString) {
		return true;
	}

	protected abstract void setPostNewBlogRequestBody(String title, String content,String metaDescription, Map<String, String> ext, PostMethod post)  throws Exception;

	public static String urlEncode(String content)
		throws UnsupportedEncodingException {
		if(StringUtils.isBlank(content)) {
			return null;
		}
		return URLEncoder.encode(content,"UTF-8");
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.hasText(getLoginUrl(),"loginUrl must be not empty");
		Assert.hasText(getPostNewBlogUrl(),"postNewBlogUrl must be not empty");		
		
		logger.info("loginUrl:"+loginUrl);
		logger.info("postNewBlogUrl:"+postNewBlogUrl);
	}
	
}