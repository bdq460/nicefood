package com.egghead.nicefood.dal;

import com.egghead.nicefood.BaseDO;

/**
 * @author zhangjun.zyk 
 * @since 2013-7-28 下午07:35:41
 * 
 */
public class CourseDO extends BaseDO{
	private int coid;
	private String name;
	private String[] materials;
	private String materialsJson;
	private String[] pics;
	private String picsJson;
	private StepDO[] steps;
	private String stepsJson;
	private String[] tags;
	private String tagsJson;
	private String description;
	private String sourceUrl;
	private String gmtCreate;
	private String gmtModified;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getCoid() {
		return coid;
	}
	public void setCoid(int coid) {
		this.coid = coid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String[] getMaterials() {
		return materials;
	}
	public void setMaterials(String[] materials) {
		this.materials = materials;
	}
	public String[] getPics() {
		return pics;
	}
	public void setPics(String[] pics) {
		this.pics = pics;
	}
	public StepDO[] getSteps() {
		return steps;
	}
	public void setSteps(StepDO[] steps) {
		this.steps = steps;
	}
	public String[] getTags() {
		return tags;
	}
	public void setTags(String[] tags) {
		this.tags = tags;
	}
	public String getSourceUrl() {
		return sourceUrl;
	}
	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}
	public String getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(String gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public String getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(String gmtModified) {
		this.gmtModified = gmtModified;
	}
	public String getPicsJson() {
		return picsJson;
	}
	public void setPicsJson(String picsJson) {
		this.picsJson = picsJson;
	}
	public String getStepsJson() {
		return stepsJson;
	}
	public void setStepsJson(String stepsJson) {
		this.stepsJson = stepsJson;
	}
	public String getTagsJson() {
		return tagsJson;
	}
	public void setTagsJson(String tagsJson) {
		this.tagsJson = tagsJson;
	}
	public String getMaterialsJson() {
		return materialsJson;
	}
	public void setMaterialsJson(String materialsJson) {
		this.materialsJson = materialsJson;
	}
}
