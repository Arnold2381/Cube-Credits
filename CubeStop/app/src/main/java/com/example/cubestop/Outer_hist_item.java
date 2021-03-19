package com.example.cubestop;

import java.util.List;

public class Outer_hist_item {
    private String month;
    private List<Inner_hist_item> details;
    public Outer_hist_item(String ParentItemTitle,
                           List<Inner_hist_item> ChildItemList)
    {
        this.month = ParentItemTitle;
        this.details = ChildItemList;
    }
    public String gettext1()
    {
        return month;
    }
    public List<Inner_hist_item> getList()
    {
        return  details;
    }
}
