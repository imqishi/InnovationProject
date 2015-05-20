package chk;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Log;


public class MD5 {
    public static String getMD5Str(String str) throws NoSuchAlgorithmException,
            UnsupportedEncodingException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");

        messageDigest.reset();

        messageDigest.update(str.getBytes("UTF-8"));

        byte[] byteArray = messageDigest.digest();

        StringBuilder md5StrBuff = new StringBuilder();

        for (byte aByteArray : byteArray) {
            if (Integer.toHexString(0xFF & aByteArray).length() == 1)
                md5StrBuff.append("0").append(
                        Integer.toHexString(0xFF & aByteArray));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & aByteArray));
        }

        return md5StrBuff.toString();
    }
    
    public static String getSign(Context context) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        StringBuilder stringBuilder = new StringBuilder();
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_SIGNATURES);
            Signature[] signatures = packageInfo.signatures;
            for (Signature signature : signatures) {
                stringBuilder.append(signature.toCharsString());
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Log.e("nForumSDK.getSign", MD5.getMD5Str(stringBuilder.toString()));
        return MD5.getMD5Str(stringBuilder.toString());
    }
	
}