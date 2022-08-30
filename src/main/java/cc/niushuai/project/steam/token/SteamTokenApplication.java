package cc.niushuai.project.steam.token;

import cc.niushuai.project.steam.token.ins.SteamTwoFactorToken;
import cc.niushuai.project.steam.token.util.GlobalUtil;
import cn.hutool.core.date.DateUtil;

/**
 * Steam 令牌计算
 *
 * @author niushuai
 * @date 2022/8/29 17:53
 */
public class SteamTokenApplication {

    public static void main(String[] args) {

        GlobalUtil.init(args);
        System.out.println("初始化命令行参数成功" + args);

        String steamGuardCode = SteamTwoFactorToken.generateSteamGuardCode(GlobalUtil.getShared_secret());
        System.out.println("当前时间: " + DateUtil.now());
        System.out.println("Steam令牌: " + steamGuardCode);
    }
}
