package gugugu.entity.apirequest.dxynCoV;

/**
 * @author MikuLink
 * @date 2020/1/31 23:28
 * for the Reisen
 * 省份级别的疫情信息
 * GsonFormat生成
 */
public class InfoAllnCoV {

    /**
     * id : 1
     * createTime : 1579537899000
     * modifyTime : 1580481674000
     * infectSource : 野生动物，可能为中华菊头蝠
     * passWay : 经呼吸道飞沫传播，亦可通过接触传播
     * imgUrl : https://img1.dxycdn.com/2020/0131/214/3394045773397760494-73.png
     * dailyPic : https://img1.dxycdn.com/2020/0131/475/3394029770349575031-73.jpg
     * summary :
     * deleted : false
     * countRemark :
     * confirmedCount : 9811
     * suspectedCount : 15238
     * curedCount : 214
     * deadCount : 213
     * virus : 新型冠状病毒 2019-nCoV
     * remark1 : 易感人群: 人群普遍易感。老年人及有基础疾病者感染后病情较重，儿童及婴幼儿也有发病
     * remark2 : 潜伏期: 一般为 3~7 天，最长不超过 14 天，潜伏期内存在传染性
     * remark3 :
     * remark4 :
     * remark5 :
     * generalRemark : 疑似病例数来自国家卫健委数据，目前为全国数据，未分省市自治区等
     * abroadRemark :
     */

    private int id;
    private long createTime;
    private long modifyTime;
    private String infectSource;
    private String passWay;
    private String imgUrl;
    private String dailyPic;
    private String summary;
    private boolean deleted;
    private String countRemark;
    private int confirmedCount;
    private int suspectedCount;
    private int curedCount;
    private int deadCount;
    private String virus;
    private String remark1;
    private String remark2;
    private String remark3;
    private String remark4;
    private String remark5;
    private String generalRemark;
    private String abroadRemark;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getInfectSource() {
        return infectSource;
    }

    public void setInfectSource(String infectSource) {
        this.infectSource = infectSource;
    }

    public String getPassWay() {
        return passWay;
    }

    public void setPassWay(String passWay) {
        this.passWay = passWay;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDailyPic() {
        return dailyPic;
    }

    public void setDailyPic(String dailyPic) {
        this.dailyPic = dailyPic;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getCountRemark() {
        return countRemark;
    }

    public void setCountRemark(String countRemark) {
        this.countRemark = countRemark;
    }

    public int getConfirmedCount() {
        return confirmedCount;
    }

    public void setConfirmedCount(int confirmedCount) {
        this.confirmedCount = confirmedCount;
    }

    public int getSuspectedCount() {
        return suspectedCount;
    }

    public void setSuspectedCount(int suspectedCount) {
        this.suspectedCount = suspectedCount;
    }

    public int getCuredCount() {
        return curedCount;
    }

    public void setCuredCount(int curedCount) {
        this.curedCount = curedCount;
    }

    public int getDeadCount() {
        return deadCount;
    }

    public void setDeadCount(int deadCount) {
        this.deadCount = deadCount;
    }

    public String getVirus() {
        return virus;
    }

    public void setVirus(String virus) {
        this.virus = virus;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    public String getRemark3() {
        return remark3;
    }

    public void setRemark3(String remark3) {
        this.remark3 = remark3;
    }

    public String getRemark4() {
        return remark4;
    }

    public void setRemark4(String remark4) {
        this.remark4 = remark4;
    }

    public String getRemark5() {
        return remark5;
    }

    public void setRemark5(String remark5) {
        this.remark5 = remark5;
    }

    public String getGeneralRemark() {
        return generalRemark;
    }

    public void setGeneralRemark(String generalRemark) {
        this.generalRemark = generalRemark;
    }

    public String getAbroadRemark() {
        return abroadRemark;
    }

    public void setAbroadRemark(String abroadRemark) {
        this.abroadRemark = abroadRemark;
    }
}
