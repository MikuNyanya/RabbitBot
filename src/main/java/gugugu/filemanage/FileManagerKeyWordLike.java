package gugugu.filemanage;

import gugugu.constant.ConstantFile;
import gugugu.constant.ConstantKeyWord;
import utils.FileUtil;
import utils.RegexUtil;
import utils.StringUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * create by MikuLink on 2019/12/5 15:44
 * for the Reisen
 * <p>
 * 模糊匹配关键词检索文件专用管理器
 */
public class FileManagerKeyWordLike extends FileManager {

    /**
     * 文件初始化
     * 以及加载文件到系统
     *
     * @throws IOException 读写异常
     */
    private static void fileInit() throws IOException {
        //先载入文件
        if (null != keyWordLikeFile) {
            return;
        }
        keyWordLikeFile = FileUtil.fileCheck(ConstantFile.APPEND_KEYWORD_FILE_LIKE_PATH);
    }

    /**
     * 根据传入指令执行对应程序
     */
    public static void doCommand(String command, String... text) {
        try {
            //检查文件状态
            fileInit();

            //执行对应指令
            switch (command) {
                case ConstantFile.FILE_COMMAND_LOAD:
                    loadFile();
                    break;
                case ConstantFile.FILE_COMMAND_WRITE:
                    writeFile(text);
                    break;
            }
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }

    /**
     * 加载文件内容
     *
     * @throws IOException 读写异常
     */
    private static void loadFile() throws IOException {
        //创建读取器
        BufferedReader reader = new BufferedReader(new FileReader(keyWordLikeFile));

        String tempKey = null;
        List<String> tempList = null;

        //逐行读取文件
        String freeTimeStr = null;
        while ((freeTimeStr = reader.readLine()) != null) {
            //过滤掉空行
            if (freeTimeStr.length() <= 0) continue;
            //第一行是关键词
            tempKey = freeTimeStr;
            //再往下读一行，是回复
            freeTimeStr = reader.readLine();
            tempList = Arrays.asList(freeTimeStr.split("\\|"));

            //内容同步到系统
            ConstantKeyWord.key_wrod_like.put(tempKey, tempList);
        }
        //关闭读取器
        reader.close();
    }

    /**
     * 对文件写入内容
     *
     * @param text 第一段为关键词，多个关键词用 \ 隔开，后面为回复列表
     * @throws IOException 读写异常
     */
    private static void writeFile(String... text) throws IOException {
        //入参检验
        if (null == text || text.length <= 0) {
            throw new IOException("必须传递文件写入内容");
        }

        //创建写入流
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ConstantFile.APPEND_KEYWORD_FILE_LIKE_PATH, true)));
        //index
        int i = 0;
        //热更新
        String keyWord = "";
        List<String> repList = new ArrayList<>();

        StringBuilder fileSb = new StringBuilder();
        for (String s : text) {
            //过滤空行
            if (StringUtil.isEmpty(s)) continue;
            //index+1
            i++;

            //第一个属于关键词，直接丢进去
            if (i == 1) {
                fileSb.append(s);
                keyWord = s;
                continue;
            }
            //第二属于第一个回复，需要换行，其他的开头加分隔符
            if (i == 2) {
                fileSb.append("\r\n");
            } else {
                fileSb.append("|");
            }
            //追加回复
            fileSb.append(s);
            repList.add(s);
        }
        //写入内容
        out.write("\r\n" + fileSb.toString());

        //把新加的内容同步到系统
        ConstantKeyWord.key_wrod_like.put(keyWord, repList);

        //关闭写入流
        out.close();
    }

    /**
     * 模糊匹配关键词
     *
     * @param inputKey 输入的关键词
     * @return 匹配到的key，可以用来获取回复列表
     */
    public static String keyWordLikeRegex(String inputKey) {
        for (String keyRegex : ConstantKeyWord.key_wrod_like.keySet()) {
            //正则匹配
            for (String keyWords : keyRegex.split("\\|")) {
                //拼接正则
                String regex = getKeyWordLikeRegex(keyWords);

                //进行正则匹配
                if (RegexUtil.regex(inputKey, regex)) {
                    //匹配到了返回map的完整key
                    return keyRegex;
                }
            }
        }
        return null;
    }

    /**
     * 解析模糊关键词，组成正则
     *
     * @param keyWords 模糊匹配关键词原始字符串
     * @return 模糊匹配正则表达式
     */
    private static String getKeyWordLikeRegex(String keyWords) {
        StringBuilder regex = new StringBuilder();
        boolean isFirst = true;
        for (String key : keyWords.split("&")) {
            if (isFirst) {
                regex.append(key);
                isFirst = false;
                continue;
            }
            regex.append("\\S*");
            regex.append(key);
        }
        return regex.toString();
    }
}
