package cc.niushuai.project.steam.token;

import cc.niushuai.project.steam.token.ins.SteamTwoFactorToken;
import cc.niushuai.project.steam.token.util.GlobalUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;

/**
 * Steam 令牌计算
 *
 * @author niushuai
 * @date 2022/8/29 17:53
 */
public class SteamTokenApplication {

    public static void main(String[] args) {

        System.out.println("接收命令行参数: " + ArrayUtil.toString(args));
        GlobalUtil.init(args);

        String steamGuardCode = SteamTwoFactorToken.generateSteamGuardCode(GlobalUtil.getShared_secret());
        System.out.println("当前时间: " + DateUtil.now());
        System.out.println("Steam令牌: " + steamGuardCode);
    }
}
