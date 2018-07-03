package com.example.yuhei.shukatsuapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yuhei.shukatsuapp.memoDatabase.HelpMemo;
import com.example.yuhei.shukatsuapp.memoDatabase.MemoDatabaseAccess;

import java.util.List;

public class MemoFragment extends Fragment {

    private ListView listView;
    private Button btnAdd;
    private MemoDatabaseAccess databaseAccess;
    private List<HelpMemo> memos;

//    MemoOpenHelper helper = null;


    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.memo, container, false);

        this.databaseAccess = MemoDatabaseAccess.getInstance(getActivity());

        this.listView = view.findViewById(R.id.listView);
        this.btnAdd = view.findViewById(R.id.btnAdd);

        this.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddClicked();
            }
        });

        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HelpMemo memo = memos.get(position);
                TextView txtMemo = (TextView) view.findViewById(R.id.txtMemo);
                if (memo.isFullDisplayed()) {
                    txtMemo.setText(memo.getShortText());
                    memo.setFullDisplayed(false);
                } else {
                    txtMemo.setText(memo.getText());
                    memo.setFullDisplayed(true);
                }
            }
        });
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        databaseAccess.open();
        this.memos = databaseAccess.getAllMemos();
        databaseAccess.close();
        MemoAdapter adapter = new MemoAdapter(getActivity(), memos);
        this.listView.setAdapter(adapter);
    }

    public void onAddClicked() {
        Intent intent = new Intent(getActivity(), MemoEditActivity.class);
        startActivity(intent);
    }

    public void onDeleteClicked(HelpMemo memo) {
        databaseAccess.open();
        databaseAccess.delete(memo);
        databaseAccess.close();

        ArrayAdapter<HelpMemo> adapter = (ArrayAdapter<HelpMemo>) listView.getAdapter();
        adapter.remove(memo);
        adapter.notifyDataSetChanged();
    }

    public void onEditClicked(HelpMemo memo) {
        Intent intent = new Intent(getActivity(), MemoEditActivity.class);
        intent.putExtra("MEMO", memo);
        startActivity(intent);
    }

    private class MemoAdapter extends ArrayAdapter<HelpMemo> {


        public MemoAdapter(Context context, List<HelpMemo> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.layout_memolist_item, parent, false);
            }

            ImageView btnEdit = convertView.findViewById(R.id.btnEdit);
            ImageView btnDelete =  convertView.findViewById(R.id.btnDelete);
            TextView txtDate = convertView.findViewById(R.id.txtDate);
            TextView txtMemo = convertView.findViewById(R.id.txtMemo);

            final HelpMemo memo = memos.get(position);
            memo.setFullDisplayed(false);
            txtDate.setText(memo.getDate());
            txtMemo.setText(memo.getShortText());
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onEditClicked(memo);
                }
            });
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDeleteClicked(memo);
                }
            });
            return convertView;
        }
    }
}
//        View view = inflater.inflate(R.layout.memo, container, false);
//
//        if (helper == null) {
//            helper = new MemoOpenHelper(getContext());
//        }
//        final ArrayList<HashMap<String, String>> memolist = new ArrayList<>();
//        SQLiteDatabase db = helper.getWritableDatabase();
//        try {
//            Cursor c = db.rawQuery("select uuid, body from MEMO_TABLE order by id", null);
//            boolean next = c.moveToFirst();
//
//            while (next) {
//                HashMap<String, String> data = new HashMap<>();
//
//                String uuid = c.getString(0);
//                String body = c.getString(1);
//                if (body.length() > 10) {
//                    body = body.substring(0, 11) + "...";
//                }
//                data.put("body", body);
//                data.put("id", uuid);
//                memolist.add(data);
//
//                next = c.moveToNext();
//            }
//        } finally {
//            db.close();
//        }
//
//        final SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), memolist,
//                android.R.layout.simple_list_item_2,
//                new String[]{"body", "id"},
//                new int[]{android.R.id.text1, android.R.id.text2});
//
//        ListView listView = view.findViewById(R.id.memoList);
//        listView.setAdapter(simpleAdapter);
//
//
//         listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//             @Override
//             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                 Intent intent = new Intent(getActivity(), MemoActivity.class);
//
//                 TwoLineListItem two = (TwoLineListItem) view;
//
//
//                 TextView idTextView = two.getText2();
//                 String idStr = (String) idTextView.getText();
//
//                 intent.putExtra("id", idStr);
//                 startActivity(intent);
//
//             }
//         });
//
//
//
//
//        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                TwoLineListItem two = (TwoLineListItem) view;
//                TextView idTextView = (TextView) two.getText2();
//                String idStr = (String) idTextView.getText();
//
//                SQLiteDatabase db = helper.getWritableDatabase();
//                try {
//                    db.execSQL("DELETE FROM MEMO_TABLE WHERE uuid = '" + idStr + "'");
//
//                } finally {
//                    db.close();
//                }
//                memolist.remove(position);
//                simpleAdapter.notifyDataSetChanged();
//                return true;
//            }
//        });
//
//        Button newButton = view.findViewById(R.id.newButton);
//        newButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(),MemoActivity.class);
//                intent.putExtra("id", "");
//                startActivity(intent);
//            }
//        });
//
//        return view;
//
//
//    }
//}
