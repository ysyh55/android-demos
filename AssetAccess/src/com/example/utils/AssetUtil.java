package com.example.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.res.AssetManager;
import android.util.Log;

public class AssetUtil {
	private static final String TAG = AssetUtil.class.getSimpleName();
	
	public static boolean isFile(AssetManager assetMgr, String path) {
		boolean isfile = false;
		InputStream in = null;
		try {
			in = assetMgr.open(path);
			isfile = true;//open success meaning exist
		} catch (IOException e) {
			// is dir or not exist
//			e.printStackTrace();
		} finally {
			try {
				if (in != null) in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return isfile;
	}
	
	public static boolean copyAssetFile(AssetManager assetMgr,
			String assetPath, String dstPath) {
		boolean isOk = false;
		InputStream in = null;
		OutputStream out = null;
		
		dstPath = FileUtil.copyPrepare(assetPath, dstPath);
		
		Log.i(TAG, "copy file: assets/"+assetPath +" -> " + dstPath);
		
		try {
			in = assetMgr.open(assetPath);
			out = new FileOutputStream(dstPath);
			FileUtil.copyFileByStream(in, out);
			isOk = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null)	in.close();
				if (out != null) out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return isOk;
	}
	
	public static boolean copyAssetDir(AssetManager assetMgr,
			String assetDir, String dstDir) {
		boolean isOk = false;
		if (isFile(assetMgr, assetDir)) {
			isOk = copyAssetFile(assetMgr, assetDir, dstDir);
		} else {
			String[] dirs;
			try {
				dirs = assetMgr.list(assetDir);
				if (dirs != null && dirs.length > 0) {
					for (String dir:dirs) {
						String src = assetDir + File.separator + dir;
						String dst = dstDir + File.separator + dir;
						isOk = copyAssetDir(assetMgr, src, dst);
						if (!isOk) break;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return isOk;
	}
}
