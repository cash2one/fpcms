/*
 * Copyright [duowan.com]
 * Web Site: http://www.duowan.com
 * Since 2005 - 2012
 */

package com.fpcms.service.impl;

import static com.duowan.common.util.holder.BeanValidatorHolder.validateWithException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.duowan.common.beanutils.BeanUtils;
import com.duowan.common.util.page.Page;
import com.fpcms.common.util.Constants;
import com.fpcms.common.util.MapUtil;
import com.fpcms.dao.CmsSiteDao;
import com.fpcms.model.CmsSite;
import com.fpcms.query.CmsSiteQuery;
import com.fpcms.service.CmsPropertyService;
import com.fpcms.service.CmsSiteService;


/**
 * [CmsSite] 的业务操作实现类
 * 
 * @author badqiu email:badqiu(a)gmail.com
 * @version 1.0
 * @since 1.0
 */
@Service("cmsSiteService")
@Transactional
public class CmsSiteServiceImpl implements CmsSiteService {

	protected static final Logger log = LoggerFactory.getLogger(CmsSiteServiceImpl.class);
	
	//
	// 请删除无用的方法，代码生成器只是为你生成一个架子
	//
	
	private CmsSiteDao cmsSiteDao;
	private CmsPropertyService cmsPropertyService;
	/**增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,请注意大小写*/
	public void setCmsSiteDao(CmsSiteDao dao) {
		this.cmsSiteDao = dao;
	}
	
	public void setCmsPropertyService(CmsPropertyService cmsPropertyService) {
		this.cmsPropertyService = cmsPropertyService;
	}



	/** 
	 * 创建CmsSite
	 **/
	public CmsSite create(CmsSite cmsSite) {
	    Assert.notNull(cmsSite,"'cmsSite' must be not null");
	    initDefaultValuesForCreate(cmsSite);
	    new CmsSiteChecker().checkCreateCmsSite(cmsSite);
	    cmsSiteDao.insert(cmsSite);
	    return cmsSite;
	}
	
	/** 
	 * 更新CmsSite
	 **/	
    public CmsSite update(CmsSite cmsSite) {
        Assert.notNull(cmsSite,"'cmsSite' must be not null");
        new CmsSiteChecker().checkUpdateCmsSite(cmsSite);
        cmsSiteDao.update(cmsSite);
        return cmsSite;
    }	
    
	/** 
	 * 删除CmsSite
	 **/
    public void removeById(String siteDomain) {
        cmsSiteDao.deleteById(siteDomain);
    }
    
	/** 
	 * 根据ID得到CmsSite
	 **/    
    public CmsSite getById(String siteDomain) {
        return cmsSiteDao.getById(siteDomain);
    }
    
	/** 
	 * 分页查询: CmsSite
	 **/      
	@Transactional(readOnly=true)
	public Page<CmsSite> findPage(CmsSiteQuery query) {
	    Assert.notNull(query,"'query' must be not null");
		return cmsSiteDao.findPage(query);
	}
	
    
	/**
	 * 为创建时初始化相关默认值 
	 **/
    public void initDefaultValuesForCreate(CmsSite cmsSite) {
    }
    
    /**
     * CmsSite的属性检查类,根据自己需要编写自定义检查
     **/
    public class CmsSiteChecker {
        /**可以在此检查只有更新才需要的特殊检查 */
        public void checkUpdateCmsSite(CmsSite cmsSite) {
            checkCmsSite(cmsSite);
        }
    
        /**可以在此检查只有创建才需要的特殊检查 */
        public void checkCreateCmsSite(CmsSite cmsSite) {
            checkCmsSite(cmsSite);
        }
        
        /** 检查到有错误请直接抛异常，不要使用 return errorCode的方式 */
        public void checkCmsSite(CmsSite cmsSite) {
        	// Bean Validator检查,属性检查失败将抛异常
            validateWithException(cmsSite);
            
        	//复杂的属性的检查一般需要分开写几个方法，如 checkProperty1(v),checkProperty2(v)
        }
    }

	@Override
	public Map<String,String> getSiteProperties(String site) {
		CmsSite cmsSite = getById(site);
		if(site == null) {
			throw new RuntimeException("not found CmsSite by site:"+site);
		}
		Map<String,String> localhostProps = cmsPropertyService.findByGroup(Constants.PROPERTY_DEFAULT_GROUP);
		Map<String,String> siteProps = cmsPropertyService.findByGroup(site);
		Map<String,String> cmsSiteMap = BeanUtils.describe(cmsSite);
		
		Map<String,String> result = new HashMap<String,String>();
		result.putAll(cmsSiteMap);
		MapUtil.mergeWithDefaultMap(result,siteProps);
		MapUtil.mergeWithDefaultMap(result,localhostProps);
		
//		result.put("city", StringUtils.defaultIfBlank(cmsSite.getCity(),props.get("city")));
//		result.put("keyword", StringUtils.defaultIfBlank(cmsSite.getKeyword(),props.get("keyword")));
//		result.put("company", StringUtils.defaultIfBlank(cmsSite.getCompany(),props.get("company")));
//		result.put("contactName", StringUtils.defaultIfBlank(cmsSite.getContactName(),props.get("contactName")));
//		result.put("mobile", StringUtils.defaultIfBlank(cmsSite.getMobile(),props.get("mobile")));
//		result.put("qq", StringUtils.defaultIfBlank(cmsSite.getQq(),props.get("qq")));
//		result.put("email", StringUtils.defaultIfBlank(cmsSite.getEmail(),props.get("email")));
		
		return result;
	}

	@Override
	public List<CmsSite> findAll() {
		return cmsSiteDao.findAll();
	}
	

}