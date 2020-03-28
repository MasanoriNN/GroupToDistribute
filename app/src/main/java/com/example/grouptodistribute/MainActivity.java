package com.example.grouptodistribute;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // TODO: グローバル変数にしてみても画面回転時に値が消えてしまう。
    // TODO: publicを外して、値を保存する方法を試してみるか？

    List<Map<String,Object>> memberStatusMapList; // 登録ボタンでこのリストに追加できるように宣言のみ外出しした。
    ListView memberListView; // ここで中身を入れるのは早すぎて無理なので宣言のみにしておく。

    int countOfMember;

    // Mapのキー
    final String[] FROM = {"number","name","check","group"};

    // リソースのコントロールID
    final int[] TO = {R.id.textView, R.id.memberNameTextView,R.id.checkBox,R.id.groupNameTextView};

    Map<String, Object> map;

    MyAdapter myAdapter;            // 外出しておかないとアダプターへの値変更通知時にfinalにしろって言われるから外出した。

    // カスタムアダプター
    private class MyAdapter extends  SimpleAdapter{

        // 外部から呼び出し可能なマップ
        public Map<Integer,Boolean> checkList = new HashMap<>();

        public MyAdapter(Context context, List<? extends Map<String, ?>> data,  // dataは文字列をキーとした値のMapを持ったList？
                         int resource, String[] from, int[] to) {
            super(context, data, resource, from, to);

            // 初期値を設定する
            for(int i=0; i<data.size();i++){
                Map map = (Map)data.get(i);

                // ここでチェック状態が入っておらず、nullが格納されているので要調査
                // 判明。データを作るよりも前にアダプターを作成していた。アダプタ作成を後ろにすることで解決。
                Log.d("log","checkの値は " + map.get("check").toString());
                checkList.put(i,(Boolean)map.get("check"));
            }
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);   // TODO: adapterのgetViewがどういう挙動、使い方をするのかを勉強したい。
            CheckBox ch = view.findViewById(R.id.checkBox);

            // チェックの状態が変化した場合はマップに記憶する      // TODO: ここでイベントを拾っている？ならここでcheckの値を変えてやれば良さそう
            ch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Log.d("checkBox",position+ "番目のonCheckedChangedが呼ばれました。");
                    Log.d("checkBox","isChecked is "+ isChecked);
                    checkList.put(position,isChecked);
                }
            });
            return view;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        memberListView = findViewById(R.id.listView);

        memberStatusMapList = new ArrayList<>();

        countOfMember = 0;

        // リストデータの生成
        map = new HashMap<>();
        map.put("number","NO.00000");
        map.put("name","安部");
        map.put("check",true);      // ここではmapにtrueが入っていた。
        map.put("group","");
        memberStatusMapList.add(map);
        countOfMember = countOfMember + 1;

        map =  new HashMap<>();
        map.put("number","NO.00001");
        map.put("name","内田");
        map.put("check",true);
        map.put("group","");
        memberStatusMapList.add(map);
        countOfMember = countOfMember + 1;

        map =  new HashMap<>();
        map.put("number","NO.00002");
        map.put("name","栗田");
        map.put("check",true);
        map.put("group","");
        memberStatusMapList.add(map);
        countOfMember = countOfMember + 1;

        map =  new HashMap<>();
        map.put("number","NO.00003");
        map.put("name","越川");
        map.put("check",true);
        map.put("group","");
        memberStatusMapList.add(map);
        countOfMember = countOfMember + 1;

        map =  new HashMap<>();
        map.put("number","NO.00004");
        map.put("name","高橋");
        map.put("check",true);
        map.put("group","");
        memberStatusMapList.add(map);
        countOfMember = countOfMember + 1;

        map =  new HashMap<>();
        map.put("number","NO.00005");
        map.put("name","戸田");
        map.put("check",true);
        map.put("group","");
        memberStatusMapList.add(map);
        countOfMember = countOfMember + 1;

        map =  new HashMap<>();
        map.put("number","NO.00006");
        map.put("name","宮永");
        map.put("check",true);
        map.put("group","");
        memberStatusMapList.add(map);
        countOfMember = countOfMember + 1;

        map =  new HashMap<>();
        map.put("number","NO.00007");
        map.put("name","森下");
        map.put("check",true);
        map.put("group","");
        memberStatusMapList.add(map);
        countOfMember = countOfMember + 1;

        map =  new HashMap<>();
        map.put("number","NO.00008");
        map.put("name","弓館");
        map.put("check",true);
        map.put("group","");
        memberStatusMapList.add(map);
        countOfMember = countOfMember + 1;

        // アダプターの設定
        myAdapter = new MyAdapter(this,
                memberStatusMapList, R.layout.list, FROM, TO);
        memberListView.setAdapter(myAdapter);  // TODO: setAdapterはどういう目的で使うのかそのうち調べたい。

        // イベント
        memberListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.this.setTitle(String.valueOf(position)+"番目がクリックされました。");
            }
        });

        findViewById(R.id.doneButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // リストビューのチェック状態をログに出力する
                ListView lv = findViewById(R.id.listView);
                for(int i = 0;i < lv.getCount();i++) {
                    MyAdapter adapter = (MyAdapter)lv.getAdapter();
                    View view = adapter.getView(i,null,lv);
                    TextView tv = view.findViewById(R.id.textView);

                    // checkboxを取りたいならこうする？
                    // CheckBox cb = view.findViewById(R.id.checkBox);

                    Log.i("MyTAG", "adapter.checkList.get(i) is " + adapter.checkList.get(i));
                    if(Boolean.TRUE.equals(adapter.checkList.get(i))) {      // TODO: ここでぬるぽ言われた。何がnullなのかを調べる。
                        Log.i("MyTAG", tv.getText().toString()+"はtrueです。");
                    }
                    else {
                        Log.i("MyTAG", tv.getText().toString()+"はfalseです。");
                    }
                }

                // AとBの数をカウントして差を求めたい。
                int countA = 0;
                int countB = 0;

                // AとBの差が2以上だった場合は繰り返すようにする。

                 do{ // 必ず一回は繰り返しを保証する。


                    // 判定した直後に初期化
                    countA = 0;
                    countB = 0;

                    for (int i = 0;i < lv.getCount();i++) {
                        MyAdapter adapter = (MyAdapter)lv.getAdapter();
                        View view = adapter.getView(i,null,lv);
                        TextView tv = view.findViewById(R.id.textView);

                        if(Boolean.TRUE.equals(adapter.checkList.get(i))) {      // bool値をそのまま==で比較するとぬるぽが出る
                            Log.i("MyTAG", tv.getText().toString()+"はtrueです。");
                            // AかBを割り振って、AかBかでそれぞれカウントアップする。
                            int random = new Random().nextInt(2);

                            Log.d("random",String.valueOf(random));

                            if(random == 0) {
                                map = memberStatusMapList.get(i);
                                map.put("group", "A");
                                countA++;
                            }else if(random == 1){
                                map = memberStatusMapList.get(i);
                                map.put("group", "B");
                                countB++;
                            }else{
                                Log.d("error","乱数が間違えています");
                            }
                        }
                        else {
                            // チェックボックスがオフだったらグループを空白にして終了。
                            Log.i("MyTAG", tv.getText().toString()+"はfalseです。");
                            map = memberStatusMapList.get(i);
                            map.put("group", "");
                            continue;
                        }
                    }
                    Log.d("while","countA = " + countA + "countB = " + countB );
                }while ((countA - countB)<=-2 || (countA - countB)>=2 ); // これはもっとスマートな方法がある気がする。

                // TODO 何故かチェックボックスをオフにしたものがオンになってしまうのを解決する。
                // 何故かチェックボックスオフの場合にonCheckedChangedが呼ばれている？アダプターへの通知の仕方が悪い？
                myAdapter.notifyDataSetChanged(); // アダプターに変更を通知することで画面を更新させる。

            }
        });

        // TODO: チェックボックスをタップしたときにマップの中身も書き換えてやらないと画面更新のたびにチェックボックスがデフォルトの値での表示になる。
        // TODO: どうやってチェックボックスの値が変更されたことを受け取る？
        // TODO: どうやってMAPの変数を変更する？
        // TODO: 画面上の表示が参考になるか？
        // TODO: リストを全部洗う形で何かできないか？

        findViewById(R.id.registrationButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // 0文字だったら何もしない
                if(((EditText) findViewById(R.id.inputNameEditText)).getText().length() == 0) {
                    return;
                }
                else{
                    Log.d("Button", "登録ボタンがタップされました。");
                    Map<String, Object> map = new HashMap<>();

                    // TODO : NOはカウントアップしてつけてやりたい。
                    // TODO : カウントアップ時に桁数によってNOの0の数を変える(count/10とかで桁数を求める？)
                    // TODO : nameはEditTextの中身をget
                    map.put("number", "NO.0000" + countOfMember);
                    countOfMember = countOfMember + 1;
                    map.put("name", ((EditText) findViewById(R.id.inputNameEditText)).getText());
                    map.put("check", true);
                    map.put("group","");
                    memberStatusMapList.add(map);

                    myAdapter.notifyDataSetChanged(); // アダプターに変更を通知することで画面を更新させる。
                }
                ((EditText) findViewById(R.id.inputNameEditText)).setText("");
            }
        });
    }
}
