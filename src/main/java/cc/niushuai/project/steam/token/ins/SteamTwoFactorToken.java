package cc.niushuai.project.steam.token.ins;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * SteamToken计算
 *
 * @author niushuai
 * @date 2022/8/29 17:55
 */
public class SteamTwoFactorToken {

    private static final byte[] s_rgchSteamguardCodeChars = {50, 51, 52, 53, 54, 55, 56, 57, 66, 67, 68, 70, 71, 72, 74, 75, 77, 78, 80, 81, 82, 84, 86, 87, 88, 89};
    private byte[] mSecret;

    public SteamTwoFactorToken(String sharedSecret) {
        if (StrUtil.isNotEmpty(sharedSecret)) {
            this.mSecret = Base64.decode(sharedSecret.getBytes());
        } else {
            throw new RuntimeException("未知参数 shared_secret");
        }
    }

    public static String generateSteamGuardCode(String sharedSecret) {
        return new SteamTwoFactorToken(sharedSecret).generateSteamGuardCode();
    }

    public final String generateSteamGuardCode() {
        return generateSteamGuardCodeForTime(currentTime());
    }

    private final String generateSteamGuardCodeForTime(long calcTime) {
        if (this.mSecret == null) {
            return "";
        }
        byte[] bArr = new byte[8];
        long j2 = calcTime / 30;
        int i = 8;
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                break;
            }
            bArr[i2] = (byte) ((int) j2);
            j2 >>>= 8;
            i = i2;
        }
        SecretKeySpec secretKeySpec = new SecretKeySpec(this.mSecret, "HmacSHA1");
        try {
            Mac instance = Mac.getInstance("HmacSHA1");
            instance.init(secretKeySpec);
            byte[] doFinal = instance.doFinal(bArr);
            int i3 = doFinal[19] & 15;
            int i4 = (doFinal[i3 + 3] & 255) | ((doFinal[i3 + 2] & 255) << 8) | ((doFinal[i3] & Byte.MAX_VALUE) << 24) | ((doFinal[i3 + 1] & 255) << 16);
            byte[] bArr2 = new byte[5];
            for (int i5 = 0; i5 < 5; i5++) {
                byte[] bArr3 = s_rgchSteamguardCodeChars;
                bArr2[i5] = bArr3[i4 % bArr3.length];
                i4 /= bArr3.length;
            }
            return new String(bArr2);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 系统时间 不带毫秒
     *
     * @author niushuai
     * @date: 2022/8/30 10:28
     * @return: {@link long} 秒级时间戳
     */
    public final long currentTime() {
        return System.currentTimeMillis() / 1000;
    }
}
