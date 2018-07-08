package com.youpinhui.pojogroup;

import java.io.Serializable;
import java.util.List;

import com.youpinhui.pojo.TbSpecification;
import com.youpinhui.pojo.TbSpecificationOption;

/**
 * 
 * A  class contains data of Specification & Specification Option 
 * @author Leon
 *
 */
public class Specification implements Serializable{

	private TbSpecification specification;
	private List<TbSpecificationOption> specificationOptionList;
	
	public TbSpecification getSpecification() {
		return specification;
	}
	public void setSpecification(TbSpecification specification) {
		this.specification = specification;
	}
	public List<TbSpecificationOption> getSpecificationOptionList() {
		return specificationOptionList;
	}
	public void setSpecificationOptionList(List<TbSpecificationOption> specificationOptionList) {
		this.specificationOptionList = specificationOptionList;
	}
	
	@Override
	public String toString() {
		return "Specification [specification=" + specification + ", specificationOptionList=" + specificationOptionList
				+ "]";
	}
	public Specification(TbSpecification specification, List<TbSpecificationOption> specificationOptionList) {
		super();
		this.specification = specification;
		this.specificationOptionList = specificationOptionList;
	}
	public Specification() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
