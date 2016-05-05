package cc.linkedme.commons.mail;

/**
 * Created by LinkedME01 on 16/3/16.
 */
import cc.linkedme.commons.log.ApiLogger;

import java.util.Date;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class MailSender {

    public static Message getMailMsg(String toAddress, String mailSubject, String mailContent) {
        MailSenderInfo mailInfo = new MailSenderInfo();
        mailInfo.setToAddress(toAddress);
        mailInfo.setSubject(mailSubject);
        mailInfo.setContent(mailContent);
        Properties pro = mailInfo.getProperties();
        MailAuthenticator authenticator = new MailAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
        try {
            // 根据session创建一个邮件消息
            Message mailMessage = new MimeMessage(sendMailSession);

            // 创建邮件发送者地址
            Address from = new InternetAddress(mailInfo.getFromAddress());

            // 设置邮件消息的发送者
            mailMessage.setFrom(from);

            // 创建邮件的接收者地址，并设置到邮件消息中
            Address to = new InternetAddress(mailInfo.getToAddress());
            mailMessage.setRecipient(Message.RecipientType.TO, to);

            // 设置邮件消息的主题
            mailMessage.setSubject(mailInfo.getSubject());

            // 设置邮件消息的主要内容
            mailMessage.setText(mailInfo.getContent());

            // 设置邮件消息发送的时间
            mailMessage.setSentDate(new Date());

            return mailMessage;
        } catch (MessagingException e) {
            ApiLogger.error("Send mail failed!", e);
        }
        return null;
    }

    /**
     * 以文本格式发送邮件
     *
     * @param toAddress 收件人的邮箱
     * @param mailSubject 邮件主题
     * @param mailContent 邮件内容
     */
    public static boolean sendTextMail(String toAddress, String mailSubject, String mailContent) {
        try {
            Transport.send(getMailMsg(toAddress, mailSubject, mailContent));
            return true;
        } catch (MessagingException e) {
            ApiLogger.error("Send mail failed!", e);
        }
        return false;
    }

    /**
     * 以HTML格式发送邮件
     *
     * @param toAddress 收件人的邮箱
     * @param mailSubject 邮件主题
     * @param mailContent 邮件内容
     */
    public static boolean sendHtmlMail(String toAddress, String mailSubject, String mailContent) {
        Message mailMessage = getMailMsg(toAddress, mailSubject, mailContent);
        Multipart mainPart = new MimeMultipart();
        // 创建一个包含HTML内容的MimeBodyPart
        BodyPart html = new MimeBodyPart();
        try {
            // 设置HTML内容
            html.setContent(mailContent, "text/html; charset=utf-8");
            mainPart.addBodyPart(html);

            // 将MiniMultipart对象设置为邮件内容
            mailMessage.setContent(mainPart);

            // 发送邮件
            Transport.send(mailMessage);
            return true;
        } catch (MessagingException e) {
            ApiLogger.error("Send mail failed!", e);
        }
        return false;
    }

    public static void main(String[] args) {
//        MailSender.sendTextMail("276386627@qq.com", "hello, wrshine", "this is a test mail from java program");
//        MailSender.sendHtmlMail("276386627@qq.com", "hello, wrshine", "this is a test mail </br> from java program");
//        MailSender.sendHtmlMail("wrshine@163.com", "hello, wrshine", "this is a test mail </br> from java program");
//        MailSender.sendHtmlMail("wrshine@gmail.com", "hello, wrshine", "this is a test mail </br> from java program");
        String url = "http://www.weibo.com";
        String resetPwdUrl = "https://www.linkedme.cc/dashboard/index.html#/access/resetpwd/123456";
        MailSender.sendHtmlMail("support@linkedme.cc", "hello, wrshine", "this is a test mail. <br /> from java program. <br /> <a href="+resetPwdUrl+">W3School</a>");
        MailSender.sendHtmlMail("wrshine@163.com", "hello, wrshine", "this is a test mail. <br /> from java program. <br /> <a href="+resetPwdUrl+">W3School</a>");
        System.out.println("success");
    }
}
