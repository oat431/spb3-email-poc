package panomete.poc.spb3email.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import panomete.poc.spb3email.service.EmailService;

import java.util.HashMap;

@Controller
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;
    private final String WELCOME_SUBJECT = "การสร้างผู้ใช้งานใหม่";
    private final String RESET_PASSWORD_SUBJECT = "การ Reset password";
    private final String USERNAME = "oat431@outlook.com";
    private final String NAME = "Sahachan Tippimwong";
//    private final String USERNAME = "jiramedwi@gmail.com";
//    private final String NAME = "Jiramed Withunsabphasiri";

    @GetMapping("/send-welcome-email")
    public ResponseEntity<?> sendEmail() {
        emailService.sendEmail(
                USERNAME,
                WELCOME_SUBJECT + "(UAT SCFC)",
                genWelcomeEmailTemplate(
                        NAME,
                        USERNAME,
                        genTempPassword()
                )
        );
        HashMap<String, String> map = new HashMap<>();
        map.put("message", "Welcome email sent successfully");
        return ResponseEntity.ok(map);
    }

    @GetMapping("/send-reset-password-email")
    public ResponseEntity<?> sendResetPasswordEmail() {
        emailService.sendEmail(
                USERNAME,
                RESET_PASSWORD_SUBJECT + "(UAT SCFC)",
                genResetPasswordEmailTemplate(
                        NAME,
                        "https://admin.scfc.cmu.ac.th/reset-password?token=" + genTempPassword()
                )
        );
        HashMap<String, String> map = new HashMap<>();
        map.put("message", "Reset Password Email sent successfully");
        return ResponseEntity.ok(map);
    }

    private String genTempPassword() {
        StringBuilder sb = new StringBuilder();
        String lowCase = "abcdefghijklmnopqrstuvxyz";
        String upCase = "ABCDEFGHIJKLMNOPQRSTUVXYZ";
        String numbers = "0123456789";
        String specialChar = "£$&()*+[]@#^-_!?";
        for (int i = 0; i < 8; i++) {
            int random = (int) (Math.random() * 4);
            switch (random) {
                case 0:
                    sb.append(lowCase.charAt((int) (Math.random() * lowCase.length())));
                    break;
                case 1:
                    sb.append(upCase.charAt((int) (Math.random() * upCase.length())));
                    break;
                case 2:
                    sb.append(numbers.charAt((int) (Math.random() * numbers.length())));
                    break;
                case 3:
                    sb.append(specialChar.charAt((int) (Math.random() * specialChar.length())));
                    break;
            }
        }
        return sb.toString();
    }

    private String genWelcomeEmailTemplate(String name, String username, String tempPassword) {
        String template = """
<!DOCTYPE html>
<html lang="th">
<head>
  <meta charset="UTF-8">
  <title>ข้อมูลบัญชีผู้ใช้ | SCFC Admin Website</title>
  <style>
      /* —— Basic, Gmail-safe inline CSS —— */
      body { margin:0; padding:0; background:#f6f9fc; font-family:Helvetica, Arial, sans-serif; }
      .wrapper { max-width:600px; margin:0 auto; background:#ffffff; border:1px solid #e0e0e0; }
      .header  { background:#5630c6; color:#ffffff; padding:24px 20px; text-align:center; }
      .header h2 { margin:0; font-weight:600; font-size:20px; }
      .content { padding:24px 20px; color:#333333; line-height:1.6; font-size:15px; }
      .content ul { margin:8px 0 16px 18px; padding:0; }
      .btn { display:inline-block; margin:12px 0; padding:12px 22px; background:#5630c6; color:#ffffff !important;
             text-decoration:none; border-radius:4px; font-weight:600; font-size:14px; }
      .footer { font-size:12px; color:#777777; padding:18px 20px; background:#f2f2f2; text-align:center; }
      a       { color:#5630c6; }
  </style>
</head>
<body>
  <div class="wrapper">
    <div class="header">
      <h2>ข้อมูลบัญชีผู้ใช้สำหรับระบบ SCFC Admin Website</h2>
    </div>

    <div class="content">
      <p>เรียน %1$s,</p>

      <p>บัญชีผู้ใช้ของคุณสำหรับระบบ <strong>SCFC Admin Website</strong>
         ได้ถูกสร้างขึ้นเรียบร้อยแล้ว คุณสามารถใช้ข้อมูลต่อไปนี้เพื่อลงชื่อเข้าใช้:</p>

      <ul>
        <li><strong>ชื่อผู้ใช้ (Username):</strong> %2$s</li>
        <li><strong>รหัสผ่านเริ่มต้น:</strong> %3$s</li>
      </ul>

      <p>
        <a href="https://admin.scfc.cmu.ac.th/login" class="btn" target="_blank" rel="noopener">
          เข้าสู่ระบบ
        </a>
      </p>

      <p><strong>โปรดเปลี่ยนรหัสผ่านของคุณทันที</strong>
         หลังจากเข้าสู่ระบบเป็นครั้งแรกเพื่อความปลอดภัยของบัญชี
         โดยกดลิงก์ด้านล่างนี้:</p>

      <p>
        <a href="https://admin.scfc.cmu.ac.th/reset-password" class="btn" target="_blank" rel="noopener">
          รีเซ็ตรหัสผ่านที่นี่
        </a>
      </p>

      <h3 style="margin:24px 0 8px 0; font-size:16px;">ข้อแนะนำสำหรับรหัสผ่านที่ปลอดภัย</h3>
      <ul>
        <li>ควรมีอักขระอย่างน้อย 8 ตัว</li>
        <li>ใช้อักษรตัวพิมพ์ใหญ่ ตัวพิมพ์เล็ก ตัวเลข และอักขระพิเศษ</li>
        <li>หลีกเลี่ยงการใช้รหัสผ่านเดิมหรือรหัสผ่านที่เดาง่าย</li>
      </ul>

      <p>
        หากคุณพบปัญหาในการเข้าสู่ระบบหรือเปลี่ยนรหัสผ่าน
        กรุณาติดต่อฝ่าย IT ที่
        <a href="mailto:siriwat.at@cmu.ac.th">siriwat.at@cmu.ac.th</a>
        |
        <a href="tel:+6653935177">053-935177</a>
      </p>

      <p style="margin-top:32px;">
        ขอแสดงความนับถือ,<br>
        สิริวัฒน์ อัฐวงศ์ (นุ๊ก)<br>
        ศูนย์แก้ไขความพิการบนใบหน้าและกะโหลกศีรษะฯ<br>
        <a href="mailto:siriwat.at@cmu.ac.th">siriwat.at@cmu.ac.th</a>
        |
        <a href="tel:+6653935177">053-935177</a>
      </p>
    </div>

    <div class="footer">
      © SCFC, Chiang Mai University
    </div>
  </div>
</body>
</html>
""";


        return String.format(template, name, username, tempPassword);
    }

    private String genResetPasswordEmailTemplate(String name, String tempLink) {
        String template = """
<!DOCTYPE html>
<html lang="th">
<head>
  <meta charset="UTF-8">
  <title>รีเซ็ตรหัสผ่าน | SCFC Admin Website</title>
  <style>
    body      { margin:0; padding:0; background:#f6f9fc; font-family:Helvetica, Arial, sans-serif; }
    .wrapper  { max-width:600px; margin:0 auto; background:#ffffff; border:1px solid #e0e0e0; }
    .header   { background:#d72c4e; color:#ffffff; padding:24px 20px; text-align:center; }
    .header h2{ margin:0; font-weight:600; font-size:20px; }
    .content  { padding:24px 20px; color:#333333; line-height:1.6; font-size:15px; }
    .btn      { display:inline-block; margin:12px 0; padding:12px 22px; background:#d72c4e; color:#ffffff !important;
                text-decoration:none; border-radius:4px; font-weight:600; font-size:14px; }
    .content ul{ margin:8px 0 16px 18px; padding:0; }
    .footer   { font-size:12px; color:#777777; padding:18px 20px; background:#f2f2f2; text-align:center; }
    a         { color:#d72c4e; }
  </style>
</head>
<body>
  <div class="wrapper">
    <div class="header">
      <h2>รีเซ็ตรหัสผ่านของคุณสำหรับ SCFC Admin Website</h2>
    </div>

    <div class="content">
      <p>เรียน %1$s,</p>

      <p>เราได้รับคำขอให้รีเซ็ตรหัสผ่านของคุณสำหรับระบบ <strong>SCFC Admin Website</strong></p>

      <p>
        หากคุณเป็นผู้ร้องขอ โปรดคลิกปุ่มด้านล่างเพื่อสร้างรหัสผ่านใหม่:
      </p>

      <p>
        <a href="%2$s" class="btn" target="_blank" rel="noopener">
          กดที่นี่เพื่อรีเซ็ตรหัสผ่าน
        </a>
      </p>

      <p><em>ลิงก์นี้จะหมดอายุภายใน 24&nbsp;ชั่วโมงเพื่อความปลอดภัย</em>  
         หากคุณไม่ได้ร้องขอให้รีเซ็ตรหัสผ่าน กรุณาเพิกเฉยต่ออีเมลฉบับนี้ และแจ้งฝ่าย IT ทันทีที่
         <a href="mailto:siriwat.at@cmu.ac.th">siriwat.at@cmu.ac.th</a> |
         <a href="tel:+6653935177">053-935177</a>
      </p>

      <h3 style="margin:24px 0 8px 0; font-size:16px;">เคล็ดลับสำหรับรหัสผ่านที่ปลอดภัย</h3>
      <ul>
        <li>ใช้อักขระอย่างน้อย 8 ตัว</li>
        <li>ใช้ตัวพิมพ์ใหญ่ / ตัวพิมพ์เล็ก / ตัวเลข / อักขระพิเศษ</li>
        <li>หลีกเลี่ยงการใช้รหัสผ่านซ้ำจากแพลตฟอร์มอื่น</li>
      </ul>

      <p style="margin-top:32px;">
        ขอแสดงความนับถือ,<br>
        สิริวัฒน์ อัฐวงศ์ (นุ๊ก)<br>
        ศูนย์แก้ไขความพิการบนใบหน้าและกะโหลกศีรษะฯ<br>
        <a href="mailto:siriwat.at@cmu.ac.th">siriwat.at@cmu.ac.th</a> |
        <a href="tel:+6653935177">053-935177</a>
      </p>
    </div>

    <div class="footer">
      © SCFC, Chiang Mai University
    </div>
  </div>
</body>
</html>
""";

        return String.format(template, name, tempLink);
    }
}
