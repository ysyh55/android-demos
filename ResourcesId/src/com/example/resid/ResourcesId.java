package com.example.resid;

import android.content.Context;
import android.content.res.Resources;

public class ResourcesId {
	private static final String TAG = "ResourcesId";
	private Context mContext;
	private Resources mResources;
	private String mPackageName;
	
	private ResourcesId() {}
	
	private static class ResourcesIdHolder {
		private static ResourcesId instance = new ResourcesId();
	}
	private void setContext(Context context) {
		if (context != null) {
			mContext = context.getApplicationContext();
			mResources = mContext.getResources();
			mPackageName = mContext.getPackageName();
		}
	}
	
	public static ResourcesId getInstance(Context context) {
		ResourcesIdHolder.instance.setContext(context);
		return ResourcesIdHolder.instance;
	}
	
	public int getResourcesId(String defType, String name) {
		if (mResources != null) {
			return mResources.getIdentifier(name, defType, mPackageName);
		}
		return 0;
	}
	
	public static int getResourcesId(Context context, String defType, String name) {
		return ResourcesId.getInstance(context).getResourcesId(defType, name);
	}
}
