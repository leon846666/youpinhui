package com.youpinhui.pojogroup;

import java.io.Serializable;
import java.util.List;

import com.youpinhui.pojo.TbGoods;
import com.youpinhui.pojo.TbGoodsDesc;
import com.youpinhui.pojo.TbItem;

/**
 * 
 *  a combination class of TbGood & TbGoodsDesc & itemList
 *  
 * @author Leon
 *
 */
public class Goods implements Serializable {

	private TbGoods goods;  //goods SPU info
	private TbGoodsDesc goodsDesc; //goods SPU extended info
	private List<TbItem> itemList; // goods SKU list 
	
	
	public Goods(TbGoods goods, TbGoodsDesc goodsDesc, List<TbItem> itemList) {
		super();
		this.goods = goods;
		this.goodsDesc = goodsDesc;
		this.itemList = itemList;
	}
	
	public Goods() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Goods [goods=" + goods + ", goodsDesc=" + goodsDesc + ", itemList=" + itemList + "]";
	}
	public TbGoods getGoods() {
		return goods;
	}
	public void setGoods(TbGoods goods) {
		this.goods = goods;
	}
	public TbGoodsDesc getGoodsDesc() {
		return goodsDesc;
	}
	public void setGoodsDesc(TbGoodsDesc goodsDesc) {
		this.goodsDesc = goodsDesc;
	}
	public List<TbItem> getItemList() {
		return itemList;
	}
	public void setItemList(List<TbItem> itemList) {
		this.itemList = itemList;
	}

}
