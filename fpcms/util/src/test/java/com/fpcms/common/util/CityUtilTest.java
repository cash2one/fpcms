package com.fpcms.common.util;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.fpcms.common.util.CityUtil.City;


public class CityUtilTest extends Assert{

	@Test
	public void test_normal_city_list() throws IOException {
		for(City city : CityUtil.getCityList()) {
			System.out.println(city.getRank()+"\t"+city.getGdp()+"\t"+city.getProvince()+"\t"+city.getCity());
		}
	}
	
	@Test
	public void test_link() throws IOException {
		for(City city : CityUtil.getCityList()) {
			System.out.println("<a href='http://"+city.getCityPinyin()+".aaafaipiao.com'>"+city.getCity()+"代开发票</a>");
		}
	}
	
	@Test
	public void test_get_city_full_pinyin() throws IOException {
		for(City city : CityUtil.getCityList()) {
			String str = city.getCity() + "=fp" +PinyinUtil.cn2Spell(city.getCity());
			System.out.println(str);
		}
		for(City city : CityUtil.getCityList()) {
			System.out.println("fp"+PinyinUtil.cn2Spell(city.getCity()));
		}
	}
	
	@Test
	public void test_getProvinceList() throws IOException {
		CityUtil.getCityList();
		System.out.println(CityUtil.getCityPinyinMap());
		System.out.println(CityUtil.getProvincePinyinMap());
		assertEquals(CityUtil.getProvincePinyinMap().toString(),"{上海=sh, 北京=bj, 广东=gd, 天津=tj, 江苏=js, 重庆=zq, 四川=sc, 浙江=zj, 湖北=hb, 山东=sd, 辽宁=ln, 湖南=hn, 河北=hb1, 河南=hn1, 黑龙江=hlj, 福建=fj, 吉林=jl, 陕西=sx, 安徽=ah, 内蒙古=nmg, 江西=jx, 云南=yn, 广西=gx, 山西=sx1, 新疆=xj, 贵州=gz, 甘肃=gs, 宁夏=nx, 青海=qh, 海南=hn2}");
	}
	
	@Test
	public void test_print_getProvinceList() throws Exception {
		for(City city: CityUtil.getCityList()) {
			String cityDesc = "rank:"+city.getRank()+" gdp:"+city.getGdp()+" "+city.getProvince();
//			String keyword = "发票"+city.getCity()+","+city.getCity()+"发票,"+city.getCity()+"代开发票,"+city.getCity()+"开发票,"+city.getCity()+"发票查询";
			String keyword = String.format("%1$s发票,%1$s代开发票,%1$s开发票,开%1$s发票",city.getCity());
			Thread.sleep(20);
//			String mainDomain = ".fpshijiazhuang.com'";
//			String mainDomain = ".fpzhangsha.com'";
			String mainDomain = ".aaafaipiao.com'";
			String company = city.getCity().charAt(1)+RandomUtil.randomSelect("荣","盛","通","辉","耀")+RandomUtil.randomSelect("税务","财税")+"咨询公司";
			System.out.println("INSERT INTO cms_site (site_domain,site_name,site_desc,city,keyword,company) VALUES ('"+city.getCityPinyin()+mainDomain+", '"+city.getCity()+"', '"+cityDesc+"', '"+city.getCity()+"', '"+keyword+"', '"+company+"') on duplicate key update site_domain=values(site_domain),site_name=values(site_name),site_desc=values(site_desc),city=values(city),keyword=values(keyword);");
		}
	}
}
