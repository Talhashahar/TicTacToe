package com.games.tictactoe.tictactoe;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

public class TicTacToeDialog extends Dialog {


    private TextView mTitle;
    private TextView mData;
    private String titleString;
    private String dataString;

    public TicTacToeDialog(@NonNull Context context) {
        super(context, android.R.style.Theme_DeviceDefault_Light);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.tic_tac_toe_dialog_layout);

        mTitle = findViewById(R.id.title);
        mData = findViewById(R.id.data);

        if(mTitle != null) {
            mTitle.setText(titleString);
        }

        if(mData != null) {
            mData.setText(dataString);
        }

        findViewById(R.id.exit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dismiss();
    }

    private void setTitle(String title) {
        titleString = title;

    }

    private void setData(String data) {
        dataString = data;

    }

    final static public class Builder {
        private String titleString;
        private String dataString;

        public Builder() {
        }

        public Builder addTitle(String title) {
            titleString = title;
            return this;
        }

        public Builder addData(String data) {
            dataString = data;
            return this;
        }

        public Builder clearData() {
            dataString = "";
            return this;
        }

        public void show(Context context) {
            TicTacToeDialog dialog = new TicTacToeDialog(context);
            dialog.setData(dataString);
            dialog.setTitle(titleString);
            dialog.show();
        }
    }

}
