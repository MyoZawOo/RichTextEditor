package com.scrat.app.richtexteditor;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import android.view.ActionMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewParent;

import com.scrat.app.richtext.RichEditText;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_GET_CONTENT = 666;
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 444;
    private RichEditText richEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        richEditText = (RichEditText) findViewById(R.id.rich_text);

//        richEditText.fromHtml("<blockquote>Android 端的富文本编辑器</blockquote>" +
//                "<ul><li>支持实时编辑</li><li>支持图片插入,加粗,斜体,下划线,删除线,列表,引用块,超链接,撤销与恢复等</li><li>使用<u>Glide</u>加载图片</li></ul>\n" +
//                "<img src=\"http://img5.duitang.com/uploads/item/201409/07/20140907195835_GUXNn.thumb.700_0.jpeg\">" +
//                "<img src=\"http://www.bz55.com/uploads/allimg/150707/139-150FG61K2.jpg\">");
        richEditText.setCustomSelectionActionModeCallback(actionModeCallBack);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.undo:
                richEditText.undo();
                break;
            case R.id.redo:
                richEditText.redo();
                break;
            case R.id.export:
                Log.e("xxx", richEditText.toHtml());
                break;
            default:
                break;
        }

        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (data == null || data.getData() == null || requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE)
            return;

        final Uri uri = data.getData();
        final int width = richEditText.getMeasuredWidth() - richEditText.getPaddingLeft() - richEditText.getPaddingRight();
        richEditText.image(uri, width);
    }

    public void setBiggerText(View v) {
        richEditText.biggerText(!richEditText.contains(RichEditText.FORMAT_BIGGERTEXT));
    }


    /**
     * 加粗
     */
    public void setBold(View v) {
        richEditText.bold(!richEditText.contains(RichEditText.FORMAT_BOLD));

    }

    /**
     * 斜体
     */
    public void setItalic(View v) {
        richEditText.italic(!richEditText.contains(RichEditText.FORMAT_ITALIC));
    }

    /**
     * 下划线
     */
    public void setUnderline(View v) {
        richEditText.underline(!richEditText.contains(RichEditText.FORMAT_UNDERLINED));
    }

    /**
     * 删除线
     */
    public void setStrikethrough(View v) {
        richEditText.strikethrough(!richEditText.contains(RichEditText.FORMAT_STRIKETHROUGH));
    }

    /**
     * 序号
     */
    public void setBullet(View v) {
        richEditText.bullet(!richEditText.contains(RichEditText.FORMAT_BULLET));
    }

    /**
     * 引用块
     */
    public void setQuote(View v) {
        richEditText.quote(!richEditText.contains(RichEditText.FORMAT_QUOTE));
    }

    public void insertImg(View v) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
        }

        Intent getImage = new Intent(Intent.ACTION_GET_CONTENT);
        getImage.addCategory(Intent.CATEGORY_OPENABLE);
        getImage.setType("image/*");
        startActivityForResult(getImage, REQUEST_CODE_GET_CONTENT);
    }

    private ActionMode.Callback actionModeCallBack = new ActionMode.Callback()

    {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_text_selection, menu);
            menu.removeItem(android.R.id.selectAll);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.bold:
                    richEditText.bold(!richEditText.contains(RichEditText.FORMAT_BOLD));
                    return true;
                case R.id.italic:
                    richEditText.italic(!richEditText.contains(RichEditText.FORMAT_ITALIC));
                    return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }

    };


}
