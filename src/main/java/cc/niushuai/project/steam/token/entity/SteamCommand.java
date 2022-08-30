package cc.niushuai.project.steam.token.entity;

import cc.niushuai.project.steam.token.enums.GuardCalcTypeEnum;
import cc.niushuai.project.steam.token.util.GlobalUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;

/**
 * steam文件令牌信息
 *
 * @author niushuai
 * @date 2022/8/30 10:47
 */
@Data
public class SteamCommand {

    private GuardCalcTypeEnum type;

    /**
     * 直接指定秘钥
     */
    private String sharedSecret;

    /**
     * 指定steam令牌文件 从文件中读取秘钥
     */
    private String path;

    public SteamCommand verify() {
        if (StrUtil.isEmpty(sharedSecret) && StrUtil.isEmpty(path)) {
            System.err.println("参数缺失!");
            printUsage();
            GlobalUtil.exit();
        }
        if (StrUtil.isNotEmpty(sharedSecret)) {
            this.setType(GuardCalcTypeEnum.COMMAND);
        } else {
            this.setType(GuardCalcTypeEnum.STEAM_FILE);
        }
        return this;
    }

    public void printUsage() {
        System.out.println("usage: \n\tjava -jar steam-token-generator.jar [-sharedSecret xxx] [-path xxx]");
        System.out.println("eg: \n\tjava -jar steam-token-generator.jar -sharedSecret sJH7ffdwqgluzJK0tB6L7LklYSg=");
        System.out.println("\tjava -jar steam-token-generator.jar -path Steamguard-16561111382543543");
        System.out.println("tips: \n\t两个参数任选其一, 若都传递则直接使用sharedSecret");
    }
}
