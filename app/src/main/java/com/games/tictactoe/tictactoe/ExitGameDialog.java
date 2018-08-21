package com.games.tictactoe.tictactoe;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class ExitGameDialog extends Dialog {

    private View.OnClickListener resetMatchListener;
    private View.OnClickListener quitGameListener;
    private View.OnClickListener backToMainMenuListener;


    public ExitGameDialog(@NonNull Context context) {
        super(context, android.R.style.Theme_DeviceDefault_Light);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.exit_game_dialog_layout);

        if(resetMatchListener != null) {
            findViewById(R.id.reset_match).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resetMatchListener.onClick(v);
                    dismiss();
                }
            });
        } else {
            findViewById(R.id.reset_match).setVisibility(View.GONE);
        }

        if(backToMainMenuListener != null) {
            findViewById(R.id.back_to_main_manu).setOnClickListener(backToMainMenuListener);
        } else {
            findViewById(R.id.back_to_main_manu).setVisibility(View.GONE);
        }

        if(quitGameListener != null) {
            findViewById(R.id.quit_the_game).setOnClickListener(quitGameListener);
        } else {
            findViewById(R.id.quit_the_game).setVisibility(View.GONE);
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

    private void setResetMatchListener(View.OnClickListener clickListener) {
        resetMatchListener = clickListener;

    }

    private void setQuitGameListener(View.OnClickListener clickListener) {
        quitGameListener = clickListener;

    }

    private void setBackToMainMenuListener(View.OnClickListener clickListener) {
        backToMainMenuListener = clickListener;

    }

    final static public class Builder {
        private View.OnClickListener resetMatchListener;
        private View.OnClickListener quitGameListener;
        private View.OnClickListener backToMainMenuListener;

        public Builder() {
        }

        public Builder addResetMatchButton(View.OnClickListener clickListener) {
            resetMatchListener = clickListener;
            return this;
        }

        public Builder addQuitGameButton(View.OnClickListener clickListener) {
            quitGameListener = clickListener;
            return this;
        }

        public Builder addBackToMenuButton(View.OnClickListener clickListener) {
            backToMainMenuListener = clickListener;
            return this;
        }

        public ExitGameDialog show(Context context) {
            ExitGameDialog dialog = new ExitGameDialog(context);
            dialog.setResetMatchListener(resetMatchListener);
            dialog.setQuitGameListener(quitGameListener);
            dialog.setBackToMainMenuListener(backToMainMenuListener);
            dialog.show();
            return dialog;
        }
    }

}
