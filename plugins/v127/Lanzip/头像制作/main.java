import java.io.File;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.List;

void onHandleMsg(Object msgInfo) {
    try {
        String content = msgInfo.getContent();
        String talker = msgInfo.getTalker();
        
        if (content == null) return;

        List atList = msgInfo.getAtUserList();
        if (atList == null || atList.isEmpty()) {
            return;
        }
        String targetWxid = (String) atList.get(0);

        // 摸头
        if (content.startsWith("摸头")) {
            startMotou(talker, targetWxid); 
        }
        // 射
        else if (content.startsWith("射")) {
            startShe(talker, targetWxid);
        }
        // 打屁股
        else if (content.startsWith("打屁股")) {
            startDa(talker, targetWxid);
        }

    } catch (Throwable e) {
        toast("执行异常");
    }
}

// ====================== 摸头 ======================
void startMotou(String talker, String wxid) { 
    new Thread(new Runnable() {
        public void run() {
            try {
                String cacheDir = "/storage/emulated/0/Android/media/com.tencent.mm/WAuxiliary/Cache/Motou/"; 
                File dir = new File(cacheDir);
                if (!dir.exists()) dir.mkdirs();
                File gifFile = new File(dir, wxid + "_motou.gif"); 
                String avatar = getAvatarUrl(wxid);
                
                if (avatar == null || avatar.equals("")) {
                    toast("获取头像失败");
                    return;
                }
                String api = "https://api.52vmy.cn/api/avath/rua?url=" + avatar;
                download(api, gifFile.getAbsolutePath());

                if (gifFile.exists() && gifFile.length() > 2048) {
                    sendEmoji(talker, gifFile.getAbsolutePath());
                } else {
                    toast("摸头生成失败");
                }
            } catch (Throwable e) {
                toast("摸头失败");
            }
        }
    }).start();
}

// ====================== 射 ======================
void startShe(String talker, String wxid) {
    new Thread(new Runnable() {
        public void run() {
            try {
                String cacheDir = "/storage/emulated/0/Android/media/com.tencent.mm/WAuxiliary/Cache/She/";
                File dir = new File(cacheDir);
                if (!dir.exists()) dir.mkdirs();

                File gifFile = new File(dir, wxid + "_she.gif");
                String avatar = getAvatarUrl(wxid);
                
                if (avatar == null || avatar.equals("")) {
                    toast("获取头像失败");
                    return;
                }

                String api = "https://api.bi71t5.cn/api/yaoshel.php?url=" + avatar;
                download(api, gifFile.getAbsolutePath());

                if (gifFile.exists() && gifFile.length() > 2048) {
                    sendEmoji(talker, gifFile.getAbsolutePath());
                } else {
                    toast("射生成失败");
                }
            } catch (Throwable e) {
                toast("射失败");
            }
        }
    }).start();
}

// ====================== 打屁股 ======================
void startDa(String talker, String wxid) {
    new Thread(new Runnable() {
        public void run() {
            try {
                String cacheDir = "/storage/emulated/0/Android/media/com.tencent.mm/WAuxiliary/Cache/Da/";
                File dir = new File(cacheDir);
                if (!dir.exists()) dir.mkdirs();

                File gifFile = new File(dir, wxid + "_da.gif");
                String avatar = getAvatarUrl(wxid);
                
                if (avatar == null || avatar.equals("")) {
                    toast("获取头像失败");
                    return;
                }

                String api = "https://api.bi71t5.cn/api/da.php?url=" + avatar;
                download(api, gifFile.getAbsolutePath());

                if (gifFile.exists() && gifFile.length() > 2048) {
                    sendEmoji(talker, gifFile.getAbsolutePath());
                } else {
                    toast("打屁股生成失败");
                }
            } catch (Throwable e) {
                toast("打屁股失败");
            }
        }
    }).start();
}

// ====================== 下载工具 ======================
void download(String urlStr, String savePath) {
    InputStream in = null;
    FileOutputStream out = null;
    try {
        URL url = new URL(urlStr);
        in = url.openStream();
        out = new FileOutputStream(savePath);

        byte[] buf = new byte[8192];
        int len;
        while ((len = in.read(buf)) != -1) {
            out.write(buf, 0, len);
        }
    } catch (Throwable e) {
        e.printStackTrace();
    } finally {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
        } catch (Throwable e) {}
    }
}

void toast(String s) {
    try {
        android.widget.Toast.makeText(getTopActivity(), s, 0).show();
    } catch (Throwable e) {}
}
