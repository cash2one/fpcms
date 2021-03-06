package com.fpcms.common.webcrawler.htmlparser;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang.StringUtils;

public class HtmlPage {

	private String title;
	private String keywords;
	private String description;
	private String content;
	
	private Anchor anchor;
	
	private String sourceLang;
	
	private String tags;
	
	
	public HtmlPage() {
	}
	
	public HtmlPage(String title, String content) {
		super();
		this.title = title;
		this.content = content;
	}

	public Anchor getAnchor() {
		return anchor;
	}

	public void setAnchor(Anchor anchor) {
		this.anchor = anchor;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = StringUtils.trim(title);
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = StringUtils.trim(keywords);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = StringUtils.trim(description);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = StringUtils.trim(content);
	}
	
	public String getSourceLang() {
		return sourceLang;
	}

	public void setSourceLang(String sourceLang) {
		this.sourceLang = sourceLang;
	}
	
	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}



	public static class Anchor {
		private String href;
		private String text;
		private String title;

		
		public Anchor() {
		}
		
		public Anchor(String href) {
			super();
			this.href = href;
		}

		public Anchor(String href, String text, String title) {
			super();
			this.href = href;
			this.text = text;
			this.title = title;
		}

		public String getHref() {
			return href;
		}

		public void setHref(String href) {
			this.href = StringUtils.trim(href);
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = StringUtils.trim(text);
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = StringUtils.trim(title);
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((href == null) ? 0 : href.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Anchor other = (Anchor) obj;
			if (href == null) {
				if (other.href != null)
					return false;
			} else if (!href.equals(other.href))
				return false;
			return true;
		}

		@Override
		public String toString() {
			String titleAttr = StringUtils.isBlank(title) ? "" : " title='"+title+"'";
			return "<a href='"+href+"'"+titleAttr+">"+StringUtils.defaultString(text,href)+"</a>";
		}
		
		public static String toFullUrl(String baseUrl,String href)  {
			String result = toFullUrl0(baseUrl, href);
			return deleteUrlParameter(result,";");
		}

		private static String deleteUrlParameter(String url,String seperator) {
			int indexOf = url.indexOf(seperator);
			if(indexOf >= 0) {
				return url.substring(0,indexOf);
			}
			return url;
		}
		
		public static String removeQueryString(String fullHref) {
			return deleteUrlParameter(fullHref,"?");
		}

		private static String toFullUrl0(String baseUrl, String href) {
			if(href.matches("https?://.*")) {
				return href;
			}
			if(href.startsWith("/")) {
				String root = getRootBaseUrl(baseUrl);
				return root	+	href;
			}else {
				if(baseUrl.endsWith("/")) {
					return baseUrl + href;
				}else {
					return baseUrl + "/" + href;
				}
			}
		}

		public static String getRootBaseUrl(String baseUrl) {
			if(StringUtils.isBlank(baseUrl)) {
				return null;
			}
			try {
				URL url = new URL(baseUrl);
				String root = url.getProtocol()+"://"+url.getHost();
				return root;
			} catch (MalformedURLException e) {
				throw new RuntimeException("MalformedURLException,url:"+baseUrl,e);
			}
		}

		
	}

}
