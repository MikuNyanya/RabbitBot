package gugugu.filemanage;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import gugugu.bots.BotRabbit;
import gugugu.constant.ConstantCommon;
import gugugu.constant.ConstantFile;
import utils.FileUtil;

import java.io.*;
import java.util.HashMap;

/**
 * create by MikuLink on 2019/12/5 15:44
 * for the Reisen
 * <p>
 * 配置文件文件专用管理器
 */
public class FileManagerConfig {
    //配置文件
    private static File configFile = null;

    /**
     * 文件初始化
     * 以及加载文件到系统
     *
     * @throws IOException 读写异常
     */
    private static void fileInit() throws IOException {
        //先载入文件
        if (null != configFile) {
            return;
        }
        configFile = FileUtil.fileCheck(ConstantFile.CONFIG_FILE_PATH);
    }

    /**
     * 根据传入指令执行对应程序
     */
    public static void doCommand(String command) {
        try {
            //检查文件状态
            fileInit();

            //执行对应指令
            switch (command) {
                case ConstantFile.FILE_COMMAND_LOAD:
                    loadFile();
                    break;
                case ConstantFile.FILE_COMMAND_WRITE:
                    writeFile();
                    break;
            }
        } catch (IOException ioEx) {
            BotRabbit.bot.getLogger().error("配置文件读写异常:" + ioEx.toString(), ioEx);
        } catch (JSONException jsonEx) {
            BotRabbit.bot.getLogger().error("配置文件json转化异常:" + jsonEx.toString(), jsonEx);
        }
    }

    /**
     * 加载文件内容
     *
     * @throws IOException 读写异常
     */
    private static void loadFile() throws IOException {
        //创建读取器
        BufferedReader reader = new BufferedReader(new FileReader(configFile));
        //读取第一行
        String configJson = null;
        while ((configJson = reader.readLine()) != null) {
            //过滤掉空行
            if (configJson.length() <= 0) continue;
            ConstantCommon.common_config = JSONObject.parseObject(configJson, HashMap.class);
            return;
        }
        //关闭读取器
        reader.close();
    }

    /**
     * 对文件写入内容
     *
     * @throws IOException 读写异常
     */
    private static void writeFile() throws IOException {
        //创建写入流
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ConstantFile.CONFIG_FILE_PATH, false)));
        //覆写原本配置
        out.write(JSONObject.toJSONString(ConstantCommon.common_config));
        //关闭写入流
        out.close();
    }

}
