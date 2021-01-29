//import com.xiaomi.xmpush.server.Constants;
//import com.xiaomi.xmpush.server.Message;
//import com.xiaomi.xmpush.server.Result;
//import com.xiaomi.xmpush.server.Sender;
//
///**
// * @author liujiabei
// * @since 2019/6/11
// */
//public class TestMiPush {
//    public static void main(String[] args){
//        Constants.useOfficial();
//        Sender sender = new Sender(APP_SECRET_KEY);
//        String messagePayload = "This is a message";
//        String title = "notification title";
//        String description = "notification description";
//        Message message = new Message.Builder()
//                .title(title)
//                .description(description).payload(messagePayload)
//                .restrictedPackageName(MY_PACKAGE_NAME)
//                .notifyType(1)     // 使用默认提示音提示
//                .build();
//        Result result = sender.send(message, regId, 3);
//        Log.v("Server response: ", "MessageId: " + result.getMessageId()
//                + " ErrorCode: " + result.getErrorCode().toString()
//                + " Reason: " + result.getReason());
//    }
//}
