package com.example.gplx_b2.myInterface;

import java.util.ArrayList;

public interface IClickAnswerItemListener {
    void onClickCheckBox(ArrayList<Integer> selectCheck, int position);
    boolean onCheckedChange(String answer, boolean isChecked, int adapterPosition);
}
