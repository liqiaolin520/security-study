package indi.qiaolin.security.browser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.assertj.core.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author qiaolin
 * @version 2018/11/21
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

    private static final String SMS_URL = "http://www.282930.cn/SMSReceiver.aspx";
    private Map<String, String> statusMap = new HashMap<>();

    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private RestTemplate restTemplate;

    public ApplicationTest(){
        statusMap.put("0", "提交成功！");
        statusMap.put("1", "错误的用户名或密码！");
        statusMap.put("2", "号码为空！");
        statusMap.put("3", "号码无效！");
        statusMap.put("4", "内容无效！");
        statusMap.put("5", "短信内容存在有非法关键字！");
        statusMap.put("6", "所属短信组必须签名！");
        statusMap.put("7", "你所属的帐户在短信平台组暂时关闭！");
        statusMap.put("8", "余额不足！");
    }

/*

    @Test
    public void testSms() throws JsonProcessingException {
        SMSVo smsVo = new SMSVo("359761482", "tj810208", "15273153835", "接到这个短信请喊我~~");
        String json = objectMapper.writeValueAsString(smsVo);

        StringBuffer sb = new StringBuffer();
        sb.append(SMS_URL).append("?");
        sb.append("username=").append(smsVo.getUsername()).append("&");
        sb.append("password=").append(smsVo.getPassword()).append("&");
        sb.append("mobiles=").append(smsVo.getMobiles()).append("&");
        sb.append("content=").append(smsVo.getContent()).append("【无需有太多】");

        //System.out.println(json);
        System.out.println(sb.toString());
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(sb.toString(), null, String.class);
        System.out.println(responseEntity);
        String body = responseEntity.getBody();


        String[] strings = StringUtils.split(body, ",", 2);
        if(!Arrays.isNullOrEmpty(strings)){
            String status = strings[0];
            System.out.println(statusMap.get(status));

        }

    }
*/


}


