package com.zhaoyao.com.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

import android.os.Parcel;
import android.os.Parcelable;
/**
 * 
 * @author lw
 * @编写时间 2016年11月30日下午4:07:48
 * 类说明 :
 */
public abstract class LWParcelable implements Parcelable{
	public LWParcelable() {
		init = initClass();
	}
	protected abstract initObject initClass();
	private static initObject init ;
	public interface initObject{
		Object initClass();
	}
	private static boolean isFilter = false;
	
	public boolean isFilter() {
		return isFilter;
	}
	public void setFilter(boolean isFilter) {
		this.isFilter = isFilter;
	}
	public static final Creator<LWParcelable> CREATOR = new Creator<LWParcelable>() {

		@Override
		public LWParcelable createFromParcel(Parcel source) {
			LWParcelable newInstance = null;
			try {
				if(init == null)return null;
				Class<?> class1 = init.initClass().getClass();
				if(class1 == null)return newInstance;
				newInstance = (LWParcelable) class1.newInstance();
				Field[] fields = class1.getDeclaredFields();
				if(isFilter)
					for (int i = 0; i < fields.length; i++) {
						fields[i].setAccessible(true);
						if(fields[i].isAnnotationPresent(FilterField.class))continue;
						fields[i].set(newInstance, source.readValue(fields[i].getType().getClassLoader()));
					}
				else
					for (int i = 0; i < fields.length; i++) {
						fields[i].setAccessible(true);
						fields[i].set(newInstance, source.readValue(fields[i].getType().getClassLoader()));
					}
			}catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			}
			return newInstance;
		}

		@Override
		public LWParcelable[] newArray(int i) {
			return new LWParcelable[i];
		}
	};

	
	@Override
	public int describeContents() {
		return 0;
	}
	@Target(ElementType.FIELD)
	// 表示用在字段上
	@Retention(RetentionPolicy.RUNTIME)
	// 表示在生命周期是运行时
	public @interface FilterField{
		public String fieldName() default "";
	}
	@Override
	public void writeToParcel(Parcel dest, int i) {
		if(init == null)return;
		Field[] fields = init.initClass().getClass().getDeclaredFields();
		if(isFilter)
		for (int j = 0; j < fields.length; j++) {
			try {
				fields[j].setAccessible(true);
				if(fields[j].isAnnotationPresent(FilterField.class))continue;
				dest.writeValue(fields[j].get(init.initClass()));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		else
			for (int j = 0; j < fields.length; j++) {
				try {
					fields[j].setAccessible(true);
					dest.writeValue(fields[j].get(init.initClass()));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
	}

}
