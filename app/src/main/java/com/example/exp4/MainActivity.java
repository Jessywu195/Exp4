package com.example.exp4;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
        Button bind, unbind, getServiceStatus;
        // 保持所启动的Service的IBinder对象
        BindService.MyBinder binder;
        // 定义一个ServiceConnection对象
        private ServiceConnection conn = new ServiceConnection() {
            // 当该Activity与Service连接成功时回调该方法
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                System.out.println("--Service Connected--");
                binder = (BindService.MyBinder) service;
            }

            // 当该Activity与Service断开连接时回调该方法
            @Override
            public void onServiceDisconnected(ComponentName name) {
                System.out.println("--Service Disconnected--");
            }
        };

        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            // 获取程序界面中的start、stop、getServiceStatus按钮
            bind =  findViewById(R.id.btn_bind);
            unbind =  findViewById(R.id.btn_unbind);
            getServiceStatus =  findViewById(R.id.btn_Status);
            //创建启动Service的Intent
            final Intent intent = new Intent(this,BindService.class);
            //为Intent设置Action属性
intent.setAction("org.crazyit.service.BIND_SERVICE");
            bind.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View source){
                    //绑定指定Serivce
                    bindService(intent,conn, Service.BIND_AUTO_CREATE);
                }
            });

            unbind.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View source){
                    //解除绑定Serivce
                    unbindService(conn);
                }
            });
            getServiceStatus.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View source){
                    // 获取、并显示Service的count值
                    Toast.makeText(MainActivity.this
                            , "Serivce的count值为：" + binder.getCount(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
