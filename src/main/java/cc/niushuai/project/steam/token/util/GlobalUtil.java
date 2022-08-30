package cc.niushuai.project.steam.token.util;

import cc.niushuai.project.steam.token.entity.SteamCommand;
import cc.niushuai.project.steam.token.entity.SteamInfo;
import cc.niushuai.project.steam.token.enums.GuardCalcTypeEnum;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;

import java.util.stream.Collectors;

/**
 * 初始化工具类
 *
 * @author niushuai
 * @date 2022/8/30 10:33
 */
public class GlobalUtil {

    private static String shared_secret = "";

    /**
     * 处理命令行参数
     *
     * @param args 命令行参数
     * @author niushuai
     * @date: 2022/8/30 10:52
     */
    public static void init(String[] args) {

        SteamCommand command = CommandParseUtil.parse(args, SteamCommand.class).verify();
        System.out.println("秘钥解析类型: " + command.getType());

        if (GuardCalcTypeEnum.COMMAND.equals(command.getType())) {
            shared_secret = command.getSharedSecret();
        } else {
            // 需要先读文件 再取值

            try {
                String steamGuardInfoJson = FileUtil.readLines(command.getPath(), "UTF-8").stream().collect(Collectors.joining());

                // 文件内容转bean
                SteamInfo steamInfo = JSONUtil.toBean(steamGuardInfoJson, SteamInfo.class);
                System.out.println("Steam ID: " + steamInfo.getSteamid());
                System.out.println("Steam 账户: " + steamInfo.getAccount_name());
                shared_secret = steamInfo.getShared_secret();
            } catch (IORuntimeException e) {
                System.err.println("Steam令牌文件未找到: " + command.getPath() + ", 请确认路径是否正确!");
            }
        }

        if (StrUtil.isEmpty(shared_secret)) {
            String msg = GuardCalcTypeEnum.COMMAND.equals(command.getType()) ? "命令行shared_secret参数" : "Steam令牌文件路径";
            System.err.println("未找到相关秘钥[shared_secret], 请确认" + msg + "是否正确");
            exit();
        }

        System.out.println("shared_secret init success: " + shared_secret);
    }

    public static String getShared_secret() {
        return shared_secret;
    }

    public static void exit() {
        System.exit(0);
    }
}
