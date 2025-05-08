package com.miaoyu.barc.user.service;

import com.miaoyu.barc.response.*;
import com.miaoyu.barc.user.mapper.UserBasicMapper;
import com.miaoyu.barc.user.mapper.VerificationCodeMapper;
import com.miaoyu.barc.user.model.UserBasicModel;
import com.miaoyu.barc.user.model.VerificationCodeModel;
import com.miaoyu.barc.utils.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class SignService {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private VerificationCodeMapper verificationCodeMapper;
    @Autowired
    private UserBasicMapper userBasicMapper;

    public ResponseEntity<J> signInUserService(String type, String account, String password) {
        UserBasicModel userBasic;
        switch (type) {
            case "username": {
                userBasic = userBasicMapper.selectByUsername(account);
                break;
            } case "email": {
                userBasic = userBasicMapper.selectByEmail(account);
                break;
            } default: {
                return ResponseEntity.ok(new ErrorR().normal("参数错误！"));
            }
        }
        if (userBasic == null) {
            return ResponseEntity.ok(new UserR().noSuchUser());
        }
        String s = passwordHash(password);
        if (s == null) {
            return ResponseEntity.ok(new SignR().signIn(false));
        }
        if (s.equals(userBasic.getPassword())) {
            String token = jwtService.jwtSigned(userBasic.getUuid());
            return ResponseEntity.ok(new SuccessR().normal(token));
        } else {
            return ResponseEntity.ok(new ErrorR().normal("密码错误"));
        }
    }
    public ResponseEntity<J> signupUserService(UserBasicModel userBasic, String email) {
        if (!userBasic.getEmail().equals(email)) {
            return ResponseEntity.ok(new ErrorR().normal("注册邮箱与验证邮箱不一致"));
        }
        userBasic.setEmail_verified(true);
        String calcPwd = passwordHash(userBasic.getPassword());
        if (Objects.isNull(calcPwd)) {
            return ResponseEntity.ok(new SignR().signUp(false));
        }
        userBasic.setPassword(calcPwd);
        userBasic.setUuid(new GenerateUUID().getUuid32u());
        boolean insert = userBasicMapper.insert(userBasic);
        if (insert) {
            return ResponseEntity.ok(new SignR().signUp(true));
        }
        return ResponseEntity.ok(new SignR().signUp(false));
    }
    public ResponseEntity<J> getSignupCodeService(String email) {
        String code = new GenerateCode().code(6);
        VerificationCodeModel vc = new VerificationCodeModel();
        vc.setUnique_id(new GenerateUUID().getUuid36l());
        vc.setCode(code);
        vc.setScenario("Signup");
        vc.setUsername(email);
        boolean insert = verificationCodeMapper.insert(vc);
        if (insert) {
            boolean b = sendSignupEmail(email, code);
            if (b) {
                return ResponseEntity.ok(new EmailR().rKeyEmail(vc.getUnique_id()));
            } else {
                return ResponseEntity.ok(new EmailR().email(false));
            }
        } else {
            return ResponseEntity.ok(new ErrorR().normal("获取失败"));
        }
    }
    public J checkSignupCode(String uniqueId, String code, String email) {
        VerificationCodeModel vcDB = verificationCodeMapper.selectByUniqueId(uniqueId);
        if (vcDB == null) {
            return new ResourceR().resourceSuch(false, null);
        }
        if (!vcDB.getUsername().equals(email) || !vcDB.getScenario().equals("Signup") || vcDB.getUsed()) {
            return new ErrorR().normal("验证信息不匹配");
        }
        if (!vcDB.getCode().equals(code)) {
            return new ErrorR().normal("验证码不匹配");
        }
        if (vcDB.getExpiration_at().isBefore(LocalDateTime.now())) {
            return new ErrorR().normal("验证码已过期");
        }
        verificationCodeMapper.updateUsed(uniqueId);
        return new SuccessR().normal("验证成功");
    }
    @Autowired
    private JavaMailSender mailSender;

    private boolean sendSignupEmail(String to, String code) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom(new InternetAddress("barc@naigos.cn", "蔚蓝收录馆")); // 必须与配置的username一致
            helper.setTo(to);
            helper.setSubject("注册验证码");
            helper.setText("Sensei！欢迎您注册蔚蓝收录馆账号。注册验证码是：" + code + "，有效期30分钟！");
            mailSender.send(message);
        }  catch (jakarta.mail.MessagingException | UnsupportedEncodingException e) {
//            throw new RuntimeException(e);
            return false;
        }
        return true;
    }
    @Autowired
    private AppService appService;
    // 私有 根据加盐值和SHA256计算密码的哈希值
    private String passwordHash(String target){
        String pwdKey = appService.getPwdKey();
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String input = target + pwdKey;
            byte[] encodedHash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b: encodedHash){
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e){
            return null;
        }
    }
}
