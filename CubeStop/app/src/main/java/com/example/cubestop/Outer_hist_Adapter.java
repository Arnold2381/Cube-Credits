package com.example.cubestop;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Outer_hist_Adapter extends RecyclerView.Adapter<Outer_hist_Adapter.ParentViewHolder> {
    private String text1;

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private List<Outer_hist_item> itemList;
    Outer_hist_Adapter(List<Outer_hist_item> itemList)
    {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ParentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.history_card, viewGroup, false);
        return new ParentViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ParentViewHolder parentViewHolder, int position)
    {

        Outer_hist_item parentItem = itemList.get(position);

        // For the created instance,
        // get the title and set it
        // as the text for the TextView

        // Create a layout manager
        // to assign a layout
        // to the RecyclerView.
        parentViewHolder.ParentItemTitle.setText(parentItem.gettext1());
        // Here we have assigned the layout
        // as LinearLayout with vertical orientation
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(
                parentViewHolder
                        .ChildRecyclerView
                        .getContext(),
                LinearLayoutManager.VERTICAL,
                false);

        // Since this is a nested layout, so
        // to define how many child items
        // should be prefetched when the
        // child RecyclerView is nested
        // inside the parent RecyclerView,
        // we use the following method
        layoutManager
                .setInitialPrefetchItemCount(
                        parentItem
                                .getList()
                                .size());

        // Create an instance of the child
        // item view adapter and set its
        // adapter, layout manager and RecyclerViewPool
        Inner_hist_Adapter childItemAdapter
                = new Inner_hist_Adapter(
                parentItem
                        .getList());
        parentViewHolder
                .ChildRecyclerView
                .setLayoutManager(layoutManager);
        parentViewHolder
                .ChildRecyclerView
                .setAdapter(childItemAdapter);
        parentViewHolder
                .ChildRecyclerView
                .setRecycledViewPool(viewPool);

    }

    // This method returns the number
    // of items we have added in the
    // ParentItemList i.e. the number
    // of instances we have created
    // of the ParentItemList
    @Override
    public int getItemCount()
    {

        return itemList.size();
    }

    // This class is to initialize
    // the Views present in
    // the parent RecyclerView
    class ParentViewHolder
            extends RecyclerView.ViewHolder {

        private TextView ParentItemTitle;
        private RecyclerView ChildRecyclerView;

        ParentViewHolder(final View itemView)
        {
            super(itemView);

            ParentItemTitle
                    = itemView
                    .findViewById(
                            R.id.month);
            ChildRecyclerView
                    = itemView
                    .findViewById(
                            R.id.items);

        }


        }
    }
