package cc.niushuai.project.steam.token.util;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.json.JSONUtil;
import org.apache.commons.cli.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 处理命令行参数
 *
 * @author niushuai
 * @date: 2022/8/30 10:42
 */
public class CommandParseUtil {

    public static <T> T parse(String[] args, Class<T> clazz) {
        CommandLineParser parser = new DefaultParser();
        //获取字段
        Field[] fields = ReflectUtil.getFields(clazz);
        //使用字段创建options，options是parse需要的参数
        Options options = new Options();
        for (Field field : fields) {
            String name = field.getName();
            Option option = Option.builder(name).argName(name)
                    .desc(name)
                    .hasArg(true)
                    .type(field.getType())
                    .build();
            options.addOption(option);
        }
        try {
            CommandLine cl = parser.parse(options, args);
            Map<String, String> map = new HashMap<>();
            for (Field argsField : fields) {
                String name = argsField.getName();
                map.put(name, cl.getOptionValue(name));
            }

            return (T) JSONUtil.toBean(JSONUtil.toJsonStr(map), clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}