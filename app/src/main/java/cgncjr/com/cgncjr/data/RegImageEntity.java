package cgncjr.com.cgncjr.data;

/**
 * 请求获得的字符串图片信息
 * Created by Administrator on 2016/4/13.
 */
public class RegImageEntity {
    private String imgUrl;//图片路径
    private String regImage;//转换为字符的图片
    private int imgLength;//转换为字符的图片大小

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getRegImage() {
        return regImage;
    }

    public void setRegImage(String regImage) {
        this.regImage = regImage;
    }

    public int getImgLength() {
        return imgLength;
    }

    public void setImgLength(int imgLength) {
        this.imgLength = imgLength;
    }
}
