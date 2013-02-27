package com.fpcms.service.article_crawl;

import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fpcms.model.CmsContent;
import com.fpcms.service.CmsContentService;

public class ArticleCrawlServiceTest extends Mockito{
	private ArticleCrawlService articleCrawlService = new ArticleCrawlService(); ;
	private CmsContentService cmsContentService = mock(CmsContentService.class);
	private ApplicationContext applicationContext;
	
	@Before
	public void setUp() throws Exception {
		applicationContext = new ClassPathXmlApplicationContext("classpath:/spring/applicationContext-crawler.xml");
		articleCrawlService.setApplicationContext(applicationContext);
		articleCrawlService.setCmsContentService(cmsContentService);
		articleCrawlService.afterPropertiesSet();
	}
	
	@Test
	public void test_hasFilterKeyword() {
		assertTrue(ArticleCrawlService.hasFilterKeyword("开贵州发票"));
		assertTrue(ArticleCrawlService.hasFilterKeyword("代开贵州发票"));
	}
	
	@Test
	public void test_return_countBySourceUrl_1() {
		when(cmsContentService.countBySourceUrl((Date)any(), (Date)any(), (String)any())).thenReturn(1);
		articleCrawlService.crawlAllSite();
		
		verify(cmsContentService,atLeastOnce()).countBySourceUrl((Date)any(), (Date)any(), (String)any());
		verify(cmsContentService,never()).create((CmsContent)any());
	}
	
	@Test
	public void test_return_countBySourceUrl_0() {
		when(cmsContentService.countBySourceUrl((Date)any(), (Date)any(), (String)any())).thenReturn(0);
		articleCrawlService.crawlAllSite();
		
		verify(cmsContentService,atLeastOnce()).countBySourceUrl((Date)any(), (Date)any(), (String)any());
		verify(cmsContentService,atLeastOnce()).create((CmsContent)any());
	}

	@Test
	public void getShoudVisitAnchorList() {
		List<String> list = articleCrawlService.getInvalidUrlList();
		removeIgnoreSite(list);
		
		assertTrue(list.toString(),list.isEmpty());
	}

	private void removeIgnoreSite(List<String> list) {
		for(ListIterator<String> it = list.listIterator(); it.hasNext();) {
			String url = it.next();
			if(url.contains("rfi.fr")) {
				it.remove();
			}
		}
	}
	
}
