package gugugu.service;

import gugugu.apirequest.amap.WeatherRequest;
import gugugu.bots.BotRabbit;
import gugugu.constant.ConstantAmap;
import gugugu.constant.ConstantCommon;
import gugugu.entity.apirequest.amap.InfoWeather;
import utils.StringUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * create by MikuLink on 2020/1/21 17:17
 * for the Reisen
 * 天气相关服务
 */
public class WeatherService {
    /**
     * 根据传入的字符串，匹配城市名称
     */
    public static List<String> matchCity(String inputCityName) {
        if (StringUtil.isEmpty(inputCityName)) {
            return null;
        }

        //模糊对比城市名称，把得到的结果全都返回出去
        List<String> cityNameList = new ArrayList<>();
        for (String mapKey : ConstantAmap.map_adcode.keySet()) {
            if (StringUtil.isEmpty(mapKey)) {
                continue;
            }
            if (mapKey.contains(inputCityName)) {
                cityNameList.add(mapKey);
            }
        }
        return cityNameList;
    }

    /**
     * 根据传入的城市名称查询天气信息
     * 查询失败时返回对应提示
     *
     * @param inputCityName 的城市名称
     * @return 结果
     */
    public static String getWeatherByCityName(String inputCityName) throws IOException {
        //基本判断
        if (StringUtil.isEmpty(inputCityName)) {
            return ConstantAmap.INPUT_CITY_NAME_EMPTY;
        }

        //去查询是否有该城市
        List<String> cityNames = matchCity(inputCityName);
        if (null == cityNames || cityNames.size() == 0) {
            return String.format(ConstantAmap.CITY_MATCH_EMPTY, inputCityName);
        }

        //如果有完全匹配的则视为匹配成功
        //如果没有完全匹配的，且有多个结果的，返回匹配失败，并列出所有匹配到的城市名称
        String cityKey = null;
        StringBuilder cityListStr = new StringBuilder();
        for (String cityName : cityNames) {
            if (inputCityName.equals(cityName)) {
                cityKey = cityName;
                break;
            }
            cityListStr.append("\n");
            cityListStr.append(String.format("[%s]",cityName));
        }
        if (StringUtil.isEmpty(cityKey)) {
            return String.format(ConstantAmap.CITY_MATCH_FAIL, inputCityName, cityListStr.toString());
        }

        //请求接口获取天气
        InfoWeather infoWeather = doWeatherRequest(ConstantAmap.map_adcode.get(cityKey));
        if (null == infoWeather) {
            return ConstantAmap.WEATHER_API_FAIL;
        }
        //组装为结果 为了直观，直接在代码里排版算了
        StringBuilder resultStr = new StringBuilder();
        resultStr.append(String.format("[%s]当前天气情况:", infoWeather.getCity()));
        resultStr.append(String.format("\n%s %s℃", infoWeather.getWeather(), infoWeather.getTemperature()));
        resultStr.append(String.format("\n%s风 %s级:", infoWeather.getWinddirection(), infoWeather.getWindpower()));
        resultStr.append(String.format("\n空气湿度:%s", infoWeather.getHumidity()));
        resultStr.append(String.format("\n===%s===", infoWeather.getReporttime()));

        return resultStr.toString();
    }

    /**
     * 请求查询天气接口并获取结果
     *
     * @param adcode 高德地图区域代码
     * @return 天气信息
     */
    public static InfoWeather doWeatherRequest(String adcode) throws IOException {
        String appKey = ConstantCommon.common_config.get(ConstantAmap.APP_KEY_KEY);
        if (StringUtil.isEmpty(appKey)) {
            BotRabbit.bot.getLogger().warning("高德appkey为空！");
            return null;
        }
        WeatherRequest request = new WeatherRequest();
        request.setAppkey(appKey);
        request.setCityAcode(adcode);

        request.doRequest();
        InfoWeather weather = request.getWeather();
        if (null == weather) {
            //记录api失败日志
            BotRabbit.bot.getLogger().warning("获取高德天气失败，body:" + request.getBody());
        }
        return weather;
    }
}
