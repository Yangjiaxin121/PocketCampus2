package com.example.yangjiaxin.pocketcampus;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class MainActivity extends AppCompatActivity {

    private EditText etstuId;
    private EditText etstuPsw;
    private EditText etCode;
    private ImageView ivCode;
    private TextView tvContent;

    //验证码url
    private String codeUrl = "http://59.74.168.16:8989/CheckCode.aspx";
    //登录url
    private String loginUrl = "http://59.74.168.16:8989/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initCode();
    }

    private void initView() {
        etstuId = (EditText) findViewById(R.id.et_stuid);
        etstuPsw = (EditText) findViewById(R.id.et_stupsw);
        etCode = (EditText) findViewById(R.id.et_code);
        ivCode = (ImageView) findViewById(R.id.iv_code);
        tvContent = (TextView) findViewById(R.id.tv_content);
    }

    /**
     * 加载验证码
     */
    private void initCode() {
        OkHttpUtils
                .get()
                .url(codeUrl)
                .build()
                .connTimeOut(5000)
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(okhttp3.Call call, Exception e) {
                        //加载失败
                    }

                    @Override
                    public void onResponse(Bitmap response) {
                        //设置验证码
                        ivCode.setImageBitmap(response);
                    }
                });
    }

    /**
     * 切换验证码
     *
     * @param view
     */

    public void reloadcode(View view) {
        codeUrl += '?';
        //修改url后重庆请求验证码
        initCode();
    }

    /**
     * 向服务器登录
     *
     * @param view
     */
    public void login(View view) {
        //用户输入的值
        String stuId = etstuId.getText().toString();
        String stuPsw = etstuPsw.getText().toString();
        final String code = etCode.getText().toString();

        //这里应该做一些空值判断

        //请求登录
        OkHttpUtils.post()
                //loginUrl就是你请求登录的url
                .url(loginUrl)
                //下面数据抓包可以得到
                .addParams("__VIEWSTATE", "dDwxNTMxMDk5Mzc0Ozs+0HHdkNocnQDf87JMO+qbSsFc0ew=")
                .addParams("__VIEWSTATEGENERATOR", "92719903")
                .addParams("txtUserName", stuId) //学号，
                .addParams("TextBox2", stuPsw)//密码
                .addParams("txtSecretCode", code) //验证码
                .addParams("RadioButtonList1", "%D1%A7%C9%FA")
                .addParams("Button1", "")
                .addParams("lbLanguage", "")
                .addHeader("Host", "59.74.168.16:8989")
                .addHeader("Referer", "http://59.74.168.16:8989/default2.aspx")
                .build()
                .connTimeOut(5000)
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e) {
                        //请求失败
                    }

                    @Override
                    public void onResponse(String response) {
                        Intent intent;
                       // System.out.println("onResponse");
                        //请求成功，response就是得到的html文件（网页源代码）
                        if (response.contains("验证码不正确") || code.equals("")) {
                            //如果源代码包含“验证码不正确”
                            Toast.makeText(MainActivity.this,"验证码不正确",Toast.LENGTH_SHORT).show();

                        } else if (response.contains("密码错误")) {
                            //如果源代码包含“密码错误”
                            Toast.makeText(MainActivity.this,"密码不正确",Toast.LENGTH_SHORT).show();

                        } else if (response.contains("用户名不存在")) {
                            //如果源代码包含“用户名不存在”
                            Toast.makeText(MainActivity.this,"用户不存在",Toast.LENGTH_SHORT).show();

                        } else {
                            //登录成功
                            Toast.makeText(MainActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                            //tvContent.setText(response);
                            intent = new Intent(MainActivity.this,ZhuActivity.class);
                            startActivity(intent);
                        }
                    }
                });
    }
}

