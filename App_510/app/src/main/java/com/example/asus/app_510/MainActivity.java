package com.example.asus.app_510;

import android.animation.ValueAnimator;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView str;
    Thread thread = null;
    boolean isRunning = false;
    int begin = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        str = (TextView)findViewById(R.id.txt);
    }

    public void start_ani(View view){

        if (thread == null){
            isRunning = true;
            MyRunable myrun = new MyRunable();
            thread = new Thread(myrun);
            thread.start();
        }else{
            isRunning = !isRunning;
            thread.start();
        }

    }

    public void doStart(View view)
    {
        //truyền this (chính là MainActivity hiện tại) qua Child Thread
        MyAsyncTask mytt =new MyAsyncTask(this);
        //Kích hoạt Tiến trình
        //khi gọi hàm này thì onPreExecute của mytt sẽ thực thi trước
        mytt.execute();
    }




    public class MyRunable implements Runnable{
        @Override
        public void run() {

            for (int i=begin; i<65536; i++){
                if(isRunning){
                    //Log.i("alert",""+i);
                    // setText không được nằm ở luồng này vì setText nằm ở luồng UI Thread
                    try {
                        Thread.sleep(500); // dừng Thread trong khoảng thời gian milisecond

                        final int finalI = i; // final: để đảm bảo code đc an toán do i thay đổi không kiểm soát được ở các luồng khác (nếu nhi)
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                str.setText("" + finalI);
                            }
                        });

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    begin = i;

                }else{
                    return;
                }

            }

        }
    }

}
