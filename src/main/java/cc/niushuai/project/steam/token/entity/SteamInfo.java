package cc.niushuai.project.steam.token.entity;

import cc.niushuai.project.steam.token.enums.GuardCalcTypeEnum;
import lombok.Data;

/**
 * steam文件令牌信息
 *
 * @author niushuai
 * @date 2022/8/30 10:47
 */
@Data
public class SteamInfo {

    private GuardCalcTypeEnum type;

    private String shared_secret;
    private String serial_number;
    private String revocation_code;
    private String uri;
    private String server_time;
    private String account_name;
    private String token_gid;
    private String identity_secret;
    private String secret_1;
    private String status;
    private Integer steamguard_scheme;
    private String steamid;
}
