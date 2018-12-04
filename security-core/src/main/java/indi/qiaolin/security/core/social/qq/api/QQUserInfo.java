package indi.qiaolin.security.core.social.qq.api;

import lombok.Getter;
import lombok.Setter;

/**
 *  QQ的用户信息
 * @author qiaolin
 * @version 2018/12/2
 **/

@Getter
@Setter
public class QQUserInfo {
    /** 返回码 */
    private Integer ret;

    /** 如果ret<0，会有相应的错误信息提示，返回数据全部用UTF-8编码。 */
    private String msg;

    /** 是否失败 */
    private Integer is_lost;

    /** 用户在QQ空间的昵称。 */
    private String nickname;

    /** 性别。 如果获取不到则默认返回"男" */
    private String gender;

    /** 所在省 */
    private String province;

    /** 所在城市 */
    private String city;

    /** 出生年 */
    private String year;

    /** 星座 */
    private String constellation;

    /** 大小为30×30像素的QQ空间头像URL。 */
    private String figureurl;

    /** 大小为50×50像素的QQ空间头像URL。 */
    private String figureurl_1;

    /** 大小为100×100像素的QQ空间头像URL。 */
    private String figureurl_2;

    /** 大小为40×40像素的QQ头像URL。 */
    private String figureurl_qq_1;

    /** 大小为100×100像素的QQ头像URL。需要注意，不是所有的用户都拥有QQ的100x100的头像，但40x40像素则是一定会有。 */
    private String figureurl_qq_2;

    /** 是否是黄钻 */
    private String is_yellow_vip;

    /** 是否是vip */
    private String vip;

    /** 黄钻等级 */
    private String yellow_vip_level;

    /** QQ等级 */
    private String level;

    /** 是否是黄钻年费用户 */
    private String is_yellow_year_vip;

    /** openId */
    private String openId;
}
